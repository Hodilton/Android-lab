package com.example.lab_6.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab_6.R;
import com.example.lab_6.view_model.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private EditText editTextUsername;
    private Switch switchNetwork;
    private CheckBox checkBoxNotifications;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        editTextUsername = rootView.findViewById(R.id.username_edit);
        switchNetwork = rootView.findViewById(R.id.network_switch);
        checkBoxNotifications = rootView.findViewById(R.id.notifications_checkbox);
        Button saveButton = rootView.findViewById(R.id.save_button);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        settingsViewModel.getUsername().observe(getViewLifecycleOwner(), username -> editTextUsername.setText(username));
        settingsViewModel.getNetworkPermission().observe(getViewLifecycleOwner(), networkPermission -> switchNetwork.setChecked(networkPermission));
        settingsViewModel.getNotificationsEnabled().observe(getViewLifecycleOwner(), notificationsEnabled -> checkBoxNotifications.setChecked(notificationsEnabled));

        saveButton.setOnClickListener(v -> {
            this.saveSettings();
            this.requireActivity().setResult(Activity.RESULT_OK);
            this.requireActivity().finish();
            Toast.makeText(getContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }

    private void saveSettings() {
        settingsViewModel.setUsername(editTextUsername.getText().toString());
        settingsViewModel.setNetworkPermission(switchNetwork.isChecked());
        settingsViewModel.setNotificationsEnabled(checkBoxNotifications.isChecked());
    }
}
