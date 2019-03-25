package com.example.jorge.gasprices;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Community {
    @PrimaryKey
    public int id;

    public String name;

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public Community(){

    }
}
