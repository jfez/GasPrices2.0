package com.example.jorge.gasprices;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DAO {
    @Insert
    void insertCommunities (List <Community> communities);

    @Insert
    void insertProvinces (List <Province> provinces);

    @Insert
    void insertTowns (List<Town> towns);

    @Query("SELECT * FROM community ORDER BY name")
    Community[] GetCommunities();

    @Query("SELECT * FROM province WHERE community_id == :comID ORDER BY name")
    Province[] GetProvinces(int comID);

    @Query("SELECT * FROM town WHERE province_id == :provID ORDER BY name")
    Town[] GetTowns(int provID);

    /*@Query("SELECT id FROM town WHERE name == :targetName")
    int GetTownID (int targetName);

    @Query("SELECT code FROM GasType WHERE label == :targetLabel")
    int GetFuelID (int targetLabel);*/

}
