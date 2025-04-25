package com.uas.locobooking.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uas.locobooking.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    Optional<Schedule> findByScheduleCode(String scheduleCode);

    boolean existsByScheduleCode(String scheduleCode);

    void deleteByScheduleCode(String scheduleCode);

    Page<Schedule> findByScheduleCodeContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("""
                SELECT s FROM Schedule s
                JOIN s.route r
                JOIN r.departureStation ds
                WHERE
                (LOWER(ds.stationName) LIKE LOWER(CONCAT('%', :stationKeyword, '%'))
                 OR LOWER(ds.stationCode) LIKE LOWER(CONCAT('%', :stationKeyword, '%')))
                AND (:departureDate IS NULL OR FUNCTION('DATE', s.departureTime) = :departureDate)
            """)
    Page<Schedule> findByDepartureStationAndDate(
            @Param("stationKeyword") String stationKeyword,
            @Param("departureDate") LocalDate departureDate,
            Pageable pageable);

    @Query("""
                SELECT s FROM Schedule s
                JOIN s.route r
                JOIN r.arrivalStation ars
                WHERE
                (LOWER(ars.stationName) LIKE LOWER(CONCAT('%', :stationKeyword, '%'))
                 OR LOWER(ars.stationCode) LIKE LOWER(CONCAT('%', :stationKeyword, '%')))
                AND (:departureDate IS NULL OR FUNCTION('DATE', s.departureTime) = :departureDate)
            """)
    Page<Schedule> findByArrivalStationAndDate(
            @Param("stationKeyword") String stationKeyword,
            @Param("departureDate") LocalDate departureDate,
            Pageable pageable);

    @Query("""
                SELECT s FROM Schedule s
                WHERE FUNCTION('DATE', s.departureTime) = :departureDate
            """)
    Page<Schedule> findByDateOnly(
            @Param("departureDate") LocalDate departureDate,
            Pageable pageable);

}
