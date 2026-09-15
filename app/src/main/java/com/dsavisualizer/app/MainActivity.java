package com.dsavisualizer.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.net.Uri;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.data.HomeCatalog;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueItem;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.ThemeHelper;
import com.dsavisualizer.app.ui.adapter.CategoryAdapter;
import com.dsavisualizer.app.ui.walkthrough.WalkthroughManager;
import com.dsavisualizer.app.ui.walkthrough.WalkthroughOverlayView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

// Google Sign-In
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import org.json.JSONObject;
import com.dsavisualizer.app.data.ApiClient;

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

/**
 * Home screen for DSA Visualizer Pro.
 * Shows searchable categories with a beginner-friendly starting point.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int AUTH_CONNECT_TIMEOUT_MS = 12000;
    private static final int AUTH_READ_TIMEOUT_MS = 15000;
    private static final int AUTH_MAX_ATTEMPTS = 3;

    private final List<CategoryItem> allCategories = new ArrayList<>();
    private CategoryAdapter adapter;
    private DrawerLayout drawerLayout;
    private ScrollView drawerScroll;
    private TextView progressText;
    private TextView accountModeText;
    private TextView accountStatusText;
    private TextView streakValueText;
    private TextView goalValueText;
    private TextView recentActivityEmptyText;
    private TextView weakTopicsText;
    private TextView continueLearningText;
    private TextView continueLearningSubtext;
    private TextView goalTitleText;
    private View controlCentreButton;
    private View classroomButton;
    private View walkthroughButton;
    private MaterialButton themeButton;
    private View menuButton;
    private View ownerManageUsersButton;
    private View ownerFeedbackButton;
    private View ownerAnalyticsButton;
    private View teacherFeedbackButton;
    private RecyclerView categoryRecyclerView;
    private LinearLayout recentQuizzesContainer;
    private ChipGroup weakTopicsChipGroup;
    private MaterialCardView goalCard;
    private MaterialCardView continueLearningCard;
    private boolean navigationInProgress = false;
    private AppPreferences appPreferences;
    private RemoteSyncManager remoteSyncManager;
    private String continueTechniqueId = "";

    // Google Sign-In
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;
    private final Handler walkthroughHandler = new Handler(Looper.getMainLooper());
    private WalkthroughOverlayView walkthroughOverlay;
    private boolean signInInFlight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.home_root), 20, 20, 20, 20);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.drawer_content), 16, 16, 16, 16);
        appPreferences = new AppPreferences(this);
        remoteSyncManager = RemoteSyncManager.getInstance(this);
        if (!appPreferences.hasGoogleSession()) {
            appPreferences.clearAuthSession();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize Google Sign-In
        initializeGoogleSignIn();
        attemptSilentSessionRefresh();

        EditText searchInput = findViewById(R.id.search_input);
        categoryRecyclerView = findViewById(R.id.category_recycler_view);
        drawerLayout = findViewById(R.id.home_drawer);
        drawerScroll = findViewById(R.id.home_drawer_scroll);
        progressText = findViewById(R.id.progress_text);
        accountModeText = findViewById(R.id.account_mode_text);
        accountStatusText = findViewById(R.id.account_status_text);
        streakValueText = findViewById(R.id.streak_value_text);
        goalValueText = findViewById(R.id.goal_value_text);
        recentActivityEmptyText = findViewById(R.id.recent_activity_empty_text);
        weakTopicsText = findViewById(R.id.weak_topics_text);
        continueLearningText = findViewById(R.id.continue_learning_text);
        continueLearningSubtext = findViewById(R.id.continue_learning_subtext);
        goalTitleText = findViewById(R.id.goal_title_text);
        controlCentreButton = findViewById(R.id.control_centre_button);
        classroomButton = findViewById(R.id.classroom_button);
        walkthroughButton = findViewById(R.id.walkthrough_button);
        themeButton = findViewById(R.id.theme_button);
        menuButton = findViewById(R.id.menu_button);
        ownerManageUsersButton = findViewById(R.id.owner_manage_users_button);
        ownerFeedbackButton = findViewById(R.id.owner_feedback_button);
        ownerAnalyticsButton = findViewById(R.id.owner_analytics_button);
        teacherFeedbackButton = findViewById(R.id.teacher_feedback_button);
        recentQuizzesContainer = findViewById(R.id.recent_quizzes_container);
        weakTopicsChipGroup = findViewById(R.id.weak_topics_chip_group);
        goalCard = findViewById(R.id.goal_card);
        continueLearningCard = findViewById(R.id.continue_learning_card);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        findViewById(R.id.feedback_button).setOnClickListener(v -> openFeedbackDialog());
        findViewById(R.id.privacy_policy_button).setOnClickListener(v -> openPrivacyPolicy());
        findViewById(R.id.delete_account_button).setOnClickListener(v -> confirmAccountDeletion());
        findViewById(R.id.switch_account_button).setOnClickListener(v -> switchGoogleAccount());
        findViewById(R.id.sign_out_button).setOnClickListener(v -> signOutAndReturnToLogin());
        walkthroughButton.setOnClickListener(v -> resumeWalkthroughFromDrawer());
        themeButton.setOnClickListener(v -> openThemeDialog());
        findViewById(R.id.about_button).setOnClickListener(v -> openAboutDialog());
        if (controlCentreButton != null) {
            controlCentreButton.setOnClickListener(v -> openControlCentre());
        }
        classroomButton.setOnClickListener(v -> openClassroom());
        ownerManageUsersButton.setOnClickListener(v -> openOwnerUsers());
        ownerFeedbackButton.setOnClickListener(v -> openOwnerFeedback());
        ownerAnalyticsButton.setOnClickListener(v -> openOwnerAnalytics());
        teacherFeedbackButton.setOnClickListener(v -> openTeacherFeedback());
        continueLearningCard.setOnClickListener(v -> {
            Object taggedTechnique = continueLearningCard.getTag();
            String selectedTechniqueId = taggedTechnique instanceof String
                ? (String) taggedTechnique
                : continueTechniqueId;
            if (selectedTechniqueId != null && !selectedTechniqueId.isEmpty()) {
                openTechnique(selectedTechniqueId);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        adapter = new CategoryAdapter(this::openCategory);
        int categorySpanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 2;
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, categorySpanCount));
        categoryRecyclerView.setAdapter(adapter);

        allCategories.addAll(HomeCatalog.getCategories());
        adapter.submitList(allCategories);
        updateThemeButtonLabel();
        updateProgress();
        updateDashboardFallback();
        refreshDashboard();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCategories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Setup Google Sign-In button
        findViewById(R.id.google_sign_in_button).setOnClickListener(v -> signInWithGoogle());
        findViewById(R.id.home_root).post(this::maybeStartHomeWalkthrough);
    }

    private void initializeGoogleSignIn() {
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail();
        if (isGoogleClientConfigured()) {
            builder.requestIdToken(BuildConfig.GOOGLE_CLIENT_ID);
        }
        GoogleSignInOptions gso = builder.build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleSignInResult(task);
                } else {
                    signInInFlight = false;
                    Toast.makeText(this, R.string.google_sign_in_cancelled, Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void signInWithGoogle() {
        if (signInInFlight) {
            return;
        }
        if (!isGoogleClientConfigured()) {
            Toast.makeText(this, R.string.google_sign_in_config_missing, Toast.LENGTH_LONG).show();
            return;
        }
        signInInFlight = true;
        GoogleSignInAccount cachedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (cachedAccount != null && isFreshIdToken(cachedAccount.getIdToken())) {
            authenticateWithServer(cachedAccount.getIdToken(), cachedAccount);
            return;
        }
        Task<GoogleSignInAccount> silentTask = googleSignInClient.silentSignIn();
        if (silentTask.isComplete()) {
            handleSilentSignInResult(silentTask);
            return;
        }
        silentTask.addOnCompleteListener(this, this::handleSilentSignInResult);
    }

    private void handleSilentSignInResult(Task<GoogleSignInAccount> task) {
        if (task.isSuccessful()) {
            GoogleSignInAccount account = task.getResult();
            if (account != null && isFreshIdToken(account.getIdToken())) {
                authenticateWithServer(account.getIdToken(), account);
                return;
            }
        }
        launchInteractiveGoogleSignIn();
    }

    private void launchInteractiveGoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void attemptSilentSessionRefresh() {
        if (!appPreferences.hasGoogleSession() || !isGoogleClientConfigured() || remoteSyncManager == null) {
            return;
        }
        remoteSyncManager.refreshCurrentUser(success -> {
            if (success) {
                runOnUiThread(this::updateProgress);
                return;
            }
            GoogleSignInAccount cachedAccount = GoogleSignIn.getLastSignedInAccount(this);
            if (cachedAccount != null && isFreshIdToken(cachedAccount.getIdToken())) {
                runOnUiThread(() -> authenticateWithServer(cachedAccount.getIdToken(), cachedAccount));
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            if (isFreshIdToken(idToken)) {
                authenticateWithServer(idToken, account);
            } else {
                signInInFlight = false;
                Toast.makeText(this, R.string.google_sign_in_failed_no_token, Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            signInInFlight = false;
            Toast.makeText(
                this,
                getString(R.string.google_sign_in_failed_status, e.getStatusCode()),
                Toast.LENGTH_LONG
            ).show();
        }
    }

    private void authenticateWithServer(String idToken, GoogleSignInAccount account) {
        Toast.makeText(this, R.string.google_sign_in_authenticating, Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            try {
                JSONObject data = performGoogleAuthExchange(idToken);
                JSONObject user = data.optJSONObject("user");
                String token = data.optString("token", "");
                String refreshToken = data.optString("refreshToken", "");
                String userId = user == null ? "" : user.optString("id", "");
                String displayName = user == null
                    ? (account.getDisplayName() == null ? "" : account.getDisplayName())
                    : user.optString("displayName", user.optString("name", ""));
                String role = user == null ? "student" : user.optString("role", "student");

                if (token.trim().isEmpty()) {
                    throw new IllegalStateException("Missing app session token");
                }

                appPreferences.saveAuthSession(token, refreshToken, userId, displayName, role, "google");

                runOnUiThread(() -> {
                    signInInFlight = false;
                    updateProgress();
                    refreshDashboard();
                    Toast.makeText(this, getString(R.string.google_sign_in_success, displayName), Toast.LENGTH_SHORT).show();
                });
            } catch (Exception error) {
                Log.e(TAG, "Google auth exchange failed", error);
                runOnUiThread(() -> {
                    signInInFlight = false;
                    Toast.makeText(this, toReadableAuthError(error), Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private JSONObject performGoogleAuthExchange(String idToken) throws Exception {
        return ApiClient.getInstance().performGoogleAuthExchange(idToken);
    }

    private String toReadableAuthError(Exception error) {
        if (error == null) {
            return getString(R.string.google_sign_in_failed_server);
        }
        String message = error.getMessage();
        if (message == null || message.trim().isEmpty()) {
            return getString(R.string.google_sign_in_failed_server);
        }

        int jsonStart = message.indexOf('{');
        if (jsonStart >= 0) {
            try {
                JSONObject payload = new JSONObject(message.substring(jsonStart));
                JSONObject errorObject = payload.optJSONObject("error");
                String serverMessage = errorObject == null ? "" : errorObject.optString("message", "");
                if (!serverMessage.trim().isEmpty()) {
                    return serverMessage;
                }
            } catch (Exception ignored) {
                // Fall back to the raw message below.
            }
        }

        return message;
    }

    private boolean isFreshIdToken(String idToken) {
        if (idToken == null || idToken.trim().isEmpty()) {
            return false;
        }
        try {
            String[] parts = idToken.split("\\.");
            if (parts.length < 2) {
                return false;
            }
            byte[] payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            JSONObject payload = new JSONObject(new String(payloadBytes, StandardCharsets.UTF_8));
            long expiresAtSeconds = payload.optLong("exp", 0L);
            long nowSeconds = System.currentTimeMillis() / 1000L;
            return expiresAtSeconds > nowSeconds + 60L;
        } catch (Exception error) {
            Log.w(TAG, "Could not inspect Google ID token expiry", error);
            return false;
        }
    }

    private void switchGoogleAccount() {
        if (!isGoogleClientConfigured()) {
            Toast.makeText(this, R.string.google_sign_in_config_missing, Toast.LENGTH_LONG).show();
            return;
        }
        if (signInInFlight) {
            return;
        }
        signInInFlight = true;
        appPreferences.clearAuthSession();
        drawerLayout.closeDrawer(GravityCompat.START);
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Toast.makeText(this, R.string.home_switch_account_done, Toast.LENGTH_SHORT).show();
            launchInteractiveGoogleSignIn();
        });
    }

    private void signOutAndReturnToLogin() {
        appPreferences.clearAuthSession();
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Toast.makeText(this, R.string.home_sign_out_done, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void openPrivacyPolicy() {
        String url = ApiConfig.getBaseUrl() + "/privacy-policy";
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open browser for Privacy Policy", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWebAccountDeletion() {
        String url = ApiConfig.getBaseUrl() + "/delete-account";
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open browser for Web Deletion page", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmAccountDeletion() {
        new MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_account_title)
            .setMessage(R.string.dialog_delete_account_message)
            .setNegativeButton(R.string.dialog_delete_account_cancel, null)
            .setNeutralButton(R.string.dialog_delete_account_web, (dialog, which) -> openWebAccountDeletion())
            .setPositiveButton(R.string.dialog_delete_account_confirm, (dialog, which) -> executeAccountDeletion())
            .show();
    }

    private void executeAccountDeletion() {
        Toast.makeText(this, R.string.toast_deleting_account, Toast.LENGTH_SHORT).show();
        RemoteSyncManager.getInstance(this).deleteAccount(success -> {
            appPreferences.clearAuthSession();
            try {
                if (googleSignInClient != null) {
                    googleSignInClient.revokeAccess();
                    googleSignInClient.signOut();
                }
            } catch (Exception ignored) {
            }
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, R.string.toast_delete_account_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    showDeletionFallbackDialog();
                }
            });
        });
    }

    private void showDeletionFallbackDialog() {
        new MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_account_fallback_title)
            .setMessage(R.string.dialog_delete_account_fallback_message)
            .setNegativeButton(R.string.dialog_delete_account_cancel, (dialog, which) -> {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            })
            .setPositiveButton(R.string.dialog_delete_account_open_web, (dialog, which) -> {
                openWebAccountDeletion();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            })
            .setCancelable(false)
            .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationInProgress = false;
        updateThemeButtonLabel();
        updateProgress();
        refreshDashboard();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    private void filterCategories(String query) {
        String normalized = query.trim().toLowerCase();
        if (normalized.isEmpty()) {
            adapter.submitList(allCategories);
            return;
        }

        List<CategoryItem> filtered = new ArrayList<>();
        for (CategoryItem item : allCategories) {
            if (item.title.toLowerCase().contains(normalized) || item.subtitle.toLowerCase().contains(normalized)) {
                filtered.add(item);
            }
        }
        adapter.submitList(filtered);
    }

    private void openCategory(CategoryItem categoryItem) {
        if (navigationInProgress) {
            return;
        }
        navigationInProgress = true;
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryItem.id);
        intent.putExtra(CategoryActivity.EXTRA_CATEGORY_TITLE, categoryItem.title);
        startActivity(intent);
    }

    private void updateProgress() {
        int visitedCount = appPreferences.getVisitedCount();
        int syncLabel;
        if (appPreferences.hasGoogleSession()) {
            syncLabel = R.string.sync_status_account_ready;
        } else if (appPreferences.hasGuestSession()) {
            syncLabel = R.string.sync_status_guest_local;
        } else {
            syncLabel = R.string.sync_status_guest_local;
        }
        progressText.setText(getString(R.string.home_progress_status, visitedCount, getString(syncLabel)));
        if (appPreferences.hasGoogleSession()) {
            accountModeText.setText(resolveRoleLabel());
            accountStatusText.setText(getString(R.string.home_account_signed_in_ready, resolveDisplayName()));
        } else {
            accountModeText.setText(getString(R.string.home_account_guest_badge));
            accountStatusText.setText(getString(R.string.home_account_guest_local, resolveDisplayName()));
        }
        updateOwnerActionsVisibility();
    }

    private void updateOwnerActionsVisibility() {
        String role = appPreferences.getAuthRole();
        boolean isStaff = "owner".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role);
        if (controlCentreButton != null) {
            controlCentreButton.setVisibility(isStaff ? View.VISIBLE : View.GONE);
        }
        classroomButton.setVisibility(appPreferences.hasGoogleSession() ? View.VISIBLE : View.GONE);
        ownerManageUsersButton.setVisibility("owner".equalsIgnoreCase(role) ? View.VISIBLE : View.GONE);
        ownerFeedbackButton.setVisibility("owner".equalsIgnoreCase(role) ? View.VISIBLE : View.GONE);
        ownerAnalyticsButton.setVisibility("owner".equalsIgnoreCase(role) ? View.VISIBLE : View.GONE);
        teacherFeedbackButton.setVisibility(isStaff ? View.VISIBLE : View.GONE);
    }

    private void refreshDashboard() {
        remoteSyncManager.fetchDashboard(snapshot -> {
            if (snapshot == null) {
                updateDashboardFallback();
                return;
            }
            updateProgress();
            streakValueText.setText(getString(R.string.home_streak_value, snapshot.streakDays));
            goalValueText.setText(getString(R.string.home_goal_value, snapshot.todaySessions, snapshot.dailyGoalTarget));
            bindGoalHighlight(snapshot.dailyGoalCompleted);
            bindRecentActivity(snapshot);
            bindWeakTopics(snapshot);
            updateContinueLearning(snapshot);
        });
    }

    private void updateDashboardFallback() {
        streakValueText.setText(getString(R.string.home_streak_value, 0));
        goalValueText.setText(getString(R.string.home_goal_value, 0, 3));
        bindGoalHighlight(false);
        bindLocalRecents();
        weakTopicsText.setVisibility(View.VISIBLE);
        weakTopicsText.setText(getString(R.string.home_weak_topics_empty));
        weakTopicsChipGroup.removeAllViews();
        updateContinueLearning(null);
    }

    private String buildLocalRecentActivityText() {
        List<String> recents = appPreferences.getRecents();
        if (recents.isEmpty()) {
            return getString(R.string.home_recent_activity_empty);
        }

        List<String> labels = new ArrayList<>();
        for (String recentsId : recents) {
            labels.add(prettifyTechniqueId(recentsId));
            if (labels.size() == 3) {
                break;
            }
        }
        return getString(R.string.home_recent_activity_prefix, android.text.TextUtils.join(", ", labels));
    }

    private String resolveDisplayName() {
        String displayName = appPreferences.getAuthDisplayName();
        return displayName.trim().isEmpty() ? getString(R.string.home_guest_default_name) : displayName;
    }

    private String resolveRoleLabel() {
        String role = appPreferences.getAuthRole();
        if ("owner".equalsIgnoreCase(role)) {
            return getString(R.string.home_account_owner_badge);
        }
        if ("teacher".equalsIgnoreCase(role)) {
            return getString(R.string.home_account_teacher_badge);
        }
        if ("student".equalsIgnoreCase(role)) {
            return getString(R.string.home_account_student_badge);
        }
        return getString(R.string.home_account_signed_in_badge);
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

    private String prettifyDifficulty(String difficulty) {
        if ("expert".equalsIgnoreCase(difficulty)) {
            return getString(R.string.difficulty_expert);
        }
        if ("level_2".equalsIgnoreCase(difficulty) || "intermediate".equalsIgnoreCase(difficulty)) {
            return getString(R.string.difficulty_level_2);
        }
        return getString(R.string.difficulty_guided);
    }

    private void bindRecentActivity(RemoteSyncManager.DashboardSnapshot snapshot) {
        recentQuizzesContainer.removeAllViews();
        if (snapshot.recentActivity.isEmpty()) {
            bindLocalRecents();
            return;
        }

        recentActivityEmptyText.setVisibility(View.GONE);
        int count = 0;
        for (RemoteSyncManager.RecentActivityItem item : snapshot.recentActivity) {
            recentQuizzesContainer.addView(createDrawerActionButton(
                getString(
                    R.string.home_recent_quiz_button,
                    prettifyTechniqueId(item.techniqueId),
                    prettifyDifficulty(item.difficulty)
                ),
                v -> openTechnique(item.techniqueId)
            ));
            count += 1;
            if (count == 4) {
                break;
            }
        }
    }

    private void bindLocalRecents() {
        recentQuizzesContainer.removeAllViews();
        List<String> recents = appPreferences.getRecents();
        if (recents.isEmpty()) {
            recentActivityEmptyText.setVisibility(View.VISIBLE);
            recentActivityEmptyText.setText(getString(R.string.home_recent_activity_empty));
            return;
        }
        recentActivityEmptyText.setVisibility(View.VISIBLE);
        recentActivityEmptyText.setText(buildLocalRecentActivityText());
        int count = 0;
        for (String techniqueId : recents) {
            recentQuizzesContainer.addView(createDrawerActionButton(
                prettifyTechniqueId(techniqueId),
                v -> openTechnique(techniqueId)
            ));
            count += 1;
            if (count == 3) {
                break;
            }
        }
    }

    private void bindWeakTopics(RemoteSyncManager.DashboardSnapshot snapshot) {
        weakTopicsChipGroup.removeAllViews();
        if (snapshot.weakTopics.isEmpty()) {
            weakTopicsText.setVisibility(View.VISIBLE);
            weakTopicsText.setText(getString(R.string.home_weak_topics_empty));
            return;
        }

        weakTopicsText.setVisibility(View.GONE);
        for (RemoteSyncManager.WeakTopicItem item : snapshot.weakTopics) {
            Chip chip = new Chip(this);
            chip.setText(getString(
                R.string.home_weak_topic_chip,
                prettifyTechniqueId(item.techniqueId),
                item.bestScore
            ));
            chip.setCheckable(false);
            chip.setClickable(true);
            chip.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(
                isLightTheme() ? Color.parseColor("#E7F0FA") : ContextCompat.getColor(this, R.color.surface_chip)
            ));
            chip.setOnClickListener(v -> openTechnique(item.techniqueId));
            weakTopicsChipGroup.addView(chip);
        }
    }

    private MaterialButton createDrawerActionButton(String text, View.OnClickListener onClickListener) {
        MaterialButton button = new MaterialButton(this, null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = dpToPx(8);
        button.setLayoutParams(params);
        button.setText(text);
        button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button.setOnClickListener(onClickListener);
        return button;
    }

    private void updateContinueLearning(RemoteSyncManager.DashboardSnapshot snapshot) {
        String nextTechniqueId = "";
        String continueDifficulty = "";
        if (snapshot != null && !snapshot.recentActivity.isEmpty()) {
            nextTechniqueId = snapshot.recentActivity.get(0).techniqueId;
            continueDifficulty = snapshot.recentActivity.get(0).difficulty;
        } else {
            List<String> recents = appPreferences.getRecents();
            if (!recents.isEmpty()) {
                nextTechniqueId = recents.get(0);
            }
        }

        continueTechniqueId = nextTechniqueId == null ? "" : nextTechniqueId;
        continueLearningCard.setTag(continueTechniqueId);
        if (continueTechniqueId.isEmpty()) {
            continueLearningText.setText(R.string.home_continue_learning_empty);
            continueLearningSubtext.setText(R.string.home_continue_learning_subtext);
            return;
        }

        continueLearningText.setText(prettifyTechniqueId(continueTechniqueId));
        if (continueDifficulty == null || continueDifficulty.trim().isEmpty()) {
            continueLearningSubtext.setText(getString(R.string.home_continue_learning_ready));
        } else {
            continueLearningSubtext.setText(getString(
                R.string.home_continue_learning_ready_with_level,
                prettifyDifficulty(continueDifficulty)
            ));
        }
    }

    private void openTechnique(String techniqueId) {
        if (techniqueId == null || techniqueId.trim().isEmpty() || navigationInProgress) {
            return;
        }
        TechniqueItem technique = AppRepository.getTechnique(techniqueId);
        if (technique == null) {
            Toast.makeText(this, R.string.home_technique_unavailable, Toast.LENGTH_SHORT).show();
            return;
        }
        navigationInProgress = true;
        drawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = new Intent(this, TechniqueDetailActivity.class);
        intent.putExtra(TechniqueDetailActivity.EXTRA_TECHNIQUE_ID, technique.id);
        startActivity(intent);
    }

    private void openControlCentre() {
        String role = appPreferences.getAuthRole();
        if (!"owner".equalsIgnoreCase(role) && !"teacher".equalsIgnoreCase(role)) {
            Toast.makeText(this, R.string.control_centre_unauthorized, Toast.LENGTH_SHORT).show();
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, ControlCentreActivity.class));
    }

    private void openOwnerUsers() {
        if (!"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.owner_users_owner_only, Toast.LENGTH_SHORT).show();
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, OwnerUsersActivity.class));
    }

    private void openOwnerFeedback() {
        if (!"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.owner_users_owner_only, Toast.LENGTH_SHORT).show();
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, OwnerFeedbackActivity.class));
    }

    private void openOwnerAnalytics() {
        if (!"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.owner_users_owner_only, Toast.LENGTH_SHORT).show();
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, OwnerAnalyticsActivity.class));
    }

    private void openTeacherFeedback() {
        if (!"teacher".equalsIgnoreCase(appPreferences.getAuthRole())
            && !"owner".equalsIgnoreCase(appPreferences.getAuthRole())) {
            Toast.makeText(this, R.string.teacher_feedback_teacher_only, Toast.LENGTH_SHORT).show();
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, TeacherFeedbackActivity.class));
    }

    private void openClassroom() {
        if (!appPreferences.hasGoogleSession()) {
            Toast.makeText(this, R.string.classroom_google_only, Toast.LENGTH_SHORT).show();
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, ClassroomActivity.class));
    }

    private void maybeStartHomeWalkthrough() {
        String stage = appPreferences.getWalkthroughStage();
        if (!WalkthroughManager.isHomeStage(stage) || appPreferences.isWalkthroughPaused()) {
            return;
        }
        startHomeWalkthrough(stage);
    }

    private void openThemeDialog() {
        String[] modes = new String[] {
            AppPreferences.THEME_SYSTEM,
            AppPreferences.THEME_LIGHT,
            AppPreferences.THEME_DARK
        };
        String[] labels = new String[] {
            getString(R.string.home_theme_option_system),
            getString(R.string.home_theme_option_light),
            getString(R.string.home_theme_option_dark)
        };

        String currentMode = appPreferences.getThemeMode();
        int selectedIndex = 0;
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equals(currentMode)) {
                selectedIndex = i;
                break;
            }
        }

        new AlertDialog.Builder(this)
            .setTitle(R.string.home_theme_dialog_title)
            .setSingleChoiceItems(labels, selectedIndex, (dialog, which) -> {
                String selectedMode = modes[which];
                if (!selectedMode.equals(appPreferences.getThemeMode())) {
                    appPreferences.setThemeMode(selectedMode);
                    ThemeHelper.applyThemeMode(selectedMode);
                    Toast.makeText(this, R.string.home_theme_changed, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                drawerLayout.closeDrawer(GravityCompat.START);
            })
            .setNegativeButton(android.R.string.cancel, null)
            .show();
    }

    private void openAboutDialog() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.home_about_title)
            .setMessage(R.string.home_about_message)
            .setPositiveButton(android.R.string.ok, null)
            .show();
    }

    private void resumeWalkthroughFromDrawer() {
        String stage = appPreferences.getWalkthroughStage();
        if (WalkthroughManager.STAGE_COMPLETE.equals(stage)) {
            appPreferences.resetWalkthrough();
            stage = appPreferences.getWalkthroughStage();
        }
        appPreferences.resumeWalkthrough();

        if (WalkthroughManager.isHomeStage(stage)) {
            startHomeWalkthrough(stage);
            return;
        }

        if (WalkthroughManager.STAGE_DETAIL.equals(stage)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, R.string.walkthrough_resume_open_topic, Toast.LENGTH_LONG).show();
            return;
        }

        if (WalkthroughManager.STAGE_CLASSROOM_STUDENT.equals(stage)
            || WalkthroughManager.STAGE_CLASSROOM_TEACHER.equals(stage)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            openClassroom();
        }
    }

    private void startHomeWalkthrough(String stage) {
        appPreferences.resumeWalkthrough();
        drawerLayout.closeDrawer(GravityCompat.START);
        showWalkthroughStep(
            categoryRecyclerView,
            R.string.walkthrough_categories_title,
            R.string.walkthrough_categories_body,
            R.string.walkthrough_next,
            false,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    showMenuWalkthroughStep(stage);
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void showMenuWalkthroughStep(String stage) {
        showWalkthroughStep(
            menuButton,
            R.string.walkthrough_menu_title,
            R.string.walkthrough_menu_body,
            R.string.walkthrough_next,
            false,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    drawerLayout.openDrawer(GravityCompat.START);
                    walkthroughHandler.postDelayed(() -> showRoleDrawerWalkthrough(stage), 240L);
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void showRoleDrawerWalkthrough(String stage) {
        Runnable afterThemeStep = () -> showRoleSpecificDrawerWalkthrough(stage);
        showDrawerStep(
            themeButton,
            R.string.walkthrough_theme_title,
            R.string.walkthrough_theme_body,
            afterThemeStep
        );
    }

    private void showRoleSpecificDrawerWalkthrough(String stage) {
        if (WalkthroughManager.STAGE_HOME_TEACHER.equals(stage)) {
            showDrawerStep(
                classroomButton,
                R.string.walkthrough_teacher_classroom_title,
                R.string.walkthrough_teacher_classroom_body,
                () -> showDrawerStep(
                    teacherFeedbackButton,
                    R.string.walkthrough_teacher_feedback_title,
                    R.string.walkthrough_teacher_feedback_body,
                    this::completeHomeWalkthrough
                )
            );
            return;
        }

        if (WalkthroughManager.STAGE_HOME_OWNER.equals(stage)) {
            showDrawerStep(
                ownerManageUsersButton,
                R.string.walkthrough_owner_users_title,
                R.string.walkthrough_owner_users_body,
                () -> showDrawerStep(
                    ownerAnalyticsButton,
                    R.string.walkthrough_owner_analytics_title,
                    R.string.walkthrough_owner_analytics_body,
                    this::completeHomeWalkthrough
                )
            );
            return;
        }

        showDrawerStep(
            classroomButton,
            R.string.walkthrough_profile_title,
            R.string.walkthrough_profile_body,
            this::completeHomeWalkthrough
        );
    }

    private void showDrawerStep(View target, int titleRes, int bodyRes, Runnable onFinish) {
        if (target == null) {
            completeHomeWalkthrough();
            return;
        }
        drawerScroll.post(() -> drawerScroll.smoothScrollTo(0, Math.max(0, target.getTop() - dpToPx(24))));
        walkthroughHandler.postDelayed(() -> showWalkthroughStep(
            target,
            titleRes,
            bodyRes,
            R.string.walkthrough_done,
            false,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    onFinish.run();
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        ), 180L);
    }

    private void completeHomeWalkthrough() {
        appPreferences.setWalkthroughStage(WalkthroughManager.STAGE_DETAIL);
        dismissWalkthrough();
        drawerLayout.closeDrawer(GravityCompat.START);
        Toast.makeText(this, R.string.walkthrough_resume_open_topic, Toast.LENGTH_LONG).show();
    }

    private void showWalkthroughStep(
        View target,
        int titleRes,
        int bodyRes,
        int nextRes,
        boolean hideSkip,
        WalkthroughOverlayView.Listener listener
    ) {
        if (target == null) {
            return;
        }
        ensureWalkthroughOverlay();
        walkthroughOverlay.showForTarget(
            target,
            getString(titleRes),
            getString(bodyRes),
            getString(nextRes),
            hideSkip,
            listener
        );
    }

    private void ensureWalkthroughOverlay() {
        if (walkthroughOverlay != null) {
            return;
        }
        walkthroughOverlay = new WalkthroughOverlayView(this);
        FrameLayout contentRoot = findViewById(android.R.id.content);
        contentRoot.addView(walkthroughOverlay, new FrameLayout.LayoutParams(
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
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void bindGoalHighlight(boolean completed) {
        int cardColor = completed ? Color.parseColor("#173B2E") : ContextCompat.getColor(this, R.color.surface_panel_muted);
        int strokeColor = completed ? Color.parseColor("#4ADE80") : ContextCompat.getColor(this, R.color.surface_stroke_muted);
        goalCard.setCardBackgroundColor(cardColor);
        goalCard.setStrokeColor(strokeColor);
        goalTitleText.setText(completed ? R.string.home_goal_title_done : R.string.home_goal_title);
    }

    private void updateThemeButtonLabel() {
        if (themeButton == null) {
            return;
        }
        String mode = appPreferences.getThemeMode();
        if (AppPreferences.THEME_LIGHT.equals(mode)) {
            themeButton.setText(R.string.home_theme_light);
            return;
        }
        if (AppPreferences.THEME_DARK.equals(mode)) {
            themeButton.setText(R.string.home_theme_dark);
            return;
        }
        themeButton.setText(R.string.home_theme_system);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private void openFeedbackDialog() {
        if (!appPreferences.hasGoogleSession()) {
            Toast.makeText(this, R.string.home_feedback_google_required, Toast.LENGTH_LONG).show();
            return;
        }
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(20);
        container.setPadding(padding, dpToPx(8), padding, 0);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        String[] types = new String[] { "bug", "content", "feature" };
        int[] labels = new int[] {
            R.string.home_feedback_bug,
            R.string.home_feedback_content,
            R.string.home_feedback_feature
        };
        for (int i = 0; i < types.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(View.generateViewId());
            radioButton.setTag(types[i]);
            radioButton.setText(labels[i]);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }

        EditText messageInput = new EditText(this);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        inputParams.topMargin = dpToPx(12);
        messageInput.setLayoutParams(inputParams);
        messageInput.setMinLines(4);
        messageInput.setHint(R.string.home_feedback_body);
        messageInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        messageInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        container.addView(radioGroup);
        container.addView(messageInput);

        new AlertDialog.Builder(this)
            .setTitle(R.string.home_feedback_title)
            .setView(container)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.home_feedback_send, (dialog, which) -> {
                String message = messageInput.getText() == null ? "" : messageInput.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(this, R.string.home_feedback_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                View checkedView = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                String type = checkedView != null && checkedView.getTag() != null
                    ? checkedView.getTag().toString()
                    : "bug";
                remoteSyncManager.submitFeedback(type, message, continueTechniqueId, "home", "", success -> {
                    Toast.makeText(
                        this,
                        success ? R.string.home_feedback_sent : R.string.home_feedback_failed,
                        Toast.LENGTH_SHORT
                    ).show();
                });
            })
            .show();
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

    private boolean isGoogleClientConfigured() {
        String clientId = BuildConfig.GOOGLE_CLIENT_ID;
        return clientId != null && !clientId.trim().isEmpty() && !clientId.contains("YOUR_");
    }

    private boolean isLightTheme() {
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true)) {
            return typedValue.data != 0;
        }
        return false;
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
