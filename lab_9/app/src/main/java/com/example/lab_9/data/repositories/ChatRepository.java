package com.example.lab_9.data.repositories;

import android.content.Context;

import com.example.lab_9.data.AppDatabase;
import com.example.lab_9.data.dao.ChatDao;
import com.example.lab_9.data.dao.UserDao;
import com.example.lab_9.data.entities.ChatEntity;
import com.example.lab_9.data.entities.UserEntity;
import com.example.lab_9.models.Chat;
import com.example.lab_9.models.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ChatRepository {
    private static ChatRepository instance;

    private final ChatDao chatDao;
    private final UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private ChatRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        chatDao = db.chatDao();
        userDao = db.userDao();
    }

    public static synchronized ChatRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ChatRepository(context);
        }
        return instance;
    }

    public void getUserChats(int userId, ChatListCallback callback) {
        executor.execute(() -> {
            List<ChatEntity> chatEntities = chatDao.getAllChats();
            List<Chat> chats = chatEntities.stream().map(chatEntity -> {
                UserEntity userEntity = userDao.getUserById(chatEntity.otherUserId);
                User user = new User(userEntity.id, userEntity.username, userEntity.email);
                return new Chat(chatEntity.id, user, chatEntity.createdAt);
            }).collect(Collectors.toList());

            callback.onChatsLoaded(chats);
        });
    }

    public void startChat(int user1Id, int user2Id, ChatCallback callback) {
        executor.execute(() -> {
            int newChatId = (int) System.currentTimeMillis();
            ChatEntity newChat = new ChatEntity(newChatId, user2Id, "now");
            chatDao.insert(newChat);

            UserEntity userEntity = userDao.getUserById(user2Id);
            User user = new User(userEntity.id, userEntity.username, userEntity.email);
            Chat chat = new Chat(newChatId, user, "now");

            callback.onChatLoaded(chat);
        });
    }

    public void deleteChat(int chatId, BasicCallback callback) {
        executor.execute(() -> {
            chatDao.deleteChat(chatId);
            callback.onSuccess();
        });
    }

    public interface ChatListCallback {
        void onChatsLoaded(List<Chat> chats);
    }

    public interface ChatCallback {
        void onChatLoaded(Chat chat);
    }

    public interface BasicCallback {
        void onSuccess();
    }
}