package com.cg.busticketui.service;
import com.cg.busticketui.dto.response.RouteResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import java.util.List;

/**
 * Service class responsible for communicating with the backend Route APIs.
 * It uses RestTemplate to fetch route data and convert it into DTOs
 * for use in the UI layer.
 */
@Service
public class RouteUIService {
    /**
     * RestTemplate used to make HTTP calls to backend services.
     */
    private final RestTemplate restTemplate;
    /**
     * Base URL of the backend Route API.
     */
    private final String BASE_URL = "http://localhost:8082/api/route";

    /**
     * Constructor-based dependency injection for RestTemplate.
     */
    public RouteUIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches routes between source and destination cities
     * by calling the backend API.
     */
    public List<RouteResponseDto> getRoutes(String source, String destination) {
        try {
            String url = BASE_URL + "/search?source=" + source + "&destination=" + destination;
            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RouteResponseDto>>() {}).getBody();
        } catch (Exception e) {
            throw new RuntimeException("No routes found");
        }
    }

    /**
     * Fetches most popular routes from backend API.
     */
    public List<RouteResponseDto> getPopularRoutes() {
        String url = BASE_URL + "/popular";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RouteResponseDto>>() {}
        ).getBody();
    }
}
