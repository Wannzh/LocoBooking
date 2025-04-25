package com.uas.locobooking.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Seat;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByCarriage(Carriage carriage);

    Optional<Seat> findBySeatNumberAndCarriage(String Seat, Carriage carriage);

     @Query("SELECT s FROM Seat s " +
           "JOIN FETCH s.carriage c " +
           "JOIN FETCH c.train t " +
           "WHERE t.trainCode = :trainCode")
    List<Seat> findAllSeatsByTrainCode(@Param("trainCode") String trainCode);
}
