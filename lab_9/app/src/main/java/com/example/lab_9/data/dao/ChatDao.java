package com.example.lab_9.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lab_9.data.entities.ChatEntity;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatEntity chat);

    @Query("SELECT * FROM chats")
    List<ChatEntity> getAllChats();

    @Query("DELETE FROM chats WHERE id = :chatId")
    void deleteChat(int chatId);
}