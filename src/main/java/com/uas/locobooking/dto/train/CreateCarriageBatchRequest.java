package com.uas.locobooking.dto.train;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCarriageBatchRequest {
    private String trainCode;
    private List<CreateCarriageRequest> carriages;
}
