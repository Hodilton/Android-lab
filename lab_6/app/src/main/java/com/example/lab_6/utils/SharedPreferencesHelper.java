package com.example.lab_6.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lab_6.models.AppSettings;
import com.google.gson.Gson;

public class SharedPreferencesHelper {

    private static final String PREFS_NAME = "MyPrefs";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private final Gson gson;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void saveSettings(AppSettings settings) {
        String json = gson.toJson(settings);
        editor.putString(PreferencesKeys.KEY_SETTINGS, json);
        editor.apply();
    }

    public AppSettings getSettings() {
        String json = sharedPreferences.getString(PreferencesKeys.KEY_SETTINGS, null);
        if (json != null) {
            return gson.fromJson(json, AppSettings.class);
        }
        return new AppSettings("", true, false); // Значения по умолчанию
    }
}