package com.uas.locobooking.services.station;

import java.util.List;

import com.uas.locobooking.dto.station.StationDto;

public interface StationService {
    StationDto createStation(StationDto stationDto);
    StationDto getStationById(String id);
    List<StationDto> getAllStations();
    StationDto updateStation(String id, StationDto stationDto);
    void deleteStation(String id);
}
