package com.cg.busticketui.client;

import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;

public interface BackendClient {

    <T> T get(String path,
              Map<String, ?> queryParams,
              ParameterizedTypeReference<T> responseType);
}