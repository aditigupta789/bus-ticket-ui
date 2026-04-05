package com.cg.busticketui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for transferring Address details to UI layer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    private Integer addressId;

    private String address;

    private String city;

    private String state;

    private String zipCode;
}