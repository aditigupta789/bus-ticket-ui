package com.cg.busticketui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OfficeBusResponseDto represents the response data for buses
 * associated with a specific office.
 *
 * <p>
 * This DTO is used to transfer bus-related information from the backend
 * to the frontend layer, typically when fetching buses for an agency office.
 * </p>
 *
 * <p>
 * It contains minimal bus details such as bus ID and type,
 * ensuring lightweight and efficient data transfer.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeBusResponseDto {

    /**
     * Unique identifier for the bus.
     */
    private Integer busId;

    /**
     * Type of the bus (e.g., AC, NON_AC, SLEEPER).
     */
    private String type;
}

