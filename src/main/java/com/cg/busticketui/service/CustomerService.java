package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.BookingResponseDto;
import com.cg.busticketui.dto.response.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public List<CustomerResponseDto> getCustomers(String name, String address) {

        try {
            String url = BASE_URL + "/customers/" + name + "/" + address;

            System.out.println("URL: " + url);

            ResponseEntity<CustomerResponseDto[]> response =
                    restTemplate.getForEntity(url, CustomerResponseDto[].class);

            if (response.getBody() == null) {
                return List.of();
            }

            return Arrays.asList(response.getBody());

        } catch (Exception e) {
            // 🔥 THIS IS THE MOST IMPORTANT LINE
            System.out.println("ERROR OCCURRED: " + e.getMessage());
            return List.of();   // NEVER CRASH UI
        }
    }

    public List<BookingResponseDto> getBookings(Integer customerId) {

        try {
            String url = BASE_URL + "/customers/" + customerId + "/bookings";

            ResponseEntity<BookingResponseDto[]> response =
                    restTemplate.getForEntity(url, BookingResponseDto[].class);

            return response.getBody() != null
                    ? Arrays.asList(response.getBody())
                    : List.of();

        } catch (Exception e) {
            return List.of();
        }
    }
}