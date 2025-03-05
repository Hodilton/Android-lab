package com.example.lab_7_1.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_7_1.model.Street;
import com.example.lab_7_1.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

public class StreetViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Street>> streetsLiveData = new MutableLiveData<>();
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public StreetViewModel(Application application) {
        super(application);
        sharedPreferencesHelper = new SharedPreferencesHelper(application);

        List<Street> streets = sharedPreferencesHelper.getStreetList();
        if (streets == null) {
            streets = new ArrayList<>();
        }

        streetsLiveData.setValue(streets);
    }

    public LiveData<List<Street>> getStreets() {
        return streetsLiveData;
    }

    public void addStreet(Street street) {
        List<Street> currentList = streetsLiveData.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(street);

        streetsLiveData.setValue(new ArrayList<>(currentList));
        sharedPreferencesHelper.saveStreetList(currentList);
    }

    public void removeStreet(int position) {
        List<Street> currentList = streetsLiveData.getValue();

        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.remove(position);

            streetsLiveData.setValue(currentList);
            sharedPreferencesHelper.saveStreetList(currentList);
        }
    }
}