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

/**
 * TripUiService handles operations related to trip data,
 * including fetching available seats and searching trips.
 *
 * <p>
 * This service communicates with backend APIs using {@link RestTemplate}
 * and processes dynamic JSON responses using {@link ObjectMapper}.
 * </p>
 *
 * <p>
 * It is designed to handle flexible backend response structures
 * by extracting relevant data nodes dynamically.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TripUiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Base URL for backend services, injected from application properties.
     */
    @Value("${app.backend.base-url}")
    private String backendBaseUrl;

    /**
     * Retrieves available seat information for a given trip.
     *
     * <p>
     * Calls backend API and extracts relevant JSON data node,
     * converting it into a generic map structure.
     * </p>
     *
     * @param tripId the ID of the trip
     * @return a map containing seat details
     *
     * @throws TripNotFoundException if the trip does not exist
     * @throws IllegalStateException if backend response is invalid or unavailable
     */
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

    /**
     * Searches trips based on source and destination.
     *
     * <p>
     * Calls backend API and extracts trip list from flexible JSON structures.
     * Returns an empty list if no trips are found.
     * </p>
     *
     * @param source      the source location
     * @param destination the destination location
     * @return a list of trips represented as maps
     *
     * @throws IllegalStateException if backend response is invalid or unavailable
     */
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

    /**
     * Executes API call to fetch seat data and handles errors.
     */
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

    /**
     * Executes API call to search trips and handles errors.
     */
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

    /**
     * Parses JSON string into JsonNode.
     */
    private JsonNode readJson(String body) {
        try {
            return objectMapper.readTree(body);
        } catch (Exception ex) {
            throw new IllegalStateException("Backend did not return valid JSON.", ex);
        }
    }

    /**
     * Extracts common wrapper fields from JSON response.
     */
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

    /**
     * Extracts trip array from various possible JSON structures.
     */
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

