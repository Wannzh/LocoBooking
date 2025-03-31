package com.uas.locobooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Train;

public interface TrainRepository extends JpaRepository<Train, String>{

}
