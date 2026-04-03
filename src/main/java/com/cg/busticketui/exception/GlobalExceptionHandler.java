package com.cg.busticketui.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // -------------------- HANDLE CUSTOM EXCEPTION --------------------
    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException ex, Model model) {
        log.error("Custom exception occurred: {}", ex.getMessage());

        model.addAttribute("error", ex.getMessage());
        return "error"; // create error.html
    }

    // -------------------- HANDLE RUNTIME EXCEPTION --------------------
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        log.error("Runtime exception occurred", ex);

        model.addAttribute("error", "Something went wrong. Please try again.");
        return "error";
    }

    // -------------------- HANDLE GENERIC EXCEPTION --------------------
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("Unexpected exception occurred", ex);

        model.addAttribute("error", "Unexpected error occurred.");
        return "error";
    }
}