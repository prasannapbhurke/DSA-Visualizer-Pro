package com.dsavisualizer.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.dsavisualizer.app.R;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Logic Block tab for LeetCode Master 2000 problems.
 *
 * Replaces the Visualization tab in the TechniqueDetailActivity
 * for all LeetCode problems (identified by "leetcode_" prefix).
 *
 * Content is loaded from assets/leetcode_part1_logic.json.
 * Each entry is keyed by LeetCode problem number.
 */
public class LogicBlockFragment extends Fragment {

    private static final String TAG = "LogicBlockFragment";
    private static final String ARG_TECHNIQUE_ID = "technique_id";
    private static final String ARG_LOGIC_ORDER = "logic_order";
    private static final String ASSET_FILE = "leetcode_part1_logic.json";

    public static LogicBlockFragment newInstance(String techniqueId, int order) {
        LogicBlockFragment fragment = new LogicBlockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TECHNIQUE_ID, techniqueId);
        args.putInt(ARG_LOGIC_ORDER, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logic_block, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Animate entrance
        view.setAlpha(0f);
        view.setTranslationY(20f);
        view.animate().alpha(1f).translationY(0f).setDuration(250).start();

        Bundle args = getArguments();
        String techniqueId = args != null ? args.getString(ARG_TECHNIQUE_ID, "") : "";
        int logicOrder = args != null ? args.getInt(ARG_LOGIC_ORDER, 0) : 0;

        TextView headerText = view.findViewById(R.id.logic_header_text);
        TextView contentText = view.findViewById(R.id.logic_content_text);
        TextView patternBadge = view.findViewById(R.id.logic_pattern_badge);
        MaterialCardView contentCard = view.findViewById(R.id.logic_content_card);

        // Look up by order (1-250); fall back to lcNumber if order is 0
        int lcNumber = parseLcNumber(techniqueId);
        String lookupKey = logicOrder > 0 ? String.valueOf(logicOrder)
                : (lcNumber > 0 ? String.valueOf(lcNumber) : null);

        if (lookupKey == null) {
            showFallback(headerText, contentText, patternBadge, techniqueId);
            return;
        }

        // Load content from assets
        try {
            String jsonStr = loadAsset(requireContext(), ASSET_FILE);
            if (jsonStr == null) {
                showFallback(headerText, contentText, patternBadge, techniqueId);
                return;
            }

            JSONObject root = new JSONObject(jsonStr);

            if (!root.has(lookupKey)) {
                showFallback(headerText, contentText, patternBadge, techniqueId);
                return;
            }

            JSONObject entry = root.getJSONObject(lookupKey);
            int entryLcNum = entry.optInt("lcNumber", lcNumber);
            String title = entry.optString("title", "Problem #" + entryLcNum);
            String pattern = entry.optString("pattern", "");
            String logicBlock = entry.optString("logicBlock", "");
            int order = entry.optInt("order", logicOrder);
            boolean isRepeat = entry.optBoolean("isRepeat", false);

            // Populate views
            if (headerText != null) {
                String repeatTag = isRepeat ? "  [Review]" : "";
                String header = "LC #" + entryLcNum + " -- " + title + repeatTag;
                if (order > 0) header += "  |  #" + order + " of 250";
                headerText.setText(header);
            }
            if (patternBadge != null && !pattern.isEmpty()) {
                patternBadge.setText("Pattern: " + pattern);
                patternBadge.setVisibility(View.VISIBLE);
            }
            if (contentText != null) {
                contentText.setText(logicBlock);
            }

        } catch (Exception e) {
            Log.e(TAG, "Failed to load logic block for LC#" + lcNumber, e);
            showFallback(headerText, contentText, patternBadge, techniqueId);
        }
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    private int parseLcNumber(String techniqueId) {
        if (techniqueId == null) return -1;
        String id = techniqueId.trim();
        // "leetcode_1929" → 1929
        if (id.startsWith("leetcode_")) {
            try {
                return Integer.parseInt(id.substring(9));
            } catch (NumberFormatException ignored) {}
        }
        return -1;
    }

    private void showFallback(TextView header, TextView content, TextView badge, String id) {
        if (header != null) header.setText("Logic Block");
        if (badge != null) badge.setVisibility(View.GONE);
        if (content != null) {
            content.setText(
                "How to think before coding:\n\n" +
                "1. Read the problem statement fully.\n" +
                "2. Study every example — understand input & output.\n" +
                "3. Ask: What exactly is happening to the input?\n" +
                "4. Solve manually on paper first.\n" +
                "5. Identify the simplest operation.\n" +
                "6. Only then write code.\n\n" +
                "Technique ID: " + id
            );
        }
    }

    @Nullable
    private String loadAsset(Context ctx, String fileName) {
        try (InputStream is = ctx.getAssets().open(fileName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            int read = is.read(buffer);
            if (read > 0) {
                return new String(buffer, 0, read, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            Log.e(TAG, "Cannot open asset: " + fileName, e);
        }
        return null;
    }
}
