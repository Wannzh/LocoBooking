package com.uas.locobooking.services.station;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.station.StationDto;
import com.uas.locobooking.models.Station;
import com.uas.locobooking.repositories.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    @Override
    public StationDto createStation(StationDto stationDto) {
        Station station = new Station();
        station.setStationName(stationDto.getStationName());
        station.setAddress(stationDto.getAddress());

        return StationDto.fromEntity(stationRepository.save(station));
    }

    @Override
    public StationDto getStationById(String id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
        return StationDto.fromEntity(station);
    }

    @Override
    public List<StationDto> getAllStations() {
        return stationRepository.findAll()
                .stream()
                .map(StationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StationDto updateStation(String id, StationDto stationDto) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not Found"));
        station.setStationName(stationDto.getStationName());
        station.setAddress(stationDto.getAddress());

        return StationDto.fromEntity(stationRepository.save(station));
    }

    @Override
    public void deleteStation(String id) {
        if (!stationRepository.existsById(id)) {
            throw new RuntimeException("Station not found");
        }
        stationRepository.deleteById(id);
    }
}