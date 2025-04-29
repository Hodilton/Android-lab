package com.example.lab_9.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    public int id;
    public String username;
    public String email;

    public UserEntity(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}