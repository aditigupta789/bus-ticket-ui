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

/**
 * AgencyOfficeServiceImpl is the implementation of {@link AgencyOfficeService}
 * responsible for handling business logic related to agency office operations.
 *
 * <p>
 * This class interacts with the BackendClient to fetch data from backend APIs
 * and applies role-based logic before making API calls.
 * </p>
 *
 * <p>
 * It uses HTTP headers to pass user role and office-specific restrictions
 * for authorization purposes.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AgencyOfficeServiceImpl implements AgencyOfficeService {

    private final BackendClient backendClient;

    /**
     * Retrieves the list of buses associated with a given office.
     *
     * <p>
     * Adds role-based headers and restricts access for AGENCY users
     * by including officeId in headers.
     * </p>
     *
     * @param officeId the ID of the office
     * @param session  the HTTP session containing user role information
     *
     * @return a list of {@link OfficeBusResponseDto} representing buses
     */
    @Override
    public List<OfficeBusResponseDto> getBuses(Integer officeId, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();

        String role = (String) session.getAttribute("role");
        headers.set("role", role);

        //agency restriction
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

    /**
     * Retrieves the list of drivers associated with a given office.
     *
     * <p>
     * Adds role-based headers and restricts access for AGENCY users
     * by including officeId in headers.
     * </p>
     *
     * @param officeId the ID of the office
     * @param session  the HTTP session containing user role information
     *
     * @return a list of {@link OfficeDriverResponseDto} representing drivers
     */
    @Override
    public List<OfficeDriverResponseDto> getDrivers(Integer officeId, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();

        String role = (String) session.getAttribute("role");
        headers.set("role", role);

        // AGENCY restriction
        if ("AGENCY".equals(role)) {
            headers.set("officeId", officeId.toString());
        }

        return backendClient.get(
                GET_DRIVERS_BY_OFFICE,
                Map.of("officeId", officeId),
                headers,   // PASS HEADERS
                new ParameterizedTypeReference<List<OfficeDriverResponseDto>>() {}
        );
    }
}