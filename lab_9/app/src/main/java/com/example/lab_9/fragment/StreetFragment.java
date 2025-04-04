package com.example.lab_9.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab_9.R;
import com.example.lab_9.adapter.StreetAdapter;
import com.example.lab_9.database.entities.StreetInfoEntity;
import com.example.lab_9.model.Street;
import com.example.lab_9.database.entities.StreetEntity;
import com.example.lab_9.view_model.StreetViewModel;

import java.util.ArrayList;
import java.util.List;

public class StreetFragment extends Fragment {

    private StreetAdapter adapter;
    private final List<Street> streets = new ArrayList<>();
    private StreetViewModel viewModel;
    private ListView listView;
    private Button addButton;
    private ProgressBar progressBar;

    public StreetFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_street, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(StreetViewModel.class);

        initViews(view);
        observeData();
        setListeners();
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listViewStreet);
        addButton = view.findViewById(R.id.buttonAddStreet);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new StreetAdapter(requireContext(), streets);
        listView.setAdapter(adapter);
    }

    private void observeData() {
        viewModel.getStreets().observe(getViewLifecycleOwner(), streetEntities -> {
            if (streetEntities != null) {
                streets.clear();
                for (StreetEntity streetEntity : streetEntities) {
                    streets.add(new Street(streetEntity.name, streetEntity.city, streetEntity.id));
                }
                adapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
            } else {
//                progressBar.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setListeners() {
        addButton.setOnClickListener(v -> showAddStreetDialog());

        listView.setOnItemClickListener((parent, v, position, id) -> {
            Street selectedStreet = streets.get(position);

            viewModel.setSelectedStreetId(selectedStreet.getId());

            StreetInfoFragment streetInfoFragment = new StreetInfoFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, streetInfoFragment)
                    .addToBackStack(null)
                    .commit();
        });

        listView.setOnItemLongClickListener((parent, v, position, id) -> {
            confirmDeleteStreet(position);
            return true;
        });
    }

    private void showAddStreetDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_street, null);
        EditText editTextName = dialogView.findViewById(R.id.editTextStreetName);
        EditText editTextCity = dialogView.findViewById(R.id.editTextStreetCity);

        EditText editTextLength = dialogView.findViewById(R.id.editTextStreetLength);
        EditText editTextYearFounded = dialogView.findViewById(R.id.editTextStreetYearFounded);
        EditText editTextDescription = dialogView.findViewById(R.id.editTextStreetDescription);

        new AlertDialog.Builder(requireContext())
                .setTitle("Добавить улицу")
                .setView(dialogView)
                .setPositiveButton("Добавить", (dialog, which) -> {
                    addStreet(editTextName.getText().toString(), editTextCity.getText().toString(),
                            editTextLength.getText().toString(), editTextYearFounded.getText().toString(),
                            editTextDescription.getText().toString());
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void addStreet(String name, String city, String length, String yearFounded, String description) {
        if (name.isEmpty() || city.isEmpty() || length.isEmpty() || yearFounded.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            StreetEntity streetEntity = new StreetEntity(name, city);
//            viewModel.addStreet(streetEntity);

            int streetId = streetEntity.id;
            int streetLength = Integer.parseInt(length);
            int foundedYear = Integer.parseInt(yearFounded);

            StreetInfoEntity streetInfo = new StreetInfoEntity(0, streetLength, foundedYear, description);
//            StreetInfoEntity streetInfoEntity = new StreetInfoEntity(streetId, streetLength, foundedYear, description);
            viewModel.addStreet(streetEntity, streetInfo);

//            adapter.notifyDataSetChanged();
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Некорректное значение длины", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteStreet(int position) {
        Street selectedStreet = streets.get(position);

        new AlertDialog.Builder(requireContext())
                .setTitle("Удаление")
                .setMessage("Удалить улицу: " + selectedStreet.getName() + "?")
                .setPositiveButton("Да", (dialog, which) -> deleteStreet(position))
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void deleteStreet(int position) {
        Street selectedStreet = streets.get(position);
        viewModel.deleteStreet(selectedStreet.getId());
//        adapter.notifyDataSetChanged();
        Toast.makeText(requireContext(), "Улица удалена", Toast.LENGTH_SHORT).show();
    }
}