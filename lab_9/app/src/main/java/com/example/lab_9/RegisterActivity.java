package com.example.lab_9;

import com.example.lab_9.databinding.ActivityRegisterBinding;
import com.example.lab_9.models.User;
import com.example.lab_9.view_models.UserViewModel;

public class RegisterActivity extends AuthBaseActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void getLayoutResource() {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void initViews() {
        emailEt = binding.emailEt;
        passwordEt = binding.passwordEt;
        usernameEt = binding.usernameEt;
        progressBar = binding.progressBar;
        actionBtn = binding.signUpBtn;
        backBtn = binding.backBtn;
    }

    @Override
    protected void setupListeners() {
        binding.backBtn.setOnClickListener(v -> finish());
        binding.signUpBtn.setOnClickListener(v -> performAuthAction());
    }

    protected void performAuthAction() {
        String username = binding.usernameEt.getText().toString().trim();
        String email = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();

        if (!validateUsername(username) ||
                !validateEmail(email) ||
                !validatePassword(password)) {
            return;
        }

        User user = new User((int) System.currentTimeMillis(), username, email);
        UserViewModel.getInstance(this).setCurrentUser(user);
    }
}