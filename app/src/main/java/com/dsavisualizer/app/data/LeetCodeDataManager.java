package com.dsavisualizer.app.data;

import android.content.Context;
import android.util.Log;

import com.dsavisualizer.app.model.LeetCodeProblemModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * High-performance repository manager for the LeetCode Master 2000 Roadmap.
 * Loads 2,000 problems from assets and provides instant in-memory filtering and search.
 */
public class LeetCodeDataManager {

    private static final String TAG = "LeetCodeDataManager";
    private static final String ASSET_FILE = "leetcode_master_2000.json";

    private static volatile LeetCodeDataManager instance;
    private final List<LeetCodeProblemModel> allProblems = new ArrayList<>(2000);
    private boolean isLoaded = false;
    private boolean isLoading = false;

    public interface OnLoadedCallback {
        void onLoaded(List<LeetCodeProblemModel> problems);
    }

    private LeetCodeDataManager() {
    }

    public static LeetCodeDataManager getInstance() {
        if (instance == null) {
            synchronized (LeetCodeDataManager.class) {
                if (instance == null) {
                    instance = new LeetCodeDataManager();
                }
            }
        }
        return instance;
    }

    public synchronized void ensureLoaded(Context context, OnLoadedCallback callback) {
        if (isLoaded) {
            if (callback != null) {
                callback.onLoaded(allProblems);
            }
            return;
        }

        if (isLoading) {
            // Already loading on background thread
            return;
        }

        isLoading = true;
        Context appContext = context.getApplicationContext();

        new Thread(() -> {
            List<LeetCodeProblemModel> loaded = loadFromAssets(appContext);
            synchronized (LeetCodeDataManager.this) {
                allProblems.clear();
                allProblems.addAll(loaded);
                isLoaded = true;
                isLoading = false;
            }

            if (callback != null) {
                callback.onLoaded(allProblems);
            }
        }, "leetcode-2000-loader").start();
    }

    private List<LeetCodeProblemModel> loadFromAssets(Context context) {
        List<LeetCodeProblemModel> list = new ArrayList<>(2000);
        try (InputStream is = context.getAssets().open(ASSET_FILE)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            int read = is.read(buffer);
            if (read > 0) {
                String json = new String(buffer, 0, read, StandardCharsets.UTF_8);
                JSONArray array = new JSONArray(json);
                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject obj = array.getJSONObject(i);
                    LeetCodeProblemModel model = new LeetCodeProblemModel(
                            obj.optInt("order", i + 1),
                            obj.optInt("lcNumber", 0),
                            obj.optString("title", ""),
                            obj.optString("difficulty", "Medium"),
                            obj.optString("pattern", "Core DSA"),
                            obj.optString("section", "Core Problems"),
                            obj.optInt("part", 1),
                            obj.optString("companies", ""),
                            obj.optString("slug", ""),
                            obj.optString("url", "")
                    );
                    list.add(model);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading LeetCode Master 2000 dataset", e);
        }
        return list;
    }

    public synchronized List<LeetCodeProblemModel> getAllProblems() {
        return Collections.unmodifiableList(allProblems);
    }

    public synchronized void syncUserState(AppPreferences preferences) {
        if (preferences == null) return;
        Set<String> solved = preferences.getLeetCodeSolvedProblems();
        Set<String> bookmarks = preferences.getLeetCodeBookmarks();

        for (LeetCodeProblemModel problem : allProblems) {
            problem.isSolved = solved.contains(problem.getId());
            problem.isBookmarked = bookmarks.contains(problem.getId());
        }
    }

    public synchronized List<LeetCodeProblemModel> filterProblems(
            int partFilter,
            String difficultyFilter,
            String statusFilter,
            String searchQuery) {

        List<LeetCodeProblemModel> filtered = new ArrayList<>();
        boolean checkQuery = searchQuery != null && !searchQuery.trim().isEmpty();
        boolean filterDiff = difficultyFilter != null && !difficultyFilter.equalsIgnoreCase("all");
        boolean filterStatus = statusFilter != null && !statusFilter.equalsIgnoreCase("all");

        for (LeetCodeProblemModel problem : allProblems) {
            // 1. Part filter (0 = all parts, otherwise 1 to 8)
            if (partFilter > 0 && problem.part != partFilter) {
                continue;
            }

            // 2. Difficulty filter
            if (filterDiff && !problem.difficulty.equalsIgnoreCase(difficultyFilter)) {
                continue;
            }

            // 3. Status filter
            if (filterStatus) {
                if ("solved".equalsIgnoreCase(statusFilter) && !problem.isSolved) {
                    continue;
                }
                if ("unsolved".equalsIgnoreCase(statusFilter) && problem.isSolved) {
                    continue;
                }
                if ("bookmarked".equalsIgnoreCase(statusFilter) && !problem.isBookmarked) {
                    continue;
                }
            }

            // 4. Search query
            if (checkQuery && !problem.matchesQuery(searchQuery)) {
                continue;
            }

            filtered.add(problem);
        }

        return filtered;
    }

    public synchronized LeetCodeProblemModel getProblemByOrder(int order) {
        if (order < 1 || order > allProblems.size()) {
            return null;
        }
        return allProblems.get(order - 1);
    }

    public synchronized LeetCodeProblemModel getProblemByNumber(int lcNumber) {
        for (LeetCodeProblemModel p : allProblems) {
            if (p.lcNumber == lcNumber) {
                return p;
            }
        }
        return null;
    }

    public synchronized int getSolvedCount() {
        int count = 0;
        for (LeetCodeProblemModel p : allProblems) {
            if (p.isSolved) count++;
        }
        return count;
    }

    public synchronized int getSolvedCountForPart(int part) {
        int count = 0;
        for (LeetCodeProblemModel p : allProblems) {
            if (p.part == part && p.isSolved) count++;
        }
        return count;
    }

    public synchronized int getSolvedCountByDifficulty(String difficulty) {
        int count = 0;
        for (LeetCodeProblemModel p : allProblems) {
            if (p.isSolved && p.difficulty.equalsIgnoreCase(difficulty)) count++;
        }
        return count;
    }

    public synchronized LeetCodeProblemModel getNextUnsolvedProblem() {
        for (LeetCodeProblemModel p : allProblems) {
            if (!p.isSolved) {
                return p;
            }
        }
        return allProblems.isEmpty() ? null : allProblems.get(0);
    }
}
