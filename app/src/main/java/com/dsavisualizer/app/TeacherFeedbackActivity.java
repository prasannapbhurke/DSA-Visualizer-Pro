package com.dsavisualizer.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.model.FeedbackInboxItem;
import com.dsavisualizer.app.ui.CompactUiHelper;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.FeedbackInboxAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TeacherFeedbackActivity extends AppCompatActivity {
    private final List<FeedbackInboxItem> items = new ArrayList<>();
    private final List<ClassFilterItem> classes = new ArrayList<>();

    private AppPreferences appPreferences;
    private FeedbackInboxAdapter adapter;
    private ProgressBar loadingIndicator;
    private TextView summaryText;
    private TextView emptyText;
    private MaterialButton refreshButton;
    private MaterialButton classFilterButton;
    private MaterialButton typeFilterButton;
    private EditText topicFilterInput;

    private String selectedClassId = "";
    private String selectedClassLabel = "";
    private String selectedType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_feedback);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.teacher_feedback_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        if (!"teacher".equalsIgnoreCase(appPreferences.getAuthRole())
            && !"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.teacher_feedback_teacher_only, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.teacher_feedback_back_button).setOnClickListener(v -> finish());
        loadingIndicator = findViewById(R.id.teacher_feedback_loading);
        summaryText = findViewById(R.id.teacher_feedback_summary_text);
        emptyText = findViewById(R.id.teacher_feedback_empty_text);
        refreshButton = findViewById(R.id.teacher_feedback_refresh_button);
        classFilterButton = findViewById(R.id.teacher_feedback_class_filter_button);
        typeFilterButton = findViewById(R.id.teacher_feedback_type_filter_button);
        topicFilterInput = findViewById(R.id.teacher_feedback_topic_input);
        CompactUiHelper.stackButtonsIfCompact(
            this,
            findViewById(R.id.teacher_feedback_filter_row),
            8,
            classFilterButton,
            typeFilterButton
        );

        RecyclerView recyclerView = findViewById(R.id.teacher_feedback_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FeedbackInboxAdapter(this::markReviewed, this::confirmDeleteFeedback);
        recyclerView.setAdapter(adapter);

        classFilterButton.setOnClickListener(v -> openClassFilterDialog());
        typeFilterButton.setOnClickListener(v -> openTypeFilterDialog());
        refreshButton.setOnClickListener(v -> fetchFeedback());
        findViewById(R.id.teacher_feedback_apply_filters_button).setOnClickListener(v -> fetchFeedback());

        updateFilterLabels();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchClasses();
        fetchFeedback();
    }

    private void fetchClasses() {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes");
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
                JSONArray classArray = data == null ? null : data.optJSONArray("classes");
                List<ClassFilterItem> loaded = new ArrayList<>();
                loaded.add(new ClassFilterItem("", getString(R.string.teacher_feedback_filter_all_classes)));
                if (classArray != null) {
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject item = classArray.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        loaded.add(new ClassFilterItem(
                            item.optString("id", ""),
                            item.optString("name", getString(R.string.teacher_feedback_filter_all_classes))
                        ));
                    }
                }

                runOnUiThread(() -> {
                    classes.clear();
                    classes.addAll(loaded);
                    if (!selectedClassId.isEmpty()) {
                        for (ClassFilterItem item : classes) {
                            if (selectedClassId.equals(item.id)) {
                                selectedClassLabel = item.name;
                                break;
                            }
                        }
                    }
                    updateFilterLabels();
                });
            } catch (Exception ignored) {
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void fetchFeedback() {
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                StringBuilder urlBuilder = new StringBuilder(ApiConfig.getBaseUrl() + "/api/teacher/feedback");
                List<String> params = new ArrayList<>();
                if (!selectedClassId.trim().isEmpty()) {
                    params.add("classId=" + URLEncoder.encode(selectedClassId, "UTF-8"));
                }
                if (!selectedType.trim().isEmpty()) {
                    params.add("type=" + URLEncoder.encode(selectedType, "UTF-8"));
                }
                String topic = topicFilterInput.getText() == null ? "" : topicFilterInput.getText().toString().trim();
                if (!topic.isEmpty()) {
                    params.add("techniqueId=" + URLEncoder.encode(topic, "UTF-8"));
                }
                if (!params.isEmpty()) {
                    urlBuilder.append('?').append(String.join("&", params));
                }

                URL url = new URL(urlBuilder.toString());
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
                JSONArray feedback = data == null ? null : data.optJSONArray("feedback");
                List<FeedbackInboxItem> loaded = new ArrayList<>();
                if (feedback != null) {
                    for (int i = 0; i < feedback.length(); i++) {
                        JSONObject item = feedback.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        loaded.add(new FeedbackInboxItem(
                            item.optString("id", ""),
                            item.optString("type", ""),
                            item.optString("message", ""),
                            item.optString("techniqueId", ""),
                            item.optString("screen", ""),
                            item.optString("difficulty", ""),
                            item.optString("createdAt", ""),
                            item.optString("userDisplayName", ""),
                            item.optString("userEmail", ""),
                            item.optString("userRole", ""),
                            item.optString("classId", ""),
                            item.optString("className", ""),
                            item.optString("reviewedAt", ""),
                            item.optString("reviewedBy", "")
                        ));
                    }
                }

                runOnUiThread(() -> {
                    items.clear();
                    items.addAll(loaded);
                    adapter.submitList(items);
                    summaryText.setText(getString(R.string.feedback_inbox_summary, items.size()));
                    emptyText.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
                    setLoading(false);
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    emptyText.setText(R.string.feedback_inbox_load_failed);
                    emptyText.setVisibility(View.VISIBLE);
                    setLoading(false);
                    Toast.makeText(this, R.string.feedback_inbox_load_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void markReviewed(FeedbackInboxItem item) {
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/teacher/feedback/" + item.id + "/review");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PATCH");
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                JSONObject body = new JSONObject();
                body.put("reviewed", true);
                try (OutputStream outputStream = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    writer.write(body.toString());
                }

                int statusCode = connection.getResponseCode();
                String responseBody = readStream(statusCode >= 200 && statusCode < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream());
                if (statusCode < 200 || statusCode >= 300) {
                    throw new IllegalStateException(responseBody);
                }

                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.teacher_feedback_mark_reviewed_done, Toast.LENGTH_SHORT).show();
                    fetchFeedback();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(this, R.string.teacher_feedback_mark_reviewed_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void openClassFilterDialog() {
        if (classes.isEmpty()) {
            Toast.makeText(this, R.string.classroom_load_failed, Toast.LENGTH_SHORT).show();
            return;
        }
        CharSequence[] names = new CharSequence[classes.size()];
        int checkedIndex = 0;
        for (int i = 0; i < classes.size(); i++) {
            names[i] = classes.get(i).name;
            if (classes.get(i).id.equals(selectedClassId)) {
                checkedIndex = i;
            }
        }
        new AlertDialog.Builder(this)
            .setTitle(R.string.teacher_feedback_pick_class)
            .setSingleChoiceItems(names, checkedIndex, (dialog, which) -> {
                ClassFilterItem selected = classes.get(which);
                selectedClassId = selected.id;
                selectedClassLabel = selected.name;
                updateFilterLabels();
                dialog.dismiss();
            })
            .show();
    }

    private void openTypeFilterDialog() {
        final String[] values = {"", "bug", "content", "feature", "general"};
        final String[] labels = {
            getString(R.string.teacher_feedback_filter_any),
            "bug",
            "content",
            "feature",
            "general",
        };
        int checkedIndex = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i].equalsIgnoreCase(selectedType)) {
                checkedIndex = i;
                break;
            }
        }
        new AlertDialog.Builder(this)
            .setTitle(R.string.teacher_feedback_pick_type)
            .setSingleChoiceItems(labels, checkedIndex, (dialog, which) -> {
                selectedType = values[which];
                updateFilterLabels();
                dialog.dismiss();
            })
            .show();
    }

    private void updateFilterLabels() {
        String classLabel = selectedClassLabel.trim().isEmpty()
            ? getString(R.string.teacher_feedback_filter_all_classes)
            : selectedClassLabel;
        String typeLabel = selectedType.trim().isEmpty()
            ? getString(R.string.teacher_feedback_filter_any)
            : selectedType;
        classFilterButton.setText(getString(R.string.teacher_feedback_filter_class, classLabel));
        typeFilterButton.setText(getString(R.string.teacher_feedback_filter_type, typeLabel));
    }

    private void confirmDeleteFeedback(FeedbackInboxItem item) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.feedback_inbox_delete_title)
            .setMessage(R.string.feedback_inbox_delete_message)
            .setNegativeButton(R.string.feedback_inbox_delete_cancel, null)
            .setPositiveButton(R.string.feedback_inbox_delete_confirm, (dialog, which) -> deleteFeedback(item))
            .show();
    }

    private void deleteFeedback(FeedbackInboxItem item) {
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/feedback/" + item.id);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
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

                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.feedback_inbox_delete_done, Toast.LENGTH_SHORT).show();
                    fetchFeedback();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(this, R.string.feedback_inbox_delete_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
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

    private static class ClassFilterItem {
        final String id;
        final String name;

        ClassFilterItem(String id, String name) {
            this.id = id == null ? "" : id;
            this.name = name == null ? "" : name;
        }
    }
}
