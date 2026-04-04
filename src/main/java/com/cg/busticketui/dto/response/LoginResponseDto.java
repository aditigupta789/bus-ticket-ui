package com.cg.busticketui.dto.response;

import lombok.Data;

/**
 * LoginResponseDto represents the response returned after a successful
 * authentication request.
 *
 * <p>
 * This DTO contains essential user details required by the frontend
 * for session management and role-based navigation.
 * </p>
 *
 * <p>
 * It is typically used by the AuthController after validating user
 * credentials through the AuthService.
 * </p>
 */
@Data
public class LoginResponseDto {

    /**
     * Username of the authenticated user.
     */
    private String username;

    /**
     * Role assigned to the authenticated user.
     *
     * <p>
     * Determines access level and redirection logic in the application
     * (e.g., ADMIN, AGENCY, CUSTOMER).
     * </p>
     */

    private String role;
}
