package com.uas.locobooking.dto.ticket;

import lombok.Data;

@Data
public class SeatDto {
    private String seatId;
    private String seatNumber;
    private boolean isAvailable;
    private String classType;  // Ekonomi, Bisnis, VIP
}
