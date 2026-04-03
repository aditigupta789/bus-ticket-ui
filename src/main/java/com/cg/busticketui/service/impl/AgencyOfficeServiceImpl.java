package com.cg.busticketui.service.impl;

import com.cg.busticketui.client.BackendClient;
import com.cg.busticketui.dto.response.OfficeBusResponseDto;
import com.cg.busticketui.dto.response.OfficeDriverResponseDto;
import com.cg.busticketui.service.AgencyOfficeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.cg.busticketui.constants.ApiEndpoints.*;

@Service
@RequiredArgsConstructor
public class AgencyOfficeServiceImpl implements AgencyOfficeService {

    private final BackendClient backendClient;

    @Override
    public List<OfficeBusResponseDto> getBuses(Integer officeId, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();

        String role = (String) session.getAttribute("role");
        headers.set("role", role);

        if ("AGENCY".equals(role)) {
            headers.set("officeId", officeId.toString());
        }
        return backendClient.get(
                GET_BUSES_BY_OFFICE,
                Map.of("officeId", officeId),
                headers,
                new ParameterizedTypeReference<List<OfficeBusResponseDto>>() {}
        );
    }

    @Override
    public List<OfficeDriverResponseDto> getDrivers(Integer officeId, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();

        String role = (String) session.getAttribute("role");
        headers.set("role", role);

        // 🔥 AGENCY restriction
        if ("AGENCY".equals(role)) {
            headers.set("officeId", officeId.toString());
        }

        return backendClient.get(
                GET_DRIVERS_BY_OFFICE,
                Map.of("officeId", officeId),
                headers,   // ✅ PASS HEADERS
                new ParameterizedTypeReference<List<OfficeDriverResponseDto>>() {}
        );
    }
}