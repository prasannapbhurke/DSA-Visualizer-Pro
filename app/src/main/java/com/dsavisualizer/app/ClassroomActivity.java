package com.dsavisualizer.app;

import android.content.Intent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.model.ClassroomInfo;
import com.dsavisualizer.app.ui.CompactUiHelper;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.ClassroomAdapter;
import com.dsavisualizer.app.ui.walkthrough.WalkthroughManager;
import com.dsavisualizer.app.ui.walkthrough.WalkthroughOverlayView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClassroomActivity extends AppCompatActivity {
    private final List<ClassroomInfo> classrooms = new ArrayList<>();

    private AppPreferences appPreferences;
    private ClassroomAdapter adapter;
    private ProgressBar loadingIndicator;
    private TextView helperText;
    private TextView emptyText;
    private MaterialButton createClassButton;
    private MaterialButton joinClassButton;
    private WalkthroughOverlayView walkthroughOverlay;
    private RemoteSyncManager remoteSyncManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.classroom_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        remoteSyncManager = RemoteSyncManager.getInstance(this);
        if (!appPreferences.hasGoogleSession()) {
            Toast.makeText(this, R.string.classroom_google_only, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        findViewById(R.id.classroom_back_button).setOnClickListener(v -> finish());
        loadingIndicator = findViewById(R.id.classroom_loading);
        helperText = findViewById(R.id.classroom_helper_text);
        emptyText = findViewById(R.id.classroom_empty_text);
        createClassButton = findViewById(R.id.classroom_create_button);
        joinClassButton = findViewById(R.id.classroom_join_button);
        CompactUiHelper.stackButtonsIfCompact(
            this,
            findViewById(R.id.classroom_action_row),
            8,
            createClassButton,
            joinClassButton
        );

        RecyclerView recyclerView = findViewById(R.id.classroom_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassroomAdapter(this::handleClassroomClicked);
        recyclerView.setAdapter(adapter);

        bindRoleActions();
        createClassButton.setOnClickListener(v -> openCreateClassDialog());
        joinClassButton.setOnClickListener(v -> openJoinClassDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshRoleThenFetchClasses();
    }

    private void refreshRoleThenFetchClasses() {
        remoteSyncManager.refreshCurrentUser(success -> {
            bindRoleActions();
            fetchClasses();
        });
    }

    private void bindRoleActions() {
        boolean canCreate = isTeacherView();
        createClassButton.setVisibility(canCreate ? View.VISIBLE : View.GONE);
        joinClassButton.setVisibility(canCreate ? View.GONE : View.VISIBLE);
        helperText.setText(canCreate
            ? getString(R.string.classroom_teacher_helper)
            : getString(R.string.classroom_student_helper));
        emptyText.setText(canCreate
            ? getString(R.string.classroom_teacher_empty_short)
            : getString(R.string.classroom_student_empty));
    }

    private boolean isTeacherView() {
        String role = appPreferences.getAuthRole();
        return "teacher".equalsIgnoreCase(role) || "owner".equalsIgnoreCase(role);
    }

    private void fetchClasses() {
        setLoading(true);
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
                JSONArray items = data == null ? null : data.optJSONArray("classes");
                List<ClassroomInfo> loaded = new ArrayList<>();
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        loaded.add(parseClassroom(item));
                    }
                }

                runOnUiThread(() -> {
                    classrooms.clear();
                    classrooms.addAll(loaded);
                    adapter.submitList(classrooms);
                    emptyText.setVisibility(classrooms.isEmpty() ? View.VISIBLE : View.GONE);
                    setLoading(false);
                    maybeStartWalkthrough();
                    maybeShowMiniTip();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    emptyText.setText(R.string.classroom_load_failed);
                    emptyText.setVisibility(View.VISIBLE);
                    setLoading(false);
                    Toast.makeText(this, R.string.classroom_load_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void openCreateClassDialog() {
        if (!isTeacherView()) {
            Toast.makeText(this, R.string.teacher_feedback_teacher_only, Toast.LENGTH_SHORT).show();
            refreshRoleThenFetchClasses();
            return;
        }
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(20);
        container.setPadding(padding, dpToPx(8), padding, 0);

        EditText nameInput = new EditText(this);
        nameInput.setHint(R.string.classroom_create_name_hint);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        nameInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        nameInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        EditText descriptionInput = new EditText(this);
        LinearLayout.LayoutParams descriptionParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        descriptionParams.topMargin = dpToPx(12);
        descriptionInput.setLayoutParams(descriptionParams);
        descriptionInput.setHint(R.string.classroom_create_description_hint);
        descriptionInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        descriptionInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        descriptionInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        container.addView(nameInput);
        container.addView(descriptionInput);

        new AlertDialog.Builder(this)
            .setTitle(R.string.classroom_create_title)
            .setView(container)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.classroom_create_button_text, (dialog, which) -> {
                String name = nameInput.getText() == null ? "" : nameInput.getText().toString().trim();
                String description = descriptionInput.getText() == null ? "" : descriptionInput.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(this, R.string.classroom_create_name_required, Toast.LENGTH_SHORT).show();
                    return;
                }
                createClass(name, description);
            })
            .show();
    }

    private void createClass(@NonNull String name, @NonNull String description) {
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                JSONObject body = new JSONObject();
                body.put("name", name);
                body.put("description", description);

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

                JSONObject payload = new JSONObject(responseBody);
                JSONObject data = payload.optJSONObject("data");
                JSONObject classroom = data == null ? null : data.optJSONObject("classroom");
                ClassroomInfo createdClassroom = classroom == null ? null : parseClassroom(classroom);

                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.classroom_create_success, Toast.LENGTH_SHORT).show();
                    if (createdClassroom != null) {
                        copyJoinCode(createdClassroom.joinCode, true);
                        Snackbar.make(findViewById(R.id.classroom_root), R.string.classroom_copy_code_ready, Snackbar.LENGTH_LONG).show();
                        showCreatedClassDialog(createdClassroom);
                    }
                    fetchClasses();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(this, R.string.classroom_create_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void openJoinClassDialog() {
        EditText input = new EditText(this);
        input.setHint(R.string.classroom_join_code_hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        input.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        input.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        new AlertDialog.Builder(this)
            .setTitle(R.string.classroom_join_title)
            .setView(input)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.classroom_join_button_text, (dialog, which) -> {
                String joinCode = input.getText() == null ? "" : input.getText().toString().trim().toUpperCase();
                if (joinCode.isEmpty()) {
                    Toast.makeText(this, R.string.classroom_join_code_required, Toast.LENGTH_SHORT).show();
                    return;
                }
                joinClass(joinCode);
            })
            .show();
    }

    private void joinClass(@NonNull String joinCode) {
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes/join");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                JSONObject body = new JSONObject();
                body.put("joinCode", joinCode);
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
                    Toast.makeText(this, R.string.classroom_join_success, Toast.LENGTH_SHORT).show();
                    Snackbar.make(findViewById(R.id.classroom_root), R.string.classroom_join_sync_ready, Snackbar.LENGTH_LONG).show();
                    fetchClasses();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(this, R.string.classroom_join_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private ClassroomInfo parseClassroom(JSONObject item) {
        return new ClassroomInfo(
            item.optString("id", ""),
            item.optString("name", ""),
            item.optString("description", ""),
            item.optString("joinCode", ""),
            item.optString("teacherName", ""),
            item.optInt("studentCount", 0),
            item.optString("joinedAt", "")
        );
    }

    private void handleClassroomClicked(@NonNull ClassroomInfo classroomInfo) {
        Intent intent = new Intent(this, ClassStudentsActivity.class);
        intent.putExtra(ClassStudentsActivity.EXTRA_CLASS_ID, classroomInfo.id);
        intent.putExtra(ClassStudentsActivity.EXTRA_CLASS_NAME, classroomInfo.name);
        intent.putExtra(ClassStudentsActivity.EXTRA_JOIN_CODE, classroomInfo.joinCode);
        intent.putExtra(ClassStudentsActivity.EXTRA_STUDENT_VIEW, !isTeacherView());
        startActivity(intent);
    }

    private void showCreatedClassDialog(@NonNull ClassroomInfo classroomInfo) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.classroom_created_dialog_title)
            .setMessage(getString(R.string.classroom_created_dialog_body, classroomInfo.name, classroomInfo.joinCode))
            .setNegativeButton(android.R.string.ok, null)
            .setPositiveButton(R.string.classroom_copy_code, (dialog, which) -> copyJoinCode(classroomInfo.joinCode))
            .show();
    }

    private void copyJoinCode(@NonNull String joinCode) {
        copyJoinCode(joinCode, false);
    }

    private void copyJoinCode(@NonNull String joinCode, boolean silent) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Join code", joinCode));
            if (!silent) {
                Toast.makeText(this, getString(R.string.classroom_copy_code_done, joinCode), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
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

    private void maybeStartWalkthrough() {
        String stage = appPreferences.getWalkthroughStage();
        if (appPreferences.isWalkthroughPaused()) {
            return;
        }
        if (WalkthroughManager.STAGE_CLASSROOM_TEACHER.equals(stage)) {
            appPreferences.resumeWalkthrough();
            showWalkthroughStep(
                createClassButton,
                R.string.walkthrough_classroom_create_title,
                R.string.walkthrough_classroom_create_body,
                new WalkthroughOverlayView.Listener() {
                    @Override
                    public void onNext() {
                        View infoTarget = emptyText.getVisibility() == View.VISIBLE ? emptyText : helperText;
                        showWalkthroughStep(
                            infoTarget,
                            R.string.walkthrough_classroom_empty_title,
                            R.string.walkthrough_classroom_empty_teacher_body,
                            new WalkthroughOverlayView.Listener() {
                                @Override
                                public void onNext() {
                                    appPreferences.completeWalkthrough();
                                    dismissWalkthrough();
                                }

                                @Override
                                public void onSkip() {
                                    pauseWalkthrough();
                                }
                            }
                        );
                    }

                    @Override
                    public void onSkip() {
                        pauseWalkthrough();
                    }
                }
            );
            return;
        }

        if (WalkthroughManager.STAGE_CLASSROOM_STUDENT.equals(stage)) {
            appPreferences.resumeWalkthrough();
            showWalkthroughStep(
                joinClassButton,
                R.string.walkthrough_classroom_join_title,
                R.string.walkthrough_classroom_join_body,
                new WalkthroughOverlayView.Listener() {
                    @Override
                    public void onNext() {
                        View infoTarget = emptyText.getVisibility() == View.VISIBLE ? emptyText : helperText;
                        showWalkthroughStep(
                            infoTarget,
                            R.string.walkthrough_classroom_empty_title,
                            R.string.walkthrough_classroom_empty_student_body,
                            new WalkthroughOverlayView.Listener() {
                                @Override
                                public void onNext() {
                                    appPreferences.completeWalkthrough();
                                    dismissWalkthrough();
                                }

                                @Override
                                public void onSkip() {
                                    pauseWalkthrough();
                                }
                            }
                        );
                    }

                    @Override
                    public void onSkip() {
                        pauseWalkthrough();
                    }
                }
            );
        }
    }

    private void maybeShowMiniTip() {
        if (!appPreferences.isWalkthroughComplete() || classrooms.isEmpty() == false) {
            return;
        }
        if (appPreferences.shouldShowTip(WalkthroughManager.TIP_CLASSROOM) && !isTeacherView()) {
            appPreferences.markTipSeen(WalkthroughManager.TIP_CLASSROOM);
            Snackbar.make(findViewById(R.id.classroom_root), R.string.walkthrough_tip_classroom, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showWalkthroughStep(
        View target,
        int titleRes,
        int bodyRes,
        WalkthroughOverlayView.Listener listener
    ) {
        ensureWalkthroughOverlay();
        walkthroughOverlay.showForTarget(
            target,
            getString(titleRes),
            getString(bodyRes),
            getString(R.string.walkthrough_done),
            false,
            listener
        );
    }

    private void ensureWalkthroughOverlay() {
        if (walkthroughOverlay != null) {
            return;
        }
        walkthroughOverlay = new WalkthroughOverlayView(this);
        FrameLayout root = findViewById(android.R.id.content);
        root.addView(walkthroughOverlay, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ));
    }

    private void dismissWalkthrough() {
        if (walkthroughOverlay != null) {
            walkthroughOverlay.clearStep();
        }
    }

    private void pauseWalkthrough() {
        appPreferences.pauseWalkthrough();
        dismissWalkthrough();
    }
}
