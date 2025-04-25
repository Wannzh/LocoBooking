package com.uas.locobooking.services.booking;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.booking.ScheduleUserDto;

public interface ScheduleUserServices {
    GenericResponse<PageResponse<ScheduleUserDto>> getAllSchedules(int page, int size, String departureStation, String arrivalStation, String departureDate);
}
