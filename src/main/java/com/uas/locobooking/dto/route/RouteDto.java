package com.uas.locobooking.dto.route;

import com.uas.locobooking.models.Route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDto {
    private String routeCode;
    private String departureStationCode;
    private String arrivalStationCode;

    public static RouteDto fromEntity(Route route) {
        return RouteDto.builder()
                .routeCode(route.getRouteCode())
                .departureStationCode(route.getDepartureStation().getStationCode())
                .arrivalStationCode(route.getArrivalStation().getStationCode())
                .build();
    }
}
