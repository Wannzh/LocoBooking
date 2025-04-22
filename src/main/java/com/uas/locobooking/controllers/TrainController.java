package com.uas.locobooking.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.train.TrainDto;
import com.uas.locobooking.services.train.TrainService;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<PageResponse<TrainDto>>> getAllTrains(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "trainName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        try {
            return ResponseEntity.ok(trainService.getAllTrains(page, size, keyword, sortBy, direction));
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<PageResponse<TrainDto>>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<PageResponse<TrainDto>>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/code/{trainCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<TrainDto>> getTrainByTrainCode(@PathVariable String trainCode) {
        try {
            return ResponseEntity.ok(trainService.getTrainByTrainCode(trainCode));
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<TrainDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<TrainDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<TrainDto>> createTrain(@RequestBody TrainDto trainDto) {
        try {
            TrainDto createdTrain = trainService.createTrain(trainDto);
            return ResponseEntity.status(201).body(GenericResponse.<TrainDto>builder()
                    .success(true)
                    .message("Train created successfully")
                    .data(createdTrain)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<TrainDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<TrainDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @PutMapping("/code/{trainCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<TrainDto>> updateTrain(@PathVariable String trainCode, @RequestBody TrainDto trainDto) {
        try {
            TrainDto updatedTrain = trainService.updateTrain(trainCode, trainDto);
            return ResponseEntity.ok(GenericResponse.<TrainDto>builder()
                    .success(true)
                    .message("Train updated successfully")
                    .data(updatedTrain)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<TrainDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<TrainDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/code/{trainCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<String>> deleteTrain(@PathVariable String trainCode) {
        try {
            trainService.deleteTrain(trainCode);
            return ResponseEntity.ok(GenericResponse.<String>builder()
                    .success(true)
                    .message("Train deleted successfully")
                    .data("Train with code " + trainCode + " deleted")
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<String>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<String>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }
}
