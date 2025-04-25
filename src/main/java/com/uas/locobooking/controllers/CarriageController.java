package com.uas.locobooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.train.CreateCarriageBatchRequest;
import com.uas.locobooking.services.train.CarriageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/carriages")
public class CarriageController {

    @Autowired
    private CarriageService carriageService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/batch")
    public ResponseEntity<GenericResponse<String>> createCarriageBatch(@RequestBody CreateCarriageBatchRequest request) {
        try {
            carriageService.createCarriagesWithSeats(request);
            return ResponseEntity.ok(GenericResponse.<String>builder()
                    .success(true)
                    .message("Carriages and seats successfully created!")
                    .data(null)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(GenericResponse.<String>builder()
                    .success(false)
                    .message("Validation error: " + e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.<String>builder()
                    .success(false)
                    .message("Error occurred: " + e.getMessage())
                    .build());
        }
    }
}
