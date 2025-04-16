package com.uas.locobooking.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Station;

public interface StationRepository extends JpaRepository<Station, String>{
    Optional<Station> findByStationCode(String stationCode);
    Page<Station> findByStationCodeContainingIgnoreCaseOrStationNameContainingIgnoreCase(String stationCodeKeyword,String stationNameKeyword,Pageable pageable);
}
