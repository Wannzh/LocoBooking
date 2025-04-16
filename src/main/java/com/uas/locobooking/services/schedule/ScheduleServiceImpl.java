package com.uas.locobooking.services.schedule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        if (scheduleDto == null) {
            throw new IllegalArgumentException("Schedule data must not be null");
        }

        Train train = trainRepository.findById(scheduleDto.getTrain().getId())
                .orElseThrow(() -> new EntityNotFoundException("Train not found with ID: " + scheduleDto.getTrain().getId()));

        Route route = routeRepository.findById(scheduleDto.getRoute().getId())
                .orElseThrow(() -> new EntityNotFoundException("Route not found with ID: " + scheduleDto.getRoute().getId()));

        Schedule schedule = Schedule.builder()
                .train(train)
                .route(route)
                .departureTime(scheduleDto.getDepartureTime())
                .arrivalTime(scheduleDto.getArrivalTime())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        log.info("Schedule created successfully with ID: {}", savedSchedule.getId());

        return ScheduleDto.fromEntity(savedSchedule);
    }

    @Override
    public ScheduleDto getScheduleById(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + id));

        log.info("Schedule retrieved: {}", id);
        return ScheduleDto.fromEntity(schedule);
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        List<ScheduleDto> schedules = scheduleRepository.findAll()
                .stream()
                .map(ScheduleDto::fromEntity)
                .collect(Collectors.toList());

        log.info("Total schedules retrieved: {}", schedules.size());
        return schedules;
    }

    @Override
    public ScheduleDto updateSchedule(String id, ScheduleDto scheduleDto) {
        if (scheduleDto == null) {
            throw new IllegalArgumentException("Schedule data must not be null");
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + id));

        Train train = trainRepository.findById(scheduleDto.getTrain().getId())
                .orElseThrow(() -> new EntityNotFoundException("Train not found with ID: " + scheduleDto.getTrain().getId()));

        Route route = routeRepository.findById(scheduleDto.getRoute().getId())
                .orElseThrow(() -> new EntityNotFoundException("Route not found with ID: " + scheduleDto.getRoute().getId()));

        schedule.setTrain(train);
        schedule.setRoute(route);
        schedule.setDepartureTime(scheduleDto.getDepartureTime());
        schedule.setArrivalTime(scheduleDto.getArrivalTime());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        log.info("Schedule updated successfully: {}", id);

        return ScheduleDto.fromEntity(updatedSchedule);
    }

    @Override
    public void deleteSchedule(String id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found with ID: " + id);
        }

        scheduleRepository.deleteById(id);
        log.info("Schedule deleted successfully: {}", id);
    }
}
