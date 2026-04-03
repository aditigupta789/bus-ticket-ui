package com.cg.busticketui.service;

import com.cg.busticketui.exception.TripNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripUiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.backend.base-url}")
    private String backendBaseUrl;

    public Map<String, Object> getAvailableSeats(Integer tripId) {
        String url = UriComponentsBuilder.fromHttpUrl(backendBaseUrl)
                .path("/trips/seats")
                .queryParam("tripId", tripId)
                .toUriString();

        String body = getBodyForSeats(url, tripId);
        JsonNode root = readJson(body);

        JsonNode data = extractDataNode(root);
        if (data == null) {
            data = root;
        }
        return objectMapper.convertValue(data, new TypeReference<Map<String, Object>>() {});
    }

    public List<Map<String, Object>> searchTrips(String source, String destination) {
        String url = UriComponentsBuilder.fromHttpUrl(backendBaseUrl)
                .path("/trips/search")
                .queryParam("source", source)
                .queryParam("destination", destination)
                .toUriString();

        String body = getBodyForSearch(url);
        JsonNode root = readJson(body);

        JsonNode tripsArray = extractTripsArrayNode(root);
        if (tripsArray == null) {
            // Fallback: try treating root itself as an array.
            if (root != null && root.isArray()) {
                tripsArray = root;
            } else {
                return List.of();
            }
        }

        return objectMapper.convertValue(tripsArray, new TypeReference<List<Map<String, Object>>>() {});
    }

    private String getBodyForSeats(String url, Integer tripId) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == 404) {
                throw new TripNotFoundException(tripId);
            }
            throw new IllegalStateException("Unable to load seat information. Please try again.", ex);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Unable to reach the server. Please try again.", ex);
        }
    }

    private String getBodyForSearch(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().value() == 404) {
                return "[]";
            }
            throw new IllegalStateException("Unable to search trips. Please try again.", ex);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Unable to reach the server. Please try again.", ex);
        }
    }

    private JsonNode readJson(String body) {
        try {
            return objectMapper.readTree(body);
        } catch (Exception ex) {
            throw new IllegalStateException("Backend did not return valid JSON.", ex);
        }
    }

    private JsonNode extractDataNode(JsonNode root) {
        if (root == null) {
            return null;
        }
        if (root.hasNonNull("object")) {
            return root.get("object");
        }
        if (root.hasNonNull("data")) {
            return root.get("data");
        }
        if (root.hasNonNull("result")) {
            return root.get("result");
        }
        if (root.hasNonNull("response")) {
            return root.get("response");
        }
        if (root.hasNonNull("payload")) {
            return root.get("payload");
        }
        return null;
    }

    private JsonNode extractTripsArrayNode(JsonNode root) {
        if (root == null) {
            return null;
        }

        JsonNode data = extractDataNode(root);
        if (data == null) {
            data = root;
        }

        if (data.isArray()) {
            return data;
        }

        if (data.isObject()) {
            // Common wrapper shapes used in APIs.
            if (data.hasNonNull("trips")) {
                return data.get("trips");
            }
            if (data.hasNonNull("content")) {
                return data.get("content");
            }
            if (data.hasNonNull("items")) {
                return data.get("items");
            }
            if (data.hasNonNull("data") && data.get("data").isArray()) {
                return data.get("data");
            }
        }

        return null;
    }
}

