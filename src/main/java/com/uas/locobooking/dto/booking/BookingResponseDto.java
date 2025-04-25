package com.uas.locobooking.dto.booking;

import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Customer;
import com.uas.locobooking.models.Schedule;
import com.uas.locobooking.models.Seat;
import com.uas.locobooking.models.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {

    private String id;

    private Seat seat;

    private Schedule schedule;

    private Carriage carriage;

    private Customer customer;
}
