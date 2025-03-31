package com.uas.locobooking.dto.route;

import com.uas.locobooking.dto.station.StationDto;

import lombok.Data;

@Data
public class RouteDto {
    private String id;
    private StationDto departureStation;
    private StationDto arrivalStation;
    private double price;
}
