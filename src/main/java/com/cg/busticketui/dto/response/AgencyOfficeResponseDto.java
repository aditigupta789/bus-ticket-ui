package com.cg.busticketui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for representing Agency Office details in responses.
 *
 * This DTO is used to transfer agency office-related data from the backend
 * to the client without exposing the internal entity structure.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyOfficeResponseDto {
    /**
     * Unique identifier of the office.
     */
    private Integer officeId;

    /**
     * Email address of the office.
     */
    private String officeMail;

    /**
     * Name of the contact person at the office.
     */
    private String officeContactPersonName;

    /**
     * Contact number of the office.
     */
    private String officeContactNumber;

    /**
     * Associated agency details.
     */
    private AgencyResponseDto agency;

    /**
     * Address details of the office.
     */
    private AddressResponseDto address;
}
