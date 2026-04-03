package com.cg.busticketui.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

import java.util.Map;

public interface BackendClient {

    <T> T get(String path,
              Map<String, ?> queryParams,
              HttpHeaders headers,
              ParameterizedTypeReference<T> responseType);
}