package com.example.lab_6.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String PREFS_NAME = "MyPrefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {return sharedPreferences.getString(key, defaultValue); }
    public boolean getBoolean(String key, boolean defaultValue) { return sharedPreferences.getBoolean(key, defaultValue); }
    public int getInt(String key, int defaultValue) { return sharedPreferences.getInt(key, defaultValue); }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}