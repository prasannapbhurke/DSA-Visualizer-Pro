package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Modern visualizer engine for String and Pattern Matching algorithms:
 * - KMP (Knuth-Morris-Pratt with LPS table)
 * - Rabin-Karp (Polynomial rolling hashes)
 * - Boyer-Moore (Right-to-left scan & Bad Character rule)
 * - Z-Algorithm (Z-array & [L, R] bounding window)
 * - Aho-Corasick (Trie automaton & dictionary matching)
 * - Suffix Array & LCP (Lexicographical suffix sorting & Kasai LCP)
 * - Manacher's Algorithm (Delimited palindrome radius expansion)
 * - Generic pattern matching
 */
public class StringPatternVisualizerEngine implements VisualizerEngine {

    private static final Set<String> SUPPORTED = new HashSet<>(Arrays.asList(
            "string_kmp",
            "string_rabin_karp",
            "string_boyer_moore",
            "string_z_algorithm",
            "string_z_algo",
            "string_aho_corasick",
            "string_suffix_array",
            "string_manacher",
            "pattern_matching"
    ));

    private final Paint boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();

    public StringPatternVisualizerEngine() {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        subTextPaint.setTextAlign(Paint.Align.CENTER);
        arrowPaint.setStyle(Paint.Style.STROKE);
        arrowPaint.setStrokeWidth(3f);
    }

    @Override
    public boolean supports(String techniqueId) {
        return techniqueId != null && SUPPORTED.contains(techniqueId);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        String text = resolveText(view);
        int len = Math.max(text.length(), 14);
        if ("string_suffix_array".equals(techniqueId)) {
            len = Math.max(len, 10);
            return Math.max(view.getWidth(), Math.max(480, len * 68 + 80));
        }
        return Math.max(view.getWidth(), Math.max(420, len * 56 + 80));
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.getHeight(), 440);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        if ("string_suffix_array".equals(techniqueId)) {
            drawSuffixArrayScene(canvas, view);
            return;
        }

        String text = resolveText(view);
        String query = resolveQuery(view);
        int windowStart = view.getPatternWindowStart();
        int matchIndex = view.getPatternMatchIndex();
        boolean found = view.isPatternFound();
        int highlightA = view.getHighlightIndex();
        int highlightB = view.getSecondaryHighlightIndex();
        int[] aux = view.getAuxiliaryArray();
        if (aux == null || aux.length == 0) {
            aux = view.getArrayValues();
        }

        float startX = 36f;
        float boxSize = 46f;
        float spacing = 8f;
        float textY = 82f;
        float patternY = 196f;
        float auxY = 310f;

        // ─────────────────────────────────────────────────────────────────────
        // 1. Text Row (Haystack)
        // ─────────────────────────────────────────────────────────────────────
        subTextPaint.setColor(Color.parseColor("#38BDF8"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        subTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("TEXT STRING (HAYSTACK):", startX, textY - 34f, subTextPaint);

        for (int i = 0; i < text.length(); i++) {
            float left = startX + i * (boxSize + spacing);
            float top = textY - boxSize / 2f;
            rect.set(left, top, left + boxSize, top + boxSize);

            char ch = text.charAt(i);
            boolean isMatchedInText = found && windowStart >= 0 && i >= windowStart && i < windowStart + query.length();
            boolean isCurrentChar = (i == highlightA || (windowStart >= 0 && matchIndex >= 0 && i == windowStart + matchIndex));
            boolean isWindowActive = windowStart >= 0 && i >= windowStart && i < windowStart + Math.max(1, query.length());

            if (isMatchedInText) {
                boxPaint.setColor(Color.parseColor("#065F46")); // Deep emerald
                borderPaint.setColor(Color.parseColor("#10B981")); // Emerald border
            } else if (isCurrentChar) {
                if (highlightB == -2 || (!found && matchIndex == -1)) {
                    boxPaint.setColor(Color.parseColor("#7F1D1D")); // Crimson mismatch
                    borderPaint.setColor(Color.parseColor("#EF4444"));
                } else {
                    boxPaint.setColor(Color.parseColor("#1E3A8A")); // Royal blue active compare
                    borderPaint.setColor(Color.parseColor("#60A5FA"));
                }
            } else if (isWindowActive) {
                boxPaint.setColor(Color.parseColor("#1E293B"));
                borderPaint.setColor(Color.parseColor("#0284C7")); // Cyan highlight for window
            } else {
                boxPaint.setColor(Color.parseColor("#0F172A"));
                borderPaint.setColor(Color.parseColor("#334155"));
            }

            boxPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 10f, 10f, boxPaint);
            canvas.drawRoundRect(rect, 10f, 10f, borderPaint);

            // Character text
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(26f);
            textPaint.setFakeBoldText(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(String.valueOf(ch), rect.centerX(), rect.centerY() + 9f, textPaint);

            // Index label above
            subTextPaint.setColor(Color.parseColor("#64748B"));
            subTextPaint.setTextSize(16f);
            subTextPaint.setFakeBoldText(false);
            subTextPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(String.valueOf(i), rect.centerX(), top - 6f, subTextPaint);
        }

        // ─────────────────────────────────────────────────────────────────────
        // 2. Pattern Row (Sliding Needle Window)
        // ─────────────────────────────────────────────────────────────────────
        if (!query.isEmpty() && !"string_manacher".equals(techniqueId) && !"string_z_algorithm".equals(techniqueId) && !"string_z_algo".equals(techniqueId)) {
            subTextPaint.setColor(Color.parseColor("#A78BFA"));
            subTextPaint.setTextSize(24f);
            subTextPaint.setFakeBoldText(true);
            subTextPaint.setTextAlign(Paint.Align.LEFT);
            String patternLabel = found ? "PATTERN: MATCH FOUND AT INDEX " + Math.max(0, windowStart) + "!" : "PATTERN (SLIDING WINDOW):";
            canvas.drawText(patternLabel, startX, patternY - 34f, subTextPaint);

            int shift = Math.max(0, windowStart >= 0 ? windowStart : 0);
            if (shift + query.length() > text.length() + 2) {
                shift = Math.max(0, text.length() - query.length());
            }

            for (int j = 0; j < query.length(); j++) {
                int textIdx = shift + j;
                float left = startX + textIdx * (boxSize + spacing);
                float top = patternY - boxSize / 2f;
                rect.set(left, top, left + boxSize, top + boxSize);

                char ch = query.charAt(j);
                boolean isMatchChar = found || (matchIndex >= 0 && j < matchIndex);
                boolean isCurrentCompare = (matchIndex >= 0 && j == matchIndex);

                if (isMatchChar) {
                    boxPaint.setColor(Color.parseColor("#065F46"));
                    borderPaint.setColor(Color.parseColor("#10B981"));
                } else if (isCurrentCompare) {
                    boxPaint.setColor(Color.parseColor("#4338CA"));
                    borderPaint.setColor(Color.parseColor("#818CF8"));
                } else {
                    boxPaint.setColor(Color.parseColor("#1E1B4B"));
                    borderPaint.setColor(Color.parseColor("#4F46E5"));
                }

                boxPaint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rect, 10f, 10f, boxPaint);
                canvas.drawRoundRect(rect, 10f, 10f, borderPaint);

                textPaint.setColor(Color.WHITE);
                textPaint.setTextSize(26f);
                textPaint.setFakeBoldText(true);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(String.valueOf(ch), rect.centerX(), rect.centerY() + 9f, textPaint);

                // j index below
                subTextPaint.setColor(Color.parseColor("#818CF8"));
                subTextPaint.setTextSize(16f);
                subTextPaint.setFakeBoldText(false);
                subTextPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("j=" + j, rect.centerX(), top + boxSize + 18f, subTextPaint);
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        // 3. Auxiliary Array Table (LPS / Z-array / Radii / Hashes)
        // ─────────────────────────────────────────────────────────────────────
        if (aux != null && aux.length > 0) {
            String auxLabel;
            if ("string_kmp".equals(techniqueId)) {
                auxLabel = "KMP LPS TABLE (Longest Proper Prefix which is also Suffix):";
            } else if ("string_z_algorithm".equals(techniqueId) || "string_z_algo".equals(techniqueId)) {
                auxLabel = "Z-ALGORITHM ARRAY (Prefix Match Lengths):";
            } else if ("string_manacher".equals(techniqueId)) {
                auxLabel = "MANACHER PALINDROME RADII P[i]:";
            } else if ("string_rabin_karp".equals(techniqueId)) {
                auxLabel = "RABIN-KARP ROLLING WINDOW HASHES:";
            } else if ("string_boyer_moore".equals(techniqueId)) {
                auxLabel = "BOYER-MOORE BAD CHARACTER SHIFTS:";
            } else if ("string_aho_corasick".equals(techniqueId)) {
                auxLabel = "AHO-CORASICK AUTOMATON STATES:";
            } else {
                auxLabel = "AUXILIARY COMPUTATION TABLE:";
            }

            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(24f);
            subTextPaint.setFakeBoldText(true);
            subTextPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(auxLabel, startX, auxY - 24f, subTextPaint);

            int displayCount = Math.min(aux.length, Math.max(text.length(), 16));
            for (int k = 0; k < displayCount; k++) {
                float left = startX + k * (boxSize + spacing);
                float top = auxY;
                rect.set(left, top, left + boxSize, top + 42f);

                boolean isActiveAux = (k == highlightA || k == matchIndex);

                if (isActiveAux) {
                    boxPaint.setColor(Color.parseColor("#451A03")); // Deep amber
                    borderPaint.setColor(Color.parseColor("#F59E0B"));
                } else {
                    boxPaint.setColor(Color.parseColor("#1C1917"));
                    borderPaint.setColor(Color.parseColor("#44403C"));
                }

                boxPaint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rect, 8f, 8f, boxPaint);
                canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

                textPaint.setColor(isActiveAux ? Color.parseColor("#FDE68A") : Color.parseColor("#FBBF24"));
                textPaint.setTextSize(20f);
                textPaint.setFakeBoldText(true);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(String.valueOf(aux[k]), rect.centerX(), rect.centerY() + 7f, textPaint);

                // Index below aux box
                subTextPaint.setColor(Color.parseColor("#78716C"));
                subTextPaint.setTextSize(14f);
                subTextPaint.setFakeBoldText(false);
                subTextPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("[" + k + "]", rect.centerX(), top + 42f + 16f, subTextPaint);
            }
        }
    }

    /**
     * Specialized rendering for Suffix Array & LCP table.
     */
    private void drawSuffixArrayScene(Canvas canvas, TechniqueVisualizerView view) {
        String text = resolveText(view);
        if (text.isEmpty()) text = "banana$";

        int highlight = view.getHighlightIndex();
        int[] sa = view.getArrayValues();
        if (sa == null || sa.length == 0) {
            sa = new int[]{6, 5, 3, 1, 0, 4, 2};
        }
        int[] lcp = view.getAuxiliaryArray();
        if (lcp == null || lcp.length == 0) {
            lcp = new int[]{0, 0, 1, 3, 0, 0, 2};
        }

        float startX = 36f;
        float boxSize = 44f;
        float spacing = 8f;
        float headerY = 70f;

        // 1. Text String
        subTextPaint.setColor(Color.parseColor("#38BDF8"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        subTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("SOURCE STRING WITH TERMINATOR: '" + text + "'", startX, headerY - 24f, subTextPaint);

        for (int i = 0; i < text.length(); i++) {
            float left = startX + i * (boxSize + spacing);
            float top = headerY - boxSize / 2f;
            rect.set(left, top, left + boxSize, top + boxSize);

            boxPaint.setColor(Color.parseColor("#0F172A"));
            borderPaint.setColor(Color.parseColor("#334155"));
            boxPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 8f, 8f, boxPaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(24f);
            textPaint.setFakeBoldText(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(String.valueOf(text.charAt(i)), rect.centerX(), rect.centerY() + 8f, textPaint);

            subTextPaint.setColor(Color.parseColor("#64748B"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(String.valueOf(i), rect.centerX(), top - 4f, subTextPaint);
        }

        // 2. Suffix Array & LCP Ranked Grid
        float tableTop = 150f;
        subTextPaint.setColor(Color.parseColor("#A78BFA"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        subTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("SUFFIX ARRAY (SA) & KASAI LCP TABLE (Sorted Lexicographical Order):", startX, tableTop - 18f, subTextPaint);

        float rowHeight = 34f;
        float colWidthRank = 70f;
        float colWidthSA = 80f;
        float colWidthLCP = 80f;
        float colWidthSuffix = 220f;

        // Headers
        float y = tableTop + 24f;
        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(18f);
        subTextPaint.setFakeBoldText(true);
        subTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("RANK", startX + colWidthRank / 2f, y, subTextPaint);
        canvas.drawText("SA[i]", startX + colWidthRank + colWidthSA / 2f, y, subTextPaint);
        canvas.drawText("LCP[i]", startX + colWidthRank + colWidthSA + colWidthLCP / 2f, y, subTextPaint);
        subTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("SUFFIX SUBSTRING", startX + colWidthRank + colWidthSA + colWidthLCP + 16f, y, subTextPaint);

        int count = Math.min(sa.length, text.length());
        for (int r = 0; r < count; r++) {
            float rowY = tableTop + 38f + r * rowHeight;
            rect.set(startX, rowY, startX + colWidthRank + colWidthSA + colWidthLCP + colWidthSuffix, rowY + rowHeight - 4f);

            boolean isCurrentRow = (r == highlight);
            if (isCurrentRow) {
                boxPaint.setColor(Color.parseColor("#312E81")); // Indigo active
                borderPaint.setColor(Color.parseColor("#818CF8"));
            } else {
                boxPaint.setColor(r % 2 == 0 ? Color.parseColor("#131B2E") : Color.parseColor("#0B1120"));
                borderPaint.setColor(Color.parseColor("#1E293B"));
            }

            boxPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 6f, 6f, boxPaint);
            canvas.drawRoundRect(rect, 6f, 6f, borderPaint);

            textPaint.setTextSize(18f);
            textPaint.setFakeBoldText(false);
            textPaint.setTextAlign(Paint.Align.CENTER);

            // Rank
            textPaint.setColor(Color.parseColor("#94A3B8"));
            canvas.drawText(String.valueOf(r), startX + colWidthRank / 2f, rowY + 22f, textPaint);

            // SA index
            textPaint.setColor(Color.parseColor("#38BDF8"));
            textPaint.setFakeBoldText(true);
            int suffixStart = sa[r];
            canvas.drawText(String.valueOf(suffixStart), startX + colWidthRank + colWidthSA / 2f, rowY + 22f, textPaint);

            // LCP value
            int lcpVal = (r < lcp.length) ? lcp[r] : 0;
            textPaint.setColor(Color.parseColor("#FBBF24"));
            canvas.drawText(String.valueOf(lcpVal), startX + colWidthRank + colWidthSA + colWidthLCP / 2f, rowY + 22f, textPaint);

            // Suffix substring
            textPaint.setColor(Color.WHITE);
            textPaint.setFakeBoldText(false);
            textPaint.setTextAlign(Paint.Align.LEFT);
            String suffixSnippet = suffixStart < text.length() ? text.substring(suffixStart) : "$";
            if (suffixSnippet.length() > 14) suffixSnippet = suffixSnippet.substring(0, 14) + "...";
            canvas.drawText(suffixSnippet, startX + colWidthRank + colWidthSA + colWidthLCP + 16f, rowY + 22f, textPaint);
        }
    }

    private static String resolveText(TechniqueVisualizerView view) {
        String t = view.getPatternText();
        if (t != null && !t.isEmpty()) {
            return t;
        }
        int[] arr = view.getArrayValues();
        if (arr != null && arr.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int val : arr) {
                if (val >= 32 && val <= 126) {
                    sb.append((char) val);
                } else {
                    sb.append((char) ('A' + (Math.abs(val) % 26)));
                }
            }
            return sb.toString();
        }
        return "ABABDABACDABABCABAB";
    }

    private static String resolveQuery(TechniqueVisualizerView view) {
        String q = view.getPatternQuery();
        if (q != null && !q.isEmpty()) {
            return q;
        }
        return "ABABCABAB";
    }
}
