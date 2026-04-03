package com.cg.busticketui.service;

import com.cg.busticketui.dto.response.OfficeBusResponseDto;
import com.cg.busticketui.dto.response.OfficeDriverResponseDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

/**
 * AgencyOfficeService defines the contract for operations related to
 * agency office management, such as retrieving buses and drivers
 * associated with a specific office.
 *
 * <p>
 * This interface acts as a service layer abstraction between the controller
 * and the underlying client or data access logic.
 * </p>
 */
public interface AgencyOfficeService {

    /**
     * Retrieves the list of buses associated with a given office.
     *
     * <p>
     * Uses the office ID to fetch relevant bus data and may utilize
     * session information for authentication or authorization.
     * </p>
     *
     * @param officeId the ID of the office
     * @param session  the HTTP session containing user/session context
     *
     * @return a list of {@link OfficeBusResponseDto} representing buses
     *         linked to the specified office
     */
    List<OfficeBusResponseDto> getBuses(Integer officeId, HttpSession session);

    /**
     * Retrieves the list of drivers associated with a given office.
     *
     * <p>
     * Uses the office ID to fetch relevant driver data and may utilize
     * session information for authentication or authorization.
     * </p>
     *
     * @param officeId the ID of the office
     * @param session  the HTTP session containing user/session context
     *
     * @return a list of {@link OfficeDriverResponseDto} representing drivers
     *         linked to the specified office
     */
    List<OfficeDriverResponseDto> getDrivers(Integer officeId, HttpSession session);
}