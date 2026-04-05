package com.cg.busticketui.controller;

import com.cg.busticketui.dto.response.AgencyRevenueDto;
import com.cg.busticketui.dto.response.CustomerResponseDto;
import com.cg.busticketui.service.AgencyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cg.busticketui.dto.response.AgencyOfficeResponseDto;
import com.cg.busticketui.dto.response.BusResponseDto;
import java.time.LocalDateTime;

import java.util.List;

/**
 * AgencyController handles web requests related to agency operations.
 *
 * <p>
 * This controller is responsible for processing user requests,
 * interacting with the service layer, and returning appropriate
 * views with data populated in the model.
 * </p>
 *
 * <p>
 * All endpoints in this controller are prefixed with "/agency".
 * </p>
 */
@Controller
@RequestMapping("/agency")
public class AgencyController {
    private final AgencyService agencyService;

    /**
     * Constructor-based dependency injection for AgencyService.
     *
     * @param agencyService the service layer responsible for
     *                      handling business logic related to agencies
     */
    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    /**
     * Handles the request to fetch customers associated with a given agency.
     *
     * <p>
     * If an agencyId is provided, it retrieves the list of customers
     * from the service layer and adds it to the model.
     * </p>
     *
     * <p>
     * If agencyId is not provided, the view is returned without customer data.
     * </p>
     *
     * @param agencyId    the ID of the agency (optional query parameter)
     * @param model       the model used to pass data to the view
     * @param httpSession the HTTP session used for maintaining user/session context
     *
     * @return the name of the view ("customers") to be rendered
     */
    @GetMapping("/customers")
    public String getCustomers(@RequestParam(required = false) Integer agencyId,
                               Model model,
                               HttpSession httpSession) {

        model.addAttribute("error", null);
        if (agencyId != null) {
            List<CustomerResponseDto> customers = agencyService.getCustomersByAgencyId(agencyId,httpSession);
            model.addAttribute("customers", customers);
        }

        return "customers";
    }

    @GetMapping("/offices")
    public String getOffices(@RequestParam(required = false) Integer agencyId,
                             HttpSession session,
                             Model model) {
        model.addAttribute("error", null);
        if (agencyId != null) {
            List<AgencyOfficeResponseDto> offices =
                    agencyService.getOfficesByAgencyId(agencyId, session);

            model.addAttribute("offices", offices);
        }

        return "agency-offices";
    }

    @GetMapping("/buses")
    public String getBuses(@RequestParam(required = false) Integer agencyId,
                           @RequestParam(required = false) String tripDate,
                           HttpSession session,
                           Model model) {
        model.addAttribute("error", null);
        if (agencyId != null && tripDate != null) {

            LocalDateTime dateTime = LocalDateTime.parse(tripDate);

            List<BusResponseDto> buses =
                    agencyService.getBusesByAgencyAndDate(agencyId, dateTime, session);

            model.addAttribute("buses", buses);
        }

        return "agency-buses";
    }


    @GetMapping("/revenue")
    public String getRevenue(@RequestParam(required = false) Integer agencyId,
                             HttpSession session,
                             Model model) {
        model.addAttribute("error", null);
        if (agencyId != null) {

            AgencyRevenueDto revenue =
                    agencyService.getRevenueByAgencyId(agencyId, session);

            model.addAttribute("revenue", revenue);
        }

        return "agency-revenue";
    }
}
