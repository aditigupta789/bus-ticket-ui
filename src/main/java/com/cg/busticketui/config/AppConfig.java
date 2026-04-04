package com.cg.busticketui.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * AppConfig is a configuration class responsible for defining
 * application-wide beans used across the project.
 *
 * <p>
 * This class centralizes common configurations such as HTTP clients
 * and JSON object mapping utilities, promoting reusability and
 * maintainability.
 * </p>
 *
 * <p>
 * Beans defined here are managed by the Spring IoC container and
 * can be injected wherever required using {@code @Autowired}
 * or constructor injection.
 * </p>
 */
@Configuration
public class AppConfig {

    /**
     * Creates and configures a {@link RestTemplate} bean.
     *
     * <p>
     * RestTemplate is used for making synchronous HTTP requests
     * to external or internal backend services.
     * </p>
     *
     * @return a RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Creates and configures an {@link ObjectMapper} bean.
     *
     * <p>
     * ObjectMapper is used for converting Java objects to JSON
     * and vice versa. It is commonly used for serialization and
     * deserialization of request and response bodies.
     * </p>
     *
     * @return an ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
