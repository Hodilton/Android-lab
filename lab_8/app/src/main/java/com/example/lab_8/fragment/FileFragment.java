package com.example.lab_8.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab_8.adapter.FileAdapter;
import com.example.lab_8.view_model.FileViewModel;
import com.example.lab_8.R;

import java.util.ArrayList;

public class FileFragment extends Fragment {
    private FileAdapter adapter;
    private FileViewModel viewModel;
    private ListView listView;
    private Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setupViewModel();
        setListeners();
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listViewFile);
        addButton = view.findViewById(R.id.buttonAddFile);
        adapter = new FileAdapter(requireContext(), new ArrayList<>());
        listView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(FileViewModel.class);

        viewModel.getFiles().observe(getViewLifecycleOwner(), files -> {
            adapter.updateList(files);
            adapter.notifyDataSetChanged();
        });
    }

    private void setListeners() {
        addButton.setOnClickListener(v -> showAddFileDialog());

        listView.setOnItemLongClickListener((parent, v, position, id) -> {
            showDeleteDialog(position);
            return true;
        });

        listView.setOnItemClickListener((parent, v, position, id) -> {
            openFileEditor(position);
        });
    }

    private void showAddFileDialog() {
        EditText input = new EditText(requireContext());
        input.setHint("Введите имя файла");

        new AlertDialog.Builder(requireContext())
                .setTitle("Добавить файл")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String fileName = input.getText().toString().trim();
                    if (!fileName.isEmpty()) {
                        viewModel.addFile(fileName);
                    } else {
                        Toast.makeText(requireContext(), "Имя файла не может быть пустым", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Удаление")
                .setMessage("Удалить файл?")
                .setPositiveButton("Да", (dialog, which) -> viewModel.removeFile(position))
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void openFileEditor(int position) {
        String fileName = viewModel.getFiles().getValue().get(position);
        viewModel.setFileName(fileName);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, new FileEditorFragment())
                .addToBackStack(null)
                .commit();
    }
}