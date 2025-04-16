package com.uas.locobooking.models;

import java.util.List;

import com.uas.locobooking.enums.CarriageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "carriage")
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", nullable = false)
    private String id;

    @Column(name = "carriage_number", nullable = false)
    private int carriageNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "carriage_type", nullable = false)
    private CarriageType carriageType;

    @Column(name = "price", nullable = false)
    private Double price; // 💰 Harga per tiket untuk gerbong ini

    @ManyToOne
    private Train train;

    @OneToMany(mappedBy = "carriage")
    private List<Seat> seats;
}
