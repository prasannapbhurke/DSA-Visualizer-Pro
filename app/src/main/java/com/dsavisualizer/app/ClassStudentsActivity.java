package com.dsavisualizer.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.model.ClassWeakTopicSummaryItem;
import com.dsavisualizer.app.model.ClassroomStudentSummary;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.ClassroomStudentAdapter;
import com.dsavisualizer.app.ui.viewmodel.ClassStudentsViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

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
    private MaterialButton sortButton;
    private MaterialButton exportButton;
    private String joinCode = "";
    private ClassStudentsViewModel viewModel;
    private boolean sortByRollNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_students);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.class_students_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        findViewById(R.id.class_students_back_button).setOnClickListener(v -> finish());

        String className = getIntent().getStringExtra(EXTRA_CLASS_NAME);
        joinCode = getIntent().getStringExtra(EXTRA_JOIN_CODE);
        boolean isStudentView = getIntent().getBooleanExtra(EXTRA_STUDENT_VIEW, false);

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
        sortButton = findViewById(R.id.class_students_sort_button);
        exportButton = findViewById(R.id.class_students_export_button);

        if (isStudentView) {
            if (sortButton != null) sortButton.setVisibility(View.GONE);
            if (exportButton != null) exportButton.setVisibility(View.GONE);
        } else {
            if (sortButton != null) {
                sortButton.setVisibility(View.VISIBLE);
                sortButton.setOnClickListener(v -> showSortDialog());
            }
            if (exportButton != null) {
                exportButton.setVisibility(View.VISIBLE);
                exportButton.setOnClickListener(v -> exportClassCsv());
            }
        }

        RecyclerView recyclerView = findViewById(R.id.class_students_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassroomStudentAdapter(this::openStudentDetail);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ClassStudentsViewModel.class);
        viewModel.getStudents().observe(this, list -> {
            students.clear();
            if (list != null) {
                students.addAll(list);
            }
            sortStudents(sortByRollNumber);
            emptyText.setVisibility(students.isEmpty() ? View.VISIBLE : View.GONE);
        });
        viewModel.getWeakTopics().observe(this, this::bindWeakTopicSummary);
        viewModel.getStudentCount().observe(this, count -> {
            summaryText.setText(getString(R.string.class_students_summary, count != null ? count : 0));
        });
        viewModel.getLoading().observe(this, this::setLoading);
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                emptyText.setText(R.string.class_students_load_failed);
                emptyText.setVisibility(View.VISIBLE);
                Toast.makeText(this, R.string.class_students_load_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra(EXTRA_STUDENT_VIEW, false)) {
            openOwnStudentDetail();
            return;
        }
        fetchStudents(false);
    }

    private void fetchStudents(boolean forceRefresh) {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        if (classId == null || classId.trim().isEmpty()) {
            Toast.makeText(this, R.string.class_students_load_failed, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        viewModel.loadStudents(classId, appPreferences.getAuthToken(), forceRefresh);
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

    private void showSortDialog() {
        String[] options = {
            getString(R.string.class_students_sort_roll),
            getString(R.string.class_students_sort_name)
        };
        new AlertDialog.Builder(this)
            .setTitle(R.string.class_students_sort_title)
            .setItems(options, (dialog, which) -> {
                sortStudents(which == 0);
                Toast.makeText(this, which == 0 ? R.string.class_students_sort_roll : R.string.class_students_sort_name, Toast.LENGTH_SHORT).show();
            })
            .show();
    }

    private void sortStudents(boolean byRoll) {
        sortByRollNumber = byRoll;
        students.sort((a, b) -> {
            if (byRoll) {
                String rollA = a.rollNumber == null ? "" : a.rollNumber.trim();
                String rollB = b.rollNumber == null ? "" : b.rollNumber.trim();
                if (rollA.isEmpty() && rollB.isEmpty()) return 0;
                if (rollA.isEmpty()) return 1;
                if (rollB.isEmpty()) return -1;
                return rollA.compareToIgnoreCase(rollB);
            } else {
                return a.displayName.compareToIgnoreCase(b.displayName);
            }
        });
        adapter.submitList(students);
    }

    private void exportClassCsv() {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        if (classId == null || classId.trim().isEmpty()) {
            Toast.makeText(this, R.string.class_students_export_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, R.string.class_students_exporting, Toast.LENGTH_SHORT).show();
        String token = appPreferences.getAuthToken();
        String sortBy = sortByRollNumber ? "roll_number" : "name";
        String urlString = ApiConfig.getBaseUrl() + "/api/classes/" + classId + "/export?sortBy=" + sortBy;

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);
                if (token != null && !token.trim().isEmpty()) {
                    conn.setRequestProperty("Authorization", "Bearer " + token.trim());
                }

                int code = conn.getResponseCode();
                if (code >= 200 && code < 300) {
                    File exportDir = new File(getCacheDir(), "exports");
                    if (!exportDir.exists()) {
                        exportDir.mkdirs();
                    }
                    String className = getIntent().getStringExtra(EXTRA_CLASS_NAME);
                    String safeClassName = (className == null ? "Class" : className).replaceAll("[^a-zA-Z0-9_-]", "_");
                    File csvFile = new File(exportDir, safeClassName + "_" + (joinCode == null ? "roster" : joinCode) + "_marksheet.csv");

                    try (InputStream in = conn.getInputStream();
                         FileOutputStream out = new FileOutputStream(csvFile)) {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }

                    Uri contentUri = FileProvider.getUriForFile(
                        ClassStudentsActivity.this,
                        getPackageName() + ".fileprovider",
                        csvFile
                    );

                    runOnUiThread(() -> {
                        Toast.makeText(ClassStudentsActivity.this, R.string.class_students_export_success, Toast.LENGTH_SHORT).show();
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/csv");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Class Marks Sheet - " + className);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share Marks Sheet"));
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ClassStudentsActivity.this, R.string.class_students_export_failed, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(ClassStudentsActivity.this, R.string.class_students_export_failed, Toast.LENGTH_SHORT).show());
            }
        });
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
}
