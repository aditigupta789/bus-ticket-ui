package com.cg.busticketui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OfficeDriverResponseDto represents the response data for drivers
 * associated with a specific office.
 *
 * <p>
 * This DTO is used to transfer driver-related information from the backend
 * to the frontend layer, typically when fetching drivers for an agency office.
 * </p>
 *
 * <p>
 * It contains essential driver details such as driver ID and name,
 * ensuring a lightweight and efficient response payload.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDriverResponseDto {

    /**
     * Unique identifier for the driver.
     */
    private Integer driverId;

    /**
     * Name of the driver.
     */
    private String name;
}

