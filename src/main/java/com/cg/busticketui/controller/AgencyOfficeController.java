package com.cg.busticketui.controller;

import com.cg.busticketui.dto.response.OfficeBusResponseDto;
import com.cg.busticketui.dto.response.OfficeDriverResponseDto;
import com.cg.busticketui.service.AgencyOfficeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AgencyOfficeController handles web requests related to agency office operations,
 * such as fetching buses and drivers associated with a specific office.
 *
 * <p>
 * This controller follows the Spring MVC pattern:
 * <ul>
 *     <li>Receives HTTP requests</li>
 *     <li>Validates input parameters</li>
 *     <li>Delegates business logic to the service layer</li>
 *     <li>Returns appropriate Thymeleaf views</li>
 * </ul>
 * </p>
 *
 * <p>
 * All endpoints are prefixed with "/agency-office".
 * </p>
 */
@Controller
@RequestMapping("/agency-office")
@RequiredArgsConstructor
@Validated
public class AgencyOfficeController {

    private final AgencyOfficeService agencyOfficeService;

    /**
     * Loads the main index page for agency office operations.
     *
     * @return the index view for agency office ("agency-office/index")
     */
    @GetMapping
    public String index() {
        return "agency-office/index";
    }

    /**
     * Handles validation exceptions triggered by invalid request parameters.
     *
     * <p>
     * Captures {@link ConstraintViolationException} and displays
     * appropriate error messages on the corresponding view page.
     * </p>
     *
     * @param ex      the validation exception thrown
     * @param model   the model used to pass error messages to the view
     * @param request the HTTP request to determine the originating endpoint
     *
     * @return the appropriate view based on the request URI
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleValidationException(ConstraintViolationException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", ex.getMessage());

        String requestUri = request.getRequestURI();
        if (requestUri != null) {
            if (requestUri.endsWith("/drivers")) {
                return "agency-office/office-drivers";
            }
            if (requestUri.endsWith("/buses")) {
                return "agency-office/office-buses";
            }
        }

        return "agency-office/index";
    }

    /**
     * Handles request to fetch buses associated with a given office.
     *
     * <p>
     * Validates the officeId and retrieves corresponding bus data
     * from the service layer.
     * </p>
     *
     * @param officeId the ID of the office (must be non-null and greater than 0)
     * @param session  the HTTP session used for maintaining user/session context
     * @param model    the model used to pass data to the view
     *
     * @return the view displaying buses ("agency-office/office-buses")
     */
    @GetMapping("/buses")
    public String getBusesByOffice(
            @RequestParam
            @NotNull(message = "Office ID is required")
            @Min(value = 1, message = "Office ID must be greater than 0")
            Integer officeId,
            HttpSession session,
            Model model
    ) {
        model.addAttribute("officeId", officeId);

        List<OfficeBusResponseDto> buses = agencyOfficeService.getBuses(officeId,session);
        model.addAttribute("buses", buses);

        return "agency-office/office-buses";
    }

    /**
     * Handles request to fetch drivers associated with a given office.
     *
     * <p>
     * Validates the officeId and retrieves corresponding driver data
     * from the service layer.
     * </p>
     *
     * @param officeId the ID of the office (must be non-null and greater than 0)
     * @param session  the HTTP session used for maintaining user/session context
     * @param model    the model used to pass data to the view
     *
     * @return the view displaying drivers ("agency-office/office-drivers")
     */

    @GetMapping("/drivers")
    public String getDriversByOffice(
            @RequestParam
            @NotNull(message = "Office ID is required")
            @Min(value = 1, message = "Office ID must be greater than 0")
            Integer officeId,
            HttpSession session,
            Model model
    ) {
        model.addAttribute("officeId", officeId);

        List<OfficeDriverResponseDto> drivers = agencyOfficeService.getDrivers(officeId,session);
        model.addAttribute("drivers", drivers);

        return "agency-office/office-drivers";
    }
}