package com.uas.locobooking.dto.ticket;

import com.uas.locobooking.models.Seat;
import com.uas.locobooking.models.Train;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDto {
    private String seatNumber;
    private boolean isAvailable;

    public static SeatDto fromEntity(Seat seat) {
        if (seat == null) return null;

        return SeatDto.builder()
                .seatNumber(seat.getSeatNumber())
                .isAvailable(seat.isAvailable())
                .build();
    }

    public Seat toEntity(Train train) {
        return Seat.builder()
                .seatNumber(seatNumber)
                .isAvailable(isAvailable)
                .build();
    }
}
