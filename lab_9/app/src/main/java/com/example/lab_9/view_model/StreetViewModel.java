package com.example.lab_9.view_model;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_9.database.entities.StreetEntity;
import com.example.lab_9.database.entities.StreetInfoEntity;
import com.example.lab_9.repository.StreetRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class StreetViewModel extends AndroidViewModel {
    private final StreetRepository repository;
    private MutableLiveData<List<StreetEntity>> streetListLiveData = new MutableLiveData<>();
    private LiveData<StreetInfoEntity> streetInfoLiveData;
    private final MutableLiveData<Integer> selectedStreetId = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public StreetViewModel(@NonNull Application application) {
        super(application);
        repository = new StreetRepository(application);
        loadStreets();
    }

    public LiveData<List<StreetEntity>> getStreets() { return streetListLiveData; }
    public LiveData<StreetInfoEntity> getStreetInfo() { return streetInfoLiveData; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    public void loadStreets() {
        isLoading.postValue(true);
        executor.execute(() -> {
            List<StreetEntity> streetEntities = repository.getAllStreetsSync();

            streetListLiveData.postValue(streetEntities);
            isLoading.postValue(false);
        });
    }
    public void loadStreetInfo(int streetId) { streetInfoLiveData = repository.getStreetInfo(streetId); }

    public void addStreet(StreetEntity street) {
        executor.execute(() -> {
            repository.insertStreet(street);

            List<StreetEntity> updatedStreets = repository.getAllStreetsSync();
            streetListLiveData.postValue(updatedStreets);
//            loadStreets();
        });
    }

    public void addStreetInfo(StreetInfoEntity info) {
        executor.execute(() -> {
            repository.insertStreetInfo(info);

            List<StreetEntity> updatedStreets = repository.getAllStreetsSync();
            streetListLiveData.postValue(updatedStreets);
//            loadStreets();
        });
    }

    public void deleteStreet(int streetId) {
        executor.execute(() -> {
            repository.deleteStreetById(streetId);
            loadStreets();
        });
    }
}