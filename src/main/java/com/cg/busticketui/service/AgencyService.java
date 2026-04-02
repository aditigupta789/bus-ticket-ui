package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AgencyService {
    private final RestTemplate restTemplate;

    public AgencyService() {
        restTemplate = new RestTemplate();
    }

    private final String BASE_URL = "http://localhost:8082/api";

    public List<CustomerResponseDto> getCustomersByAgencyId(Integer agencyId) {

        String url = BASE_URL + "/agency/" + agencyId + "/customers";

        ResponseEntity<CustomerResponseDto[]> response =
                restTemplate.getForEntity(url, CustomerResponseDto[].class);

        return Arrays.asList(response.getBody());
    }
}