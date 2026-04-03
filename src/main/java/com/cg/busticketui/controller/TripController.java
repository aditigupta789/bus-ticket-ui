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

/**
 * TripController handles web requests related to trip operations,
 * including trip search and seat availability.
 *
 * <p>
 * This controller interacts with the TripUiService to fetch trip-related data
 * and prepares the model for rendering Thymeleaf views.
 * </p>
 *
 * <p>
 * All endpoints are prefixed with "/trip".
 * </p>
 */
@Controller
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripUiService tripUiService;

    /**
     * Loads the trip dashboard page.
     *
     * @param model the model used to pass data to the view
     * @return the trip dashboard view ("trip/trip-list")
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "trip/trip-list";
    }

    /**
     * Fetches available seats for a given trip.
     *
     * <p>
     * Retrieves seat availability using the trip ID and handles
     * both specific and generic exceptions gracefully.
     * </p>
     *
     * @param tripId the ID of the trip
     * @param model  the model used to pass seat data or error messages
     *
     * @return the seat result view ("trip/seats-result")
     */
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

    /**
     * Searches for trips based on source and destination.
     *
     * <p>
     * Retrieves matching trips from the service layer and handles
     * any potential errors by returning an empty result set with
     * an error message.
     * </p>
     *
     * @param source      the source location
     * @param destination the destination location
     * @param model       the model used to pass trip data or error messages
     *
     * @return the trip search result view ("trip/search-result")
     */

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
