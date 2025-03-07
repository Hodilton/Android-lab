package com.example.lab_8.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "file_prefs";
    private static final String KEY_STREET_LIST = "file_list";

    private final SharedPreferences preferences;
    private final Gson gson;

    public SharedPreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveFileList(List<String> files) {
        String json = gson.toJson(files);
        preferences.edit().putString(KEY_STREET_LIST, json).apply();
    }

    public List<String> getFileList() {
        String json = preferences.getString(KEY_STREET_LIST, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}