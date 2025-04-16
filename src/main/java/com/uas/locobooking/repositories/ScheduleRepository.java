package com.uas.locobooking.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    Optional<Schedule> findByScheduleCode(String scheduleCode);

    boolean existsByScheduleCode(String scheduleCode);

    void deleteByScheduleCode(String scheduleCode);

    Page<Schedule> findByScheduleCodeContainingIgnoreCase(String keyword, Pageable pageable);
}

