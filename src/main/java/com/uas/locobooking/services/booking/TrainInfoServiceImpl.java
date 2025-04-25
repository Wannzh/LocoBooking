package com.uas.locobooking.services.booking;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.booking.TrainInfoDto;
import com.uas.locobooking.models.Seat;
import com.uas.locobooking.repositories.SeatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainInfoServiceImpl implements TrainInfoService {

    private final SeatRepository seatRepository;

    @Override
    public GenericResponse<List<TrainInfoDto>> getTrainInfoByCode(String trainCode) {
        List<Seat> seats = seatRepository.findAllSeatsByTrainCode(trainCode);

        List<TrainInfoDto> dtos = seats.stream()
                .map(TrainInfoDto::fromEntity)
                .collect(Collectors.toList());

        return GenericResponse.<List<TrainInfoDto>>builder()
                .success(true)
                .message("Data berhasil ditemukan")
                .data(dtos)
                .build();
    }
}
