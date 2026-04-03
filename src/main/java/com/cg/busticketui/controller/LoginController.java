package com.cg.busticketui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            HttpSession session,
            Model model
    ) {
        // No user store exists in this UI-only project; this keeps the navigation flow working.
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "Username and password are required.");
            return "login";
        }

        session.setAttribute("loggedIn", true);
        session.setAttribute("username", username);
        return "redirect:/trip/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
