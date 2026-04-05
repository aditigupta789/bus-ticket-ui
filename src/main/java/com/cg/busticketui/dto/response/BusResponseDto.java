package com.cg.busticketui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing bus details in responses.
 *
 * This DTO is used to transfer bus-related information from the backend
 * to the client, including basic bus details and its associated office.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusResponseDto {

    /**
     * Unique identifier of the bus.
     */
    private Integer busId;

    /**
     * Registration number of the bus.
     */
    private String registrationNumber;

    /**
     * Seating capacity of the bus.
     */
    private Integer capacity;

    /**
     * Type of the bus (e.g., AC, Non-AC, Sleeper).
     */
    private String type;

    /**
     * Associated agency office details where the bus is registered.
     */
    private AgencyOfficeResponseDto office;
}
