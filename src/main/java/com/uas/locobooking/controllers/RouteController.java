package com.uas.locobooking.controllers;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.route.RouteDto;
import com.uas.locobooking.services.route.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<RouteDto>> createRoute(@RequestBody RouteDto routeDto) {
        try {
            RouteDto createdRoute = routeService.createRoute(routeDto);
            return ResponseEntity.status(201).body(GenericResponse.<RouteDto>builder()
                    .success(true)
                    .message("Route created successfully")
                    .data(createdRoute)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<RouteDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<RouteDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<PageResponse<RouteDto>>> getAllRoutes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "routeCode") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        try {
            return ResponseEntity.ok(routeService.getAllRoutes(page, size, keyword, sortBy, direction));
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<PageResponse<RouteDto>>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<PageResponse<RouteDto>>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/{routeCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<RouteDto>> getRouteByCode(@PathVariable String routeCode) {
        try {
            RouteDto route = routeService.getRouteById(routeCode);
            return ResponseEntity.ok(GenericResponse.<RouteDto>builder()
                    .success(true)
                    .message("Route fetched successfully")
                    .data(route)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<RouteDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<RouteDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @PutMapping("/{routeCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<RouteDto>> updateRoute(@PathVariable String routeCode,
            @RequestBody RouteDto routeDto) {
        try {
            RouteDto updated = routeService.updateRoute(routeCode, routeDto);
            return ResponseEntity.ok(GenericResponse.<RouteDto>builder()
                    .success(true)
                    .message("Route updated successfully")
                    .data(updated)
                    .build());
        } catch (ResponseStatusException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(ex.getStatusCode()).body(GenericResponse.<RouteDto>builder()
                    .success(false)
                    .message(ex.getReason())
                    .data(null)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.<RouteDto>builder()
                    .success(false)
                    .message("Terjadi kesalahan di sistem internal")
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/{routeCode}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<GenericResponse<String>> deleteRoute(@PathVariable String routeCode) {
        try {
            routeService.deleteRoute(routeCode);
            return ResponseEntity.ok(GenericResponse.<String>builder()
                    .success(true)
                    .message("Route deleted successfully")
                    .data("Route " + routeCode + " deleted")
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
