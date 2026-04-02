package com.cg.busticketui.controller;

import com.cg.busticketui.dto.response.CustomerResponseDto;
import com.cg.busticketui.service.AgencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/agency")
public class AgencyController {
    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping("/customers")
    public String getCustomers(@RequestParam(required = false) Integer agencyId,
                               Model model) {

        if (agencyId != null) {
            List<CustomerResponseDto> customers = agencyService.getCustomersByAgencyId(agencyId);
            model.addAttribute("customers", customers);
        }

        return "customers";
    }
}
