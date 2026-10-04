package com.dsavisualizer.app.data;

import android.content.Context;
import android.util.Log;

import com.dsavisualizer.app.DsaApplication;
import com.dsavisualizer.app.model.TechniqueContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * High-performance repository for LeetCode Part 1 problems (1 to 250).
 * Loads algorithms (Iterative, Functions, Recursion) and codes (Java, C++, Python)
 * dynamically from leetcode_part1_content.json.
 */
public final class LeetCodePart1Repository {
    private static final String TAG = "LeetCodePart1Repo";
    private static final Map<String, TechniqueContent> CONTENT = new ConcurrentHashMap<>();
    private static final Map<String, String> VIS_TYPES = new ConcurrentHashMap<>();
    private static final AtomicBoolean LOADED = new AtomicBoolean(false);

    private LeetCodePart1Repository() {
    }

    public static void ensureLoaded(Context context) {
        if (LOADED.get()) {
            return;
        }
        if (context == null) {
            context = DsaApplication.getInstance();
            if (context == null) {
                return;
            }
        }
        synchronized (LOADED) {
            if (LOADED.get()) {
                return;
            }
            loadInternal(context.getApplicationContext());
            LOADED.set(true);
        }
    }

    private static void loadInternal(Context context) {
        try {
            InputStream is = context.getAssets().open("leetcode_part1_content.json");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[16384];
            int read;
            while ((read = is.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            is.close();
            String jsonStr = baos.toString("UTF-8");
            JSONObject root = new JSONObject(jsonStr);

            Iterator<String> keys = root.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject obj = root.getJSONObject(key);

                int lcNumber = obj.optInt("lcNumber", 0);
                String title = obj.optString("title", "LeetCode #" + lcNumber);
                String pattern = obj.optString("pattern", "Core DSA");
                String techId = obj.optString("techniqueId", "leetcode_" + lcNumber);
                int sampleTarget = obj.optInt("sampleTarget", 0);
                String visType = obj.optString("visType", "array_default");

                JSONArray arrJson = obj.optJSONArray("sampleInput");
                int[] sampleInput;
                if (arrJson != null && arrJson.length() > 0) {
                    sampleInput = new int[arrJson.length()];
                    for (int i = 0; i < arrJson.length(); i++) {
                        sampleInput[i] = arrJson.getInt(i);
                    }
                } else {
                    sampleInput = new int[]{1, 2, 3, 4};
                }

                TechniqueContent content = new TechniqueContent(
                        techId,
                        title,
                        pattern,
                        "Solved using optimal " + pattern + " algorithmic paradigm.",
                        "O(n)",
                        "O(1)",
                        "O(n)",
                        "O(n)",
                        sampleInput,
                        sampleTarget
                );
                content.visualizationType = visType;
                content.sourceType = "LeetCode Roadmap Part 1";
                content.sourceTitle = title;

                JSONObject algoObj = obj.optJSONObject("algorithm");
                if (algoObj == null) {
                    algoObj = obj.optJSONObject("algorithms");
                }
                if (algoObj != null) {
                    content.algorithmByStyle.put("Iterative", algoObj.optString("Iterative", ""));
                    content.algorithmByStyle.put("Functions", algoObj.optString("Functions", ""));
                    content.algorithmByStyle.put("Recursion", algoObj.optString("Recursion", ""));
                }

                JSONObject codeObj = obj.optJSONObject("code");
                if (codeObj != null) {
                    String[] languages = {"Java", "C++", "C", "Python"};
                    String[] styles = {"Iterative", "Functions", "Recursion"};

                    for (String lang : languages) {
                        JSONObject langObj = codeObj.optJSONObject(lang);
                        if (langObj == null && "C".equals(lang)) {
                            langObj = codeObj.optJSONObject("C++");
                        } else if (langObj == null && "C++".equals(lang)) {
                            langObj = codeObj.optJSONObject("C");
                        }
                        if (langObj != null) {
                            for (String style : styles) {
                                String code = langObj.optString(style, "");
                                if (!code.isEmpty()) {
                                    content.putCode(lang, style, code, false);
                                    content.putCode(lang, style, code, true);
                                    content.codeByStyle.put(style, code);
                                    content.codeByStyle.put(style + "_" + lang, code);
                                    if ("C++".equals(lang)) {
                                        content.putCode("C", style, code, false);
                                        content.putCode("C", style, code, true);
                                        content.codeByStyle.put(style + "_C", code);
                                    } else if ("C".equals(lang)) {
                                        content.putCode("C++", style, code, false);
                                        content.putCode("C++", style, code, true);
                                        content.codeByStyle.put(style + "_C++", code);
                                    }
                                }
                            }
                        }
                    }
                }

                // Default explanation steps from algorithm
                String iterAlgo = content.algorithmByStyle.get("Iterative");
                if (iterAlgo != null) {
                    String[] lines = iterAlgo.split("\n");
                    for (String line : lines) {
                        line = line.trim();
                        if (!line.isEmpty()) {
                            content.explanationSteps.add(line);
                        }
                    }
                }

                CONTENT.put(key, content);
                CONTENT.put(techId, content);
                CONTENT.put("leetcode_" + lcNumber, content);
                CONTENT.put(String.valueOf(lcNumber), content);
                VIS_TYPES.put(key, visType);
                VIS_TYPES.put(techId, visType);
                VIS_TYPES.put("leetcode_" + lcNumber, visType);
                VIS_TYPES.put(String.valueOf(lcNumber), visType);

                // Setup common aliases
                registerAliases(lcNumber, content, visType);
            }
            Log.d(TAG, "Loaded " + CONTENT.size() + " LeetCode content entries.");
        } catch (Exception e) {
            Log.e(TAG, "Failed to load leetcode_part1_content.json", e);
        }
    }

    private static void registerAliases(int lcNumber, TechniqueContent content, String visType) {
        String alias = null;
        switch (lcNumber) {
            case 1: alias = "leetcode_two_sum"; break;
            case 1929: alias = "leetcode_concatenation_array"; break;
            case 1480: alias = "leetcode_running_sum"; break;
            case 1470: alias = "leetcode_shuffle_array"; break;
            case 1431: alias = "leetcode_kids_candies"; break;
            case 1672: alias = "leetcode_richest_customer"; break;
            case 217: alias = "leetcode_contains_duplicate"; break;
            case 242: alias = "leetcode_valid_anagram"; break;
            case 136: alias = "leetcode_single_number"; break;
            case 349: alias = "leetcode_intersection_two_arrays"; break;
            case 350: alias = "leetcode_intersection_two_arrays_ii"; break;
            case 121: alias = "leetcode_best_time_stock"; break;
            case 122: alias = "leetcode_best_time_stock_ii"; break;
            case 88: alias = "leetcode_merge_sorted_array"; break;
            case 27: alias = "leetcode_remove_element"; break;
            case 26: alias = "leetcode_remove_duplicates_sorted_array"; break;
            case 283: alias = "leetcode_move_zeroes"; break;
            case 125: alias = "leetcode_valid_palindrome"; break;
            case 344: alias = "leetcode_reverse_string"; break;
            case 167: alias = "leetcode_two_sum_ii"; break;
            case 15: alias = "leetcode_3sum"; break;
            case 18: alias = "leetcode_4sum"; break;
            case 977: alias = "leetcode_squares_sorted_array"; break;
            case 11: alias = "leetcode_container_most_water"; break;
            case 141: alias = "leetcode_linked_list_cycle"; break;
            case 20: alias = "leetcode_valid_parentheses"; break;
            case 206: alias = "leetcode_reverse_linked_list"; break;
            case 21: alias = "leetcode_merge_two_sorted_lists"; break;
            case 226: alias = "leetcode_invert_binary_tree"; break;
            case 704: alias = "leetcode_binary_search"; break;
            case 876: alias = "leetcode_middle_linked_list"; break;
            case 739: alias = "leetcode_daily_temperatures"; break;
            case 155: alias = "leetcode_min_stack"; break;
        }
        if (alias != null) {
            CONTENT.put(alias, content);
            VIS_TYPES.put(alias, visType);
        }
    }

    public static TechniqueContent getContent(String techniqueId) {
        ensureLoaded(null);
        if (techniqueId == null) return null;
        TechniqueContent c = CONTENT.get(techniqueId);
        if (c != null) return c;
        if (techniqueId.startsWith("leetcode_")) {
            String num = techniqueId.substring(9);
            c = CONTENT.get(num);
            if (c != null) return c;
        }
        return null;
    }

    public static String getVisType(String techniqueId) {
        ensureLoaded(null);
        if (techniqueId == null) return "array_default";
        String type = VIS_TYPES.get(techniqueId);
        if (type != null) return type;
        if (techniqueId.startsWith("leetcode_")) {
            String num = techniqueId.substring(9);
            type = VIS_TYPES.get(num);
            if (type != null) return type;
        }
        return "array_default";
    }

    public static boolean hasProblem(String techniqueId) {
        ensureLoaded(null);
        if (techniqueId == null) return false;
        if (CONTENT.containsKey(techniqueId)) return true;
        if (techniqueId.startsWith("leetcode_")) {
            return CONTENT.containsKey(techniqueId.substring(9));
        }
        return false;
    }
}
