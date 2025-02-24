package com.example.lab_4.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab_4.R;
import com.example.lab_4.model.Street;
import com.example.lab_4.view_model.StreetViewModel;

public class AddStreetFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextLength;
    private Button btnSave;
    private StreetViewModel ViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_street, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextStreetName);
        editTextLength = view.findViewById(R.id.editTextStreetLength);
        btnSave = view.findViewById(R.id.buttonSaveStreet);

        ViewModel = new ViewModelProvider(requireActivity()).get(StreetViewModel.class);

        btnSave.setOnClickListener(v -> saveStreet());
    }

    private void saveStreet() {
        String name = editTextName.getText().toString().trim();
        String lengthStr = editTextLength.getText().toString().trim();

        if (name.isEmpty() || lengthStr.isEmpty()) {
            Toast.makeText(requireContext(), "Введите название и длину", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int length = Integer.parseInt(lengthStr);
            Street street = new Street(name, length, R.drawable.street1);

            ViewModel.addStreet(street);

            Toast.makeText(requireContext(), "Улица добавлена", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager().popBackStack();
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Некорректное значение длины", Toast.LENGTH_SHORT).show();
        }
    }
}