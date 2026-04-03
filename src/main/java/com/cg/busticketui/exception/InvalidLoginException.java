package com.cg.busticketui.exception;

/**
 * InvalidLoginException is thrown when user authentication fails
 * due to invalid credentials such as incorrect username or password.
 *
 * <p>
 * This exception is typically used in the authentication service layer
 * and is handled globally by {@link GlobalExceptionHandler} to display
 * appropriate error messages on the login page.
 * </p>
 */
public class InvalidLoginException extends RuntimeException {

    /**
     * Constructs a new InvalidLoginException with the specified error message.
     *
     * @param message the detail message describing the reason for login failure
     */
    public InvalidLoginException(String message) {
        super(message);
    }
}