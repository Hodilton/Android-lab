package com.example.lab_6.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_6.utils.PreferencesKeys;
import com.example.lab_6.utils.SharedPreferencesHelper;

public class SettingsViewModel extends AndroidViewModel {
    private SharedPreferencesHelper sharedPreferencesHelper;

    private MutableLiveData<String> username;
    private MutableLiveData<Boolean> networkPermission;
    private MutableLiveData<Boolean> notificationsEnabled;

    public SettingsViewModel(Application application) {
        super(application);

        sharedPreferencesHelper = new SharedPreferencesHelper(application);
        username = new MutableLiveData<>();
        networkPermission = new MutableLiveData<>();
        notificationsEnabled = new MutableLiveData<>();

        this.loadSettings();
    }

    public LiveData<String> getUsername() { return username; }
    public LiveData<Boolean> getNetworkPermission() { return networkPermission; }
    public LiveData<Boolean> getNotificationsEnabled() { return notificationsEnabled; }

    public void setUsername(String username) {
        this.username.setValue(username);
        sharedPreferencesHelper.saveString(PreferencesKeys.KEY_USERNAME, username);
    }
    public void setNetworkPermission(Boolean networkPermission) {
        this.networkPermission.setValue(networkPermission);
        sharedPreferencesHelper.saveBoolean(PreferencesKeys.KEY_NETWORK_PERMISSION, networkPermission);
    }
    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled.setValue(notificationsEnabled);
        sharedPreferencesHelper.saveBoolean(PreferencesKeys.KEY_NOTIFICATIONS_ENABLED, notificationsEnabled);
    }

    private void loadSettings() {
        username.setValue(sharedPreferencesHelper.getString(PreferencesKeys.KEY_USERNAME, ""));
        networkPermission.setValue(sharedPreferencesHelper.getBoolean(PreferencesKeys.KEY_NETWORK_PERMISSION, true));
        notificationsEnabled.setValue(sharedPreferencesHelper.getBoolean(PreferencesKeys.KEY_NOTIFICATIONS_ENABLED, false));
    }
}