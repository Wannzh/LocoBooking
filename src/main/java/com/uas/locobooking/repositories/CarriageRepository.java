package com.uas.locobooking.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Train;


public interface CarriageRepository extends JpaRepository<Carriage, String>{
    List<Carriage> findByTrain(Train train);

    Optional<Carriage> findByCarriageNumber(Integer carriage);
}
