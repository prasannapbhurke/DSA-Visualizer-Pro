package com.dsavisualizer.app.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dsavisualizer.app.data.ApiClient;
import com.dsavisualizer.app.model.ClassWeakTopicSummaryItem;
import com.dsavisualizer.app.model.ClassroomStudentSummary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassStudentsViewModel extends ViewModel {

    private final MutableLiveData<List<ClassroomStudentSummary>> students = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<ClassWeakTopicSummaryItem>> weakTopics = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> studentCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> classAverageScore = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> classTotalQuizzes = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(null);

    public LiveData<List<ClassroomStudentSummary>> getStudents() {
        return students;
    }

    public LiveData<List<ClassWeakTopicSummaryItem>> getWeakTopics() {
        return weakTopics;
    }

    public LiveData<Integer> getStudentCount() {
        return studentCount;
    }

    public LiveData<Integer> getClassAverageScore() {
        return classAverageScore;
    }

    public LiveData<Integer> getClassTotalQuizzes() {
        return classTotalQuizzes;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadStudents(String classId, String token, boolean forceRefresh) {
        List<ClassroomStudentSummary> current = students.getValue();
        if (!forceRefresh && current != null && !current.isEmpty()) {
            return;
        }

        loading.setValue(true);
        errorMessage.setValue(null);

        ApiClient.getInstance().get("/api/classes/" + classId + "/students", token, new ApiClient.Callback() {
            @Override
            public void onSuccess(@NonNull JSONObject payload) {
                JSONObject data = payload.optJSONObject("data");
                JSONObject classroom = data == null ? null : data.optJSONObject("classroom");

                List<ClassWeakTopicSummaryItem> weakTopicSummary = new ArrayList<>();
                JSONArray weakTopicArray = classroom == null ? null : classroom.optJSONArray("weakTopicSummary");
                if (weakTopicArray != null) {
                    for (int i = 0; i < weakTopicArray.length(); i++) {
                        JSONObject item = weakTopicArray.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        weakTopicSummary.add(new ClassWeakTopicSummaryItem(
                            item.optString("techniqueId", ""),
                            item.optInt("studentCount", 0),
                            item.optInt("lowestBestScore", 0)
                        ));
                    }
                }

                JSONArray items = data == null ? null : data.optJSONArray("students");
                List<ClassroomStudentSummary> loaded = new ArrayList<>();
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        JSONObject user = item.optJSONObject("user");
                        JSONObject summary = item.optJSONObject("summary");
                        loaded.add(new ClassroomStudentSummary(
                            user == null ? "" : user.optString("id", ""),
                            user == null ? "" : user.optString("displayName", ""),
                            user == null ? "" : user.optString("email", ""),
                            item.optString("rollNumber", ""),
                            item.optString("studentBatch", ""),
                            summary == null ? 0 : summary.optInt("totalTechniquesStarted", 0),
                            summary == null ? 0 : summary.optInt("completedTechniques", 0),
                            summary == null ? 0 : summary.optInt("totalPracticeSessions", 0),
                            summary == null ? 0 : summary.optInt("averageQuizScore", 0),
                            summary == null ? 0 : summary.optInt("streakDays", 0),
                            item.optString("lastActiveAt", ""),
                            item.optString("joinedAt", "")
                        ));
                    }
                }

                int count = classroom == null ? loaded.size() : classroom.optInt("studentCount", loaded.size());
                int avgScore = classroom == null ? 0 : classroom.optInt("averageQuizScore", 0);
                int quizzes = classroom == null ? 0 : classroom.optInt("totalQuizzes", 0);
                students.setValue(loaded);
                weakTopics.setValue(weakTopicSummary);
                studentCount.setValue(count);
                classAverageScore.setValue(avgScore);
                classTotalQuizzes.setValue(quizzes);
                loading.setValue(false);
            }

            @Override
            public void onError(@NonNull Exception error) {
                loading.setValue(false);
                errorMessage.setValue(error.getMessage() == null ? "Failed to load students" : error.getMessage());
            }
        });
    }
}
