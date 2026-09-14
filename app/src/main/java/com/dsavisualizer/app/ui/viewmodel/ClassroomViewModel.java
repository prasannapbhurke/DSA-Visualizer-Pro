package com.dsavisualizer.app.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dsavisualizer.app.data.ApiClient;
import com.dsavisualizer.app.model.ClassroomInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassroomViewModel extends ViewModel {

    private final MutableLiveData<List<ClassroomInfo>> classrooms = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(null);

    public LiveData<List<ClassroomInfo>> getClassrooms() {
        return classrooms;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadClasses(String token, boolean forceRefresh) {
        List<ClassroomInfo> current = classrooms.getValue();
        if (!forceRefresh && current != null && !current.isEmpty()) {
            return;
        }

        loading.setValue(true);
        errorMessage.setValue(null);

        ApiClient.getInstance().get("/api/classes", token, new ApiClient.Callback() {
            @Override
            public void onSuccess(@NonNull JSONObject payload) {
                JSONObject data = payload.optJSONObject("data");
                JSONArray items = data == null ? null : data.optJSONArray("classes");
                List<ClassroomInfo> loaded = new ArrayList<>();
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        loaded.add(new ClassroomInfo(
                            item.optString("id", ""),
                            item.optString("name", ""),
                            item.optString("description", ""),
                            item.optString("joinCode", ""),
                            item.optString("teacherName", ""),
                            item.optInt("studentCount", 0),
                            item.optString("joinedAt", "")
                        ));
                    }
                }
                classrooms.setValue(loaded);
                loading.setValue(false);
            }

            @Override
            public void onError(@NonNull Exception error) {
                loading.setValue(false);
                errorMessage.setValue(error.getMessage() == null ? "Failed to load classrooms" : error.getMessage());
            }
        });
    }

    public void addClassroom(@NonNull ClassroomInfo classroomInfo) {
        List<ClassroomInfo> list = new ArrayList<>();
        list.add(classroomInfo);
        List<ClassroomInfo> existing = classrooms.getValue();
        if (existing != null) {
            list.addAll(existing);
        }
        classrooms.setValue(list);
    }
}
