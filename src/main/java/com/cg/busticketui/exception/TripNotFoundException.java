package com.cg.busticketui.exception;

/**
 * TripNotFoundException is thrown when a trip with the specified ID
 * does not exist in the system.
 *
 * <p>
 * This exception is typically used in the service or client layer
 * when attempting to retrieve trip details or seat availability
 * for a non-existent trip.
 * </p>
 *
 * <p>
 * It is handled by the global exception handler to display a
 * meaningful error message to the user.
 * </p>
 */
public class TripNotFoundException extends RuntimeException {

    /**
     * Constructs a new TripNotFoundException for the given trip ID.
     *
     * @param tripId the ID of the trip that was not found
     */
    public TripNotFoundException(Integer tripId) {
        super("No trip with this id exists.");
    }
}
