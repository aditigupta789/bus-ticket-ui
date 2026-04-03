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

@Controller
@RequestMapping("/agency-office")
@RequiredArgsConstructor
@Validated
public class AgencyOfficeController {

    private final AgencyOfficeService agencyOfficeService;

    @GetMapping
    public String index() {
        return "agency-office/index";
    }

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

    // -------------------- GET BUSES --------------------
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