package com.uas.locobooking.dto.booking;

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
public class ScheduleUserDto {
    private String scheduleCode;
    private String trainCode;
    private String trainName;
    private String departureStation;
    private String arrivalStation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public static ScheduleUserDto fromEntity(Schedule schedule) {
        if (schedule == null)
            return null;

        return ScheduleUserDto.builder()
                .scheduleCode(schedule.getScheduleCode())
                .trainCode(schedule.getTrain() != null ? schedule.getTrain().getTrainCode() : null)
                .trainName(schedule.getTrain() != null ? schedule.getTrain().getTrainName() : null)
                .departureStation(
                        schedule.getRoute() != null ? schedule.getRoute().getDepartureStation().getStationName() : null)
                .arrivalStation(
                        schedule.getRoute() != null ? schedule.getRoute().getArrivalStation().getStationName() : null)
                .departureTime(schedule.getDepartureTime())
                .arrivalTime(schedule.getArrivalTime())
                .build();
    }
}
