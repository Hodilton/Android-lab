package com.example.lab_9.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lab_9.database.entities.StreetEntity;
import com.example.lab_9.database.entities.StreetInfoEntity;

import java.util.List;

@Dao
public interface StreetDao {
    @Insert
    void insert(StreetEntity street);

    @Query("SELECT * FROM streets")
    LiveData<List<StreetEntity>> getAllStreets();

    @Query("SELECT * FROM street_info WHERE streetId = :streetId")
    LiveData<StreetInfoEntity> getInfoByStreetId(int streetId);

    @Query("DELETE FROM streets WHERE id = :streetId")
    void deleteById(int streetId);
}