package com.uas.locobooking.services.train;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.train.TrainDto;
import com.uas.locobooking.models.Train;
import com.uas.locobooking.repositories.TrainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainRepository trainRepository;

    @Override
    public TrainDto createTrain(TrainDto trainDto) {
        Train train = new Train();
        train.setTrainName(trainDto.getTrainName());
        train.setTrainCode(trainDto.getTrainCode());
        train.setTotalSeats(trainDto.getTotalSeats());

        return TrainDto.fromEntity(trainRepository.save(train));
    }

    @Override
    public TrainDto getTrainById(String id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));
        return TrainDto.fromEntity(train);
    }

    @Override
    public List<TrainDto> getAllTrains() {
        return trainRepository.findAll()
                .stream()
                .map(TrainDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TrainDto updateTrain(String id, TrainDto trainDto) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not Found"));
        train.setTrainName(trainDto.getTrainName());
        train.setTrainCode(trainDto.getTrainCode());
        train.setTotalSeats(trainDto.getTotalSeats());

        return TrainDto.fromEntity(trainRepository.save(train));
    }

    @Override
    public void deleteTrain(String id) {
        if (!trainRepository.existsById(id)) {
            throw new RuntimeException("Train not found");
        }
        trainRepository.deleteById(id);
    }
}