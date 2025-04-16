package com.uas.locobooking.services.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.schedule.ScheduleDto;
import com.uas.locobooking.models.Schedule;
import com.uas.locobooking.models.Train;
import com.uas.locobooking.models.Route;
import com.uas.locobooking.repositories.RouteRepository;
import com.uas.locobooking.repositories.ScheduleRepository;
import com.uas.locobooking.repositories.TrainRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;

    @Override
    public GenericResponse<ScheduleDto> createSchedule(ScheduleDto scheduleDto) {
        if (scheduleDto == null) {
            throw new IllegalArgumentException("Schedule data must not be null");
        }

        if (scheduleDto.getScheduleCode() == null || scheduleDto.getScheduleCode().isEmpty()) {
            throw new IllegalArgumentException("Schedule code must not be empty");
        }

        Train train = trainRepository.findByTrainCode(scheduleDto.getTrainCode())
                .orElseThrow(() -> new RuntimeException("Train not found with code: " + scheduleDto.getTrainCode()));

        Route route = routeRepository.findByRouteCode(scheduleDto.getRouteCode())
                .orElseThrow(
                        () -> new EntityNotFoundException("Route not found with code: " + scheduleDto.getRouteCode()));

        Schedule schedule = Schedule.builder()
                .scheduleCode(scheduleDto.getScheduleCode())
                .train(train)
                .route(route)
                .departureTime(scheduleDto.getDepartureTime())
                .arrivalTime(scheduleDto.getArrivalTime())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        log.info("Schedule created successfully with scheduleCode: {}", savedSchedule.getScheduleCode());

        return GenericResponse.<ScheduleDto>builder()
                .success(true)
                .message("Schedule created successfully")
                .data(ScheduleDto.fromEntity(savedSchedule))
                .build();
    }

    @Override
    public GenericResponse<ScheduleDto> getScheduleByScheduleCode(String scheduleCode) {
        Schedule schedule = scheduleRepository.findByScheduleCode(scheduleCode)
                .orElseThrow(
                        () -> new EntityNotFoundException("Schedule not found with scheduleCode: " + scheduleCode));

        log.info("Schedule retrieved: {}", scheduleCode);

        return GenericResponse.<ScheduleDto>builder()
                .success(true)
                .message("Berhasil mendapatkan schedule")
                .data(ScheduleDto.fromEntity(schedule))
                .build();
    }

    @Override
    public GenericResponse<PageResponse<ScheduleDto>> getAllSchedules(int page, int size, String keyword, String sortBy,
            String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Schedule> schedulePage;

        if (keyword != null && !keyword.isEmpty()) {
            schedulePage = scheduleRepository.findByScheduleCodeContainingIgnoreCase(keyword, pageable);
        } else {
            schedulePage = scheduleRepository.findAll(pageable);
        }

        List<ScheduleDto> scheduleDtos = schedulePage.getContent()
                .stream()
                .map(ScheduleDto::fromEntity)
                .collect(Collectors.toList());

        PageResponse<ScheduleDto> pageResponse = PageResponse.<ScheduleDto>builder()
                .items(scheduleDtos)
                .page(schedulePage.getNumber())
                .size(schedulePage.getSize())
                .totalItem(schedulePage.getTotalElements())
                .totalPage(schedulePage.getTotalPages())
                .build();

        return GenericResponse.<PageResponse<ScheduleDto>>builder()
                .success(true)
                .message("Berhasil mendapatkan data schedule")
                .data(pageResponse)
                .build();
    }

    @Override
    public GenericResponse<ScheduleDto> updateSchedule(String scheduleCode, ScheduleDto scheduleDto) {
        if (scheduleDto == null) {
            throw new IllegalArgumentException("Schedule data must not be null");
        }

        if (scheduleDto.getScheduleCode() == null || scheduleDto.getScheduleCode().isEmpty()) {
            throw new IllegalArgumentException("Schedule code must not be empty");
        }

        Schedule schedule = scheduleRepository.findByScheduleCode(scheduleCode)
                .orElseThrow(
                        () -> new EntityNotFoundException("Schedule not found with scheduleCode: " + scheduleCode));

        Train train = trainRepository.findByTrainCode(scheduleDto.getTrainCode())
                .orElseThrow(() -> new RuntimeException("Train not found with code: " + scheduleDto.getTrainCode()));

        Route route = routeRepository.findByRouteCode(scheduleDto.getRouteCode())
                .orElseThrow(
                        () -> new EntityNotFoundException("Route not found with code: " + scheduleDto.getRouteCode()));

        schedule.setScheduleCode(scheduleDto.getScheduleCode());
        schedule.setTrain(train);
        schedule.setRoute(route);
        schedule.setDepartureTime(scheduleDto.getDepartureTime());
        schedule.setArrivalTime(scheduleDto.getArrivalTime());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        log.info("Schedule updated successfully: {}", scheduleCode);

        return GenericResponse.<ScheduleDto>builder()
                .success(true)
                .message("Schedule updated successfully")
                .data(ScheduleDto.fromEntity(updatedSchedule))
                .build();
    }

    @Override
    public GenericResponse<Void> deleteSchedule(String scheduleCode) {
        if (!scheduleRepository.existsByScheduleCode(scheduleCode)) {
            throw new EntityNotFoundException("Schedule not found with scheduleCode: " + scheduleCode);
        }

        scheduleRepository.deleteByScheduleCode(scheduleCode);
        log.info("Schedule deleted successfully: {}", scheduleCode);

        return GenericResponse.<Void>builder()
                .success(true)
                .message("Schedule deleted successfully")
                .data(null) // No data for delete operation, hence null
                .build();
    }
}
