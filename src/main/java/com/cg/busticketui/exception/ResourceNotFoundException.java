package com.cg.busticketui.exception;

/**
 * ResourceNotFoundException is thrown when a requested resource
 * cannot be found in the system.
 *
 * <p>
 * This exception is typically used in the service or client layer
 * when entities such as customers, agencies, trips, or bookings
 * are not available for the given input.
 * </p>
 *
 * <p>
 * It is handled globally by {@link GlobalExceptionHandler} to provide
 * meaningful error messages to the user interface.
 * </p>
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified error message.
     *
     * @param message the detail message describing which resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
