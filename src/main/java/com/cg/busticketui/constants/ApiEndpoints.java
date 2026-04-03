package com.cg.busticketui.constants;

/**
 * ApiEndpoints is a utility class that contains constant definitions
 * for backend API endpoint paths used across the application.
 *
 * <p>
 * Centralizing API endpoints helps in maintaining consistency,
 * avoiding hardcoded strings, and simplifying future updates.
 * </p>
 *
 * <p>
 * This class is not meant to be instantiated.
 * </p>
 */
public final class ApiEndpoints {

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws IllegalStateException always thrown to indicate misuse
     */
    private ApiEndpoints() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Endpoint to fetch all buses associated with a specific office.
     *
     * <p>
     * Typically used by passing office-related query parameters
     * or identifiers to retrieve the list of buses.
     * </p>
     */
    public static final String GET_BUSES_BY_OFFICE = "/api/offices/buses";

    /**
     * Endpoint to fetch all drivers associated with a specific office.
     *
     * <p>
     * Typically used by passing office-related query parameters
     * or identifiers to retrieve the list of drivers.
     * </p>
     */
    public static final String GET_DRIVERS_BY_OFFICE = "/api/offices/drivers";

}