package com.example.lab_9.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab_9.R;
import com.example.lab_9.adapter.StreetAdapter;
import com.example.lab_9.model.Street;
import com.example.lab_9.database.entities.StreetEntity;
import com.example.lab_9.repository.StreetRepository;

import java.util.ArrayList;
import java.util.List;

public class StreetFragment extends Fragment {

    private StreetAdapter adapter;
    private List<Street> streets;
    private StreetRepository streetRepository;
    private ListView listView;
    private Button addButton;

    public StreetFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_street, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setListeners();
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listViewStreet);
        addButton = view.findViewById(R.id.buttonAddStreet);

        streetRepository = new StreetRepository(requireContext());

        loadStreets();

        adapter = new StreetAdapter(requireContext(), streets);
        listView.setAdapter(adapter);
    }

    private void loadStreets() {
        List<StreetEntity> streetEntities = streetRepository.getAllStreets();

        streets = new ArrayList<>();
        for (StreetEntity streetEntity : streetEntities) {
            streets.add(new Street(streetEntity.name, streetEntity.id));
        }
    }

    private void setListeners() {
        addButton.setOnClickListener(v -> showAddStreetDialog());

        listView.setOnItemLongClickListener((parent, v, position, id) -> {
            confirmDeleteStreet(position);
            return true;
        });
    }

    private void showAddStreetDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_street, null);
        EditText editTextName = dialogView.findViewById(R.id.editTextStreetName);
        EditText editTextLength = dialogView.findViewById(R.id.editTextStreetLength);

        new AlertDialog.Builder(requireContext())
                .setTitle("Добавить улицу")
                .setView(dialogView)
                .setPositiveButton("Добавить", (dialog, which) -> {
                    addStreet(editTextName.getText().toString(), editTextLength.getText().toString());
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void addStreet(String name, String lengthStr) {
        if (name.isEmpty() || lengthStr.isEmpty()) {
            Toast.makeText(requireContext(), "Введите название и длину", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int length = Integer.parseInt(lengthStr);
            StreetEntity streetEntity = new StreetEntity(name);
            streetRepository.insertStreet(streetEntity); // Вставляем в базу данных
            loadStreets(); // Обновляем список улиц
            adapter.notifyDataSetChanged(); // Обновляем адаптер
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
        streetRepository.deleteStreetById(selectedStreet.getId()); // Удаляем из базы данных
        streets.remove(position); // Удаляем из списка
        adapter.notifyDataSetChanged();
        Toast.makeText(requireContext(), "Улица удалена", Toast.LENGTH_SHORT).show();
    }
}