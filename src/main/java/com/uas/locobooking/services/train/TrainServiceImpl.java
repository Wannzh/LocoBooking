package com.uas.locobooking.services.train;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.train.TrainDto;
import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Seat;
import com.uas.locobooking.models.Train;
import com.uas.locobooking.repositories.CarriageRepository;
import com.uas.locobooking.repositories.SeatRepository;
import com.uas.locobooking.repositories.TrainRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainRepository trainRepository;
    private final CarriageRepository carriageRepository;
    private final SeatRepository seatRepository;

    private static final Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);

    private final List<Integer> allowedSeats = List.of(20, 40, 60, 80, 100, 120);

    private void validateTotalSeats(int totalSeats) {
        if (!allowedSeats.contains(totalSeats)) {
            throw new IllegalArgumentException("Total seats must be one of: " + allowedSeats);
        }
    }

    @Override
    public TrainDto createTrain(TrainDto trainDto) {
        validateTotalSeats(trainDto.getTotalSeats());
        Train train = trainDto.toEntity();
        Train savedTrain = trainRepository.save(train);
        return TrainDto.fromEntity(savedTrain);
    }

    @Override
    public GenericResponse<TrainDto> getTrainByTrainCode(String trainCode) {
        Train train = trainRepository.findByTrainCode(trainCode)
                .orElseThrow(() -> new RuntimeException("Train not found with trainCode: " + trainCode));

        return GenericResponse.<TrainDto>builder()
                .success(true)
                .message("Train fetched successfully")
                .data(TrainDto.fromEntity(train))
                .build();
    }

    @Override
    public GenericResponse<PageResponse<TrainDto>> getAllTrains(int page, int size, String keyword, String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Train> trainPage;

        if (keyword != null && !keyword.isEmpty()) {
            trainPage = trainRepository.findByTrainCodeContainingIgnoreCaseOrTrainNameContainingIgnoreCase(
                    keyword, keyword, pageable);
        } else {
            trainPage = trainRepository.findAll(pageable);
        }

        List<TrainDto> items = trainPage.getContent()
                .stream()
                .map(TrainDto::fromEntity)
                .collect(Collectors.toList());

        PageResponse<TrainDto> pageResponse = PageResponse.<TrainDto>builder()
                .page(trainPage.getNumber())
                .size(trainPage.getSize())
                .totalPage(trainPage.getTotalPages())
                .totalItem(trainPage.getTotalElements())
                .items(items)
                .build();

        return GenericResponse.<PageResponse<TrainDto>>builder()
                .success(true)
                .message("Trains fetched successfully")
                .data(pageResponse)
                .build();
    }

    @Override
    public TrainDto updateTrain(String trainCode, TrainDto trainDto) {
        validateTotalSeats(trainDto.getTotalSeats());

        Train train = trainRepository.findByTrainCode(trainCode)
                .orElseThrow(() -> new RuntimeException("Train not found with trainCode: " + trainCode));

        train.setTrainName(trainDto.getTrainName());
        train.setTrainCode(trainDto.getTrainCode());
        train.setTotalSeats(trainDto.getTotalSeats());

        Train updatedTrain = trainRepository.save(train);

        synchronizeCarriageWithTotalSeats(updatedTrain);

        return TrainDto.fromEntity(updatedTrain);
    }

    @Transactional
    @Override
    public void deleteTrain(String trainCode) {
        if (!trainRepository.existsByTrainCode(trainCode)) {
            throw new RuntimeException("Train not found with trainCode: " + trainCode);
        }
        trainRepository.deleteByTrainCode(trainCode);
    }

    private void synchronizeCarriageWithTotalSeats(Train train) {
        int allowedCarriages = train.getTotalSeats() / 20;

        List<Carriage> carriages = carriageRepository.findByTrain(train)
                .stream()
                .sorted(Comparator.comparingInt(Carriage::getCarriageNumber))
                .collect(Collectors.toList());

        if (carriages.size() > allowedCarriages) {
            int excess = carriages.size() - allowedCarriages;

            // Hapus carriage paling belakang
            List<Carriage> toRemove = carriages.stream()
                    .sorted((c1, c2) -> Integer.compare(c2.getCarriageNumber(), c1.getCarriageNumber()))
                    .limit(excess)
                    .collect(Collectors.toList());

            for (Carriage carriage : toRemove) {
                logger.info("Deleting carriage no. {} from train {} due to reduced seat count.",
                        carriage.getCarriageNumber(), train.getTrainCode());

                List<Seat> seats = seatRepository.findByCarriage(carriage);
                seatRepository.deleteAll(seats);
                carriageRepository.delete(carriage);
            }
        }

        // Update nomor urut carriage agar konsisten 1,2,3,...
        List<Carriage> remainingCarriages = carriageRepository.findByTrain(train)
                .stream()
                .sorted(Comparator.comparingInt(Carriage::getCarriageNumber))
                .collect(Collectors.toList());

        for (int i = 0; i < remainingCarriages.size(); i++) {
            Carriage carriage = remainingCarriages.get(i);
            int expectedNumber = i + 1;
            if (carriage.getCarriageNumber() != expectedNumber) {
                carriage.setCarriageNumber(expectedNumber);
                carriageRepository.save(carriage);
                logger.info("Re-numbering carriage to {}", expectedNumber);
            }
        }
    }
}
