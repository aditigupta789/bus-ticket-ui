package com.cg.busticketui.controller;
import com.cg.busticketui.service.RouteUIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling route-related UI requests.
 */
@Controller
@RequestMapping("/routes")
public class RouteController {
    /**
     * Service used to fetch route data from backend APIs.
     */
    private final RouteUIService routeUIService;
    /**
     * Constructor-based dependency injection for RouteUIService.
     */
    public RouteController(RouteUIService routeUIService) {
        this.routeUIService = routeUIService;
    }

    /**
     * Displays the home page with search form and options.
     */
    @GetMapping
    public String home(Model model) {
        return "route/route-home";
    }

    /**
     * Handles search request for routes between source and destination.
     * Fetches data from service and adds it to the model.
     */

    @GetMapping("/search")
    public String searchRoutes(@RequestParam String source, @RequestParam String destination, Model model) {
        try {
            model.addAttribute("routes", routeUIService.getRoutes(source, destination));
            model.addAttribute("type", "search");

        } catch (Exception e) {
            model.addAttribute("error", "No routes found!");
        }
        return "route/route-search-result";
    }


    /**
     * Retrieves and displays the most popular routes.
     */

    @GetMapping("/popular")
    public String popularRoutes(Model model) {
        model.addAttribute("routes", routeUIService.getPopularRoutes());
        model.addAttribute("type", "popular");
        return "route/popular-routes";
    }
}