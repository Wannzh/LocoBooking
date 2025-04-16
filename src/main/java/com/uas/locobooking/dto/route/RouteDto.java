package com.uas.locobooking.dto.route;

import com.uas.locobooking.dto.station.StationDto;
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
    private String id;
    private StationDto departureStation;
    private StationDto arrivalStation;
    private double price;

    public static RouteDto fromEntity(Route route) {
        if (route == null) return null;
        
        return RouteDto.builder()
                .id(route.getId())
                .departureStation(route.getDepartureStation() != null ? StationDto.fromEntity(route.getDepartureStation()) : null)
                .arrivalStation(route.getArrivalStation() != null ? StationDto.fromEntity(route.getArrivalStation()) : null)
                .price(route.getPrice())
                .build();
    }

    public Route toEntity() {
        return Route.builder()
                .id(id)
                .departureStation(departureStation != null ? departureStation.toEntity() : null)
                .arrivalStation(arrivalStation != null ? arrivalStation.toEntity() : null)
                .price(price)
                .build();
    }
}
