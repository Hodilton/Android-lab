package com.example.lab_9.bottom_nav.chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_9.adapters.ChatAdapter;
import com.example.lab_9.bottom_nav.BaseFragment;
import com.example.lab_9.databinding.FragmentChatsBinding;
import com.example.lab_9.models.Chat;
import com.example.lab_9.view_models.UserViewModel;

import java.util.ArrayList;

public class ChatsFragment extends BaseFragment<FragmentChatsBinding> {
    private ChatAdapter chatAdapter;
    private UserViewModel userViewModel;

    @Override
    protected FragmentChatsBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChatsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = UserViewModel.getInstance(requireContext());

        setupAdapter();
        observeData();

        userViewModel.loadUserChats();
    }

    private void setupAdapter() {
        chatAdapter = new ChatAdapter(requireContext(), new ArrayList<>());
        chatAdapter.setOnChatClickListener(this::openChatActivity);
        chatAdapter.setOnChatLongClickListener(this::showDeleteChatDialog);
        binding.chatsLv.setAdapter(chatAdapter);
    }

    private void observeData() {
        userViewModel.getChats().observe(getViewLifecycleOwner(), chats -> {
            if (chats != null) {
                chatAdapter.updateList(chats);
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить чаты", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openChatActivity(Chat chat) {
        if (chat == null || chat.getOtherUser() == null) {
            Toast.makeText(requireContext(), "Invalid chat data", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void showDeleteChatDialog(Chat chat) {
        new ChatActionDialog.Builder(requireContext())
                .setOnConfirmAction(() -> deleteChatWithUser(chat))
                .setOnCancelAction(() -> { })
                .setOnDeleteChatAction(() -> { })
                .build()
                .show();
    }

    private void deleteChatWithUser(Chat chat) {
        setLoadingState(true);
        userViewModel.deleteChat(chat, () -> {
            requireActivity().runOnUiThread(() -> {
                setLoadingState(false);
                Toast.makeText(requireContext(), "Chat deleted", Toast.LENGTH_SHORT).show();
            });
        });
    }
}