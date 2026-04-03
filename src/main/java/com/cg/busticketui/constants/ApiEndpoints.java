package com.cg.busticketui.constants;

public final class ApiEndpoints {

    // Prevent instantiation
    private ApiEndpoints() {
        throw new IllegalStateException("Utility class");
    }

    // -------------------- OFFICE APIs --------------------

    public static final String GET_BUSES_BY_OFFICE = "/api/offices/buses";

    public static final String GET_DRIVERS_BY_OFFICE = "/api/offices/drivers";

}