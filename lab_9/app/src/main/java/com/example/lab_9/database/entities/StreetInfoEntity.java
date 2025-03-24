package com.example.lab_9.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Index;

@Entity(
        tableName = "street_info",
        foreignKeys = @ForeignKey(
                entity = StreetEntity.class,
                parentColumns = "id",
                childColumns = "streetId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = "streetId")
)
public class StreetInfoEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int streetId;
    public int length;
    public int yearFounded;
    public String description;
    public int imageResourceId;

    public StreetInfoEntity(int streetId, int length, int yearFounded, String description, int imageResourceId) {
        this.streetId = streetId;
        this.length = length;
        this.yearFounded = yearFounded;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
}