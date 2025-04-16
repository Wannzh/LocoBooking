package com.uas.locobooking.services.schedule;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.schedule.ScheduleDto;

public interface ScheduleService {
    GenericResponse<ScheduleDto> createSchedule(ScheduleDto scheduleDto);

    GenericResponse<ScheduleDto> getScheduleByScheduleCode(String scheduleCode);

    GenericResponse<PageResponse<ScheduleDto>> getAllSchedules(int page, int size, String keyword, String sortBy,
            String direction);

    GenericResponse<ScheduleDto> updateSchedule(String scheduleCode, ScheduleDto scheduleDto);

    GenericResponse<Void> deleteSchedule(String scheduleCode);
}
