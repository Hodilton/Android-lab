package com.example.lab_1;

import android.os.Bundle;

import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import android.widget.Button;
import android.content.Intent;

import model.User;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etAge, etSalary;
    private Button btnGoToSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        etSalary = findViewById(R.id.etSalary);
        btnGoToSecond = findViewById(R.id.btnGoToSecond);

        btnGoToSecond.setOnClickListener(v -> openSecondActivity());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openSecondActivity() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String age = etAge.getText().toString();
        String salary = etSalary.getText().toString();

        User user = new User(firstName, lastName, age, salary);

        String userJson = User.toJson(user);

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("USER_DATA", userJson);

        startActivity(intent);
    }
}