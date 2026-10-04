package com.dsavisualizer.app.data;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Small background sync layer for guest auth, authenticated progress, and practice sessions.
 * Uses HttpURLConnection to avoid adding another networking stack.
 */
public class RemoteSyncManager {
    private static final long AUTH_REFRESH_CACHE_MS = 3 * 60 * 1000L;

    public interface AuthStateCallback {
        void onResult(boolean ready);
    }

    public interface DashboardCallback {
        void onResult(@Nullable DashboardSnapshot snapshot);
    }

    public interface CompletionCallback {
        void onResult(boolean success);
    }

    public interface CurrentUserCallback {
        void onResult(boolean success);
    }

    public static class TechniqueProgress {
        public final int attempts;
        public final int bestScore;
        public final boolean completed;
        public final String lastDifficulty;
        public final String masteryLevel;
        public final String lastPlayedAt;

        public TechniqueProgress(int attempts, int bestScore, boolean completed, String lastDifficulty, String masteryLevel, String lastPlayedAt) {
            this.attempts = attempts;
            this.bestScore = bestScore;
            this.completed = completed;
            this.lastDifficulty = lastDifficulty == null ? "" : lastDifficulty;
            this.masteryLevel = masteryLevel == null ? "" : masteryLevel;
            this.lastPlayedAt = lastPlayedAt == null ? "" : lastPlayedAt;
        }
    }

    public static class RecentActivityItem {
        public final String techniqueId;
        public final String difficulty;
        public final int score;
        public final String createdAt;

        public RecentActivityItem(String techniqueId, String difficulty, int score, String createdAt) {
            this.techniqueId = techniqueId == null ? "" : techniqueId;
            this.difficulty = difficulty == null ? "" : difficulty;
            this.score = score;
            this.createdAt = createdAt == null ? "" : createdAt;
        }
    }

    public static class WeakTopicItem {
        public final String techniqueId;
        public final int bestScore;
        public final int attempts;
        public final String masteryLevel;

        public WeakTopicItem(String techniqueId, int bestScore, int attempts, String masteryLevel) {
            this.techniqueId = techniqueId == null ? "" : techniqueId;
            this.bestScore = bestScore;
            this.attempts = attempts;
            this.masteryLevel = masteryLevel == null ? "" : masteryLevel;
        }
    }

    public static class DashboardSnapshot {
        public final String userDisplayName;
        public final String userRole;
        public final int totalTechniquesStarted;
        public final int completedTechniques;
        public final int totalPracticeSessions;
        public final int streakDays;
        public final int todaySessions;
        public final int dailyGoalTarget;
        public final boolean dailyGoalCompleted;
        public final java.util.List<RecentActivityItem> recentActivity;
        public final java.util.List<WeakTopicItem> weakTopics;

        public DashboardSnapshot(
            String userDisplayName,
            String userRole,
            int totalTechniquesStarted,
            int completedTechniques,
            int totalPracticeSessions,
            int streakDays,
            int todaySessions,
            int dailyGoalTarget,
            boolean dailyGoalCompleted,
            java.util.List<RecentActivityItem> recentActivity,
            java.util.List<WeakTopicItem> weakTopics
        ) {
            this.userDisplayName = userDisplayName == null ? "" : userDisplayName;
            this.userRole = userRole == null ? "" : userRole;
            this.totalTechniquesStarted = totalTechniquesStarted;
            this.completedTechniques = completedTechniques;
            this.totalPracticeSessions = totalPracticeSessions;
            this.streakDays = streakDays;
            this.todaySessions = todaySessions;
            this.dailyGoalTarget = dailyGoalTarget;
            this.dailyGoalCompleted = dailyGoalCompleted;
            this.recentActivity = recentActivity == null ? java.util.Collections.emptyList() : recentActivity;
            this.weakTopics = weakTopics == null ? java.util.Collections.emptyList() : weakTopics;
        }
    }

    private static volatile RemoteSyncManager instance;

    private final Context appContext;
    private final AppPreferences preferences;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private volatile boolean guestInitInFlight = false;
    private final Object refreshLock = new Object();
    private volatile boolean refreshInFlight = false;

    private RemoteSyncManager(Context context) {
        appContext = context.getApplicationContext();
        preferences = new AppPreferences(appContext);
    }

    public static RemoteSyncManager getInstance(Context context) {
        if (instance == null) {
            synchronized (RemoteSyncManager.class) {
                if (instance == null) {
                    instance = new RemoteSyncManager(context);
                }
            }
        }
        return instance;
    }

    public void ensureGuestSession() {
        ensureGuestSession(null);
    }

    public void ensureGuestSession(@Nullable AuthStateCallback callback) {
        if (!preferences.hasAuthSession()) {
            dispatchAuthResult(callback, false);
            return;
        }
        if (!preferences.hasGuestSession()) {
            dispatchAuthResult(callback, true);
            return;
        }
        if (preferences.hasAuthSession()) {
            dispatchAuthResult(callback, true);
            return;
        }
        if (guestInitInFlight) {
            return;
        }

        guestInitInFlight = true;
        executor.execute(() -> {
            boolean success = false;
            try {
                JSONObject body = new JSONObject();
                body.put("displayName", resolveGuestDisplayName());
                JSONObject response = performJsonRequest("POST", "/api/auth/guest", null, body);
                JSONObject data = response.optJSONObject("data");
                JSONObject user = data == null ? null : data.optJSONObject("user");
                String token = data == null ? "" : data.optString("token", "");
                String userId = user == null ? "" : user.optString("id", "");
                String displayName = user == null ? resolveGuestDisplayName() : user.optString("displayName", resolveGuestDisplayName());
                if (!token.trim().isEmpty()) {
                    preferences.saveGuestSession(token, userId, displayName);
                    success = true;
                }
            } catch (Exception ignored) {
                success = false;
            } finally {
                guestInitInFlight = false;
                dispatchAuthResult(callback, success || preferences.hasAuthSession());
            }
        });
    }

    public void syncTechniqueOpened(@NonNull String techniqueId) {
        preferences.markVisited(techniqueId);
        preferences.addRecent(techniqueId);
    }

    public void savePracticeProgress(@NonNull String techniqueId, @NonNull String difficulty, int score, boolean completed) {
        if (!preferences.hasGoogleSession()) {
            return;
        }
        ensureGuestSession(ready -> {
            if (!ready) {
                return;
            }
            executor.execute(() -> {
                try {
                    String token = preferences.getAuthToken();
                    TechniqueProgress current = loadTechniqueProgress(token, techniqueId);
                    int nextAttempts = current.attempts + 1;
                    int nextBestScore = Math.max(current.bestScore, score);
                    String masteryLevel = resolveMasteryLevel(nextBestScore);

                    JSONObject progress = new JSONObject();
                    progress.put("attempts", nextAttempts);
                    progress.put("bestScore", nextBestScore);
                    progress.put("completed", current.completed || completed);
                    progress.put("lastDifficulty", difficulty);
                    progress.put("masteryLevel", masteryLevel);
                    progress.put("lastPlayedAt", formatIsoNow());

                    JSONObject body = new JSONObject();
                    body.put("progress", progress);
                    performJsonRequest("PUT", "/api/techniques/" + techniqueId + "/progress", token, body);
                } catch (Exception ignored) {
                    // Keep the app responsive even if the backend is unavailable.
                }
            });
        });
    }

    public void savePracticeSession(@NonNull String techniqueId, @NonNull String difficulty, int score, long durationMs) {
        if (!preferences.hasGoogleSession()) {
            return;
        }
        ensureGuestSession(ready -> {
            if (!ready) {
                return;
            }
            executor.execute(() -> {
                try {
                    JSONObject session = new JSONObject();
                    session.put("techniqueId", techniqueId);
                    session.put("difficulty", difficulty);
                    session.put("score", Math.max(0, score));
                    session.put("durationMs", Math.max(0L, durationMs));

                    JSONObject body = new JSONObject();
                    body.put("session", session);
                    performJsonRequest("POST", "/api/sessions", preferences.getAuthToken(), body);
                } catch (Exception ignored) {
                    // Keep the app responsive even if the backend is unavailable.
                }
            });
        });
    }

    public void fetchDashboard(@Nullable DashboardCallback callback) {
        if (!preferences.hasGoogleSession()) {
            dispatchDashboardResult(callback, null);
            return;
        }
        ensureGuestSession(ready -> {
            if (!ready) {
                dispatchDashboardResult(callback, null);
                return;
            }
            executor.execute(() -> {
                try {
                    JSONObject response = performJsonRequest("GET", "/api/dashboard", preferences.getAuthToken(), null);
                    JSONObject data = response.optJSONObject("data");
                    JSONObject dashboard = data == null ? null : data.optJSONObject("dashboard");
                    dispatchDashboardResult(callback, dashboard == null ? null : parseDashboard(dashboard));
                } catch (Exception ignored) {
                    dispatchDashboardResult(callback, null);
                }
            });
        });
    }

    public void refreshCurrentUser(@Nullable CurrentUserCallback callback) {
        if (!preferences.hasGoogleSession()) {
            dispatchCurrentUserResult(callback, false);
            return;
        }
        if (preferences.isAuthValidationFresh(AUTH_REFRESH_CACHE_MS)) {
            dispatchCurrentUserResult(callback, true);
            return;
        }
        executor.execute(() -> {
            try {
                JSONObject response = performJsonRequest("GET", "/api/me", preferences.getAuthToken(), null);
                JSONObject data = response.optJSONObject("data");
                JSONObject user = data == null ? null : data.optJSONObject("user");
                if (user == null) {
                    dispatchCurrentUserResult(callback, false);
                    return;
                }
                applyCurrentUser(user);
                preferences.markAuthValidatedNow();
                dispatchCurrentUserResult(callback, true);
            } catch (Exception ignored) {
                dispatchCurrentUserResult(callback, false);
            }
        });
    }

    public interface StudentFeedbackCallback {
        void onResult(@NonNull java.util.List<com.dsavisualizer.app.model.FeedbackInboxItem> items);
    }

    public void submitFeedback(
        @NonNull String type,
        @NonNull String message,
        @Nullable String techniqueId,
        @Nullable String screen,
        @Nullable String difficulty,
        @Nullable CompletionCallback callback
    ) {
        if (!preferences.hasGoogleSession()) {
            dispatchCompletion(callback, false);
            return;
        }
        ensureGuestSession(ready -> {
            if (!ready) {
                dispatchCompletion(callback, false);
                return;
            }
            executor.execute(() -> {
                try {
                    JSONObject feedback = new JSONObject();
                    feedback.put("type", type);
                    feedback.put("message", message);
                    feedback.put("techniqueId", techniqueId == null ? "" : techniqueId);
                    feedback.put("screen", screen == null ? "" : screen);
                    feedback.put("difficulty", difficulty == null ? "" : difficulty);

                    // Automatic Device Diagnostic Telemetry
                    JSONObject deviceInfo = new JSONObject();
                    try {
                        deviceInfo.put("deviceModel", Build.MANUFACTURER + " " + Build.MODEL);
                        deviceInfo.put("osVersion", "Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");
                        String versionName = "1.0.0";
                        long versionCode = 1;
                        try {
                            android.content.pm.PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
                            versionName = pInfo.versionName;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                versionCode = pInfo.getLongVersionCode();
                            } else {
                                versionCode = pInfo.versionCode;
                            }
                        } catch (Exception ignored) {}
                        deviceInfo.put("appVersion", "v" + versionName + " (" + versionCode + ")");
                        android.util.DisplayMetrics dm = appContext.getResources().getDisplayMetrics();
                        deviceInfo.put("display", dm.widthPixels + "x" + dm.heightPixels + " (" + dm.densityDpi + "dpi)");
                    } catch (Exception ignored) {}
                    feedback.put("deviceInfo", deviceInfo);

                    JSONObject body = new JSONObject();
                    body.put("feedback", feedback);
                    performJsonRequest("POST", "/api/feedback", preferences.getAuthToken(), body);
                    dispatchCompletion(callback, true);
                } catch (Exception ignored) {
                    dispatchCompletion(callback, false);
                }
            });
        });
    }

    public void fetchStudentFeedback(@Nullable StudentFeedbackCallback callback) {
        if (!preferences.hasGoogleSession()) {
            if (callback != null) {
                mainHandler.post(() -> callback.onResult(new java.util.ArrayList<>()));
            }
            return;
        }
        executor.execute(() -> {
            java.util.List<com.dsavisualizer.app.model.FeedbackInboxItem> resultList = new java.util.ArrayList<>();
            try {
                JSONObject res = performJsonRequest("GET", "/api/student/feedback", preferences.getAuthToken(), null, false);
                org.json.JSONArray array = res.optJSONArray("data");
                if (array == null && res.has("feedback")) {
                    array = res.optJSONArray("feedback");
                }
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        resultList.add(new com.dsavisualizer.app.model.FeedbackInboxItem(
                            obj.optString("id", ""),
                            obj.optString("type", ""),
                            obj.optString("message", ""),
                            obj.optString("techniqueId", obj.optString("technique_id", "")),
                            obj.optString("screen", ""),
                            obj.optString("difficulty", ""),
                            obj.optString("createdAt", obj.optString("created_at", "")),
                            obj.optString("userDisplayName", obj.optString("user_display_name", "")),
                            obj.optString("userEmail", obj.optString("user_email", "")),
                            obj.optString("userRole", obj.optString("user_role", "")),
                            obj.optString("classId", obj.optString("class_id", "")),
                            obj.optString("className", obj.optString("class_name", "")),
                            obj.optString("reviewedAt", obj.optString("reviewed_at", "")),
                            obj.optString("reviewedBy", obj.optString("reviewed_by", "")),
                            obj.optString("adminReply", obj.optString("admin_reply", "")),
                            obj.optString("repliedAt", obj.optString("replied_at", ""))
                        ));
                    }
                }
            } catch (Exception e) {
                Log.w("RemoteSyncManager", "fetchStudentFeedback error", e);
            }
            if (callback != null) {
                mainHandler.post(() -> callback.onResult(resultList));
            }
        });
    }

    public void deleteAccount(@Nullable CompletionCallback callback) {
        executor.execute(() -> {
            boolean success = false;
            try {
                String token = preferences.getAuthToken();
                if (token != null && !token.trim().isEmpty()) {
                    try {
                        performJsonRequest("DELETE", "/api/auth/account", token, null, false);
                        success = true;
                    } catch (Exception primaryErr) {
                        try {
                            performJsonRequest("DELETE", "/api/users/me", token, null, false);
                            success = true;
                        } catch (Exception fallbackErr) {
                            Log.e("RemoteSyncManager", "Failed to delete account on server", fallbackErr);
                            success = false;
                        }
                    }
                } else {
                    success = true;
                }
            } catch (Exception ignored) {
                success = false;
            } finally {
                dispatchCompletion(callback, success);
            }
        });
    }

    private TechniqueProgress loadTechniqueProgress(String token, String techniqueId) throws IOException, JSONException {
        JSONObject response = performJsonRequest("GET", "/api/techniques/" + techniqueId + "/progress", token, null);
        JSONObject data = response.optJSONObject("data");
        JSONObject progress = data == null ? null : data.optJSONObject("progress");
        if (progress == null) {
            return new TechniqueProgress(0, 0, false, "", "", "");
        }
        return new TechniqueProgress(
            progress.optInt("attempts", 0),
            progress.optInt("bestScore", 0),
            progress.optBoolean("completed", false),
            progress.optString("lastDifficulty", ""),
            progress.optString("masteryLevel", ""),
            progress.optString("lastPlayedAt", "")
        );
    }

    private JSONObject performJsonRequest(String method, String endpoint, @Nullable String bearerToken, @Nullable JSONObject body)
        throws IOException, JSONException {
        return performJsonRequest(method, endpoint, bearerToken, body, true);
    }

    private JSONObject performJsonRequest(
        String method,
        String endpoint,
        @Nullable String bearerToken,
        @Nullable JSONObject body,
        boolean allowRefresh
    ) throws IOException, JSONException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ApiConfig.getBaseUrl() + endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            if (bearerToken != null && !bearerToken.trim().isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            }

            if (body != null) {
                connection.setDoOutput(true);
                try (OutputStream outputStream = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    writer.write(body.toString());
                }
            }

            int statusCode = connection.getResponseCode();
            InputStream stream = statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream();
            String responseBody = readStream(stream);

            if (statusCode < 200 || statusCode >= 300) {
                if (allowRefresh && statusCode == 401 && bearerToken != null && !bearerToken.trim().isEmpty() && preferences.hasGoogleSession()) {
                    if (tryRefreshAuthSession()) {
                        return performJsonRequest(method, endpoint, preferences.getAuthToken(), body, false);
                    }
                }
                throw new IOException("HTTP " + statusCode + " " + responseBody);
            }

            if (responseBody == null || responseBody.trim().isEmpty()) {
                return new JSONObject();
            }
            return new JSONObject(responseBody);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private boolean tryRefreshAuthSession() {
        String refreshToken = preferences.getAuthRefreshToken();
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            return false;
        }

        synchronized (refreshLock) {
            if (refreshInFlight) {
                return preferences.hasGoogleSession() && !preferences.getAuthToken().trim().isEmpty();
            }
            refreshInFlight = true;
        }

        try {
            JSONObject body = new JSONObject();
            body.put("refreshToken", refreshToken);
            JSONObject response = performJsonRequest("POST", "/api/auth/refresh", null, body, false);
            JSONObject data = response.optJSONObject("data");
            JSONObject user = data == null ? null : data.optJSONObject("user");
            String nextToken = data == null ? "" : data.optString("token", "");
            String nextRefreshToken = data == null ? "" : data.optString("refreshToken", "");
            if (user == null || nextToken.trim().isEmpty()) {
                return false;
            }
            preferences.saveAuthSession(
                nextToken,
                nextRefreshToken,
                user.optString("id", preferences.getAuthUserId()),
                user.optString("displayName", user.optString("name", preferences.getAuthDisplayName())),
                user.optString("role", preferences.getAuthRole()),
                "google"
            );
            return true;
        } catch (Exception ignored) {
            return false;
        } finally {
            synchronized (refreshLock) {
                refreshInFlight = false;
            }
        }
    }

    private String resolveGuestDisplayName() {
        String existing = preferences.getAuthDisplayName();
        if (!existing.trim().isEmpty()) {
            return existing;
        }
        return "Guest " + Build.MODEL;
    }

    private String resolveMasteryLevel(int bestScore) {
        if (bestScore >= 8) {
            return "gold";
        }
        if (bestScore >= 4) {
            return "silver";
        }
        if (bestScore >= 1) {
            return "bronze";
        }
        return "";
    }

    private String formatIsoNow() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US).format(new Date());
    }

    private void dispatchAuthResult(@Nullable AuthStateCallback callback, boolean ready) {
        if (callback == null) {
            return;
        }
        mainHandler.post(() -> callback.onResult(ready));
    }

    private DashboardSnapshot parseDashboard(JSONObject dashboard) {
        JSONObject user = dashboard.optJSONObject("user");
        JSONObject summary = dashboard.optJSONObject("summary");
        applyCurrentUser(user);

        java.util.List<RecentActivityItem> recentItems = new java.util.ArrayList<>();
        org.json.JSONArray recentActivity = dashboard.optJSONArray("recentActivity");
        if (recentActivity != null) {
            for (int i = 0; i < recentActivity.length(); i++) {
                JSONObject item = recentActivity.optJSONObject(i);
                if (item == null) {
                    continue;
                }
                recentItems.add(new RecentActivityItem(
                    item.optString("techniqueId", ""),
                    item.optString("difficulty", ""),
                    item.optInt("score", 0),
                    item.optString("createdAt", "")
                ));
            }
        }

        java.util.List<WeakTopicItem> weakTopicItems = new java.util.ArrayList<>();
        org.json.JSONArray weakTopics = dashboard.optJSONArray("weakTopics");
        if (weakTopics != null) {
            for (int i = 0; i < weakTopics.length(); i++) {
                JSONObject item = weakTopics.optJSONObject(i);
                if (item == null) {
                    continue;
                }
                weakTopicItems.add(new WeakTopicItem(
                    item.optString("techniqueId", ""),
                    item.optInt("bestScore", 0),
                    item.optInt("attempts", 0),
                    item.optString("masteryLevel", "")
                ));
            }
        }

        return new DashboardSnapshot(
            user == null ? preferences.getAuthDisplayName() : user.optString("displayName", preferences.getAuthDisplayName()),
            user == null ? preferences.getAuthRole() : user.optString("role", preferences.getAuthRole()),
            summary == null ? 0 : summary.optInt("totalTechniquesStarted", 0),
            summary == null ? 0 : summary.optInt("completedTechniques", 0),
            summary == null ? 0 : summary.optInt("totalPracticeSessions", 0),
            summary == null ? 0 : summary.optInt("streakDays", 0),
            summary == null ? 0 : summary.optInt("todaySessions", 0),
            summary == null ? 3 : summary.optInt("dailyGoalTarget", 3),
            summary != null && summary.optBoolean("dailyGoalCompleted", false),
            recentItems,
            weakTopicItems
        );
    }

    private void applyCurrentUser(@Nullable JSONObject user) {
        if (user == null) {
            return;
        }
        preferences.updateAuthProfile(
            user.optString("id", preferences.getAuthUserId()),
            user.optString("displayName", user.optString("name", preferences.getAuthDisplayName())),
            user.optString("role", preferences.getAuthRole()),
            user.optString("provider", preferences.getAuthProvider())
        );
    }

    private void dispatchDashboardResult(@Nullable DashboardCallback callback, @Nullable DashboardSnapshot snapshot) {
        if (callback == null) {
            return;
        }
        mainHandler.post(() -> callback.onResult(snapshot));
    }

    private void dispatchCompletion(@Nullable CompletionCallback callback, boolean success) {
        if (callback == null) {
            return;
        }
        mainHandler.post(() -> callback.onResult(success));
    }

    private void dispatchCurrentUserResult(@Nullable CurrentUserCallback callback, boolean success) {
        if (callback == null) {
            return;
        }
        mainHandler.post(() -> callback.onResult(success));
    }

    private String readStream(@Nullable InputStream stream) throws IOException {
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
