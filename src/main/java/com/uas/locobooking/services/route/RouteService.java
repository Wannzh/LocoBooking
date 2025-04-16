package com.uas.locobooking.services.route;

import com.uas.locobooking.dto.GenericResponse;
import com.uas.locobooking.dto.PageResponse;
import com.uas.locobooking.dto.route.RouteDto;

public interface RouteService {
    RouteDto createRoute(RouteDto routeDto);
    RouteDto getRouteById(String routeCode);
    RouteDto updateRoute(String routeCode, RouteDto routeDto);
    void deleteRoute(String routeCode);
    GenericResponse<PageResponse<RouteDto>> getAllRoutes(int page, int size, String keyword, String sortBy,
                        String direction);
}
