package com.cg.busticketui.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException ex, Model model) {
        log.error("Custom exception occurred: {}", ex.getMessage());

        model.addAttribute("error", ex.getMessage());
        return "error"; // create error.html
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleGeneric(RuntimeException ex, Model model) {

        log.error("Runtime exception occurred", ex);

        model.addAttribute("error", ex.getMessage());
        return "error";//"customers"
    }

    // -------------------- HANDLE GENERIC EXCEPTION --------------------
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("Unexpected exception occurred", ex);

        model.addAttribute("error", "Unexpected error occurred.");
        return "error";
    }
}