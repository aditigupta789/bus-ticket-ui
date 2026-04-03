package com.cg.busticketui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BusTicketUiApplication is the entry point of the Spring Boot application.
 *
 * <p>
 * This class bootstraps and launches the Bus Ticket UI application using
 * Spring Boot's auto-configuration and component scanning features.
 * </p>
 *
 * <p>
 * The {@link SpringBootApplication} annotation enables:
 * <ul>
 *     <li>Auto-configuration of Spring components</li>
 *     <li>Component scanning within the base package</li>
 *     <li>Configuration support</li>
 * </ul>
 * </p>
 */
@SpringBootApplication
public class BusTicketUiApplication {

    /**
     * Main method that starts the Spring Boot application.
     *
     * @param args command-line arguments passed during application startup
     */
	public static void main(String[] args) {
		SpringApplication.run(BusTicketUiApplication.class, args);
	}

}
