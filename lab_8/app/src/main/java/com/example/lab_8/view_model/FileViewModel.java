package com.example.lab_8.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_8.utils.SharedPreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileViewModel extends AndroidViewModel {
    private final MutableLiveData<List<String>> filesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> fileNameLiveData = new MutableLiveData<>();
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public FileViewModel(@NonNull Application application) {
        super(application);
        sharedPreferencesHelper = new SharedPreferencesHelper(application);
        loadFiles();
    }

    public LiveData<List<String>> getFiles() {
        return filesLiveData;
    }
    public LiveData<String> getFileName() { return fileNameLiveData; }
    public void setFileName(String fileName) { fileNameLiveData.setValue(fileName); }

    public void addFile(String fileName) {
        File internalFile = new File(getApplication().getFilesDir(), fileName);
        try {
            if (!internalFile.exists()) {
                try (FileOutputStream fos = new FileOutputStream(internalFile)) {
                    fos.write("".getBytes());
                }
            }

            List<String> fileList = filesLiveData.getValue();
            if (fileList == null) {
                fileList = new ArrayList<>();
            }

            fileList.add(fileName);
            sharedPreferencesHelper.saveFileList(fileList);
            filesLiveData.setValue(fileList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFile(int position) {
        List<String> fileList = filesLiveData.getValue();
        if (fileList != null && position >= 0 && position < fileList.size()) {
            String fileName = fileList.get(position);
            File internalFile = new File(getApplication().getFilesDir(), fileName);
            if (internalFile.exists()) {
                internalFile.delete();
            }

            fileList.remove(position);
            sharedPreferencesHelper.saveFileList(fileList);
            filesLiveData.setValue(fileList);
        }
    }

    private void loadFiles() {
        List<String> files = sharedPreferencesHelper.getFileList();
        if (files == null) {
            files = new ArrayList<>();
        }
        filesLiveData.setValue(files);
    }
}