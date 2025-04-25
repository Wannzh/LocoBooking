package com.uas.locobooking.dto.booking;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckPesananDto {
    private String name;
    private String seatNumber;
    private Integer carriageNumber;
    private double price;
    private LocalDateTime departureTime;
    private String idBooking;
}
