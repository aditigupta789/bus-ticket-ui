package com.cg.busticketui.controller;

import com.cg.busticketui.dto.response.BookingResponseDto;
import com.cg.busticketui.dto.response.CustomerResponseDto;
import com.cg.busticketui.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String loadPage() {
        return "agencyCustomers";
    }

    // 🔥 FETCH CUSTOMERS
    @GetMapping("/search")
    public String getCustomers(
            @RequestParam String name,
            @RequestParam String address,
            HttpSession session,
            Model model) {

        List<CustomerResponseDto> customers =
                customerService.getCustomers(name, address,session);

        model.addAttribute("customers", customers);

        return "agencyCustomers"; // IMPORTANT
    }

    // 🔥 FETCH BOOKINGS
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