package com.uas.locobooking.services.train;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.train.TrainDto;

public interface TrainService {
    TrainDto createTrain(TrainDto trainDto);
    GenericResponse<TrainDto> getTrainByTrainCode(String trainCode);
    GenericResponse<PageResponse<TrainDto>> getAllTrains(int page, int size, String keyword, String sortBy,
    String direction);
    TrainDto updateTrain(String trainCode, TrainDto trainDto);
    void deleteTrain(String trainCode);
}
