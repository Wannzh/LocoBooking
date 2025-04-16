package com.uas.locobooking.services.ticket;

import java.util.List;

import com.uas.locobooking.dto.ticket.SeatDto;

public interface SeatService {
    SeatDto createSeat(SeatDto seatDto);
    SeatDto getSeatById(String id);
    List<SeatDto> getAllSeats();
    SeatDto updateSeat(String id, SeatDto seatDto);
    void deleteSeat(String id);
}
