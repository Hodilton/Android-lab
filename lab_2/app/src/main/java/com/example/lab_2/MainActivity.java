package com.example.lab_2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStreetList = findViewById(R.id.btnStreetList);
        btnStreetList.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, StreetActivity.class)));
    }
}