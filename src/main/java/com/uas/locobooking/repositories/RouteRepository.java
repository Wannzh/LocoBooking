package com.uas.locobooking.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Route;

public interface RouteRepository extends JpaRepository<Route, String>{
    Optional<Route> findByRouteCode(String routeCode);
    Page<Route> findByRouteCodeContainingIgnoreCase(String routeCode, Pageable pageable);
}
