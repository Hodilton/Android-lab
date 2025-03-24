package com.example.lab_9.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.lab_9.database.AppDatabase;
import com.example.lab_9.database.entities.StreetEntity;
import com.example.lab_9.database.entities.StreetInfoEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StreetRepository {
    private final AppDatabase db;
    private final ExecutorService executorService;

    public StreetRepository(Context context) {
        db = AppDatabase.getDatabase(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertStreet(StreetEntity street) {
        executorService.execute(() -> db.streetDao().insert(street));
    }

    public void deleteStreetById(int streetId) {
        executorService.execute(() -> db.streetDao().deleteById(streetId));
    }

    public void insertStreetInfo(StreetInfoEntity info) {
        executorService.execute(() -> db.streetInfoDao().insert(info));
    }

    public LiveData<List<StreetEntity>> getAllStreets() {
        return db.streetDao().getAllStreets();
    }

    public LiveData<StreetInfoEntity> getStreetInfo(int streetId) {
        return db.streetInfoDao().getInfoByStreetId(streetId);
    }
}