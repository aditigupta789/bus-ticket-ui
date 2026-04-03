package com.cg.busticketui.service;

import com.cg.busticketui.dto.LoginDto;
import com.cg.busticketui.dto.response.LoginResponseDto;
import com.cg.busticketui.exception.InvalidLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AuthService handles authentication-related operations such as
 * validating user credentials and retrieving authentication details.
 *
 * <p>
 * This service communicates with the backend authentication API
 * using {@link RestTemplate} to perform login operations.
 * </p>
 */
@Service
public class AuthService {

    private final RestTemplate restTemplate;

    /**
     * Constructor-based dependency injection for RestTemplate.
     *
     * @param restTemplate the RestTemplate used to make HTTP requests
     */
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Base URL for authentication endpoints.
     */
    private final String BASE_URL = "http://localhost:8082/auth";

    /**
     * Authenticates a user using the provided login credentials.
     *
     * <p>
     * Sends a POST request to the backend authentication API and
     * retrieves user details upon successful authentication.
     * </p>
     *
     * <p>
     * If authentication fails, an {@link InvalidLoginException}
     * is thrown with a generic error message.
     * </p>
     *
     * @param login the login request containing username and password
     *
     * @return {@link LoginResponseDto} containing authenticated user details
     *
     * @throws InvalidLoginException if login fails due to invalid credentials
     */
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