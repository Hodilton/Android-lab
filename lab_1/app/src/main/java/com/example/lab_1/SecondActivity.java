package com.example.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import model.User;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        TextView tvData = findViewById(R.id.tvData);
        Button btnGoToMain = findViewById(R.id.btnGoToMain);
        
        Intent intent = getIntent();
        String userJson = intent.getStringExtra("USER_DATA");
        
        User user = User.fromJson(userJson);
        
        String result = formatUserData(user);
        
        tvData.setText(result);

        btnGoToMain.setOnClickListener(v -> {
            Intent backIntent = new Intent(SecondActivity.this, MainActivity.class);
            backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backIntent);
        });
    }

    private String formatUserData(User user) {
        return  "Имя: " + user.getFirstName() + "\n" +
                "Фамилия: " + user.getLastName() + "\n" +
                "Возраст: " + user.getAge() + "\n" +
                "Зарплата: " + user.getSalary();
    }
}