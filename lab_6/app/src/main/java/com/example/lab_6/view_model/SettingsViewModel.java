package com.example.lab_6.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_6.models.AppSettings;
import com.example.lab_6.utils.SharedPreferencesHelper;

public class SettingsViewModel extends AndroidViewModel {
    private final SharedPreferencesHelper sharedPreferencesHelper;
    private final MutableLiveData<AppSettings> appSettings;

    public SettingsViewModel(Application application) {
        super(application);

        sharedPreferencesHelper = new SharedPreferencesHelper(application);
        appSettings = new MutableLiveData<>();

        this.loadSettings();
    }

    public LiveData<AppSettings> getAppSettings() {
        return appSettings;
    }

    public void saveSettings(AppSettings settings) {
        sharedPreferencesHelper.saveSettings(settings);
        appSettings.setValue(settings);
    }

    private void loadSettings() {
        appSettings.setValue(sharedPreferencesHelper.getSettings());
    }
}