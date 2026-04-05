package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.AgencyOfficeResponseDto;
import com.cg.busticketui.dto.response.AgencyRevenueDto;
import com.cg.busticketui.dto.response.BusResponseDto;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * AgencyService handles business logic related to agency operations,
 * particularly fetching customers associated with a specific agency.
 *
 * <p>
 * This service communicates with a backend API using {@link RestTemplate}
 * and applies role-based authorization via HTTP headers.
 * </p>
 */
@Service
public class AgencyService {
    private final RestTemplate restTemplate;

    /**
     * Default constructor initializing RestTemplate.
     */
    public AgencyService() {
        restTemplate = new RestTemplate();
    }

    /**
     * Base URL for backend API endpoints.
     */
    private final String BASE_URL = "http://localhost:8082/api";

    /**
     * Retrieves a list of customers associated with a given agency ID.
     *
     * <p>
     * Sends a GET request to the backend service with role-based headers.
     * If the user has the "AGENCY" role, the agencyId is also included
     * in the headers for access restriction.
     * </p>
     *
     * <p>
     * Handles various HTTP exceptions and converts them into
     * application-specific exceptions.
     * </p>
     *
     * @param agencyId the ID of the agency
     * @param session  the HTTP session containing user role information
     *
     * @return a list of {@link CustomerResponseDto} representing customers
     *
     * @throws ResourceNotFoundException if the agency does not exist
     * @throws RuntimeException          if access is denied or any other error occurs
     */
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

    public List<AgencyOfficeResponseDto> getOfficesByAgencyId(Integer agencyId, HttpSession session) {

        String url = BASE_URL + "/agency/" + agencyId + "/offices";

        HttpHeaders headers = new HttpHeaders();
        headers.set("role", (String) session.getAttribute("role"));

        if ("AGENCY".equals(session.getAttribute("role"))) {
            headers.set("agencyId", agencyId.toString());
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<AgencyOfficeResponseDto[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            AgencyOfficeResponseDto[].class
                    );

            return Arrays.asList(response.getBody());

        } catch (Exception e) {
            throw new RuntimeException("Error fetching offices");
        }
    }

    public List<BusResponseDto> getBusesByAgencyAndDate(Integer agencyId, LocalDateTime tripDate, HttpSession session) {

        String url = BASE_URL + "/agency/" + agencyId + "/buses?tripDate=" + tripDate;

        HttpHeaders headers = new HttpHeaders();
        headers.set("role", (String) session.getAttribute("role"));

        if ("AGENCY".equals(session.getAttribute("role"))) {
            headers.set("agencyId", agencyId.toString());
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<BusResponseDto[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            BusResponseDto[].class
                    );

            return Arrays.asList(response.getBody());

        } catch (Exception e) {
            throw new RuntimeException("Error fetching buses");
        }
    }

    public AgencyRevenueDto getRevenueByAgencyId(Integer agencyId, HttpSession session) {

        String url = BASE_URL + "/agency/" + agencyId + "/revenue";

        HttpHeaders headers = new HttpHeaders();
        headers.set("role", (String) session.getAttribute("role"));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<AgencyRevenueDto> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            AgencyRevenueDto.class
                    );

            return response.getBody();

        } catch (HttpClientErrorException.Forbidden e) {
            throw new RuntimeException("Only ADMIN can access revenue");

        } catch (Exception e) {
            throw new RuntimeException("Error fetching revenue");
        }
    }
}