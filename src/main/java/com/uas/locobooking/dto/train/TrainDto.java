package com.uas.locobooking.dto.train;

import lombok.Data;

@Data
public class TrainDto {
    private String id;
    private String trainName;
    private String trainCode;
    private int totalSeats;
}
