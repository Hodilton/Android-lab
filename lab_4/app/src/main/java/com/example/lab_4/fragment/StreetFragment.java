package com.example.lab_4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

import com.example.lab_4.R;
import com.example.lab_4.adapter.StreetAdapter;
import com.example.lab_4.model.Street;

public class StreetFragment extends Fragment {

    public StreetFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_street, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listViewStreet);

        String[] streetNames = getResources().getStringArray(R.array.streets);

        List<Street> streets = new ArrayList<>();
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[0]), 4300, R.drawable.street1));
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[1]), 1200, R.drawable.street1));
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[2]), 1600, R.drawable.street1));
        streets.add(new Street(String.format(getString(R.string.street_name), streetNames[3]), 15600, R.drawable.street1));

        StreetAdapter adapter = new StreetAdapter(requireContext(), streets);
        listView.setAdapter(adapter);
    }
}