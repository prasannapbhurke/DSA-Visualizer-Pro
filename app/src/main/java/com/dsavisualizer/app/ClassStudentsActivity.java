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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONObject;

import com.dsavisualizer.app.data.ApiClient;
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
    private ClassStudentsViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(ClassStudentsViewModel.class);
        viewModel.getStudents().observe(this, list -> {
            students.clear();
            if (list != null) {
                students.addAll(list);
            }
            adapter.submitList(students);
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
