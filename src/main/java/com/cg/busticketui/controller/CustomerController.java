package com.cg.busticketui.controller;

import com.cg.busticketui.dto.response.BookingResponseDto;
import com.cg.busticketui.dto.response.CustomerResponseDto;
import com.cg.busticketui.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CustomerController handles web requests related to customer operations,
 * including searching customers and retrieving their bookings.
 *
 * <p>
 * This controller interacts with the CustomerService to fetch data
 * and populates the model for rendering views using Thymeleaf.
 * </p>
 *
 * <p>
 * All endpoints are prefixed with "/customer".
 * </p>
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructor-based dependency injection for CustomerService.
     *
     * @param customerService the service responsible for handling
     *                        customer-related business logic
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Loads the initial customer page.
     *
     * @return the main customer view ("agencyCustomers")
     */
    @GetMapping
    public String loadPage() {
        return "agencyCustomers";
    }

    /**
     * Handles request to fetch bookings for a specific customer.
     *
     * <p>
     * Retrieves booking details using the customer ID and adds
     * them to the model for display.
     * </p>
     *
     * @param customerId the ID of the customer
     * @param session    the HTTP session used for maintaining user/session context
     * @param model      the model used to pass data to the view
     *
     * @return the customer view ("agencyCustomers") populated with bookings
     */
    @GetMapping("/bookings")
    public String getBookings(
            @RequestParam Integer customerId,
            HttpSession session,
            Model model) {

        List<BookingResponseDto> bookings =
                customerService.getBookings(customerId,session);

        model.addAttribute("bookings", bookings);

        return "agencyCustomers"; // IMPORTANT
    }
}