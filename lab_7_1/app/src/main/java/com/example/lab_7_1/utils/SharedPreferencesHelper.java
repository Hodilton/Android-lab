package com.example.lab_7_1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lab_7_1.model.Street;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "street_prefs";
    private static final String KEY_STREET_LIST = "street_list";
    private SharedPreferences prefs;
    private Gson gson;

    public SharedPreferencesHelper(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveStreetList(List<Street> streets) {
        String json = gson.toJson(streets);
        prefs.edit().putString(KEY_STREET_LIST, json).apply();
    }

    public List<Street> getStreetList() {
        String json = prefs.getString(KEY_STREET_LIST, null);

        if (json == null) {
            return null;
        }

        Type type = new TypeToken<List<Street>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void logSavedData() {
        String json = prefs.getString(KEY_STREET_LIST, "Нет данных");
        Log.i("SharedPreferences", "Сохраненные улицы: " + json);
    }

}