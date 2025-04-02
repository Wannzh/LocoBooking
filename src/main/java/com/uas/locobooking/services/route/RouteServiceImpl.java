package com.uas.locobooking.services.route;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
        // Konversi StationDto menjadi Station menggunakan repository untuk mencari Station yang sesuai
        Station departureStation = stationRepository.findById(routeDto.getDepartureStation().getId())
                .orElseThrow(() -> new RuntimeException("Departure Station not found"));
        Station arrivalStation = stationRepository.findById(routeDto.getArrivalStation().getId())
                .orElseThrow(() -> new RuntimeException("Arrival Station not found"));

        Route route = Route.builder()
                .departureStation(departureStation)  // Menggunakan Station yang telah ditemukan
                .arrivalStation(arrivalStation)      // Menggunakan Station yang telah ditemukan
                .price(routeDto.getPrice())
                .build();

        return RouteDto.fromEntity(routeRepository.save(route));
    }

    @Override
    public RouteDto getRouteById(String id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        return RouteDto.fromEntity(route);
    }

    @Override
    public List<RouteDto> getAllRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(RouteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RouteDto updateRoute(String id, RouteDto routeDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        // Konversi StationDto menjadi Station menggunakan repository untuk mencari Station yang sesuai
        Station departureStation = stationRepository.findById(routeDto.getDepartureStation().getId())
                .orElseThrow(() -> new RuntimeException("Departure Station not found"));
        Station arrivalStation = stationRepository.findById(routeDto.getArrivalStation().getId())
                .orElseThrow(() -> new RuntimeException("Arrival Station not found"));

        route = route.toBuilder()
                .departureStation(departureStation)  // Menggunakan Station yang telah ditemukan
                .arrivalStation(arrivalStation)      // Menggunakan Station yang telah ditemukan
                .price(routeDto.getPrice())
                .build();

        return RouteDto.fromEntity(routeRepository.save(route));
    }

    @Override
    public void deleteRoute(String id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Route not found");
        }
        routeRepository.deleteById(id);
    }
}
