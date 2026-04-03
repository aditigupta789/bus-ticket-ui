package com.cg.busticketui.controller;

import com.cg.busticketui.exception.TripNotFoundException;
import com.cg.busticketui.service.TripUiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripUiService tripUiService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "trip/trip-list";
    }

    @GetMapping("/seats")
    public String getSeats(
            @RequestParam(name = "tripId") Integer tripId,
            Model model
    ) {
        model.addAttribute("tripId", tripId);

        try {
            Map<String, Object> seats = tripUiService.getAvailableSeats(tripId);
            model.addAttribute("seats", seats);
            model.addAttribute("seatsError", null);
        } catch (TripNotFoundException ex) {
            model.addAttribute("seats", null);
            model.addAttribute("seatsError", ex.getMessage());
        } catch (Exception ex) {
            model.addAttribute("seats", null);
            model.addAttribute("seatsError", ex.getMessage());
        }

        return "trip/seats-result";
    }

    @GetMapping("/search")
    public String searchTrips(
            @RequestParam(name = "source") String source,
            @RequestParam(name = "destination") String destination,
            Model model
    ) {
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);

        try {
            List<Map<String, Object>> trips = tripUiService.searchTrips(source, destination);
            model.addAttribute("trips", trips);
            model.addAttribute("tripsError", null);
        } catch (Exception ex) {
            model.addAttribute("trips", List.of());
            model.addAttribute("tripsError", ex.getMessage());
        }

        return "trip/search-result";
    }
}
