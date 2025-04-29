package com.example.lab_9.data.repositories;

import android.content.Context;

import com.example.lab_9.data.AppDatabase;
import com.example.lab_9.data.dao.UserDao;
import com.example.lab_9.data.entities.UserEntity;
import com.example.lab_9.models.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class UserRepository {
    private static UserRepository instance;

    private final UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private UserRepository(Context context) {
        userDao = AppDatabase.getDatabase(context).userDao();
    }

    public static synchronized UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void getAllUsersExclude(int excludeId, UserListCallback callback) {
        executor.execute(() -> {
            List<UserEntity> userEntities = userDao.getAllUsersExclude(excludeId);
            List<User> users = userEntities.stream()
                    .map(entity -> new User(entity.id, entity.username, entity.email))
                    .collect(Collectors.toList());
            callback.onUsersLoaded(users);
        });
    }

    public void insertUser(User user, BasicCallback callback) {
        executor.execute(() -> {
            UserEntity entity = new UserEntity(user.getId(), user.getUsername(), user.getEmail());
            userDao.insert(entity);
            callback.onSuccess();
        });
    }

    public interface UserListCallback {
        void onUsersLoaded(List<User> users);
    }

    public interface BasicCallback {
        void onSuccess();
    }
}