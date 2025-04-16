package com.uas.locobooking.services.station;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.station.StationDto;

public interface StationService {
    StationDto createStation(StationDto stationDto);
    GenericResponse<PageResponse<StationDto>> getAllStations(int page, int size, String keyword,
    String sortBy, String direction);
    GenericResponse<StationDto> getStationById(String stationCode);
    StationDto updateStation(String id, StationDto stationDto);
    void deleteStation(String id);
}
