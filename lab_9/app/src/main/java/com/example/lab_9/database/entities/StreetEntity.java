package com.example.lab_9.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "streets")
public class StreetEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String city;

    public StreetEntity(String name, String city) {
        this.name = name;
        this.city = city;
    }
}