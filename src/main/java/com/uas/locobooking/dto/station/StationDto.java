package com.uas.locobooking.dto.station;

import com.uas.locobooking.models.Station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDto {
    private String stationCode;
    private String stationName;
    private String address;

    public static StationDto fromEntity(Station station) {
        if (station == null) return null;
        return StationDto.builder()
                .stationCode(station.getStationCode())
                .stationName(station.getStationName())
                .address(station.getAddress())
                .build();
    }

    public Station toEntity() {
        return Station.builder()
                .stationCode(stationCode)
                .stationName(stationName)
                .address(address)
                .build();
    }
}