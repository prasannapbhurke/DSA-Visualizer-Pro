package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.model.StudentProgressEntry;
import com.dsavisualizer.app.model.TeacherNote;
import com.dsavisualizer.app.ui.CompactUiHelper;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dsavisualizer.app.data.ApiClient;
import java.util.ArrayList;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CLASS_ID = "extra_class_id";
    public static final String EXTRA_STUDENT_ID = "extra_student_id";
    public static final String EXTRA_STUDENT_NAME = "extra_student_name";
    public static final String EXTRA_CLASS_NAME = "extra_class_name";

    private AppPreferences appPreferences;
    private ProgressBar loadingIndicator;
    private TextView classNameText;
    private TextView summaryText;
    private TextView weakTopicsText;
    private TextView recentActivityText;
    private LinearLayout progressContainer;
    private LinearLayout notesContainer;
    private TextView emptyNotesText;
    private MaterialButton addFeedbackButton;
    private MaterialButton viewResourcesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.student_detail_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        findViewById(R.id.student_detail_back_button).setOnClickListener(v -> finish());

        String studentName = getIntent().getStringExtra(EXTRA_STUDENT_NAME);
        ((TextView) findViewById(R.id.student_detail_title_text)).setText(
            studentName == null || studentName.trim().isEmpty()
                ? getString(R.string.classroom_student_default_name)
                : studentName
        );

        classNameText = findViewById(R.id.student_detail_class_name_text);
        classNameText.setText(getString(
            R.string.student_detail_class_name,
            getIntent().getStringExtra(EXTRA_CLASS_NAME) == null
                ? getString(R.string.owner_users_meta_unknown)
                : getIntent().getStringExtra(EXTRA_CLASS_NAME)
        ));
        loadingIndicator = findViewById(R.id.student_detail_loading);
        summaryText = findViewById(R.id.student_detail_summary_text);
        weakTopicsText = findViewById(R.id.student_detail_weak_topics_text);
        recentActivityText = findViewById(R.id.student_detail_recent_activity_text);
        progressContainer = findViewById(R.id.student_detail_progress_container);
        notesContainer = findViewById(R.id.student_detail_notes_container);
        emptyNotesText = findViewById(R.id.student_detail_empty_notes_text);
        addFeedbackButton = findViewById(R.id.student_detail_add_feedback_button);
        LinearLayout notesHeader = findViewById(R.id.student_detail_notes_header);
        CompactUiHelper.stackButtonsIfCompact(
            this,
            notesHeader,
            8,
            notesHeader == null ? null : notesHeader.getChildAt(0),
            addFeedbackButton
        );
        addFeedbackButton.setOnClickListener(v -> openAddFeedbackDialog());
        viewResourcesButton = findViewById(R.id.student_detail_view_resources_button);
        viewResourcesButton.setOnClickListener(v -> openResources());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchStudentDetail();
    }

    private void fetchStudentDetail() {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        String studentId = getIntent().getStringExtra(EXTRA_STUDENT_ID);
        if (classId == null || studentId == null || classId.trim().isEmpty() || studentId.trim().isEmpty()) {
            Toast.makeText(this, R.string.student_detail_load_failed, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setLoading(true);
        ApiClient.getInstance().get("/api/classes/" + classId + "/students/" + studentId, appPreferences.getAuthToken(), new ApiClient.Callback() {
            @Override
            public void onSuccess(@NonNull JSONObject payload) {
                JSONObject data = payload.optJSONObject("data");
                JSONObject student = data == null ? null : data.optJSONObject("student");
                if (student == null) {
                    onError(new IllegalStateException("Missing student payload"));
                    return;
                }
                String viewerRole = student.optString("viewerRole", "teacher");

                List<StudentProgressEntry> progressEntries = new ArrayList<>();
                JSONArray progress = student.optJSONArray("progressEntries");
                if (progress != null) {
                    for (int i = 0; i < progress.length(); i++) {
                        JSONObject item = progress.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        progressEntries.add(new StudentProgressEntry(
                            item.optString("techniqueId", ""),
                            item.optInt("attempts", 0),
                            item.optInt("bestScore", 0),
                            item.optBoolean("completed", false),
                            item.optString("masteryLevel", ""),
                            item.optString("lastDifficulty", ""),
                            item.optString("lastPlayedAt", "")
                        ));
                    }
                }

                List<TeacherNote> notes = new ArrayList<>();
                JSONArray teacherNotes = student.optJSONArray("teacherNotes");
                if (teacherNotes != null) {
                    for (int i = 0; i < teacherNotes.length(); i++) {
                        JSONObject item = teacherNotes.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        notes.add(new TeacherNote(
                            item.optString("message", ""),
                            item.optString("status", ""),
                            item.optString("createdAt", ""),
                            item.optString("teacherName", "")
                        ));
                    }
                }

                List<String> weakTopics = new ArrayList<>();
                JSONArray weakTopicsArray = student.optJSONArray("weakTopics");
                if (weakTopicsArray != null) {
                    for (int i = 0; i < weakTopicsArray.length(); i++) {
                        JSONObject item = weakTopicsArray.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        weakTopics.add(getString(
                            R.string.student_detail_weak_topic_line,
                            prettifyTechniqueId(item.optString("techniqueId", "")),
                            item.optInt("bestScore", 0),
                            item.optInt("attempts", 0)
                        ));
                    }
                }

                List<String> recentActivity = new ArrayList<>();
                JSONArray recentActivityArray = student.optJSONArray("recentActivity");
                if (recentActivityArray != null) {
                    for (int i = 0; i < recentActivityArray.length(); i++) {
                        JSONObject item = recentActivityArray.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        recentActivity.add(getString(
                            R.string.student_detail_recent_line,
                            prettifyTechniqueId(item.optString("techniqueId", "")),
                            item.optString("difficulty", ""),
                            item.optInt("score", 0)
                        ));
                    }
                }

                JSONObject summary = student.optJSONObject("summary");
                addFeedbackButton.setVisibility("teacher".equalsIgnoreCase(viewerRole) ? View.VISIBLE : View.GONE);
                bindSummary(summary, student.optString("joinedAt", ""));
                bindWeakTopics(weakTopics);
                bindRecentActivity(recentActivity);
                bindProgressEntries(progressEntries);
                bindTeacherNotes(notes);
                setLoading(false);
            }

            @Override
            public void onError(@NonNull Exception error) {
                setLoading(false);
                Toast.makeText(StudentDetailActivity.this, R.string.student_detail_load_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindSummary(JSONObject summary, String joinedAt) {
        summaryText.setText(getString(
            R.string.student_detail_summary,
            summary == null ? 0 : summary.optInt("totalTechniquesStarted", 0),
            summary == null ? 0 : summary.optInt("completedTechniques", 0),
            summary == null ? 0 : summary.optInt("totalPracticeSessions", 0),
            summary == null ? 0 : summary.optInt("streakDays", 0),
            joinedAt == null || joinedAt.trim().isEmpty() ? getString(R.string.owner_users_meta_unknown) : joinedAt
        ));
    }

    private void bindWeakTopics(List<String> weakTopics) {
        if (weakTopics.isEmpty()) {
            weakTopicsText.setText(R.string.student_detail_weak_topics_empty);
            return;
        }
        weakTopicsText.setText(android.text.TextUtils.join("\n", weakTopics));
    }

    private void bindRecentActivity(List<String> recentActivity) {
        if (recentActivity.isEmpty()) {
            recentActivityText.setText(R.string.student_detail_recent_empty);
            return;
        }
        recentActivityText.setText(android.text.TextUtils.join("\n", recentActivity));
    }

    private void bindProgressEntries(List<StudentProgressEntry> progressEntries) {
        progressContainer.removeAllViews();
        if (progressEntries.isEmpty()) {
            TextView empty = buildBodyText(getString(R.string.student_detail_progress_empty));
            progressContainer.addView(empty);
            return;
        }
        for (StudentProgressEntry entry : progressEntries) {
            TextView line = buildBodyText(getString(
                R.string.student_detail_progress_line,
                prettifyTechniqueId(entry.techniqueId),
                entry.bestScore,
                entry.attempts,
                entry.lastDifficulty.trim().isEmpty() ? "-" : entry.lastDifficulty,
                entry.completed ? getString(R.string.student_detail_completed_yes) : getString(R.string.student_detail_completed_no)
            ));
            progressContainer.addView(line);
        }
    }

    private void bindTeacherNotes(List<TeacherNote> notes) {
        notesContainer.removeAllViews();
        emptyNotesText.setVisibility(notes.isEmpty() ? View.VISIBLE : View.GONE);
        for (TeacherNote note : notes) {
            TextView line = buildBodyText(getString(
                R.string.student_detail_teacher_note_line,
                note.status.trim().isEmpty() ? "-" : note.status,
                note.teacherName.trim().isEmpty() ? getString(R.string.owner_users_meta_unknown) : note.teacherName,
                note.createdAt.trim().isEmpty() ? getString(R.string.owner_users_meta_unknown) : note.createdAt,
                note.message
            ));
            notesContainer.addView(line);
        }
    }

    private TextView buildBodyText(String text) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = dpToPx(8);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_muted_soft));
        textView.setTextSize(13f);
        return textView;
    }

    private void openAddFeedbackDialog() {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(20);
        container.setPadding(padding, dpToPx(8), padding, 0);

        EditText statusInput = new EditText(this);
        statusInput.setHint(R.string.student_detail_feedback_status_hint);
        statusInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        statusInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        statusInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        EditText messageInput = new EditText(this);
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        messageParams.topMargin = dpToPx(12);
        messageInput.setLayoutParams(messageParams);
        messageInput.setHint(R.string.student_detail_feedback_message_hint);
        messageInput.setMinLines(4);
        messageInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        messageInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        container.addView(statusInput);
        container.addView(messageInput);

        new AlertDialog.Builder(this)
            .setTitle(R.string.student_detail_add_feedback)
            .setView(container)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.home_feedback_send, (dialog, which) -> {
                String message = messageInput.getText() == null ? "" : messageInput.getText().toString().trim();
                String status = statusInput.getText() == null ? "" : statusInput.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(this, R.string.home_feedback_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                submitTeacherFeedback(message, status);
            })
            .show();
    }

    private void submitTeacherFeedback(String message, String status) {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        String studentId = getIntent().getStringExtra(EXTRA_STUDENT_ID);
        setLoading(true);
        try {
            JSONObject body = new JSONObject();
            body.put("message", message);
            body.put("status", status);

            ApiClient.getInstance().post("/api/classes/" + classId + "/students/" + studentId + "/feedback", body, appPreferences.getAuthToken(), new ApiClient.Callback() {
                @Override
                public void onSuccess(@NonNull JSONObject response) {
                    Toast.makeText(StudentDetailActivity.this, R.string.student_detail_feedback_saved, Toast.LENGTH_SHORT).show();
                    fetchStudentDetail();
                }

                @Override
                public void onError(@NonNull Exception error) {
                    setLoading(false);
                    Toast.makeText(StudentDetailActivity.this, R.string.student_detail_feedback_failed, Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            setLoading(false);
            Toast.makeText(this, R.string.student_detail_feedback_failed, Toast.LENGTH_LONG).show();
        }
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

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void openResources() {
        Intent intent = new Intent(this, ClassResourcesActivity.class);
        intent.putExtra(ClassResourcesActivity.EXTRA_CLASS_ID, getIntent().getStringExtra(EXTRA_CLASS_ID));
        intent.putExtra(ClassResourcesActivity.EXTRA_CLASS_NAME, getIntent().getStringExtra(EXTRA_CLASS_NAME));
        startActivity(intent);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private int resolveThemeColor(int attrResId, int fallbackColorResId) {
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(attrResId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return ContextCompat.getColor(this, typedValue.resourceId);
            }
            return typedValue.data;
        }
        return ContextCompat.getColor(this, fallbackColorResId);
    }
}
