package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.OfficeBusResponseDto;
import com.cg.busticketui.dto.response.OfficeDriverResponseDto;

import java.util.List;

public interface AgencyOfficeService {

    List<OfficeBusResponseDto> getBuses(Integer officeId);

    List<OfficeDriverResponseDto> getDrivers(Integer officeId);
}