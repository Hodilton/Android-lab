package com.example.lab_9;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab_9.bottom_nav.chats.ChatsFragment;
import com.example.lab_9.bottom_nav.users.UsersFragment;
import com.example.lab_9.bottom_nav.profile.ProfileFragment;
import com.example.lab_9.view_models.UserViewModel;
import com.example.lab_9.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int DEFAULT_FRAGMENT_ID = R.id.chats;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        observeData();
        setupFragments();
        setupBottomNavigation();
    }

    private void observeData() {
        UserViewModel.getInstance(this).getCurrentUser().observe(this, user -> {
            if (user == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }

    private void setupFragments() {
        replaceFragment(new ChatsFragment());
        binding.bottomNav.setSelectedItemId(DEFAULT_FRAGMENT_ID);
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.chats) {
                replaceFragment(new ChatsFragment());
                return true;
            } else if (itemId == R.id.new_chat) {
                replaceFragment(new UsersFragment());
                return true;
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }
}

//package com.example.lab_9;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import com.example.lab_9.data.AppDatabase;
//import com.example.lab_9.data.repositories.ChatRepository;
//import com.example.lab_9.data.repositories.UserRepository;
//import com.example.lab_9.models.Chat;
//import com.example.lab_9.models.User;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "DatabaseTest";
//
//    private UserRepository userRepository;
//    private ChatRepository chatRepository;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Инициализация репозиториев
//        userRepository = UserRepository.getInstance(this);
//        chatRepository = ChatRepository.getInstance(this);
//
//        // Тестирование работы с пользователями
//        testUserOperations();
//
//        // Тестирование работы с чатами
//        testChatOperations();
//    }
//
//    private void testUserOperations() {
//        // Создаем тестового пользователя
//        User testUser = new User(1, "TestUser", "test@example.com");
//
//        // Добавляем пользователя в базу
//        userRepository.insertUser(testUser, () -> {
//            Log.e(TAG, "Пользователь успешно добавлен: " + testUser);
//
//            // Проверяем получение пользователя по ID (косвенно через чаты)
//            userRepository.getAllUsersExclude(0, users -> {
//                Log.e(TAG, "Список всех пользователей (исключая 0):");
//                for (User user : users) {
//                    Log.e(TAG, user.toString());
//                }
//
//                if (!users.isEmpty()) {
//                    Log.e(TAG, "Первый пользователь в списке: " + users.get(0));
//                } else {
//                    Log.e(TAG, "Список пользователей пуст");
//                }
//            });
//        });
//    }
//
//    private void testChatOperations() {
//        // Сначала создадим второго пользователя для чата
//        User secondUser = new User(2, "SecondUser", "second@example.com");
//        userRepository.insertUser(secondUser, () -> {
//            Log.e(TAG, "Второй пользователь добавлен для теста чатов");
//
//            // Создаем чат между пользователем 1 и пользователем 2
//            chatRepository.startChat(1, 2, chat -> {
//                Log.e(TAG, "Чат успешно создан: " + chat);
//
//                // Получаем все чаты для пользователя 1
//                chatRepository.getUserChats(1, chats -> {
//                    Log.e(TAG, "Список чатов пользователя 1:");
//                    for (Chat c : chats) {
//                        Log.e(TAG, c.toString());
//                    }
//
//                    if (!chats.isEmpty()) {
//                        // Удаляем первый чат из списка
//                        int chatIdToDelete = chats.get(0).getId();
//                        chatRepository.deleteChat(chatIdToDelete, () -> {
//                            Log.e(TAG, "Чат с ID " + chatIdToDelete + " успешно удален");
//
//                            // Проверяем, что чат действительно удален
//                            chatRepository.getUserChats(1, updatedChats -> {
//                                Log.e(TAG, "Обновленный список чатов после удаления:");
//                                for (Chat c : updatedChats) {
//                                    Log.e(TAG, c.toString());
//                                }
//
//                                if (updatedChats.isEmpty()) {
//                                    Log.e(TAG, "Тест удаления чата прошел успешно");
//                                } else {
//                                    Log.e(TAG, "Тест удаления чата: чат все еще в списке");
//                                }
//                            });
//                        });
//                    }
//                });
//            });
//        });
//    }
//}