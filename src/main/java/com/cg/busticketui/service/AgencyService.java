package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.CustomerResponseDto;
import com.cg.busticketui.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

    public List<CustomerResponseDto> getCustomersByAgencyId(Integer agencyId, HttpSession session) {

        String url = BASE_URL + "/agency/" + agencyId + "/customers";
        HttpHeaders headers = new HttpHeaders();

        headers.set("role", (String) session.getAttribute("role"));
        if ("AGENCY".equals(session.getAttribute("role"))) {
            headers.set("agencyId", agencyId.toString());
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<CustomerResponseDto[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            CustomerResponseDto[].class
                    );

            return Arrays.asList(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Agency not found");

        } catch (HttpClientErrorException.Forbidden e) {
            throw new RuntimeException("Access Denied");

        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }
}