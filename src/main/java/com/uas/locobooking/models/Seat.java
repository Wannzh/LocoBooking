package com.uas.locobooking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", nullable = false)
    private String id;

    @ManyToOne
    private Train train;
    
    @Column(name = "seat_number", nullable = false)
    private String seatNumber; // contoh : "A1", "A2", "B1", "B2", "E1", "E2"

    @Column(name = "seat_class", nullable = false)
    private String seatClass; // Ekonomi, Bisnis, Eksekutif

    @Column(name = "seat_available", nullable = false)
    private boolean isAvailable; // TRUE jika bisa dipesan, FALSE sudah dipesan
}
