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
    private static final String KEY_PRO_UNLOCKED = "pro_unlocked";
    private static final String KEY_PRO_CODE = "pro_code";
    private static final String KEY_HAS_RATED_STORE = "has_rated_store";
    private static final String KEY_EXPLORED_ACTION_COUNT = "explored_action_count";
    private static final String KEY_NQUEENS_BOARD_SIZE = "nqueens_board_size";
    private static final String KEY_SUDOKU_GRID_SIZE = "sudoku_grid_size";
    private static final String KEY_SUDOKU_PRESET = "sudoku_preset";
    private static final String KEY_KNIGHTS_TOUR_BOARD_SIZE = "knights_tour_board_size";
    private static final String KEY_KNIGHTS_TOUR_START_POS = "knights_tour_start_pos";
    private static final String KEY_MAZE_SIZE = "maze_size";
    private static final String KEY_MAZE_PRESET = "maze_preset";
    private static final String KEY_HAMILTONIAN_NODES = "hamiltonian_nodes";
    private static final String KEY_HAMILTONIAN_TYPE = "hamiltonian_type";
    private static final String KEY_GRAPH_COLORING_COLORS = "graph_coloring_colors";
    private static final String KEY_GRAPH_COLORING_PRESET = "graph_coloring_preset";
    private static final String KEY_LEETCODE_SOLVED = "leetcode_solved";
    private static final String KEY_LEETCODE_BOOKMARKS = "leetcode_bookmarks";
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

    public Set<String> getVisitedTopics() {
        return new LinkedHashSet<>(preferences.getStringSet(scopedKey(KEY_PROGRESS), new LinkedHashSet<>()));
    }

    public boolean isVisited(String topicId) {
        return topicId != null && getVisitedTopics().contains(topicId);
    }

    public int getVisitedCount() {
        return preferences.getStringSet(scopedKey(KEY_PROGRESS), new LinkedHashSet<>()).size();
    }

    public Set<String> getLeetCodeSolvedProblems() {
        return new LinkedHashSet<>(preferences.getStringSet(scopedKey(KEY_LEETCODE_SOLVED), new LinkedHashSet<>()));
    }

    public boolean isLeetCodeSolved(String problemId) {
        return problemId != null && getLeetCodeSolvedProblems().contains(problemId);
    }

    public void setLeetCodeSolved(String problemId, boolean solved) {
        if (problemId == null) return;
        Set<String> set = getLeetCodeSolvedProblems();
        if (solved) {
            set.add(problemId);
        } else {
            set.remove(problemId);
        }
        preferences.edit().putStringSet(scopedKey(KEY_LEETCODE_SOLVED), set).apply();
    }

    public Set<String> getLeetCodeBookmarks() {
        return new LinkedHashSet<>(preferences.getStringSet(scopedKey(KEY_LEETCODE_BOOKMARKS), new LinkedHashSet<>()));
    }

    public boolean isLeetCodeBookmarked(String problemId) {
        return problemId != null && getLeetCodeBookmarks().contains(problemId);
    }

    public void toggleLeetCodeBookmark(String problemId) {
        if (problemId == null) return;
        Set<String> set = getLeetCodeBookmarks();
        if (set.contains(problemId)) {
            set.remove(problemId);
        } else {
            set.add(problemId);
        }
        preferences.edit().putStringSet(scopedKey(KEY_LEETCODE_BOOKMARKS), set).apply();
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

    public boolean isProUnlocked() {
        return preferences.getBoolean(KEY_PRO_UNLOCKED, false);
    }

    public void setProUnlocked(boolean unlocked, String code) {
        preferences.edit()
            .putBoolean(KEY_PRO_UNLOCKED, unlocked)
            .putString(KEY_PRO_CODE, code == null ? "" : code.trim())
            .apply();
    }

    public String getProCode() {
        return preferences.getString(KEY_PRO_CODE, "");
    }

    public boolean hasRatedStore() {
        return preferences.getBoolean(KEY_HAS_RATED_STORE, false);
    }

    public void setRatedStore(boolean rated) {
        preferences.edit().putBoolean(KEY_HAS_RATED_STORE, rated).apply();
    }

    public int incrementExploredActions() {
        int current = preferences.getInt(KEY_EXPLORED_ACTION_COUNT, 0) + 1;
        preferences.edit().putInt(KEY_EXPLORED_ACTION_COUNT, current).apply();
        return current;
    }

    public int getNQueensBoardSize() {
        return preferences.getInt(KEY_NQUEENS_BOARD_SIZE, 4);
    }

    public void setNQueensBoardSize(int size) {
        preferences.edit().putInt(KEY_NQUEENS_BOARD_SIZE, size).apply();
    }

    public int getSudokuGridSize() {
        return preferences.getInt(KEY_SUDOKU_GRID_SIZE, 4);
    }

    public void setSudokuGridSize(int size) {
        preferences.edit().putInt(KEY_SUDOKU_GRID_SIZE, size).apply();
    }

    public int getSudokuPreset() {
        return preferences.getInt(KEY_SUDOKU_PRESET, 1);
    }

    public void setSudokuPreset(int preset) {
        preferences.edit().putInt(KEY_SUDOKU_PRESET, preset).apply();
    }

    public int getKnightsTourBoardSize() {
        return preferences.getInt(KEY_KNIGHTS_TOUR_BOARD_SIZE, 5);
    }

    public void setKnightsTourBoardSize(int size) {
        preferences.edit().putInt(KEY_KNIGHTS_TOUR_BOARD_SIZE, size).apply();
    }

    public int getKnightsTourStartPos() {
        return preferences.getInt(KEY_KNIGHTS_TOUR_START_POS, 1);
    }

    public void setKnightsTourStartPos(int pos) {
        preferences.edit().putInt(KEY_KNIGHTS_TOUR_START_POS, pos).apply();
    }

    public int getMazeSize() {
        return preferences.getInt(KEY_MAZE_SIZE, 5);
    }

    public void setMazeSize(int size) {
        preferences.edit().putInt(KEY_MAZE_SIZE, size).apply();
    }

    public int getMazePreset() {
        return preferences.getInt(KEY_MAZE_PRESET, 1);
    }

    public void setMazePreset(int preset) {
        preferences.edit().putInt(KEY_MAZE_PRESET, preset).apply();
    }

    public int getHamiltonianNodes() {
        return preferences.getInt(KEY_HAMILTONIAN_NODES, 5);
    }

    public void setHamiltonianNodes(int nodes) {
        preferences.edit().putInt(KEY_HAMILTONIAN_NODES, nodes).apply();
    }

    public int getHamiltonianType() {
        return preferences.getInt(KEY_HAMILTONIAN_TYPE, 1);
    }

    public void setHamiltonianType(int type) {
        preferences.edit().putInt(KEY_HAMILTONIAN_TYPE, type).apply();
    }

    public int getGraphColoringColors() {
        return preferences.getInt(KEY_GRAPH_COLORING_COLORS, 3);
    }

    public void setGraphColoringColors(int colors) {
        preferences.edit().putInt(KEY_GRAPH_COLORING_COLORS, colors).apply();
    }

    public int getGraphColoringPreset() {
        return preferences.getInt(KEY_GRAPH_COLORING_PRESET, 1);
    }

    public void setGraphColoringPreset(int preset) {
        preferences.edit().putInt(KEY_GRAPH_COLORING_PRESET, preset).apply();
    }

    public int getDpPreset(String techniqueId) {
        String key = "dp_preset_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 1);
    }

    public void setDpPreset(String techniqueId, int preset) {
        String key = "dp_preset_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, preset).apply();
    }

    public int getStringPatternPreset(String techniqueId) {
        String key = "strpat_preset_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 1);
    }

    public void setStringPatternPreset(String techniqueId, int preset) {
        String key = "strpat_preset_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, preset).apply();
    }

    public String getStringPatternText(String techniqueId, String defaultText) {
        String key = "strpat_text_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getString(key, defaultText != null ? defaultText : "");
    }

    public void setStringPatternText(String techniqueId, String text) {
        String key = "strpat_text_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putString(key, text != null ? text : "").apply();
    }

    public String getStringPatternQuery(String techniqueId, String defaultQuery) {
        String key = "strpat_query_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getString(key, defaultQuery != null ? defaultQuery : "");
    }

    public void setStringPatternQuery(String techniqueId, String query) {
        String key = "strpat_query_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putString(key, query != null ? query : "").apply();
    }

    public int getGraphPreset(String techniqueId) {
        String key = "graph_preset_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 1);
    }

    public void setGraphPreset(String techniqueId, int preset) {
        String key = "graph_preset_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, preset).apply();
    }

    public int getGraphStartNode(String techniqueId) {
        String key = "graph_start_node_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 0);
    }

    public void setGraphStartNode(String techniqueId, int node) {
        String key = "graph_start_node_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, node).apply();
    }

    public int getGraphNodeCount(String techniqueId) {
        String key = "graph_node_count_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 5);
    }

    public void setGraphNodeCount(String techniqueId, int count) {
        String key = "graph_node_count_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, count).apply();
    }

    public int getAdvancedDsPreset(String techniqueId) {
        String key = "advds_preset_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 1);
    }

    public void setAdvancedDsPreset(String techniqueId, int preset) {
        String key = "advds_preset_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, preset).apply();
    }

    public String getAdvancedDsCustomInput(String techniqueId, String defaultInput) {
        String key = "advds_input_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getString(key, defaultInput != null ? defaultInput : "");
    }

    public void setAdvancedDsCustomInput(String techniqueId, String input) {
        String key = "advds_input_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putString(key, input != null ? input : "").apply();
    }

    public int getSpecializedPreset(String techniqueId) {
        String key = "spec_preset_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getInt(key, 1);
    }

    public void setSpecializedPreset(String techniqueId, int preset) {
        String key = "spec_preset_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putInt(key, preset).apply();
    }

    public String getSpecializedCustomInput(String techniqueId, String defaultInput) {
        String key = "spec_input_" + (techniqueId != null ? techniqueId : "generic");
        return preferences.getString(key, defaultInput != null ? defaultInput : "");
    }

    public void setSpecializedCustomInput(String techniqueId, String input) {
        String key = "spec_input_" + (techniqueId != null ? techniqueId : "generic");
        preferences.edit().putString(key, input != null ? input : "").apply();
    }

    public int getHuffmanPreset() {
        return preferences.getInt("huffman_preset", 1);
    }

    public void setHuffmanPreset(int preset) {
        preferences.edit().putInt("huffman_preset", preset).apply();
    }

    public String getHuffmanCustomInput() {
        return getHuffmanCustomInput("");
    }

    public String getHuffmanCustomInput(String defaultInput) {
        return preferences.getString("huffman_custom_input", defaultInput != null ? defaultInput : "");
    }

    public void setHuffmanCustomInput(String input) {
        preferences.edit().putString("huffman_custom_input", input != null ? input : "").apply();
    }

    public int getAvlTreePreset() {
        return preferences.getInt("avl_tree_preset", 1);
    }

    public void setAvlTreePreset(int preset) {
        preferences.edit().putInt("avl_tree_preset", preset).apply();
    }

    public String getAvlTreeCustomInput() {
        return getAvlTreeCustomInput("");
    }

    public String getAvlTreeCustomInput(String defaultInput) {
        return preferences.getString("avl_tree_custom_input", defaultInput != null ? defaultInput : "");
    }

    public void setAvlTreeCustomInput(String input) {
        preferences.edit().putString("avl_tree_custom_input", input != null ? input : "").apply();
    }

    public String getLastSeenReply(String feedbackId) {
        return preferences.getString("last_seen_reply_" + feedbackId, "");
    }

    public void setLastSeenReply(String feedbackId, String reply) {
        preferences.edit().putString("last_seen_reply_" + feedbackId, reply != null ? reply : "").apply();
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
