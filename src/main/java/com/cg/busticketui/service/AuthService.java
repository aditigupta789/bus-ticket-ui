package com.cg.busticketui.service;

import com.cg.busticketui.dto.LoginDto;
import com.cg.busticketui.dto.response.LoginResponseDto;
import com.cg.busticketui.exception.InvalidLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String BASE_URL = "http://localhost:8082/auth";

    public LoginResponseDto login(LoginDto login) {

        try {
            ResponseEntity<LoginResponseDto> response =
                    restTemplate.postForEntity(
                            BASE_URL + "/login",
                            login,
                            LoginResponseDto.class
                    );

            return response.getBody();

        } catch (Exception e) {
            throw new InvalidLoginException("Invalid username or password");
        }
    }
}