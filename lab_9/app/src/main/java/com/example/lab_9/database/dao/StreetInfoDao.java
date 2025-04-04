package com.example.lab_9.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lab_9.database.entities.StreetInfoEntity;

@Dao
public interface StreetInfoDao {
    @Insert
    void insert(StreetInfoEntity streetInfo);

    @Query("SELECT * FROM street_info WHERE streetId = :streetId")
    LiveData<StreetInfoEntity> getInfoByStreetId(int streetId);

    @Query("SELECT * FROM street_info WHERE streetId = :streetId")
    StreetInfoEntity getInfoByStreetIdSync(int streetId);
}