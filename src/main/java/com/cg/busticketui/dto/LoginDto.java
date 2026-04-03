package com.cg.busticketui.dto;

import lombok.Data;

/**
 * LoginDto represents the request data required for user authentication.
 *
 * <p>
 * This DTO is used to capture login credentials entered by the user
 * (typically from a login form) and transfer them to the authentication
 * service for validation.
 * </p>
 *
 * <p>
 * It acts as an input model between the frontend layer and the backend
 * authentication logic.
 * </p>
 */
@Data
public class LoginDto {

    /**
     * Username of the user attempting to log in.
     */
    private String username;

    /**
     * Password of the user attempting to log in.
     *
     * <p>
     * This field is sensitive and should be handled securely.
     * It must not be logged or exposed in responses.
     * </p>
     */
    private String password;
}
