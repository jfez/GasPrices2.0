package com.example.jorge.gasprices;

import androidx.room.Entity;

@Entity

public enum GasType {
    G95 ("Gasoline 95",15),
    G98 ("Gasoline 98", 3),
    GOA ("Diesel", 4),
    NGO ("Premium Diesel", 5),
    GLP ("Liquefied Petroleum Gas",17);



    private String label;
    private int code;

    public int getCode() {
        return code;
    }

    GasType(String toString, int value) {
        label = toString;
        code = value;
    }

    @Override
    public String toString() {
        return label;
    }

}
