package com.cg.busticketui.controller;

import com.cg.busticketui.dto.LoginDto;
import com.cg.busticketui.dto.response.LoginResponseDto;
import com.cg.busticketui.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * AuthController handles authentication-related operations such as
 * rendering the login page and processing login requests.
 *
 * <p>
 * This controller interacts with the AuthService to validate user credentials
 * and manages session data for authenticated users.
 * </p>
 */
@Controller
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor-based dependency injection for AuthService.
     *
     * @param authService the service responsible for handling authentication logic
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Displays the login page.
     *
     * @return the login view ("login")
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Handles login form submission.
     *
     * <p>
     * Authenticates the user using provided credentials and stores
     * user-related information in the HTTP session.
     * Based on the user's role, redirects to the appropriate dashboard.
     * </p>
     *
     * @param login   the login request data (username, password, etc.)
     * @param session the HTTP session used to store authenticated user details
     *
     * @return a redirect URL based on the user's role, or login page on failure
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto login,
                        HttpSession session) {

        LoginResponseDto user = authService.login(login);

        session.setAttribute("user", user.getUsername());
        session.setAttribute("role", user.getRole());

        switch (user.getRole()) {
            case "ADMIN":
                return "redirect:/admin/home";
            case "AGENCY":
                return "redirect:/agency/home";
            case "CUSTOMER":
                return "redirect:/customer";
        }

        return "login";
    }
}