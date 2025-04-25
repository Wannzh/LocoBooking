package com.uas.locobooking.services.booking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.booking.ScheduleUserDto;
import com.uas.locobooking.models.Schedule;
import com.uas.locobooking.repositories.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleUserServiceImpl implements ScheduleUserServices {

    private final ScheduleRepository scheduleRepository;

    @Override
    public GenericResponse<PageResponse<ScheduleUserDto>> getAllSchedules(int page, int size, String departureStation,
            String arrivalStation, String departureDate) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedulePage;

        LocalDate departureDateParsed = null;
        if (departureDate != null && !departureDate.isEmpty()) {
            try {
                departureDateParsed = LocalDate.parse(departureDate);
            } catch (DateTimeParseException e) {
                return GenericResponse.<PageResponse<ScheduleUserDto>>builder()
                        .success(false)
                        .message("Format tanggal keberangkatan tidak valid. Gunakan format yyyy-MM-dd.")
                        .build();
            }
        }

        if (departureStation != null && !departureStation.isEmpty()) {
            schedulePage = scheduleRepository.findByDepartureStationAndDate(
                    departureStation,
                    departureDateParsed,
                    pageable);
        } else if (arrivalStation != null && !arrivalStation.isEmpty()) {
            schedulePage = scheduleRepository.findByArrivalStationAndDate(
                    arrivalStation,
                    departureDateParsed,
                    pageable);
        } else if (departureDateParsed != null) {
            schedulePage = scheduleRepository.findByDateOnly(departureDateParsed, pageable);
        } else {
            schedulePage = scheduleRepository.findAll(pageable);
        }

        List<ScheduleUserDto> scheduleDtos = schedulePage.getContent()
                .stream()
                .map(ScheduleUserDto::fromEntity)
                .collect(Collectors.toList());

        PageResponse<ScheduleUserDto> pageResponse = PageResponse.<ScheduleUserDto>builder()
                .items(scheduleDtos)
                .page(schedulePage.getNumber())
                .size(schedulePage.getSize())
                .totalItem(schedulePage.getTotalElements())
                .totalPage(schedulePage.getTotalPages())
                .build();

        return GenericResponse.<PageResponse<ScheduleUserDto>>builder()
                .success(true)
                .message("Berhasil mendapatkan data schedule")
                .data(pageResponse)
                .build();
    }

}
