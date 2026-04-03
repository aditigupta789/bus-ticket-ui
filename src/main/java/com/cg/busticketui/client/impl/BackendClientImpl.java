package com.cg.busticketui.client.impl;

import com.cg.busticketui.client.BackendClient;
import com.cg.busticketui.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BackendClientImpl implements BackendClient {

    private static final Logger log = LoggerFactory.getLogger(BackendClientImpl.class);

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String backendBaseUrl;

    @Override
    public <T> T get(String path,
                     Map<String, ?> queryParams,
                     ParameterizedTypeReference<T> responseType) {

        URI uri = buildUri(path, queryParams);

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    responseType
            );

            // Null safety
            if (response.getBody() == null) {
                log.warn("Empty response received from backend for URI: {}", uri);
                throw new CustomException("No data received from backend.");
            }

            return response.getBody();

        } catch (HttpStatusCodeException ex) {

            String responseBody = ex.getResponseBodyAsString();

            log.error("Backend API error: status={}, body={}",
                    ex.getStatusCode(),
                    responseBody);

            // Preserve backend message if available
            String message = (responseBody != null && !responseBody.isBlank())
                    ? responseBody
                    : "Backend error: " + ex.getStatusCode();

            throw new CustomException(message);

        } catch (Exception ex) {
            log.error("Unexpected error while calling backend API: {}", uri, ex);
            throw new CustomException("Unexpected error while calling backend service.");
        }
    }

    // -------------------- HELPER METHOD --------------------
    private URI buildUri(String path, Map<String, ?> queryParams) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(backendBaseUrl)
                .path(path);

        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(builder::queryParam);
        }

        return builder.build(true).toUri();
    }
}