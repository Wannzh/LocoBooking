package com.uas.locobooking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", nullable = false)
    private String id;

    @Column(name = "route_code", nullable = false)
    private String routeCode;

    @ManyToOne
    private Station departureStation;

    @ManyToOne
    private Station arrivalStation;

}
