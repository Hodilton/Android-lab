package com.example.lab_9.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "chats",
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class,
                        parentColumns = "id",
                        childColumns = "otherUserId",
                        onDelete = ForeignKey.CASCADE)
        })
public class ChatEntity {
    @PrimaryKey
    public int id;
    public int otherUserId;
    public String createdAt;

    public ChatEntity(int id, int otherUserId, String createdAt) {
        this.id = id;
        this.otherUserId = otherUserId;
        this.createdAt = createdAt;
    }
}