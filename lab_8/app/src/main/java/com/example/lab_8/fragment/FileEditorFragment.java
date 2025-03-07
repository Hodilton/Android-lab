package com.example.lab_8.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.lab_8.R;
import com.example.lab_8.view_model.FileViewModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEditorFragment extends Fragment {
    private FileViewModel viewModel;
    private EditText editText;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupViewModel();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                saveFileContent(s.toString());
            }
        });
    }

    private void initViews(View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(FileViewModel.class);
        editText = view.findViewById(R.id.editTextFileContent);
    }

    private void setupViewModel() {
        viewModel.getFileName().observe(getViewLifecycleOwner(), fileName -> {
            if (fileName == null || fileName.isEmpty()) {
                Toast.makeText(requireContext(), "Ошибка: Имя файла не задано", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                this.fileName = fileName;
                loadFileContent();
            }
        });
    }

    private void loadFileContent() {
        File file = new File(requireContext().getFilesDir(), fileName);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[(int) file.length()];
                fis.read(buffer);
                editText.setText(new String(buffer));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Ошибка загрузки файла", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveFileContent(String content) {
        File file = new File(requireContext().getFilesDir(), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Ошибка сохранения файла", Toast.LENGTH_SHORT).show();
        }
    }
}