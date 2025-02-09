package com.example.lab_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.lab_2.adapter.StreetAdapter;
import com.example.lab_2.model.Street;

public class StreetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street);

        ListView listView = findViewById(R.id.listViewStreet);
        Button btnGoToMain = findViewById(R.id.btnBackStreet);

        String[] streetNames = getResources().getStringArray(R.array.streets);

        List<Street> streets = new ArrayList<>();
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[0]), 4300, R.drawable.street1));
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[1]), 1200, R.drawable.street2));
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[2]), 1600, R.drawable.street3));
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[3]), 15600, R.drawable.street4));

        StreetAdapter adapter = new StreetAdapter(this, streets);
        listView.setAdapter(adapter);

        btnGoToMain.setOnClickListener(v -> {
            Intent backIntent = new Intent(StreetActivity.this, MainActivity.class);
            backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backIntent);
        });
    }
}