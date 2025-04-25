package com.uas.locobooking.services.booking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.booking.BookingRequestDto;
import com.uas.locobooking.dto.booking.BookingResponseDto;
import com.uas.locobooking.dto.booking.CheckPesananDto;

@Service
public interface BookingService {

    List<CheckPesananDto> checkPesananTiket(String email);
    
    BookingResponseDto createPesananTiket(BookingRequestDto booking);

    void batalPemesanan(String bookingId);
}
