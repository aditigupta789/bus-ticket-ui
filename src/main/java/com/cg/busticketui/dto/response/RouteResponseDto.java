package com.cg.busticketui.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * DTO for route details used in UI.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDto {
    /**
     * Name of the source (starting) city.
     */
    private String fromCity;
    /**
     * Name of the destination (ending) city.
     */
    private String toCity;
    /**
     * Total duration of the route (in hours).
     */
    private int duration;
    /**
     * Number of intermediate stops (break points) in the route.
     */
    private int breakPoints;

}