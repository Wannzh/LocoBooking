package com.uas.locobooking.dto.schedule;

import java.time.LocalDateTime;

import com.uas.locobooking.dto.route.RouteDto;
import com.uas.locobooking.dto.train.TrainDto;

import lombok.Data;

@Data
public class ScheduleDto {
    private String id;
    private TrainDto train;
    private RouteDto route;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}
