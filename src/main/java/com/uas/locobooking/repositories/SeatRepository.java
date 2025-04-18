package com.uas.locobooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Seat;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByCarriage(Carriage carriage);
}
