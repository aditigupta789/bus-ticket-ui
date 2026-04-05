package com.cg.busticketui.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for transferring Agency details to UI layer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyResponseDto {

    private Integer agencyId;

    private String name;

    private String contactPersonName;

    private String email;

    private String phone;

    private List<AgencyOfficeResponseDto> offices;
}