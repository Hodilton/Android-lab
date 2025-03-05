package com.example.lab_7_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import com.example.lab_7_1.R;
import com.example.lab_7_1.adapter.StreetAdapter;
import com.example.lab_7_1.view_model.StreetViewModel;

public class StreetFragment extends Fragment {

    private StreetAdapter adapter;
    private StreetViewModel viewModel;
    private ListView listView;
    private Button addButton;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_street, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setupViewModel();
        setListeners();
        loadStreetsWithDelay();
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listViewStreet);
        addButton = view.findViewById(R.id.buttonAddStreet);
        adapter = new StreetAdapter(requireContext(), new ArrayList<>());
        progressBar = view.findViewById(R.id.progressBar);
        listView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(StreetViewModel.class);

        viewModel.getStreets().observe(getViewLifecycleOwner(), streets -> {
            adapter.updateList(streets);
            adapter.notifyDataSetChanged();
        });
    }

    private void setListeners() {
        addButton.setOnClickListener(v -> openAddStreetFragment());

        listView.setOnItemLongClickListener((parent, v, position, id) -> {
            showDeleteDialog(position);
            return true;
        });
    }

    private void loadStreetsWithDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                viewModel.getStreets().observe(getViewLifecycleOwner(), streets -> {
                    adapter.updateList(streets);
                    adapter.notifyDataSetChanged();
                });
            });
        }).start();
    }

    private void openAddStreetFragment() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, new AddStreetFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Удаление")
                .setMessage("Удалить улицу ?")
                .setPositiveButton("Да", (dialog, which) -> viewModel.removeStreet(position))
                .setNegativeButton("Отмена", null)
                .show();
    }
}