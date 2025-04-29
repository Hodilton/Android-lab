package com.example.lab_9.view_models;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_9.data.repositories.ChatRepository;
import com.example.lab_9.data.repositories.UserRepository;
import com.example.lab_9.models.Chat;
import com.example.lab_9.models.User;

import java.util.List;

public class UserViewModel {
    private static UserViewModel instance;

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<List<Chat>> chats = new MutableLiveData<>();

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    private UserViewModel(Context context) {
        userRepository = UserRepository.getInstance(context.getApplicationContext());
        chatRepository = ChatRepository.getInstance(context.getApplicationContext());
    }

    public static synchronized UserViewModel getInstance(Context context) {
        if (instance == null) {
            instance = new UserViewModel(context);
        }
        return instance;
    }

    public LiveData<User> getCurrentUser() { return currentUser; }
    public LiveData<List<User>> getUsers() { return users; }
    public LiveData<List<Chat>> getChats() { return chats; }

    public void setCurrentUser(User user) {
        currentUser.postValue(user);
        if (user != null) {
            userRepository.insertUser(user, () -> {});
        }
    }

    public void loadUsersExcludeCurrent() {
        if (currentUser.getValue() != null) {
            userRepository.getAllUsersExclude(currentUser.getValue().getId(),
                    users::postValue);
        }
    }

    public void loadUserChats() {
        if (currentUser.getValue() != null) {
            chatRepository.getUserChats(currentUser.getValue().getId(),
                    chats::postValue);
        }
    }

    public void startChatWithUser(User otherUser, ChatRepository.ChatCallback callback) {
        if (currentUser.getValue() != null) {
            chatRepository.startChat(currentUser.getValue().getId(),
                    otherUser.getId(),
                    callback);
        }
    }

    public void deleteChat(Chat chat, ChatRepository.BasicCallback callback) {
        chatRepository.deleteChat(chat.getId(), callback);
    }

    public void logout() {
        currentUser.postValue(null);
    }
}