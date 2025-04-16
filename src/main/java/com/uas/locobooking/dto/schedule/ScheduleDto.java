package com.uas.locobooking.dto.schedule;

import java.time.LocalDateTime;

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
    private String scheduleCode;
    private String trainCode; 
    private String routeCode; 
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public static ScheduleDto fromEntity(Schedule schedule) {
        if (schedule == null)
            return null;

        return ScheduleDto.builder()
                .scheduleCode(schedule.getScheduleCode())
                .trainCode(schedule.getTrain() != null ? schedule.getTrain().getTrainCode() : null)
                .routeCode(schedule.getRoute() != null ? schedule.getRoute().getRouteCode() : null)
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .build();
    }

}
