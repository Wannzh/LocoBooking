package com.uas.locobooking.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Train;

public interface TrainRepository extends JpaRepository<Train, String>{
    Optional<Train> findByTrainCode(String trainCode); // Menemukan Train berdasarkan trainCode
    boolean existsByTrainCode(String trainCode); // Mengecek apakah Train dengan trainCode ada
    void deleteByTrainCode(String trainCode); // Menghapus Train berdasarkan trainCode
    Page<Train> findByTrainCodeContainingIgnoreCaseOrTrainNameContainingIgnoreCase(String code, String name, Pageable pageable);

}
