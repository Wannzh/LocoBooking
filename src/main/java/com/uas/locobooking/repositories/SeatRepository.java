package com.uas.locobooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Seat;

public interface SeatRepository extends JpaRepository<Seat, String> {

}
