package com.example.lab_9.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.lab_9.data.entities.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity user);

    @Query("SELECT * FROM users WHERE id != :excludeId")
    List<UserEntity> getAllUsersExclude(int excludeId);

    @Query("SELECT * FROM users WHERE id = :userId")
    UserEntity getUserById(int userId);
}