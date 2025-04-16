package com.uas.locobooking.services.train;

import com.uas.locobooking.dto.train.CreateCarriageBatchRequest;

public interface CarriageService {
    void createCarriagesWithSeats(CreateCarriageBatchRequest request);
}
