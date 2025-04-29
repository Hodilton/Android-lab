package com.example.lab_9;

import android.content.Intent;

import com.example.lab_9.databinding.ActivityLoginBinding;
import com.example.lab_9.models.User;
import com.example.lab_9.view_models.UserViewModel;

public class LoginActivity extends AuthBaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void getLayoutResource() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void initViews() {
        emailEt = binding.emailEt;
        passwordEt = binding.passwordEt;
        progressBar = binding.progressBar;
        actionBtn = binding.loginBtn;
        backBtn = binding.goToRegisterActivityTv;
    }

    protected void setupListeners() {
        binding.loginBtn.setOnClickListener(v -> performAuthAction());
        binding.goToRegisterActivityTv.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    protected void performAuthAction() {
        String email = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();

        if (!validateEmail(email) || !validatePassword(password)) {
            return;
        }

        User user = new User(1, "Default User", email);
        UserViewModel.getInstance(this).setCurrentUser(user);

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}