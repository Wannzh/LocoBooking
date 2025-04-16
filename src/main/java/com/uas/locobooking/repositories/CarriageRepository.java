package com.uas.locobooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Carriage;
import com.uas.locobooking.models.Train;


public interface CarriageRepository extends JpaRepository<Carriage, String>{
    List<Carriage> findByTrain(Train train);
}
