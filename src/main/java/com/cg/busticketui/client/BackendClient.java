package com.cg.busticketui.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * BackendClient is a generic interface used to communicate with backend services
 * via HTTP GET requests.
 *
 * <p>
 * This interface abstracts the logic required to call backend APIs and retrieve
 * responses in a type-safe manner using {@link ParameterizedTypeReference}.
 * </p>
 *
 * <p>
 * It is typically implemented using tools like RestTemplate or WebClient.
 * </p>
 */
public interface BackendClient {

    /**
     * Executes an HTTP GET request to the specified backend endpoint.
     *
     * @param path          the API endpoint path (e.g., "/api/buses")
     * @param queryParams   a map of query parameters to be appended to the URL
     *                      (can be null if no query parameters are required)
     * @param headers       HTTP headers to be included in the request
     *                      (e.g., authorization headers, content type, etc.)
     * @param responseType  the expected response type wrapped in
     *                      {@link ParameterizedTypeReference} to support
     *                      generic types like List, Map, etc.
     * @param <T>           the type of the response body
     *
     * @return the response body converted into the specified type
     *
     * @throws RuntimeException if the backend call fails or response cannot be parsed
     */
    <T> T get(String path,
              Map<String, ?> queryParams,
              HttpHeaders headers,
              ParameterizedTypeReference<T> responseType);
}