package com.dsavisualizer.app.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.dsavisualizer.app.ui.walkthrough.WalkthroughManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple local storage for favorites, recent topics, and viewed progress.
 */
public class AppPreferences {

    private static final String PREFS_NAME = "dsa_visualizer_pro";
    private static final String KEY_FAVORITES = "favorites";
    private static final String KEY_RECENTS = "recents";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_NOTE_PREFIX = "note_";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_AUTH_USER_ID = "auth_user_id";
    private static final String KEY_AUTH_DISPLAY_NAME = "auth_display_name";
    private static final String KEY_AUTH_ROLE = "auth_role";
    private static final String KEY_AUTH_PROVIDER = "auth_provider";
    private static final String KEY_AUTH_REFRESH_TOKEN = "auth_refresh_token";
    private static final String KEY_AUTH_VALIDATED_AT = "auth_validated_at";
    private static final String KEY_MISTAKE_PREFIX = "mistake_";
    private static final String KEY_WALKTHROUGH_STAGE = "walkthrough_stage";
    private static final String KEY_WALKTHROUGH_PAUSED = "walkthrough_paused";
    private static final String KEY_WALKTHROUGH_TIP_PREFIX = "walkthrough_tip_";
    private static final String KEY_THEME_MODE = "theme_mode";
    private static final String KEY_CODE_READER_THEME = "code_reader_theme";
    private static final String KEY_CODE_READER_TEXT_SIZE = "code_reader_text_size";
    private static final String SEPARATOR = "||";

    public static final String THEME_SYSTEM = "system";
    public static final String THEME_LIGHT = "light";
    public static final String THEME_DARK = "dark";
    public static final String CODE_THEME_CLASSIC = "classic";
    public static final String CODE_THEME_DIM = "dim";
    public static final String CODE_THEME_CONTRAST = "contrast";

    private final SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public Set<String> getFavorites() {
        return new LinkedHashSet<>(preferences.getStringSet(scopedKey(KEY_FAVORITES), new LinkedHashSet<>()));
    }

    public boolean isFavorite(String topicId) {
        return getFavorites().contains(topicId);
    }

    public void toggleFavorite(String topicId) {
        Set<String> favorites = getFavorites();
        if (favorites.contains(topicId)) {
            favorites.remove(topicId);
        } else {
            favorites.add(topicId);
        }
        preferences.edit().putStringSet(scopedKey(KEY_FAVORITES), favorites).apply();
    }

    public void addRecent(String topicId) {
        List<String> recents = getRecents();
        recents.remove(topicId);
        recents.add(0, topicId);
        while (recents.size() > 5) {
            recents.remove(recents.size() - 1);
        }
        preferences.edit().putString(scopedKey(KEY_RECENTS), String.join(SEPARATOR, recents)).apply();
    }

    public List<String> getRecents() {
        String raw = preferences.getString(scopedKey(KEY_RECENTS), "");
        List<String> recents = new ArrayList<>();

        if (raw == null || raw.isEmpty()) {
            return recents;
        }

        for (String item : raw.split(java.util.regex.Pattern.quote(SEPARATOR))) {
            if (!item.trim().isEmpty()) {
                recents.add(item);
            }
        }

        return recents;
    }

    public void markVisited(String topicId) {
        Set<String> viewed = new LinkedHashSet<>(preferences.getStringSet(scopedKey(KEY_PROGRESS), new LinkedHashSet<>()));
        viewed.add(topicId);
        preferences.edit().putStringSet(scopedKey(KEY_PROGRESS), viewed).apply();
    }

    public int getVisitedCount() {
        return preferences.getStringSet(scopedKey(KEY_PROGRESS), new LinkedHashSet<>()).size();
    }

    public void saveNote(String topicId, String note) {
        preferences.edit().putString(scopedKey(KEY_NOTE_PREFIX + topicId), note == null ? "" : note).apply();
    }

    public String getNote(String topicId) {
        return preferences.getString(scopedKey(KEY_NOTE_PREFIX + topicId), "");
    }

    public void saveAuthSession(String token, String refreshToken, String userId, String displayName, String role, String provider) {
        preferences.edit()
            .putString(KEY_AUTH_TOKEN, token == null ? "" : token)
            .putString(KEY_AUTH_REFRESH_TOKEN, refreshToken == null ? "" : refreshToken)
            .putString(KEY_AUTH_USER_ID, userId == null ? "" : userId)
            .putString(KEY_AUTH_DISPLAY_NAME, displayName == null ? "" : displayName)
            .putString(KEY_AUTH_ROLE, role == null ? "" : role)
            .putString(KEY_AUTH_PROVIDER, provider == null ? "" : provider)
            .putLong(KEY_AUTH_VALIDATED_AT, System.currentTimeMillis())
            .apply();
    }

    public void updateAuthProfile(String userId, String displayName, String role, String provider) {
        preferences.edit()
            .putString(KEY_AUTH_USER_ID, normalizeOrExisting(userId, getAuthUserId()))
            .putString(KEY_AUTH_DISPLAY_NAME, normalizeOrExisting(displayName, getAuthDisplayName()))
            .putString(KEY_AUTH_ROLE, normalizeOrExisting(role, getAuthRole()))
            .putString(KEY_AUTH_PROVIDER, normalizeOrExisting(provider, getAuthProvider()))
            .putLong(KEY_AUTH_VALIDATED_AT, System.currentTimeMillis())
            .apply();
    }

    public long getAuthValidatedAt() {
        return preferences.getLong(KEY_AUTH_VALIDATED_AT, 0L);
    }

    public void markAuthValidatedNow() {
        preferences.edit().putLong(KEY_AUTH_VALIDATED_AT, System.currentTimeMillis()).apply();
    }

    public boolean isAuthValidationFresh(long maxAgeMs) {
        long validatedAt = getAuthValidatedAt();
        return validatedAt > 0L && (System.currentTimeMillis() - validatedAt) <= maxAgeMs;
    }

    public void saveGuestSession(String token, String userId, String displayName) {
        saveAuthSession(token, "", userId, displayName, "guest", "guest");
    }

    public String getAuthToken() {
        return preferences.getString(KEY_AUTH_TOKEN, "");
    }

    public String getAuthRefreshToken() {
        return preferences.getString(KEY_AUTH_REFRESH_TOKEN, "");
    }

    public String getAuthUserId() {
        return preferences.getString(KEY_AUTH_USER_ID, "");
    }

    public String getAuthDisplayName() {
        return preferences.getString(KEY_AUTH_DISPLAY_NAME, "");
    }

    public String getAuthRole() {
        return preferences.getString(KEY_AUTH_ROLE, "");
    }

    public String getAuthProvider() {
        return preferences.getString(KEY_AUTH_PROVIDER, "");
    }

    public boolean hasAuthSession() {
        return !getAuthToken().trim().isEmpty();
    }

    public boolean hasGuestSession() {
        return hasAuthSession() && "guest".equalsIgnoreCase(getAuthProvider());
    }

    public boolean hasGoogleSession() {
        return hasAuthSession() && "google".equalsIgnoreCase(getAuthProvider());
    }

    public String getGuestToken() {
        return getAuthToken();
    }

    public String getGuestUserId() {
        return getAuthUserId();
    }

    public String getGuestDisplayName() {
        return getAuthDisplayName();
    }

    public void clearAuthSession() {
        preferences.edit()
            .remove(KEY_AUTH_TOKEN)
            .remove(KEY_AUTH_REFRESH_TOKEN)
            .remove(KEY_AUTH_USER_ID)
            .remove(KEY_AUTH_DISPLAY_NAME)
            .remove(KEY_AUTH_ROLE)
            .remove(KEY_AUTH_PROVIDER)
            .remove(KEY_AUTH_VALIDATED_AT)
            .apply();
    }

    public void clearGuestSession() {
        clearAuthSession();
    }

    public String getWalkthroughStage() {
        String savedStage = preferences.getString(scopedKey(KEY_WALKTHROUGH_STAGE), "");
        if (savedStage == null || savedStage.trim().isEmpty()) {
            return WalkthroughManager.defaultStageForRole(getAuthRole());
        }
        return savedStage;
    }

    public boolean isWalkthroughComplete() {
        return WalkthroughManager.STAGE_COMPLETE.equals(getWalkthroughStage());
    }

    public void setWalkthroughStage(String stage) {
        preferences.edit().putString(scopedKey(KEY_WALKTHROUGH_STAGE), stage == null ? "" : stage).apply();
    }

    public void completeWalkthrough() {
        preferences.edit()
            .putString(scopedKey(KEY_WALKTHROUGH_STAGE), WalkthroughManager.STAGE_COMPLETE)
            .putBoolean(scopedKey(KEY_WALKTHROUGH_PAUSED), false)
            .apply();
    }

    public void resetWalkthrough() {
        preferences.edit()
            .putString(scopedKey(KEY_WALKTHROUGH_STAGE), WalkthroughManager.defaultStageForRole(getAuthRole()))
            .putBoolean(scopedKey(KEY_WALKTHROUGH_PAUSED), false)
            .apply();
    }

    public boolean isWalkthroughPaused() {
        return preferences.getBoolean(scopedKey(KEY_WALKTHROUGH_PAUSED), false);
    }

    public void pauseWalkthrough() {
        preferences.edit().putBoolean(scopedKey(KEY_WALKTHROUGH_PAUSED), true).apply();
    }

    public void resumeWalkthrough() {
        preferences.edit().putBoolean(scopedKey(KEY_WALKTHROUGH_PAUSED), false).apply();
    }

    public boolean shouldShowTip(String tipId) {
        return !preferences.getBoolean(scopedKey(KEY_WALKTHROUGH_TIP_PREFIX + tipId), false);
    }

    public void markTipSeen(String tipId) {
        preferences.edit().putBoolean(scopedKey(KEY_WALKTHROUGH_TIP_PREFIX + tipId), true).apply();
    }

    public String getThemeMode() {
        String savedMode = preferences.getString(KEY_THEME_MODE, THEME_DARK);
        if (THEME_LIGHT.equals(savedMode) || THEME_DARK.equals(savedMode) || THEME_SYSTEM.equals(savedMode)) {
            return savedMode;
        }
        return THEME_DARK;
    }

    public void setThemeMode(String mode) {
        String normalizedMode = THEME_DARK;
        if (THEME_LIGHT.equals(mode) || THEME_DARK.equals(mode) || THEME_SYSTEM.equals(mode)) {
            normalizedMode = mode;
        }
        preferences.edit().putString(KEY_THEME_MODE, normalizedMode).apply();
    }

    public String getCodeReaderTheme() {
        String savedTheme = preferences.getString(scopedKey(KEY_CODE_READER_THEME), CODE_THEME_CLASSIC);
        if (CODE_THEME_DIM.equals(savedTheme) || CODE_THEME_CONTRAST.equals(savedTheme) || CODE_THEME_CLASSIC.equals(savedTheme)) {
            return savedTheme;
        }
        return CODE_THEME_CLASSIC;
    }

    public void setCodeReaderTheme(String theme) {
        String normalizedTheme = CODE_THEME_CLASSIC;
        if (CODE_THEME_DIM.equals(theme) || CODE_THEME_CONTRAST.equals(theme) || CODE_THEME_CLASSIC.equals(theme)) {
            normalizedTheme = theme;
        }
        preferences.edit().putString(scopedKey(KEY_CODE_READER_THEME), normalizedTheme).apply();
    }

    public float getCodeReaderTextSizeSp() {
        float savedSize = preferences.getFloat(scopedKey(KEY_CODE_READER_TEXT_SIZE), 16f);
        return Math.max(13f, Math.min(24f, savedSize));
    }

    public void setCodeReaderTextSizeSp(float textSizeSp) {
        float normalizedSize = Math.max(13f, Math.min(24f, textSizeSp));
        preferences.edit().putFloat(scopedKey(KEY_CODE_READER_TEXT_SIZE), normalizedSize).apply();
    }

    public void saveMistakeReview(String techniqueId, String prompt, String correctLabel, String explanation, String difficulty) {
        if (techniqueId == null || techniqueId.trim().isEmpty()) {
            return;
        }
        JSONArray entries = readMistakeArray(techniqueId);
        JSONArray nextEntries = new JSONArray();
        try {
            JSONObject item = new JSONObject();
            item.put("prompt", prompt == null ? "" : prompt);
            item.put("correctLabel", correctLabel == null ? "" : correctLabel);
            item.put("explanation", explanation == null ? "" : explanation);
            item.put("difficulty", difficulty == null ? "" : difficulty);
            item.put("createdAt", System.currentTimeMillis());
            nextEntries.put(item);
            for (int i = 0; i < entries.length() && nextEntries.length() < 12; i++) {
                nextEntries.put(entries.getJSONObject(i));
            }
        } catch (Exception ignored) {
            return;
        }
        preferences.edit().putString(scopedKey(KEY_MISTAKE_PREFIX + techniqueId), nextEntries.toString()).apply();
    }

    public List<MistakeReview> getMistakeReviews(String techniqueId) {
        List<MistakeReview> result = new ArrayList<>();
        JSONArray entries = readMistakeArray(techniqueId);
        for (int i = 0; i < entries.length(); i++) {
            JSONObject item = entries.optJSONObject(i);
            if (item == null) {
                continue;
            }
            result.add(new MistakeReview(
                item.optString("prompt", ""),
                item.optString("correctLabel", ""),
                item.optString("explanation", ""),
                item.optString("difficulty", "")
            ));
        }
        return result;
    }

    private JSONArray readMistakeArray(String techniqueId) {
        String raw = preferences.getString(scopedKey(KEY_MISTAKE_PREFIX + techniqueId), "[]");
        try {
            return new JSONArray(raw == null ? "[]" : raw);
        } catch (Exception ignored) {
            return new JSONArray();
        }
    }

    private String scopedKey(String baseKey) {
        return baseKey + "__" + resolveScopeId();
    }

    private String resolveScopeId() {
        String provider = preferences.getString(KEY_AUTH_PROVIDER, "").trim();
        String userId = preferences.getString(KEY_AUTH_USER_ID, "").trim();
        if (!provider.isEmpty() && !userId.isEmpty()) {
            return provider + "_" + userId;
        }
        if (!provider.isEmpty()) {
            return provider;
        }
        return "anonymous";
    }

    private String normalizeOrExisting(String value, String existing) {
        if (value == null || value.trim().isEmpty()) {
            return existing == null ? "" : existing;
        }
        return value.trim();
    }

    public static class MistakeReview {
        public final String prompt;
        public final String correctLabel;
        public final String explanation;
        public final String difficulty;

        public MistakeReview(String prompt, String correctLabel, String explanation, String difficulty) {
            this.prompt = prompt == null ? "" : prompt;
            this.correctLabel = correctLabel == null ? "" : correctLabel;
            this.explanation = explanation == null ? "" : explanation;
            this.difficulty = difficulty == null ? "" : difficulty;
        }
    }
}
