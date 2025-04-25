package com.uas.locobooking.dto.booking;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {
    private String username;
    private String scheduleCode;
    private String seatNumber;
    private Integer carriageNumber;
    
    }
    