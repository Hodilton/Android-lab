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
    long insert(StreetEntity street);

    @Query("SELECT * FROM streets")
    LiveData<List<StreetEntity>> getAllStreets();

    @Query("SELECT * FROM streets WHERE id = :streetId")
    LiveData<StreetEntity> getStreetById(int streetId);

    @Query("SELECT * FROM streets")
    List<StreetEntity> getAllStreetsSync();

    @Query("DELETE FROM streets WHERE id = :streetId")
    void deleteById(int streetId);

    @Query("SELECT * FROM streets WHERE id = :streetId")
    StreetEntity getStreetByIdSync(int streetId);
}