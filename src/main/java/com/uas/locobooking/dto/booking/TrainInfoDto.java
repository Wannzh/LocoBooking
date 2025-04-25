package com.uas.locobooking.dto.booking;

import com.uas.locobooking.enums.CarriageType;
import com.uas.locobooking.models.Seat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainInfoDto {
    private String trainName;
    private String trainCode;
    private int carriageNumber;
    private CarriageType carriageType;
    private double price;
    private String seatNumber;
    private boolean seatAvailable;

    public static TrainInfoDto fromEntity(Seat seat) {
        return TrainInfoDto.builder()
                .trainName(seat.getCarriage().getTrain().getTrainName())
                .trainCode(seat.getCarriage().getTrain().getTrainCode())
                .carriageNumber(seat.getCarriage().getCarriageNumber())
                .carriageType(seat.getCarriage().getCarriageType())
                .price(seat.getCarriage().getPrice())
                .seatNumber(seat.getSeatNumber())
                .seatAvailable(seat.isAvailable())
                .build();
    }
}
