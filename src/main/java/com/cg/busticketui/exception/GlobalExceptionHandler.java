package com.cg.busticketui.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler provides centralized exception handling
 * across all controllers in the application.
 *
 * <p>
 * Annotated with {@link ControllerAdvice}, this class intercepts
 * exceptions thrown by controllers and maps them to appropriate
 * views with meaningful error messages.
 * </p>
 *
 * <p>
 * It ensures consistent error handling, better user experience,
 * and proper logging for debugging and monitoring.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles InvalidLoginException thrown during authentication failures.
     *
     * @param ex    the exception containing error details
     * @param model the model used to pass error messages to the view
     * @return the login view with error message
     */
    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLogin(InvalidLoginException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login";
    }

    /**
     * Handles ResourceNotFoundException for missing entities
     * such as agencies, customers, etc.
     *
     * @param ex    the exception containing error details
     * @param model the model used to pass error messages to the view
     * @return the customers view with error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleAgencyNotFound(ResourceNotFoundException ex, Model model) {

        model.addAttribute("error", ex.getMessage());
        return "customers";
    }

    /**
     * Handles application-specific CustomException.
     *
     * <p>
     * Logs the error and returns a generic error page.
     * </p>
     *
     * @param ex    the exception containing error details
     * @param model the model used to pass error messages to the view
     * @return the error view
     */
    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException ex, Model model) {
        log.error("Custom exception occurred: {}", ex.getMessage());

        model.addAttribute("error", ex.getMessage());
        return "error"; // create error.html
    }

    /**
     * Handles generic RuntimeException.
     *
     * <p>
     * Logs the exception with stack trace and returns a generic error page.
     * </p>
     *
     * @param ex    the runtime exception
     * @param model the model used to pass error messages to the view
     * @return the error view
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleGeneric(RuntimeException ex, Model model) {

        log.error("Runtime exception occurred", ex);

        model.addAttribute("error", ex.getMessage());
        return "error";//"customers"
    }

    /**
     * Handles all uncaught exceptions as a fallback mechanism.
     *
     * <p>
     * Ensures that no exception is left unhandled and provides
     * a user-friendly error message.
     * </p>
     *
     * @param ex    the exception
     * @param model the model used to pass error messages to the view
     * @return the error view
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("Unexpected exception occurred", ex);

        model.addAttribute("error", "Unexpected error occurred.");
        return "error";
    }
}