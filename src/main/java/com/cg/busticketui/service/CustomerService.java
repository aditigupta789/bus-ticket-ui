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

@Service
public class CustomerService {

    private final RestTemplate restTemplate;

    public CustomerService() {
        this.restTemplate = new RestTemplate();
    }

    private final String BASE_URL = "http://localhost:8082/api";

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