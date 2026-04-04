package com.cg.busticketui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin/home")
    public String adminDashboard() {
        return "dashboard/admin-dashboard";
    }

    @GetMapping("/agency/home")
    public String agencyDashboard() {
        return "dashboard/agency-dashboard";
    }

    @GetMapping("/customer/home")
    public String customerDashboard() {
        return "dashboard/customer-dashboard";
    }
}