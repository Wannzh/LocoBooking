package com.uas.locobooking.services.schedule;

import java.util.List;

import com.uas.locobooking.dto.schedule.ScheduleDto;

public interface ScheduleService {
    ScheduleDto createSchedule(ScheduleDto scheduleDto);
    ScheduleDto getScheduleById(String id);
    List<ScheduleDto> getAllSchedules();
    ScheduleDto updateSchedule(String id, ScheduleDto scheduleDto);
    void deleteSchedule(String id);
}
