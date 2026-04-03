package com.cg.busticketui.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLogin(InvalidLoginException ex, Model model) {

        model.addAttribute("error", ex.getMessage());
        return "login";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleAgencyNotFound(ResourceNotFoundException ex, Model model) {

        model.addAttribute("error", ex.getMessage());
        return "customers";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleGeneric(RuntimeException ex, Model model) {

        model.addAttribute("error", ex.getMessage());
        return "customers";
    }
}