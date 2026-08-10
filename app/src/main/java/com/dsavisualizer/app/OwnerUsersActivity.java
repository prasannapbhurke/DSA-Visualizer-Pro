package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.model.ManagedUser;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.OwnerUserAdapter;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OwnerUsersActivity extends AppCompatActivity {
    private static final int USERS_CONNECT_TIMEOUT_MS = 12000;
    private static final int USERS_READ_TIMEOUT_MS = 15000;
    private static final int USERS_MAX_ATTEMPTS = 2;
    private final List<ManagedUser> users = new ArrayList<>();

    private AppPreferences appPreferences;
    private OwnerUserAdapter adapter;
    private ProgressBar loadingIndicator;
    private TextView summaryText;
    private TextView emptyText;
    private MaterialButton retryButton;
    private Spinner sortSpinner;
    private int selectedSortIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_users);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.owner_users_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        if (!"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.owner_users_owner_only, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.owner_users_back_button).setOnClickListener(v -> finish());
        loadingIndicator = findViewById(R.id.owner_users_loading);
        summaryText = findViewById(R.id.owner_users_summary_text);
        emptyText = findViewById(R.id.owner_users_empty_text);
        retryButton = findViewById(R.id.owner_users_retry_button);
        sortSpinner = findViewById(R.id.owner_users_sort_spinner);
        retryButton.setOnClickListener(v -> fetchUsers());
        setupSortSpinner();

        RecyclerView recyclerView = findViewById(R.id.owner_users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OwnerUserAdapter(this::toggleUserRole);
        recyclerView.setAdapter(adapter);

        fetchUsers();
    }

    private void fetchUsers() {
        setLoading(true);
        new Thread(() -> {
            try {
                JSONObject payload = performUsersRequest();
                JSONObject data = payload.optJSONObject("data");
                JSONArray items = data == null ? null : data.optJSONArray("users");
                List<ManagedUser> loadedUsers = new ArrayList<>();
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        loadedUsers.add(parseManagedUser(item));
                    }
                }

                runOnUiThread(() -> {
                    users.clear();
                    users.addAll(loadedUsers);
                    sortAndSubmitUsers();
                    bindSummary();
                    bindEmptyState(false);
                    setLoading(false);
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    bindEmptyState(true);
                    setLoading(false);
                    if (error instanceof AuthRejectedException) {
                        handleRejectedOwnerSession();
                        return;
                    }
                    Toast.makeText(this, R.string.owner_users_load_failed, Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private JSONObject performUsersRequest() throws Exception {
        Exception lastError = null;
        for (int attempt = 1; attempt <= USERS_MAX_ATTEMPTS; attempt++) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/users");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(USERS_CONNECT_TIMEOUT_MS);
                connection.setReadTimeout(USERS_READ_TIMEOUT_MS);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                int statusCode = connection.getResponseCode();
                String responseBody = readStream(statusCode >= 200 && statusCode < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream());

                if (statusCode >= 200 && statusCode < 300) {
                    return new JSONObject(responseBody);
                }
                if (statusCode == 401 || statusCode == 403) {
                    throw new AuthRejectedException("Owner session rejected");
                }

                boolean shouldRetry = statusCode >= 500 || statusCode == 408 || statusCode == 429;
                IllegalStateException error = new IllegalStateException(
                    "Owner users request failed with status " + statusCode + ": " + responseBody
                );
                if (!shouldRetry || attempt == USERS_MAX_ATTEMPTS) {
                    throw error;
                }
                lastError = error;
            } catch (AuthRejectedException error) {
                throw error;
            } catch (Exception error) {
                lastError = error;
                if (attempt == USERS_MAX_ATTEMPTS) {
                    throw error;
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            try {
                Thread.sleep(900L * attempt);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                throw interruptedException;
            }
        }
        throw lastError == null ? new IllegalStateException("Owner users request failed") : lastError;
    }

    private void toggleUserRole(@NonNull ManagedUser user) {
        if ("owner".equalsIgnoreCase(user.role)) {
            return;
        }
        String nextRole = "teacher".equalsIgnoreCase(user.role) ? "student" : "teacher";
        adapter.setPendingUserId(user.id);

        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/users/" + user.id + "/role");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                JSONObject body = new JSONObject();
                body.put("role", nextRole);
                try (OutputStream outputStream = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    writer.write(body.toString());
                }

                int statusCode = connection.getResponseCode();
                String responseBody = readStream(statusCode >= 200 && statusCode < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream());

                if (statusCode < 200 || statusCode >= 300) {
                    if (statusCode == 401 || statusCode == 403) {
                        throw new AuthRejectedException("Owner session rejected");
                    }
                    throw new IllegalStateException(responseBody);
                }

                JSONObject payload = new JSONObject(responseBody);
                JSONObject data = payload.optJSONObject("data");
                JSONObject userObject = data == null ? null : data.optJSONObject("user");
                ManagedUser updatedUser = userObject == null ? null : parseManagedUser(userObject);

                runOnUiThread(() -> {
                    adapter.setPendingUserId("");
                    if (updatedUser != null) {
                        replaceUser(updatedUser);
                        bindSummary();
                    }
                    Toast.makeText(
                        this,
                        getString("teacher".equalsIgnoreCase(nextRole)
                            ? R.string.owner_users_promote_success
                            : R.string.owner_users_demote_success, user.getPrimaryLabel()),
                        Toast.LENGTH_SHORT
                    ).show();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    adapter.setPendingUserId("");
                    if (error instanceof AuthRejectedException) {
                        handleRejectedOwnerSession();
                        return;
                    }
                    Toast.makeText(this, R.string.owner_users_role_update_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private ManagedUser parseManagedUser(JSONObject item) {
        return new ManagedUser(
            item.optString("id", ""),
            item.optString("displayName", item.optString("name", "")),
            item.optString("email", ""),
            item.optString("role", "student"),
            item.optString("lastSeenAt", ""),
            item.optString("createdAt", "")
        );
    }

    private void replaceUser(ManagedUser updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id.equals(updatedUser.id)) {
                users.set(i, updatedUser);
                sortAndSubmitUsers();
                return;
            }
        }
        users.add(updatedUser);
        sortAndSubmitUsers();
    }

    private void setupSortSpinner() {
        if (sortSpinner == null) {
            return;
        }
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.owner_users_sort_options,
            R.layout.item_owner_user_sort_selected
        );
        sortAdapter.setDropDownViewResource(R.layout.item_owner_user_sort_dropdown);
        sortSpinner.setAdapter(sortAdapter);
        sortSpinner.setSelection(selectedSortIndex);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selectedSortIndex == position && users.isEmpty()) {
                    return;
                }
                selectedSortIndex = position;
                sortAndSubmitUsers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Keep the last selected sort.
            }
        });
    }

    private void sortAndSubmitUsers() {
        Collections.sort(users, resolveUserComparator());
        adapter.submitList(new ArrayList<>(users));
    }

    private Comparator<ManagedUser> resolveUserComparator() {
        switch (selectedSortIndex) {
            case 1:
                return Comparator.comparing(user -> normalizedDate(user.lastSeenAt));
            case 2:
                return Comparator.comparing(this::normalizedDisplayName);
            case 3:
                return Comparator.comparing(this::normalizedDisplayName).reversed();
            case 4:
                return Comparator.comparing((ManagedUser user) -> normalizedDate(user.createdAt)).reversed();
            case 5:
                return Comparator.comparing(user -> normalizedDate(user.createdAt));
            case 6:
                return Comparator
                    .comparing((ManagedUser user) -> normalizedText(user.role))
                    .thenComparing(this::normalizedDisplayName);
            case 0:
            default:
                return Comparator.comparing((ManagedUser user) -> normalizedDate(user.lastSeenAt)).reversed();
        }
    }

    private String normalizedDisplayName(ManagedUser user) {
        String primary = user == null ? "" : user.getPrimaryLabel();
        return normalizedText(primary);
    }

    private String normalizedDate(String value) {
        return value == null ? "" : value.trim();
    }

    private String normalizedText(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.US);
    }

    private void bindSummary() {
        int teacherCount = 0;
        int studentCount = 0;
        for (ManagedUser user : users) {
            if ("teacher".equalsIgnoreCase(user.role)) {
                teacherCount += 1;
            } else if ("student".equalsIgnoreCase(user.role)) {
                studentCount += 1;
            }
        }
        summaryText.setText(getString(R.string.owner_users_summary, users.size(), teacherCount, studentCount));
        emptyText.setVisibility(users.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void bindEmptyState(boolean failed) {
        if (failed) {
            emptyText.setText(R.string.owner_users_load_failed_hint);
            emptyText.setVisibility(View.VISIBLE);
            summaryText.setText(R.string.owner_users_summary_empty);
            return;
        }
        emptyText.setText(R.string.owner_users_empty);
        emptyText.setVisibility(users.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
        retryButton.setEnabled(!loading);
        retryButton.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
    }

    private void handleRejectedOwnerSession() {
        appPreferences.clearAuthSession();
        Toast.makeText(this, R.string.owner_users_session_expired, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    private static final class AuthRejectedException extends Exception {
        AuthRejectedException(String message) {
            super(message);
        }
    }
}
