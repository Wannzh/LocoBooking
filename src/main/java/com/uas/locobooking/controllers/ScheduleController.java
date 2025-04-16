package com.uas.locobooking.controllers;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.schedule.ScheduleDto;
import com.uas.locobooking.services.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<GenericResponse<ScheduleDto>> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        try {
            GenericResponse<ScheduleDto> response = scheduleService.createSchedule(scheduleDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.<ScheduleDto>builder()
                    .success(false)
                    .message("Failed to create schedule: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/{scheduleCode}")
    public ResponseEntity<GenericResponse<ScheduleDto>> getScheduleByScheduleCode(@PathVariable String scheduleCode) {
        try {
            GenericResponse<ScheduleDto> response = scheduleService.getScheduleByScheduleCode(scheduleCode);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.<ScheduleDto>builder()
                    .success(false)
                    .message("Failed to get schedule: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PageResponse<ScheduleDto>>> getAllSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "scheduleCode") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        try {
            GenericResponse<PageResponse<ScheduleDto>> response = scheduleService.getAllSchedules(page, size, keyword,
                    sortBy, direction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.<PageResponse<ScheduleDto>>builder()
                    .success(false)
                    .message("Failed to get schedules: " + e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{scheduleCode}")
    public ResponseEntity<GenericResponse<ScheduleDto>> updateSchedule(
            @PathVariable String scheduleCode,
            @RequestBody ScheduleDto scheduleDto) {
        try {
            GenericResponse<ScheduleDto> response = scheduleService.updateSchedule(scheduleCode, scheduleDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.<ScheduleDto>builder()
                    .success(false)
                    .message("Failed to update schedule: " + e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/{scheduleCode}")
    public ResponseEntity<GenericResponse<String>> deleteSchedule(@PathVariable String scheduleCode) {
        try {
            scheduleService.deleteSchedule(scheduleCode);

            GenericResponse<String> response = GenericResponse.<String>builder()
                    .success(true)
                    .message("Schedule deleted successfully")
                    .data("Schedule with code " + scheduleCode + " deleted")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.<String>builder()
                    .success(false)
                    .message("Failed to delete schedule: " + e.getMessage())
                    .build());
        }
    }
}