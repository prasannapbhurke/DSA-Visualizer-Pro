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

import com.dsavisualizer.app.data.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class OwnerUsersActivity extends AppCompatActivity {
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
        ApiClient.getInstance().get("/api/users", appPreferences.getAuthToken(), new ApiClient.Callback() {
            @Override
            public void onSuccess(@NonNull JSONObject payload) {
                JSONObject data = payload.optJSONObject("data");
                JSONArray items = data == null ? null : data.optJSONArray("users");
                List<ManagedUser> loadedUsers = new ArrayList<>();
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.optJSONObject(i);
                        if (item != null) {
                            loadedUsers.add(parseManagedUser(item));
                        }
                    }
                }
                users.clear();
                users.addAll(loadedUsers);
                sortAndSubmitUsers();
                bindSummary();
                bindEmptyState(false);
                setLoading(false);
            }

            @Override
            public void onError(@NonNull Exception error) {
                bindEmptyState(true);
                setLoading(false);
                if (error instanceof ApiClient.ApiException) {
                    int code = ((ApiClient.ApiException) error).getStatusCode();
                    if (code == 401 || code == 403) {
                        handleRejectedOwnerSession();
                        return;
                    }
                }
                Toast.makeText(OwnerUsersActivity.this, R.string.owner_users_load_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void toggleUserRole(@NonNull ManagedUser user) {
        if ("owner".equalsIgnoreCase(user.role)) {
            return;
        }
        String nextRole = "teacher".equalsIgnoreCase(user.role) ? "student" : "teacher";
        adapter.setPendingUserId(user.id);

        try {
            JSONObject body = new JSONObject();
            body.put("role", nextRole);

            ApiClient.getInstance().put("/api/users/" + user.id + "/role", body, appPreferences.getAuthToken(), new ApiClient.Callback() {
                @Override
                public void onSuccess(@NonNull JSONObject payload) {
                    adapter.setPendingUserId("");
                    JSONObject data = payload.optJSONObject("data");
                    JSONObject userObject = data == null ? null : data.optJSONObject("user");
                    ManagedUser updatedUser = userObject == null ? null : parseManagedUser(userObject);
                    if (updatedUser != null) {
                        replaceUser(updatedUser);
                        bindSummary();
                    }
                    Toast.makeText(
                        OwnerUsersActivity.this,
                        getString("teacher".equalsIgnoreCase(nextRole)
                            ? R.string.owner_users_promote_success
                            : R.string.owner_users_demote_success, user.getPrimaryLabel()),
                        Toast.LENGTH_SHORT
                    ).show();
                }

                @Override
                public void onError(@NonNull Exception error) {
                    adapter.setPendingUserId("");
                    if (error instanceof ApiClient.ApiException) {
                        int code = ((ApiClient.ApiException) error).getStatusCode();
                        if (code == 401 || code == 403) {
                            handleRejectedOwnerSession();
                            return;
                        }
                    }
                    Toast.makeText(OwnerUsersActivity.this, R.string.owner_users_role_update_failed, Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            adapter.setPendingUserId("");
            Toast.makeText(this, R.string.owner_users_role_update_failed, Toast.LENGTH_LONG).show();
        }
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
}
