package com.uas.locobooking.services.route;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.route.RouteDto;
import com.uas.locobooking.models.Route;
import com.uas.locobooking.models.Station;
import com.uas.locobooking.repositories.RouteRepository;
import com.uas.locobooking.repositories.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
        private final RouteRepository routeRepository;
        private final StationRepository stationRepository;

        @Override
        public RouteDto createRoute(RouteDto routeDto) {
                Station departureStation = stationRepository.findByStationCode(routeDto.getDepartureStationCode())
                                .orElseThrow(() -> new RuntimeException("Departure Station not found"));
                Station arrivalStation = stationRepository.findByStationCode(routeDto.getArrivalStationCode())
                                .orElseThrow(() -> new RuntimeException("Arrival Station not found"));

                Route route = Route.builder()
                                .routeCode(routeDto.getRouteCode())
                                .departureStation(departureStation)
                                .arrivalStation(arrivalStation)
                                .build();

                return RouteDto.fromEntity(routeRepository.save(route));
        }

        @Override
        public RouteDto getRouteById(String routeCode) {
                Route route = routeRepository.findByRouteCode(routeCode)
                                .orElseThrow(() -> new RuntimeException("Route not found"));
                return RouteDto.fromEntity(route);
        }

        @Override
        public RouteDto updateRoute(String routeCode, RouteDto routeDto) {
                Route route = routeRepository.findByRouteCode(routeCode)
                                .orElseThrow(() -> new RuntimeException("Route not found"));

                Station departureStation = stationRepository.findByStationCode(routeDto.getDepartureStationCode())
                                .orElseThrow(() -> new RuntimeException("Departure Station not found"));
                Station arrivalStation = stationRepository.findByStationCode(routeDto.getArrivalStationCode())
                                .orElseThrow(() -> new RuntimeException("Arrival Station not found"));

                route = route.toBuilder()
                                .departureStation(departureStation)
                                .arrivalStation(arrivalStation)
                                .build();

                return RouteDto.fromEntity(routeRepository.save(route));
        }

        @Override
        public void deleteRoute(String routeCode) {
                Route route = routeRepository.findByRouteCode(routeCode)
                                .orElseThrow(() -> new RuntimeException("Route not found"));
                routeRepository.delete(route);
        }

        @Override
        public GenericResponse<PageResponse<RouteDto>> getAllRoutes(int page, int size, String keyword, String sortBy,
                        String direction) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
                Page<Route> routePage;

                if (keyword != null && !keyword.isEmpty()) {
                        routePage = routeRepository.findByRouteCodeContainingIgnoreCase(keyword, pageable);
                } else {
                        routePage = routeRepository.findAll(pageable);
                }

                List<RouteDto> routeDtos = routePage.getContent().stream()
                                .map(RouteDto::fromEntity)
                                .collect(Collectors.toList());

                PageResponse<RouteDto> pageResponse = PageResponse.<RouteDto>builder()
                                .page(routePage.getNumber())
                                .size(routePage.getSize())
                                .totalItem(routePage.getTotalElements())
                                .totalPage(routePage.getTotalPages())
                                .items(routeDtos)
                                .build();

                return GenericResponse.<PageResponse<RouteDto>>builder()
                                .success(true)
                                .message("Success get routes")
                                .data(pageResponse)
                                .build();
        }

}
