package com.example.lab_9.bottom_nav.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_9.bottom_nav.BaseFragment;
import com.example.lab_9.adapters.UserAdapter;
import com.example.lab_9.databinding.FragmentUsersBinding;
import com.example.lab_9.models.User;
import com.example.lab_9.view_models.UserViewModel;

import java.util.ArrayList;

public class UsersFragment extends BaseFragment<FragmentUsersBinding> {
    private UserAdapter userAdapter;
    private UserViewModel userViewModel;

    @Override
    protected FragmentUsersBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentUsersBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = UserViewModel.getInstance(requireContext());

        setupAdapter();
        observeData();

        userViewModel.loadUsersExcludeCurrent();
    }

    private void setupAdapter() {
        userAdapter = new UserAdapter(requireContext(), new ArrayList<>());
        userAdapter.setOnUserLongClickListener(this::showUserActionsDialog);
        binding.usersLv.setAdapter(userAdapter);
    }

    private void observeData() {
        userViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null) {
                userAdapter.updateList(users);
            } else {
                Toast.makeText(requireContext(), "Не удалось загрузить пользователей", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUserActionsDialog(User user) {
        new UserActionDialog.Builder(requireContext())
                .setOnConfirmAction(() -> createNewChatWithUser(user))
                .setOnCancelAction(() -> {  })
                .setOnCreateChatAction(() -> {  })
                .build()
                .show();
    }

    private void createNewChatWithUser(User otherUser) {
        setLoadingState(true);
        userViewModel.startChatWithUser(otherUser, chat -> {
            requireActivity().runOnUiThread(() -> {
                setLoadingState(false);
                Toast.makeText(requireContext(),
                        "Чат создан с " + otherUser.getUsername(),
                        Toast.LENGTH_SHORT).show();
            });
        });
    }
}