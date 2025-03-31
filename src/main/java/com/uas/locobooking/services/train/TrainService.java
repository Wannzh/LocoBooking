package com.uas.locobooking.services.train;

import java.util.List;

import com.uas.locobooking.dto.train.TrainDto;

public interface TrainService {
    TrainDto createTrain(TrainDto trainDto);
    TrainDto getTrainById(String id);
    List<TrainDto> getAllTrains();
    TrainDto updateTrain(String id, TrainDto trainDto);
    void deleteTrain(String id);
}
