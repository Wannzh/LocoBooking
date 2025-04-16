package com.uas.locobooking.services.train;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.uas.locobooking.dto.train.CreateCarriageBatchRequest;
import com.uas.locobooking.dto.train.CreateCarriageRequest;
import com.uas.locobooking.enums.CarriageType;
import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Seat;
import com.uas.locobooking.models.Train;
import com.uas.locobooking.repositories.CarriageRepository;
import com.uas.locobooking.repositories.SeatRepository;
import com.uas.locobooking.repositories.TrainRepository;

@Service
public class CarriageServiceImpl implements CarriageService {

    @Autowired
    private CarriageRepository carriageRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Override
    public void createCarriagesWithSeats(CreateCarriageBatchRequest request) {
        Train train = trainRepository.findByTrainCode(request.getTrainCode())
                .orElseThrow(() -> new RuntimeException("Train not found with code: " + request.getTrainCode()));

        int totalSeats = train.getTotalSeats();
        int maxCarriages = totalSeats / 20;

        List<Carriage> existingCarriages = carriageRepository.findByTrain(train);
        int currentCarriageCount = existingCarriages.size();

        int incomingCarriages = request.getCarriages().size();
        if (currentCarriageCount + incomingCarriages > maxCarriages) {
            throw new IllegalArgumentException("Cannot add more carriages. This train only supports up to "
                    + maxCarriages + " carriages (" + totalSeats + " seats).");
        }

        for (CreateCarriageRequest c : request.getCarriages()) {
            boolean exists = existingCarriages.stream()
                    .anyMatch(existing -> existing.getCarriageNumber() == c.getCarriageNumber());
            if (exists) {
                throw new IllegalArgumentException(
                        "Carriage number " + c.getCarriageNumber() + " already exists for this train.");
            }
        }

        for (CreateCarriageRequest c : request.getCarriages()) {
            CarriageType type = c.getCarriageType();
            if (type == null) {
                throw new IllegalArgumentException(
                        "Carriage type cannot be null. Valid types: EKSEKUTIF, BISNIS, PREMIUM.");
            }

            Double price = type.getPrice();

            Carriage carriage = Carriage.builder()
                    .carriageNumber(c.getCarriageNumber())
                    .carriageType(type)
                    .price(price)
                    .train(train)
                    .build();

            carriage = carriageRepository.save(carriage);

            List<Seat> seats = new ArrayList<>();
            char[] rows = { 'A', 'B', 'C', 'D', 'E' };
            int totalRows = 4;

            for (int i = 1; i <= totalRows; i++) {
                for (char row : rows) {
                    String seatNumber = row + String.valueOf(i);
                    Seat seat = Seat.builder()
                            .seatNumber(seatNumber)
                            .carriage(carriage)
                            .isAvailable(true)
                            .build();
                    seats.add(seat);
                }
            }
            seatRepository.saveAll(seats);
        }
    }
}