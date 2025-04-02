package com.uas.locobooking.dto.schedule;

import java.time.LocalDateTime;

import com.uas.locobooking.dto.route.RouteDto;
import com.uas.locobooking.dto.train.TrainDto;
import com.uas.locobooking.models.Schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private String id;
    private TrainDto train;
    private RouteDto route;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public static ScheduleDto fromEntity(Schedule schedule) {
        if (schedule == null) return null;
        
        return ScheduleDto.builder()
                .id(schedule.getId())
                .train(schedule.getTrain() != null ? TrainDto.fromEntity(schedule.getTrain()) : null)
                .route(schedule.getRoute() != null ? RouteDto.fromEntity(schedule.getRoute()) : null)
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .build();
    }

    public Schedule toEntity() {
        return Schedule.builder()
                .id(id)
                .train(train != null ? train.toEntity() : null)
                .route(route != null ? route.toEntity() : null)
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .build();
    }
}
