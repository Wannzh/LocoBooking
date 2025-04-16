package com.uas.locobooking.services.ticket;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.ticket.SeatDto;
import com.uas.locobooking.models.Seat;
import com.uas.locobooking.repositories.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Override
    public SeatDto createSeat(SeatDto seatDto) {
        Seat seat = Seat.builder()
                .seatNumber(seatDto.getSeatNumber())
                .isAvailable(seatDto.isAvailable())
                .build();

        return SeatDto.fromEntity(seatRepository.save(seat));
    }

    @Override
    public SeatDto getSeatById(String id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        return SeatDto.fromEntity(seat);
    }

    @Override
    public List<SeatDto> getAllSeats() {
        return seatRepository.findAll()
                .stream()
                .map(SeatDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SeatDto updateSeat(String id, SeatDto seatDto) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
                        
        seat = seat.toBuilder()
                .seatNumber(seatDto.getSeatNumber())
                .isAvailable(seatDto.isAvailable())
                .build();

        return SeatDto.fromEntity(seatRepository.save(seat));
    }

    @Override
    public void deleteSeat(String id) {
        if (!seatRepository.existsById(id)) {
            throw new RuntimeException("Seat not found");
        }
        seatRepository.deleteById(id);
    }
}
