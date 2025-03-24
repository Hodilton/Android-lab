package com.example.lab_9.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_9.database.entities.StreetEntity;
import com.example.lab_9.database.entities.StreetInfoEntity;
import com.example.lab_9.repository.StreetRepository;

import java.util.List;
import java.util.concurrent.Executors;

public class StreetViewModel extends AndroidViewModel {
    private final StreetRepository repository;
    private final MutableLiveData<List<StreetEntity>> streetListLiveData = new MutableLiveData<>();
    private final MutableLiveData<StreetInfoEntity> streetInfoLiveData = new MutableLiveData<>();

    public StreetViewModel(@NonNull Application application) {
        super(application);
        repository = new StreetRepository(application);
        loadStreets();
    }

    public LiveData<List<StreetEntity>> getStreets() { return streetListLiveData; }
    public LiveData<StreetInfoEntity> getStreetInfo() { return streetInfoLiveData; }

    public void loadStreets() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<StreetEntity> streets = repository.getAllStreets();
            streetListLiveData.postValue(streets);
        });
    }

    public void loadStreetInfo(int streetId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            StreetInfoEntity info = repository.getStreetInfo(streetId);
            streetInfoLiveData.postValue(info);
        });
    }

    public void addStreet(StreetEntity street) {
        repository.insertStreet(street);
        loadStreets();
    }

    public void addStreetInfo(StreetInfoEntity info) {
        repository.insertStreetInfo(info);
    }
}