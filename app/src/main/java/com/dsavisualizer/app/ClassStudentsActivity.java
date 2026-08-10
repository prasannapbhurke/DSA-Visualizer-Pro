package com.dsavisualizer.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.model.ClassWeakTopicSummaryItem;
import com.dsavisualizer.app.model.ClassroomStudentSummary;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.ClassroomStudentAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClassStudentsActivity extends AppCompatActivity {
    public static final String EXTRA_CLASS_ID = "extra_class_id";
    public static final String EXTRA_CLASS_NAME = "extra_class_name";
    public static final String EXTRA_JOIN_CODE = "extra_join_code";
    public static final String EXTRA_STUDENT_VIEW = "extra_student_view";

    private final List<ClassroomStudentSummary> students = new ArrayList<>();

    private AppPreferences appPreferences;
    private ClassroomStudentAdapter adapter;
    private ProgressBar loadingIndicator;
    private TextView subtitleText;
    private TextView summaryText;
    private TextView emptyText;
    private TextView weakTopicsSummaryText;
    private MaterialButton copyCodeButton;
    private MaterialButton resourcesButton;
    private String joinCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_students);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.class_students_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        findViewById(R.id.class_students_back_button).setOnClickListener(v -> finish());

        String className = getIntent().getStringExtra(EXTRA_CLASS_NAME);
        joinCode = getIntent().getStringExtra(EXTRA_JOIN_CODE);

        ((TextView) findViewById(R.id.class_students_title_text)).setText(className == null ? getString(R.string.class_students_title) : className);
        subtitleText = findViewById(R.id.class_students_subtitle_text);
        subtitleText.setText(getString(R.string.class_students_subtitle, joinCode == null ? "" : joinCode));
        summaryText = findViewById(R.id.class_students_summary_text);
        emptyText = findViewById(R.id.class_students_empty_text);
        weakTopicsSummaryText = findViewById(R.id.class_students_weak_topics_text);
        loadingIndicator = findViewById(R.id.class_students_loading);
        copyCodeButton = findViewById(R.id.class_students_copy_code_button);
        copyCodeButton.setOnClickListener(v -> copyJoinCode());
        resourcesButton = findViewById(R.id.class_students_resources_button);
        resourcesButton.setOnClickListener(v -> openResources());

        RecyclerView recyclerView = findViewById(R.id.class_students_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassroomStudentAdapter(this::openStudentDetail);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra(EXTRA_STUDENT_VIEW, false)) {
            openOwnStudentDetail();
            return;
        }
        fetchStudents();
    }

    private void fetchStudents() {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        if (classId == null || classId.trim().isEmpty()) {
            Toast.makeText(this, R.string.class_students_load_failed, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes/" + classId + "/students");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                int statusCode = connection.getResponseCode();
                String responseBody = readStream(statusCode >= 200 && statusCode < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream());
                if (statusCode < 200 || statusCode >= 300) {
                    throw new IllegalStateException(responseBody);
                }

                JSONObject payload = new JSONObject(responseBody);
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
                        loaded.add(parseStudent(item));
                    }
                }

                runOnUiThread(() -> {
                    students.clear();
                    students.addAll(loaded);
                    adapter.submitList(students);
                    int count = classroom == null ? loaded.size() : classroom.optInt("studentCount", loaded.size());
                    summaryText.setText(getString(R.string.class_students_summary, count));
                    bindWeakTopicSummary(weakTopicSummary);
                    emptyText.setVisibility(students.isEmpty() ? View.VISIBLE : View.GONE);
                    setLoading(false);
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    emptyText.setText(R.string.class_students_load_failed);
                    emptyText.setVisibility(View.VISIBLE);
                    setLoading(false);
                    Toast.makeText(this, R.string.class_students_load_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private ClassroomStudentSummary parseStudent(JSONObject item) {
        JSONObject user = item.optJSONObject("user");
        JSONObject summary = item.optJSONObject("summary");
        return new ClassroomStudentSummary(
            user == null ? "" : user.optString("id", ""),
            user == null ? "" : user.optString("displayName", ""),
            user == null ? "" : user.optString("email", ""),
            summary == null ? 0 : summary.optInt("totalTechniquesStarted", 0),
            summary == null ? 0 : summary.optInt("completedTechniques", 0),
            summary == null ? 0 : summary.optInt("totalPracticeSessions", 0),
            summary == null ? 0 : summary.optInt("streakDays", 0),
            item.optString("lastActiveAt", ""),
            item.optString("joinedAt", "")
        );
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void copyJoinCode() {
        if (joinCode == null || joinCode.trim().isEmpty()) {
            Toast.makeText(this, R.string.classroom_copy_code_missing, Toast.LENGTH_SHORT).show();
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Join code", joinCode));
            Toast.makeText(this, getString(R.string.classroom_copy_code_done, joinCode), Toast.LENGTH_SHORT).show();
        }
    }

    private void bindWeakTopicSummary(List<ClassWeakTopicSummaryItem> summaryItems) {
        if (summaryItems.isEmpty()) {
            weakTopicsSummaryText.setText(R.string.class_students_weak_topics_empty);
            return;
        }
        List<String> lines = new ArrayList<>();
        for (ClassWeakTopicSummaryItem item : summaryItems) {
            lines.add(getString(
                R.string.class_students_weak_topics_line,
                prettifyTechniqueId(item.techniqueId),
                item.studentCount,
                item.lowestBestScore
            ));
        }
        weakTopicsSummaryText.setText(android.text.TextUtils.join("\n", lines));
    }

    private void openStudentDetail(ClassroomStudentSummary student) {
        Intent intent = new Intent(this, StudentDetailActivity.class);
        intent.putExtra(StudentDetailActivity.EXTRA_CLASS_ID, getIntent().getStringExtra(EXTRA_CLASS_ID));
        intent.putExtra(StudentDetailActivity.EXTRA_STUDENT_ID, student.userId);
        intent.putExtra(StudentDetailActivity.EXTRA_STUDENT_NAME, student.displayName);
        startActivity(intent);
    }

    private void openOwnStudentDetail() {
        Intent intent = new Intent(this, StudentDetailActivity.class);
        intent.putExtra(StudentDetailActivity.EXTRA_CLASS_ID, getIntent().getStringExtra(EXTRA_CLASS_ID));
        intent.putExtra(StudentDetailActivity.EXTRA_STUDENT_ID, appPreferences.getAuthUserId());
        intent.putExtra(StudentDetailActivity.EXTRA_STUDENT_NAME, appPreferences.getAuthDisplayName());
        intent.putExtra(StudentDetailActivity.EXTRA_CLASS_NAME, getIntent().getStringExtra(EXTRA_CLASS_NAME));
        startActivity(intent);
        finish();
    }

    private void openResources() {
        Intent intent = new Intent(this, ClassResourcesActivity.class);
        intent.putExtra(ClassResourcesActivity.EXTRA_CLASS_ID, getIntent().getStringExtra(EXTRA_CLASS_ID));
        intent.putExtra(ClassResourcesActivity.EXTRA_CLASS_NAME, getIntent().getStringExtra(EXTRA_CLASS_NAME));
        startActivity(intent);
    }

    private String prettifyTechniqueId(String techniqueId) {
        if (techniqueId == null || techniqueId.trim().isEmpty()) {
            return getString(R.string.home_unknown_topic);
        }
        String[] parts = techniqueId.trim().split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                builder.append(part.substring(1));
            }
        }
        return builder.toString();
    }

    private String readStream(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }
}
