package com.example.lab_9.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "streets")
public class StreetEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    public StreetEntity(String name) {
        this.name = name;
    }
}