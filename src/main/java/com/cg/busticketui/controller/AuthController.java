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

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

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