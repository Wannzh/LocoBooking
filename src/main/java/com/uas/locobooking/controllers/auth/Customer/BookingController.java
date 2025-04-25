package com.uas.locobooking.controllers.auth.Customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.uas.locobooking.constants.MessageConstant;
import com.uas.locobooking.dto.GeneralResponse;
import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.booking.BookingRequestDto;
import com.uas.locobooking.dto.booking.CheckPesananDto;
import com.uas.locobooking.dto.booking.ScheduleUserDto;
import com.uas.locobooking.dto.booking.TrainInfoDto;
import com.uas.locobooking.services.booking.BookingService;
import com.uas.locobooking.services.booking.ScheduleUserServices;
import com.uas.locobooking.services.booking.TrainInfoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {

    private final ScheduleUserServices scheduleUserServices;
    private final TrainInfoService trainInfoService;


    @Autowired
    private BookingService bookingService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<Object> createPesananTiket(@RequestBody BookingRequestDto dto) {
        try {
            System.out.println("data123 " + dto);
            // Booking response = bookingService.createPesananTiket(dto);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(bookingService.createPesananTiket(dto),
                            MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/check-pesanan")
    public ResponseEntity<Object> checkPesananTiket(@RequestParam String username) {
        try {
            System.out.println("data123 " + username);
            List<CheckPesananDto> response = bookingService.checkPesananTiket(username);
            return ResponseEntity.ok(GeneralResponse.success(response, MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<GenericResponse<String>> cancelTiket(@PathVariable String bookingId) {
        try {
            bookingService.batalPemesanan(bookingId);
            return ResponseEntity.ok(GenericResponse.<String>builder()
                    .success(true)
                    .message("Tiket cancel successfully")
                    .data("Tiket " + bookingId + " cancelled")
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

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/schedule")
    public GenericResponse<PageResponse<ScheduleUserDto>> getAllSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String departureStation,
            @RequestParam(required = false) String arrivalStation,
            @RequestParam(required = false) String departureDate) {
        return scheduleUserServices.getAllSchedules(page, size, departureStation, arrivalStation, departureDate);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/train-info")
    public ResponseEntity<GenericResponse<List<TrainInfoDto>>> getTrainInfo(@RequestParam String trainCode) {
        try {
            // Call service method that returns a list of TrainInfoDto
            GenericResponse<List<TrainInfoDto>> response = trainInfoService.getTrainInfoByCode(trainCode);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle any errors that might occur
            return ResponseEntity.internalServerError().body(GenericResponse.<List<TrainInfoDto>>builder()
                    .success(false)
                    .message("Failed to get train info: " + e.getMessage())
                    .build());
        }
    }
        

}
