package com.uas.locobooking.services.station;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
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
                station.setStationCode(stationDto.getStationCode());
                station.setStationName(stationDto.getStationName());
                station.setAddress(stationDto.getAddress());

                return StationDto.fromEntity(stationRepository.save(station));
        }

        @Override
        public GenericResponse<PageResponse<StationDto>> getAllStations(int page, int size, String keyword,
                        String sortBy, String direction) {
                Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending()
                                : Sort.by(sortBy).ascending();

                Pageable pageable = PageRequest.of(page, size, sort);
                Page<Station> stationPage;

                if (keyword != null && !keyword.isEmpty()) {
                        stationPage = stationRepository
                                        .findByStationCodeContainingIgnoreCaseOrStationNameContainingIgnoreCase(
                                                        keyword, keyword, pageable);
                } else {
                        stationPage = stationRepository.findAll(pageable);
                }

                List<StationDto> items = stationPage.getContent()
                                .stream()
                                .map(StationDto::fromEntity)
                                .collect(Collectors.toList());

                PageResponse<StationDto> pageResponse = PageResponse.<StationDto>builder()
                                .page(stationPage.getNumber())
                                .size(stationPage.getSize())
                                .totalPage(stationPage.getTotalPages())
                                .totalItem(stationPage.getTotalElements())
                                .items(items)
                                .build();

                return GenericResponse.<PageResponse<StationDto>>builder()
                                .success(true)
                                .message("Stations fetched successfully")
                                .data(pageResponse)
                                .build();
        }

        @Override
        public GenericResponse<StationDto> getStationById(String stationCode) {
                Station station = stationRepository.findByStationCode(stationCode)
                                .orElseThrow(() -> new RuntimeException("Station not found"));

                return GenericResponse.<StationDto>builder()
                                .success(true)
                                .message("Station fetched successfully")
                                .data(StationDto.fromEntity(station))
                                .build();
        }

        @Override
        public StationDto updateStation(String stationCode, StationDto stationDto) {
                Station station = stationRepository.findByStationCode(stationCode)
                                .orElseThrow(() -> new RuntimeException("Station not found"));
                station.setStationName(stationDto.getStationName());
                station.setAddress(stationDto.getAddress());
                return StationDto.fromEntity(stationRepository.save(station));
        }

        @Override
        public void deleteStation(String stationCode) {
                Station station = stationRepository.findByStationCode(stationCode)
                                .orElseThrow(() -> new RuntimeException("Station not found"));
                stationRepository.delete(station);
        }
}