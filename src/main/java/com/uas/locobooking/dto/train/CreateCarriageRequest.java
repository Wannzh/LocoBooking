package com.uas.locobooking.dto.train;

import com.uas.locobooking.enums.CarriageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCarriageRequest {
    private int carriageNumber;
    private CarriageType carriageType; // EKSEKUTIF, BISNIS, PREMIUM
}
