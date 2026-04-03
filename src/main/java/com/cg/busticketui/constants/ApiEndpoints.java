package com.cg.busticketui.constants;

public final class ApiEndpoints {

    private ApiEndpoints() {
        throw new IllegalStateException("Utility class");
    }

    public static final String GET_BUSES_BY_OFFICE = "/api/offices/buses";

    public static final String GET_DRIVERS_BY_OFFICE = "/api/offices/drivers";

}