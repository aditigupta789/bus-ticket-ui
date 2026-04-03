package com.cg.busticketui.client.impl;

import com.cg.busticketui.client.BackendClient;
import com.cg.busticketui.exception.CustomException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * HTTP client for communicating with the backend REST API.
 *
 * <p>All backend error responses are intercepted here. If the backend returns a
 * structured JSON error body (containing a {@code message} field), only that
 * human-readable message is surfaced to the UI layer via {@link CustomException}.
 * Raw JSON is never exposed to the end user.
 */
@Component
@RequiredArgsConstructor
public class BackendClientImpl implements BackendClient {

    private static final Logger log = LoggerFactory.getLogger(BackendClientImpl.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.backend.base-url}")
    private String backendBaseUrl;

    // -------------------- GET --------------------

    @Override
    public <T> T get(String path,
                     Map<String, ?> queryParams,
                     HttpHeaders headers,
                     ParameterizedTypeReference<T> responseType) {

        URI uri = buildUri(path, queryParams);
        log.debug("Calling backend GET: {}", uri);

        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<T> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    responseType
            );

            if (response.getBody() == null) {
                log.warn("Empty response body received from backend for URI: {}", uri);
                throw new CustomException("No data received from the server. Please try again.");
            }

            return response.getBody();

        } catch (HttpStatusCodeException ex) {

            String rawBody = ex.getResponseBodyAsString();
            log.error("Backend API error — status: {}, body: {}", ex.getStatusCode(), rawBody);

            String userMessage = extractMessageFromJson(rawBody);
            throw new CustomException(userMessage);

        } catch (CustomException ex) {
            throw ex;

        } catch (Exception ex) {
            log.error("Unexpected error while calling backend API: {}", uri, ex);
            throw new CustomException("Unable to reach the server. Please try again later.");
        }
    }

    // -------------------- HELPERS --------------------

    /**
     * Builds the target URI from the base URL, path, and optional query parameters.
     */
    private URI buildUri(String path, Map<String, ?> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(backendBaseUrl)
                .path(path);

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }

        return builder.build(true).toUri();
    }

    /**
     * Attempts to parse a JSON error body and return the value of its {@code message} field.
     *
     * <p>If parsing fails or the field is absent, a generic fallback message is returned
     * so the UI always receives a clean, readable string.
     *
     * <p>Example backend body handled:
     * <pre>
     * {"path":"/api/offices/drivers","status":404,"error":{},"message":"Agency Office not found with id: 176","timestamp":"..."}
     * </pre>
     *
     * @param rawJson the raw response body string from the backend
     * @return a human-readable error message
     */
    private String extractMessageFromJson(String rawJson) {
        if (rawJson == null || rawJson.isBlank()) {
            return "An unexpected error occurred. Please try again.";
        }
        try {
            JsonNode root = objectMapper.readTree(rawJson);
            JsonNode messageNode = root.get("message");
            if (messageNode != null && !messageNode.isNull() && !messageNode.asText().isBlank()) {
                return messageNode.asText();
            }
        } catch (Exception parseEx) {
            log.warn("Could not parse backend error body as JSON: {}", rawJson);
        }
        // Fallback: don't show raw body, show a safe generic message
        return "An unexpected error occurred. Please try again.";
    }
}