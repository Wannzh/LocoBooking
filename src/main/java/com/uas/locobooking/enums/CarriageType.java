package com.uas.locobooking.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CarriageType {
    EKSEKUTIF,
    BISNIS,
    PREMIUM;

    // Untuk parsing dari JSON
    @JsonCreator
    public static CarriageType fromString(String value) {
        return CarriageType.valueOf(value.toUpperCase());
    }

    // Untuk serialisasi ke JSON (opsional)
    @JsonValue
    public String toJson() {
        return this.name();
    }

    public double getPrice() {
        switch (this) {
            case EKSEKUTIF:
                return 250000; // Harga untuk gerbong eksekutif
            case BISNIS:
                return 175000; // Harga untuk gerbong bisnis
            case PREMIUM:
                return 125000; // Harga untuk gerbong premium
            default:
                throw new IllegalArgumentException("Unknown carriage type: " + this);
        }
    }
}
