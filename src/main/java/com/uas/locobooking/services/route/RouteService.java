package com.uas.locobooking.services.route;

import java.util.List;

import com.uas.locobooking.dto.route.RouteDto;

public interface RouteService {
    RouteDto createRoute(RouteDto routeDto);
    RouteDto getRouteById(String id);
    List<RouteDto> getAllRoutes();
    RouteDto updateRoute(String id, RouteDto routeDto);
    void deleteRoute(String id);
}
