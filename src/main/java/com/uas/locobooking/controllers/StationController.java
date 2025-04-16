package com.uas.locobooking.controllers;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.station.StationDto;
import com.uas.locobooking.services.station.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<GenericResponse<PageResponse<StationDto>>> getAllStations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "stationName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        try {
            return ResponseEntity.ok(stationService.getAllStations(page, size, keyword, sortBy, direction));
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<PageResponse<StationDto>>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<PageResponse<StationDto>>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/code/{stationCode}")
    public ResponseEntity<GenericResponse<StationDto>> getStationByCode(@PathVariable String stationCode) {
        try {
            return ResponseEntity.ok(stationService.getStationById(stationCode));
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<StationDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<StationDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse<StationDto>> createStation(@RequestBody StationDto stationDto) {
        try {
            StationDto createdStation = stationService.createStation(stationDto);
            return ResponseEntity.status(201).body(GenericResponse.<StationDto>builder()
                    .success(true)
                    .message("Station created successfully")
                    .data(createdStation)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<StationDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<StationDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @PutMapping("/code/{stationCode}")
    public ResponseEntity<GenericResponse<StationDto>> updateStation(@PathVariable String stationCode, @RequestBody StationDto stationDto) {
        try {
            StationDto updatedStation = stationService.updateStation(stationCode, stationDto);
            return ResponseEntity.ok(GenericResponse.<StationDto>builder()
                    .success(true)
                    .message("Station updated successfully")
                    .data(updatedStation)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<StationDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<StationDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/code/{stationCode}")
    public ResponseEntity<GenericResponse<String>> deleteStation(@PathVariable String stationCode) {
        try {
            stationService.deleteStation(stationCode);
            return ResponseEntity.ok(GenericResponse.<String>builder()
                    .success(true)
                    .message("Station deleted successfully")
                    .data("Station with code " + stationCode + " deleted")
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
