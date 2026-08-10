package com.dsavisualizer.app;

import android.os.Bundle;
import android.view.View;
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
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.FeedbackInboxAdapter;
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

public class OwnerFeedbackActivity extends AppCompatActivity {
    private final List<FeedbackInboxItem> items = new ArrayList<>();

    private AppPreferences appPreferences;
    private FeedbackInboxAdapter adapter;
    private ProgressBar loadingIndicator;
    private TextView summaryText;
    private TextView emptyText;
    private MaterialButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_feedback);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.owner_feedback_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        if (!"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.owner_users_owner_only, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.owner_feedback_back_button).setOnClickListener(v -> finish());
        loadingIndicator = findViewById(R.id.owner_feedback_loading);
        summaryText = findViewById(R.id.owner_feedback_summary_text);
        emptyText = findViewById(R.id.owner_feedback_empty_text);
        refreshButton = findViewById(R.id.owner_feedback_refresh_button);
        refreshButton.setOnClickListener(v -> fetchFeedback());

        RecyclerView recyclerView = findViewById(R.id.owner_feedback_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FeedbackInboxAdapter(null, this::confirmDeleteFeedback);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchFeedback();
    }

    private void fetchFeedback() {
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/feedback");
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
}
