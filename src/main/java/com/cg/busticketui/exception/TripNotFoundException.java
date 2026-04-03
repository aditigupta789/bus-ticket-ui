package com.cg.busticketui.exception;

public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException(Integer tripId) {
        super("No trip with this id exists.");
    }
}
