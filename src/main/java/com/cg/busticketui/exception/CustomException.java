package com.cg.busticketui.exception;

/**
 * CustomException is a generic runtime exception used across the application
 * to represent business or application-specific errors.
 *
 * <p>
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * without explicit declaration in method signatures.
 * </p>
 *
 * <p>
 * It is typically used in the service or client layers to indicate
 * failures such as invalid operations, backend errors, or unexpected states.
 * </p>
 */
public class CustomException extends RuntimeException {

    /**
     * Constructs a new CustomException with the specified error message.
     *
     * @param message the detail message describing the exception
     */
    public CustomException(String message) {
        super(message);
    }

}