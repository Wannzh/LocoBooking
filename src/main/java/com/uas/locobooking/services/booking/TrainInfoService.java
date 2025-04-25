package com.uas.locobooking.services.booking;

import java.util.List;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.booking.TrainInfoDto;


public interface TrainInfoService {
    GenericResponse<List<TrainInfoDto>> getTrainInfoByCode(String trainCode);

}
