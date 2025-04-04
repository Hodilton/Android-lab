package com.example.lab_9.view_model;

import android.app.Application;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
    private final MutableLiveData<StreetInfoEntity> streetInfoLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedStreetId = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<StreetEntity> selectedStreet = new MutableLiveData<>();
    private final MutableLiveData<StreetInfoEntity> streetInfo = new MutableLiveData<>();

    public StreetViewModel(@NonNull Application application) {
        super(application);
        repository = new StreetRepository(application);
        loadStreets();
    }

    public LiveData<List<StreetEntity>> getStreets() { return streetListLiveData; }
    public LiveData<StreetInfoEntity> getStreetInfo() { return streetInfoLiveData; }
    public LiveData<StreetEntity> getSelectedStreet() { return selectedStreet; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    public void loadStreets() {
        isLoading.postValue(true);
        executor.execute(() -> {
            List<StreetEntity> streetEntities = repository.getAllStreetsSync();

            streetListLiveData.postValue(streetEntities);
            isLoading.postValue(false);
        });
    }

    public void loadStreetInfo(int streetId) {
        LiveData<StreetInfoEntity> info = repository.getStreetInfo(streetId);
        info.observeForever(new Observer<StreetInfoEntity>() {
            @Override
            public void onChanged(StreetInfoEntity streetInfo) {
                streetInfoLiveData.postValue(streetInfo);
                info.removeObserver(this);
            }
        });
    }

    public void setSelectedStreetId(int streetId) {
        selectedStreetId.postValue(streetId);
    }

    public void addStreet(StreetEntity street, StreetInfoEntity info) {
//        executor.execute(() -> {
//            repository.insertStreet(street);
//
//            List<StreetEntity> currentList = streetListLiveData.getValue();
//
//            if (currentList != null) {
//                // Добавляем новую улицу в текущий список
//                currentList.add(street);
//                // Обновляем LiveData с новым списком
//                streetListLiveData.postValue(currentList);
//            } else {
//                // Если список еще не загружен, то загружаем его заново
//                List<StreetEntity> updatedStreets = repository.getAllStreetsSync();
//                streetListLiveData.postValue(updatedStreets);
//            }
//        });
        isLoading.postValue(true);
        executor.execute(() -> {
            // Вставляем улицу и получаем её ID
            long streetId = repository.insertStreet(street);

            // Устанавливаем ID для информации
            info.streetId = (int) streetId;

            // Вставляем информацию о улице
            repository.insertStreetInfo(info);

            // Обновляем список
            List<StreetEntity> updatedStreets = repository.getAllStreetsSync();
            streetListLiveData.postValue(updatedStreets);
            isLoading.postValue(false);
        });
    }

    public void addStreetInfo(StreetInfoEntity info) {
        executor.execute(() -> {
            repository.insertStreetInfo(info);
        });
    }

    public void deleteStreet(int streetId) {
//        executor.execute(() -> {
//            repository.deleteStreetById(streetId);
//
//            List<StreetEntity> currentList = streetListLiveData.getValue();
//
//            if (currentList != null) {
//                // Ищем и удаляем улицу из текущего списка
//                for (int i = 0; i < currentList.size(); i++) {
//                    if (currentList.get(i).id == streetId) {
//                        currentList.remove(i);
//                        break;
//                    }
//                }
//                // Обновляем LiveData с новым списком
//                streetListLiveData.postValue(currentList);
//            } else {
//                // Если список еще не загружен, то загружаем его заново
//                List<StreetEntity> updatedStreets = repository.getAllStreetsSync();
//                streetListLiveData.postValue(updatedStreets);
//            }
//        });
        isLoading.postValue(true);
        repository.deleteStreetById(streetId);
            executor.execute(() -> {
                List<StreetEntity> updatedStreets = repository.getAllStreetsSync();
                streetListLiveData.postValue(updatedStreets);
                isLoading.postValue(false);
            });
        }

//    public void setSelectedStreetId(int streetId) {
//        executor.execute(() -> {
//            StreetEntity street = repository.getStreetByIdSync(streetId);
//            selectedStreet.postValue(street);
//
//            StreetInfoEntity info = repository.getStreetInfoSync(streetId);
//            streetInfo.postValue(info);
//        });
//    }
}