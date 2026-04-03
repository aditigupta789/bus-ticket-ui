package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.OfficeBusResponseDto;
import com.cg.busticketui.dto.response.OfficeDriverResponseDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface AgencyOfficeService {

    List<OfficeBusResponseDto> getBuses(Integer officeId, HttpSession session);

    List<OfficeDriverResponseDto> getDrivers(Integer officeId, HttpSession session);
}