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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.viewmodel.OwnerAnalyticsViewModel;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dsavisualizer.app.data.ApiClient;
import java.util.ArrayList;
import java.util.List;

public class OwnerAnalyticsActivity extends AppCompatActivity {
    private AppPreferences appPreferences;
    private ProgressBar loadingIndicator;
    private TextView summaryText;
    private TextView topicsText;
    private TextView issuesText;
    private TextView auditLogsText;
    private MaterialButton refreshButton;
    private MaterialButton exportBackupButton;
    private MaterialButton restoreBackupButton;
    private OwnerAnalyticsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_analytics);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.owner_analytics_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        if (!"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.owner_users_owner_only, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.owner_analytics_back_button).setOnClickListener(v -> finish());
        loadingIndicator = findViewById(R.id.owner_analytics_loading);
        summaryText = findViewById(R.id.owner_analytics_summary_text);
        topicsText = findViewById(R.id.owner_analytics_topics_text);
        issuesText = findViewById(R.id.owner_analytics_issues_text);
        auditLogsText = findViewById(R.id.owner_analytics_audit_logs_text);
        refreshButton = findViewById(R.id.owner_analytics_refresh_button);
        exportBackupButton = findViewById(R.id.owner_analytics_export_button);
        restoreBackupButton = findViewById(R.id.owner_analytics_restore_button);

        viewModel = new ViewModelProvider(this).get(OwnerAnalyticsViewModel.class);
        viewModel.getUiState().observe(this, state -> {
            if (state == null) {
                return;
            }
            summaryText.setText(getString(
                R.string.owner_analytics_summary,
                state.totalUsers,
                state.activeTeachers,
                state.totalClasses,
                state.activeClasses,
                state.totalResources,
                state.totalFeedback,
                state.totalPracticeSessions
            ));

            List<String> topicLines = new ArrayList<>();
            for (OwnerAnalyticsViewModel.TopicItem topic : state.topics) {
                topicLines.add(getString(
                    R.string.owner_analytics_topic_line,
                    prettifyTechniqueId(topic.techniqueId),
                    topic.uses
                ));
            }
            topicsText.setText(topicLines.isEmpty()
                ? getString(R.string.owner_analytics_topics_empty)
                : android.text.TextUtils.join("\n", topicLines));

            List<String> issueLines = new ArrayList<>();
            for (OwnerAnalyticsViewModel.IssueItem issue : state.issues) {
                issueLines.add(getString(
                    R.string.owner_analytics_issue_line,
                    prettifyTechniqueId(issue.techniqueId),
                    issue.type,
                    issue.count
                ));
            }
            issuesText.setText(issueLines.isEmpty()
                ? getString(R.string.owner_analytics_issues_empty)
                : android.text.TextUtils.join("\n", issueLines));

            List<String> auditLines = new ArrayList<>();
            for (OwnerAnalyticsViewModel.AuditItem audit : state.auditLogs) {
                auditLines.add(getString(
                    R.string.owner_analytics_audit_line,
                    audit.action,
                    audit.actorName,
                    audit.targetName,
                    audit.createdAt
                ));
            }
            auditLogsText.setText(auditLines.isEmpty()
                ? getString(R.string.owner_analytics_audit_empty)
                : android.text.TextUtils.join("\n\n", auditLines));
        });
        viewModel.getLoading().observe(this, this::setLoading);
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, R.string.owner_analytics_load_failed, Toast.LENGTH_LONG).show();
            }
        });

        refreshButton.setOnClickListener(v -> fetchOwnerAnalytics(true));
        exportBackupButton.setOnClickListener(v -> exportBackup());
        restoreBackupButton.setOnClickListener(v -> openRestoreDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchOwnerAnalytics(false);
    }

    private void fetchOwnerAnalytics() {
        fetchOwnerAnalytics(true);
    }

    private void fetchOwnerAnalytics(boolean forceRefresh) {
        viewModel.loadAnalytics(appPreferences.getAuthToken(), forceRefresh);
    }

    private void exportBackup() {
        setLoading(true);
        new Thread(() -> {
            try {
                JSONObject backupResponse = performAuthorizedRequest("GET", "/api/admin/backup", null);
                JSONObject data = backupResponse.optJSONObject("data");
                JSONObject backup = data == null ? null : data.optJSONObject("backup");
                String payload = backup == null ? "{}" : backup.toString(2);
                runOnUiThread(() -> {
                    setLoading(false);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DSA Visualizer Backup");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, payload);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.owner_analytics_export_button)));
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(this, R.string.owner_analytics_backup_failed, Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void openRestoreDialog() {
        EditText input = new EditText(this);
        input.setMinLines(10);
        input.setHint(R.string.owner_analytics_restore_hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        input.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        new AlertDialog.Builder(this)
            .setTitle(R.string.owner_analytics_restore_button)
            .setView(input)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.owner_analytics_restore_confirm, (dialog, which) -> {
                String raw = input.getText() == null ? "" : input.getText().toString().trim();
                if (raw.isEmpty()) {
                    Toast.makeText(this, R.string.owner_analytics_restore_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                restoreBackup(raw);
            })
            .show();
    }

    private void restoreBackup(String rawJson) {
        setLoading(true);
        new Thread(() -> {
            try {
                JSONObject incoming = new JSONObject(rawJson);
                JSONObject backupData = incoming.optJSONObject("backupData");
                if (backupData == null) {
                    throw new IllegalStateException("backupData missing");
                }
                JSONObject body = new JSONObject();
                body.put("backupData", backupData);
                performAuthorizedRequest("POST", "/api/admin/restore", body);
                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.owner_analytics_restore_success, Toast.LENGTH_LONG).show();
                    fetchOwnerAnalytics();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(this, R.string.owner_analytics_restore_failed, Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private JSONObject performAuthorizedRequest(String method, String endpoint, JSONObject body) throws Exception {
        return ApiClient.getInstance().execute(method, endpoint, body, appPreferences.getAuthToken());
    }

    private String prettifyTechniqueId(String techniqueId) {
        if (techniqueId == null || techniqueId.trim().isEmpty() || "general".equalsIgnoreCase(techniqueId)) {
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
