package com.cg.busticketui.dto.response;

import lombok.*;

/**
 * Data Transfer Object (DTO) for representing customer details in responses.
 *
 * This DTO is used to transfer basic customer information from the backend
 * to the client without exposing the internal entity structure.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    /**
     * Unique identifier of the customer.
     */
    private Integer customerId;

    /**
     * Full name of the customer.
     */
    private String name;

    /**
     * Email address of the customer.
     */
    private String email;

    /**
     * Contact phone number of the customer.
     */
    private String phone;

    /**
     * City where the customer resides.
     */
    private String city;
}

