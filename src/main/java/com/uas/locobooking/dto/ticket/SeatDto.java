package com.uas.locobooking.dto.ticket;

import com.uas.locobooking.models.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDto {
    private String seatId;
    private String seatNumber;
    private boolean isAvailable;
    private String classType;  // Ekonomi, Bisnis, VIP
    private String trainId;    // Menambahkan trainId

    public static SeatDto fromEntity(Seat seat) {
        if (seat == null) return null;

        return SeatDto.builder()
                .seatId(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .isAvailable(seat.isAvailable())
                .classType(seat.getSeatClass())
                .trainId(seat.getTrain() != null ? seat.getTrain().getId() : null)  // Mengambil trainId dari objek Train
                .build();
    }

    public Seat toEntity() {
        return Seat.builder()
                .id(seatId)
                .seatNumber(seatNumber)
                .isAvailable(isAvailable)
                .seatClass(classType)
                .build();
    }
}
