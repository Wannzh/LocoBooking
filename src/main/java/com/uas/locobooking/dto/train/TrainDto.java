package com.uas.locobooking.dto.train;

import com.uas.locobooking.models.Train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainDto {
    private String id;
    private String trainName;
    private String trainCode;
    private int totalSeats;

    public static TrainDto fromEntity(Train train) {
        if (train == null) return null;

        return TrainDto.builder()
                .id(train.getId())
                .trainName(train.getTrainName())
                .trainCode(train.getTrainCode())
                .totalSeats(train.getTotalSeats())
                .build();
    }

    public Train toEntity() {
        return Train.builder()
                .id(id)
                .trainName(trainName)
                .trainCode(trainCode)
                .totalSeats(totalSeats)
                .build();
    }
}
