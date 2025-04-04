package com.example.lab_9.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab_9.R;
import com.example.lab_9.database.entities.StreetInfoEntity;
import com.example.lab_9.view_model.StreetViewModel;

public class StreetInfoFragment extends Fragment {

    private StreetViewModel viewModel;
    private TextView streetNameTextView;
    private TextView streetLengthTextView;
    private TextView streetYearFoundedTextView;
    private TextView streetDescriptionTextView;

    public StreetInfoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_street_info, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(StreetViewModel.class);

        streetNameTextView = view.findViewById(R.id.textViewStreetName);
        streetLengthTextView = view.findViewById(R.id.textViewStreetLength);
        streetYearFoundedTextView = view.findViewById(R.id.textViewStreetYearFounded);
        streetDescriptionTextView = view.findViewById(R.id.textViewStreetDescription);

        viewModel.getSelectedStreet().observe(getViewLifecycleOwner(), street -> {
            if (street != null) {
                streetNameTextView.setText(street.name);
            } else {
                streetNameTextView.setText("Неизвестная улица");
            }
        });

        viewModel.getStreetInfo().observe(getViewLifecycleOwner(), info -> {
            if (info != null) {
                streetLengthTextView.setText("Длина: " + info.length + " м");
                streetYearFoundedTextView.setText("Основана в: " + info.yearFounded);
                streetDescriptionTextView.setText(info.description);
            } else {
                streetLengthTextView.setText("Длина: нет данных");
                streetYearFoundedTextView.setText("Год основания: неизвестен");
                streetDescriptionTextView.setText("Описание отсутствует");
            }
        });
    }
}