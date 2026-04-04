package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.BookingResponseDto;
import com.cg.busticketui.dto.response.CustomerResponseDto;
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

/**
 * CustomerService handles business logic related to customer operations,
 * including searching customers and retrieving their bookings.
 *
 * <p>
 * This service communicates with backend APIs using {@link RestTemplate}
 * and applies role-based authorization through HTTP headers.
 * </p>
 */
@Service
public class CustomerService {

    private final RestTemplate restTemplate;

    /**
     * Default constructor initializing RestTemplate.
     */
    public CustomerService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Base URL for backend API endpoints.
     */
    private final String BASE_URL = "http://localhost:8082/api";

    /**
     * Retrieves a list of customers based on name and address.
     *
     * <p>
     * Sends a GET request to the backend service with role-based headers.
     * Returns an empty list if no customers are found.
     * </p>
     *
     * @param name    the name of the customer
     * @param address the address of the customer
     * @param session the HTTP session containing user role information
     *
     * @return a list of {@link CustomerResponseDto} matching the criteria
     *
     * @throws RuntimeException if access is denied or an error occurs
     */
    public List<CustomerResponseDto> getCustomers(String name, String address, HttpSession session) {

        String url = BASE_URL + "/customers/" + name + "/" + address;

        HttpHeaders headers = new HttpHeaders();

        String role = (String) session.getAttribute("role");
        headers.set("role", role);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {

            ResponseEntity<CustomerResponseDto[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            CustomerResponseDto[].class
                    );
            return response.getBody() != null
                    ? Arrays.asList(response.getBody())
                    : List.of();

        } catch (HttpClientErrorException.Forbidden e) {
            throw new RuntimeException("Access Denied");

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Customer not found");

        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    /**
     * Retrieves booking details for a specific customer.
     *
     * <p>
     * Sends a GET request to the backend service with role-based headers.
     * If the user has the "CUSTOMER" role, access is restricted by including
     * the userId in the headers.
     * </p>
     *
     * @param customerId the ID of the customer
     * @param session    the HTTP session containing user role information
     *
     * @return a list of {@link BookingResponseDto} representing bookings
     *
     * @throws RuntimeException if access is denied or an error occurs
     */

    public List<BookingResponseDto> getBookings(Integer customerId, HttpSession session) {

        String url = BASE_URL + "/customers/" + customerId + "/bookings";

        HttpHeaders headers = new HttpHeaders();

        String role = (String) session.getAttribute("role");
        headers.set("role", role);

        if ("CUSTOMER".equals(role)) {
            headers.set("userId", customerId.toString());
        }

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<BookingResponseDto[]> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            BookingResponseDto[].class
                    );

            return response.getBody() != null
                    ? Arrays.asList(response.getBody())
                    : List.of();

        } catch (HttpClientErrorException.Forbidden e) {
            throw new RuntimeException("You cannot access this data");

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Bookings not found");

        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }
}