package com.uas.locobooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Station;

public interface StationRepository extends JpaRepository<Station, String>{

}
