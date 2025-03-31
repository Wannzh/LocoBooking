package com.uas.locobooking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uas.locobooking.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String>{

}
