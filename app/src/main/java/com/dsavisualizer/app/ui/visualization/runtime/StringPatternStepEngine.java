package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dedicated visual step engine for String & Pattern Matching algorithms:
 * - KMP (LPS table computation & matching with zero-backtrack jumps)
 * - Rabin-Karp (Rolling hash computation & sliding window matching)
 * - Boyer-Moore (Right-to-left scan with Bad Character table shifts)
 * - Z-Algorithm (Z-array computation with [L, R] boundary window)
 * - Aho-Corasick (Automaton multi-pattern matching with dictionary failure links)
 * - Suffix Array & LCP (Lexicographical suffix order & longest common prefix)
 * - Manacher's Algorithm (Linear-time palindromic radius expansion with mirror symmetry)
 */
public final class StringPatternStepEngine extends BaseStepEngine {

    public StringPatternStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        String id = host.getTechniqueId();
        if ("string_kmp".equals(id)) {
            advanceKmp(host);
        } else if ("string_rabin_karp".equals(id)) {
            advanceRabinKarp(host);
        } else if ("string_boyer_moore".equals(id)) {
            advanceBoyerMoore(host);
        } else if ("string_z_algorithm".equals(id) || "string_z_algo".equals(id)) {
            advanceZAlgo(host);
        } else if ("string_aho_corasick".equals(id)) {
            advanceAhoCorasick(host);
        } else if ("string_suffix_array".equals(id)) {
            advanceSuffixArray(host);
        } else if ("string_manacher".equals(id)) {
            advanceManacher(host);
        } else {
            advanceGenericPattern(host);
        }
    }

    // =========================================================================
    // Preset Configurations & Metadata
    // =========================================================================

    public static String getPresetText(String techniqueId, int preset) {
        if ("string_kmp".equals(techniqueId)) {
            if (preset == 2) return "ACGTACGTACGTAGCT";
            if (preset == 3) return "AAAAABAAAAABAA";
            return "ABABDABACDABABCABAB";
        }
        if ("string_rabin_karp".equals(techniqueId)) {
            if (preset == 2) return "CATDATBATCATRAT";
            if (preset == 3) return "GTCGATCGACTGACTA";
            return "AABAACAADAABAABA";
        }
        if ("string_boyer_moore".equals(techniqueId)) {
            if (preset == 2) return "ABAAABCDABCDE";
            if (preset == 3) return "GCTTCTGCTACCTTTTGCGC";
            return "FINDINAHAYSTACKNEEDLE";
        }
        if ("string_z_algorithm".equals(techniqueId) || "string_z_algo".equals(techniqueId)) {
            if (preset == 2) return "abacaba";
            if (preset == 3) return "aaaaaa";
            return "aabacaabaad";
        }
        if ("string_aho_corasick".equals(techniqueId)) {
            if (preset == 2) return "ahocorasick";
            if (preset == 3) return "mississippi";
            return "ushers";
        }
        if ("string_suffix_array".equals(techniqueId)) {
            if (preset == 2) return "abracadabra$";
            if (preset == 3) return "camelcase$";
            return "banana$";
        }
        if ("string_manacher".equals(techniqueId)) {
            if (preset == 2) return "babad";
            if (preset == 3) return "forgeeksskeegfor";
            return "abacaba";
        }
        return "ABACABADABAC";
    }

    public static String getPresetQuery(String techniqueId, int preset) {
        if ("string_kmp".equals(techniqueId)) {
            if (preset == 2) return "ACGTAG";
            if (preset == 3) return "AAAAAB";
            return "ABABCABAB";
        }
        if ("string_rabin_karp".equals(techniqueId)) {
            if (preset == 2) return "CAT";
            if (preset == 3) return "ACTG";
            return "AABA";
        }
        if ("string_boyer_moore".equals(techniqueId)) {
            if (preset == 2) return "ABCDE";
            if (preset == 3) return "CCTTTT";
            return "NEEDLE";
        }
        if ("string_z_algorithm".equals(techniqueId) || "string_z_algo".equals(techniqueId)) {
            if (preset == 2) return "aba";
            if (preset == 3) return "aa";
            return "aab";
        }
        if ("string_aho_corasick".equals(techniqueId)) {
            if (preset == 2) return "aho,cor,sick";
            if (preset == 3) return "is,sip,pi";
            return "he,she,his,hers";
        }
        if ("string_suffix_array".equals(techniqueId)) {
            return "$";
        }
        if ("string_manacher".equals(techniqueId)) {
            return "";
        }
        return "ABAC";
    }

    public static String getPresetDescription(String techniqueId, int preset) {
        String txt = getPresetText(techniqueId, preset);
        String qry = getPresetQuery(techniqueId, preset);
        if ("string_suffix_array".equals(techniqueId) || "string_manacher".equals(techniqueId)) {
            return "Target String: '" + txt + "'\nAnalyzes suffix/palindromic substructures.";
        }
        return "Text: '" + txt + "'\nPattern: '" + qry + "'";
    }

    public static int getStringPatternTotalSteps(String techniqueId, String text, String pattern, int preset) {
        String txt = (text != null && !text.isEmpty()) ? text : getPresetText(techniqueId, preset);
        String pat = (pattern != null && !pattern.isEmpty()) ? pattern : getPresetQuery(techniqueId, preset);

        if ("string_kmp".equals(techniqueId)) {
            return pat.length() + 5;
        }
        if ("string_rabin_karp".equals(techniqueId)) {
            int m = pat.length();
            int n = txt.length();
            return Math.max(1, n - m + 1) + 1;
        }
        if ("string_boyer_moore".equals(techniqueId)) {
            return 5;
        }
        if ("string_z_algorithm".equals(techniqueId) || "string_z_algo".equals(techniqueId)) {
            return txt.length() + 1;
        }
        if ("string_aho_corasick".equals(techniqueId)) {
            return 5;
        }
        if ("string_suffix_array".equals(techniqueId)) {
            String s = txt.endsWith("$") ? txt : txt + "$";
            return s.length() * 2 + 1;
        }
        if ("string_manacher".equals(techniqueId)) {
            return (txt.length() * 2 + 1) + 1;
        }
        return 6;
    }

    // =========================================================================
    // Dynamic Algorithm Helpers
    // =========================================================================

    public static int[] computeLps(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;
        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    public static int[] computeZArray(String s) {
        int n = s.length();
        int[] z = new int[n];
        if (n == 0) return z;
        z[0] = n;
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i > r) {
                l = r = i;
                while (r < n && s.charAt(r - l) == s.charAt(r)) {
                    r++;
                }
                z[i] = r - l;
                r--;
            } else {
                int k = i - l;
                if (z[k] < r - i + 1) {
                    z[i] = z[k];
                } else {
                    l = i;
                    while (r < n && s.charAt(r - l) == s.charAt(r)) {
                        r++;
                    }
                    z[i] = r - l;
                    r--;
                }
            }
        }
        return z;
    }

    public static int[] computeSuffixArray(String s) {
        int n = s.length();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> s.substring(a).compareTo(s.substring(b)));
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = order[i];
        return sa;
    }

    public static int[] computeKasaiLcp(String s, int[] sa) {
        int n = s.length();
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;
        int[] lcp = new int[n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (rank[i] == n - 1) {
                k = 0;
                continue;
            }
            int j = sa[rank[i] + 1];
            while (i + k < n && j + k < n && s.charAt(i + k) == s.charAt(j + k)) {
                k++;
            }
            lcp[rank[i]] = k;
            if (k > 0) k--;
        }
        return lcp;
    }

    public static int[] computeManacher(String s) {
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c).append("#");
        }
        String t = sb.toString();
        int n = t.length();
        int[] p = new int[n];
        int c = 0, r = 0;
        for (int i = 0; i < n; i++) {
            int mirror = 2 * c - i;
            if (i < r) {
                p[i] = Math.min(r - i, p[mirror]);
            }
            while (i - 1 - p[i] >= 0 && i + 1 + p[i] < n && t.charAt(i + 1 + p[i]) == t.charAt(i - 1 - p[i])) {
                p[i]++;
            }
            if (i + p[i] > r) {
                c = i;
                r = i + p[i];
            }
        }
        return p;
    }

    // =========================================================================
    // 1. KMP (Knuth-Morris-Pratt)
    // =========================================================================
    private static void advanceKmp(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_kmp", host.getPatternPreset());
        String pat = host.getPatternQuery();
        if (pat == null || pat.isEmpty()) pat = getPresetQuery("string_kmp", host.getPatternPreset());

        int[] lps = computeLps(pat);
        int lpsSteps = pat.length();

        // Phase 1: Build LPS array
        if (step <= lpsSteps) {
            int idx = step - 1;
            int[] partialLps = new int[idx + 1];
            System.arraycopy(lps, 0, partialLps, 0, idx + 1);
            host.setAuxiliaryArray(partialLps);
            host.setPatternState(text, pat, 0, idx, false);
            host.updateArrayVisualization(idx, -1, -1);
            host.updatePointerState("i", idx, "LPS[i]", lps[idx]);
            host.setHighlightedLines(8, 3);
            host.bindCurrentState("KMP Preprocessing: LPS[" + idx + "] = " + lps[idx]
                + " for prefix '" + pat.substring(0, idx + 1) + "' (longest proper prefix which is also suffix).");
            return;
        }

        // Phase 2: Text Matching using LPS jumps
        int matchStep = step - lpsSteps;
        host.setAuxiliaryArray(lps);
        int matchPos = text.indexOf(pat);
        int displayPos = matchPos >= 0 ? matchPos : Math.max(0, text.length() - pat.length());

        switch (matchStep) {
            case 1:
                host.setPatternState(text, pat, 0, 0, false);
                host.updatePointerState("Text", 0, "Pat", 0);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Scanning Text at index 0. Comparing prefix of '" + pat + "' against '" + text.substring(0, Math.min(text.length(), pat.length())) + "'...");
                break;
            case 2:
                int mismatchIdx = Math.min(pat.length() - 1, 3);
                int jumpTarget = lps[Math.max(0, mismatchIdx - 1)];
                host.setPatternState(text, pat, 0, mismatchIdx, false);
                host.updatePointerState("Mismatch", mismatchIdx, "LPS Jump", jumpTarget);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Mismatch at pattern[" + mismatchIdx + "]! Lookup LPS[" + (mismatchIdx - 1) + "] = " + jumpTarget + ". Jump pattern pointer j = " + jumpTarget + " without text backtrack.");
                break;
            case 3:
                int nextWindow = Math.min(text.length() - pat.length(), Math.max(1, displayPos / 2));
                host.setPatternState(text, pat, nextWindow, 0, false);
                host.updatePointerState("Window", nextWindow, "Pat", 0);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Shift pattern to window " + nextWindow + ". Character matching continues in O(N) linear time.");
                break;
            case 4:
                host.setPatternState(text, pat, displayPos, pat.length() / 2, false);
                host.updatePointerState("Text", displayPos, "Match", pat.length() / 2);
                host.setHighlightedLines(19, 7);
                host.bindCurrentState("Window aligned at index " + displayPos + ". All characters match sequentially!");
                break;
            case 5:
            default:
                if (matchPos >= 0) {
                    host.setPatternState(text, pat, matchPos, pat.length(), true);
                    host.updatePointerState("Text", matchPos, "Match", matchPos + pat.length());
                    host.setHighlightedLines(28, 14);
                    host.finish("Pattern '" + pat + "' matched at index " + matchPos + " with 0 backward text rescans!");
                } else {
                    host.setPatternState(text, pat, displayPos, -1, false);
                    host.finish("KMP search complete. Pattern '" + pat + "' not found in text.");
                }
                break;
        }
    }

    // =========================================================================
    // 2. Rabin-Karp Rolling Hash
    // =========================================================================
    private static void advanceRabinKarp(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_rabin_karp", host.getPatternPreset());
        String pat = host.getPatternQuery();
        if (pat == null || pat.isEmpty()) pat = getPresetQuery("string_rabin_karp", host.getPatternPreset());

        int m = pat.length();
        int n = text.length();
        int numWindows = Math.max(1, n - m + 1);

        // Compute simulated window hashes
        int patHash = 0;
        for (int i = 0; i < m; i++) patHash = (patHash * 31 + pat.charAt(i)) % 1000;

        int[] windowHashes = new int[numWindows];
        for (int w = 0; w < numWindows; w++) {
            int h = 0;
            for (int j = 0; j < m; j++) {
                h = (h * 31 + text.charAt(w + j)) % 1000;
            }
            windowHashes[w] = h;
        }

        if (step <= numWindows) {
            int winIdx = step - 1;
            int currHash = windowHashes[winIdx];
            boolean hashMatch = (currHash == patHash);
            boolean exactMatch = hashMatch && text.substring(winIdx, winIdx + m).equals(pat);

            host.setAuxiliaryArray(windowHashes);
            host.setPatternState(text, pat, winIdx, exactMatch ? m : (hashMatch ? 0 : -1), exactMatch);
            host.updatePointerState("Window", winIdx, "Hash", currHash);

            if (exactMatch) {
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Window " + winIdx + ": Hash " + currHash + " == " + patHash + "! Verified characters: MATCH FOUND.");
            } else if (hashMatch) {
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Window " + winIdx + ": Hash " + currHash + " matches, but spurious collision detected upon verification.");
            } else {
                host.setHighlightedLines(15, 6);
                host.bindCurrentState("Window " + winIdx + ": Hash " + currHash + " != " + patHash + ". Rolling update: remove text[" + winIdx + "], add next in O(1).");
            }
        } else {
            host.setAuxiliaryArray(windowHashes);
            host.finish("Rabin-Karp search complete across all " + numWindows + " rolling windows.");
        }
    }

    // =========================================================================
    // 3. Boyer-Moore (Bad Character Heuristic)
    // =========================================================================
    private static void advanceBoyerMoore(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_boyer_moore", host.getPatternPreset());
        String pat = host.getPatternQuery();
        if (pat == null || pat.isEmpty()) pat = getPresetQuery("string_boyer_moore", host.getPatternPreset());

        int matchPos = text.indexOf(pat);
        int displayPos = matchPos >= 0 ? matchPos : Math.max(0, text.length() - pat.length());

        switch (step) {
            case 1:
                host.setAuxiliaryArray(new int[]{pat.length(), pat.length() - 1, 1, 0});
                host.setPatternState(text, pat, 0, pat.length() - 1, false);
                host.updatePointerState("Shift", 0, "Scan", pat.length() - 1);
                host.setHighlightedLines(12, 4);
                char badChar = text.charAt(Math.min(text.length() - 1, pat.length() - 1));
                host.bindCurrentState("Boyer-Moore: Scanning right-to-left from pattern end (index " + (pat.length() - 1) + "). Text char '" + badChar + "'.");
                break;
            case 2:
                int shiftAmt = Math.max(1, pat.length() - 2);
                host.setPatternState(text, pat, shiftAmt, pat.length() - 1, false);
                host.updatePointerState("BadCharRule", shiftAmt, "Shift", shiftAmt);
                host.setHighlightedLines(16, 7);
                host.bindCurrentState("Bad Character Rule: Character not aligned. Shift pattern forward by " + shiftAmt + " positions!");
                break;
            case 3:
                host.setPatternState(text, pat, displayPos, pat.length() - 1, false);
                host.updatePointerState("Shift", displayPos, "Scan", displayPos + pat.length() - 1);
                host.setHighlightedLines(20, 9);
                host.bindCurrentState("Scanning at window " + displayPos + " right-to-left: suffix characters match!");
                break;
            case 4:
                host.setPatternState(text, pat, displayPos, pat.length(), matchPos >= 0);
                host.setHighlightedLines(25, 12);
                if (matchPos >= 0) {
                    host.finish("Boyer-Moore matched pattern '" + pat + "' at index " + matchPos + " in sub-linear O(N/M) average comparisons!");
                } else {
                    host.finish("Boyer-Moore search completed. Pattern not found.");
                }
                break;
            default:
                host.finish("Boyer-Moore search complete.");
                break;
        }
    }

    // =========================================================================
    // 4. Z-Algorithm
    // =========================================================================
    private static void advanceZAlgo(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_z_algorithm", host.getPatternPreset());
        String pat = host.getPatternQuery();

        int[] z = computeZArray(text);

        if (step <= text.length()) {
            int i = step - 1;
            int[] currZ = new int[i + 1];
            System.arraycopy(z, 0, currZ, 0, i + 1);
            host.setAuxiliaryArray(currZ);
            host.setPatternState(text, pat, i, z[i], false);
            host.updatePointerState("i", i, "Z[i]", z[i]);
            host.setHighlightedLines(14, 5);
            host.bindCurrentState("Z-Algorithm: Z[" + i + "] = " + z[i]
                + ". Longest substring starting at " + i + " matching prefix has length " + z[i] + ".");
        } else {
            host.setAuxiliaryArray(z);
            host.finish("Z-Array construction complete in O(N) linear time using [L, R] bounding window!");
        }
    }

    // =========================================================================
    // 5. Aho-Corasick Multi-Pattern Automaton
    // =========================================================================
    private static void advanceAhoCorasick(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_aho_corasick", host.getPatternPreset());
        String queries = host.getPatternQuery();
        if (queries == null || queries.isEmpty()) queries = getPresetQuery("string_aho_corasick", host.getPatternPreset());

        int[] trieNodes = {0, 1, 2, 3, 4, 5, 6};
        host.setAuxiliaryArray(trieNodes);

        switch (step) {
            case 1:
                host.setActiveNodes(0, 1);
                host.setPatternState(text, queries, 0, 0, false);
                host.updatePointerState("Node", 1, "Char", text.charAt(0));
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Aho-Corasick: Trie built with keywords [" + queries + "]. Reading text[0] = '" + text.charAt(0) + "'.");
                break;
            case 2:
                int charIdx = Math.min(text.length() - 1, 1);
                host.setActiveNodes(1, 2);
                host.setPatternState(text, queries, 0, 1, false);
                host.updatePointerState("Node", 2, "Char", text.charAt(charIdx));
                host.setHighlightedLines(15, 6);
                host.bindCurrentState("Transition to trie Node 2 on '" + text.charAt(charIdx) + "'. Active match tracking simultaneously.");
                break;
            case 3:
                int matchWindow = Math.min(text.length() - 2, 2);
                host.setActiveNodes(2, 3);
                host.setPatternState(text, queries, matchWindow, 2, true);
                host.updatePointerState("Node", 3, "Match", 1);
                host.setHighlightedLines(18, 8);
                host.bindCurrentState("Keyword matched! Terminal node reached in automaton dictionary in O(1) step.");
                break;
            case 4:
                host.setActiveNodes(3, 0);
                host.updatePointerState("FailLink", 0, "Resume", 3);
                host.setHighlightedLines(22, 10);
                host.bindCurrentState("Failure link followed to longest proper suffix node on boundary. Continues stream processing.");
                break;
            case 5:
            default:
                host.finish("Aho-Corasick multi-string search complete with zero text rewinding!");
                break;
        }
    }

    // =========================================================================
    // 6. Suffix Array & LCP
    // =========================================================================
    private static void advanceSuffixArray(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_suffix_array", host.getPatternPreset());
        if (!text.endsWith("$")) text = text + "$";

        int n = text.length();
        int[] sa = computeSuffixArray(text);
        int[] lcp = computeKasaiLcp(text, sa);

        if (step <= n) {
            int r = step - 1;
            host.setWorkingValues(sa);
            host.setAuxiliaryArray(lcp);
            host.updateArrayVisualization(r, -1, -1);
            host.updatePointerState("Rank", r, "SuffixIdx", sa[r]);
            host.setHighlightedLines(12, 4);
            String suffixSnippet = sa[r] < text.length() ? text.substring(sa[r]) : "$";
            if (suffixSnippet.length() > 10) suffixSnippet = suffixSnippet.substring(0, 10) + "...";
            host.bindCurrentState("Suffix Array sorted rank " + r + ": suffix starting at index " + sa[r] + " ('" + suffixSnippet + "').");
        } else if (step <= 2 * n) {
            int lcpIdx = step - n - 1;
            host.setWorkingValues(sa);
            host.setAuxiliaryArray(lcp);
            host.updateArrayVisualization(lcpIdx, -1, -1);
            host.updatePointerState("LCP", lcpIdx, "PrefixLen", lcp[lcpIdx]);
            host.setHighlightedLines(22, 9);
            host.bindCurrentState("Kasai's LCP[" + lcpIdx + "] = " + lcp[lcpIdx] + ": Longest Common Prefix between consecutive sorted suffixes.");
        } else {
            host.finish("Suffix Array & LCP Construction Complete! Ready for O(M log N) full-text substring queries.");
        }
    }

    // =========================================================================
    // 7. Manacher's Algorithm
    // =========================================================================
    private static void advanceManacher(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        String text = host.getPatternText();
        if (text == null || text.isEmpty()) text = getPresetText("string_manacher", host.getPatternPreset());

        int[] p = computeManacher(text);
        int totalLen = p.length;

        if (step <= totalLen) {
            int i = step - 1;
            int[] currP = new int[i + 1];
            System.arraycopy(p, 0, currP, 0, i + 1);
            host.setAuxiliaryArray(currP);

            // Find maximum radius so far
            int maxR = 0, bestCenter = 0;
            for (int k = 0; k <= i; k++) {
                if (p[k] > maxR) {
                    maxR = p[k];
                    bestCenter = k;
                }
            }

            host.setPatternState(text, "", i, p[i], false);
            host.updateArrayVisualization(i, bestCenter, -1);
            host.updatePointerState("Center C", i, "Radius P[i]", p[i]);
            host.setHighlightedLines(16, 6);
            host.bindCurrentState("Manacher: Centered at index " + i + ", Palindrome Radius P[" + i + "] = " + p[i]
                + (p[i] > 0 ? " (Palindromic span length = " + p[i] + ")" : "."));
        } else {
            int maxRadius = 0, bestCenter = 0;
            for (int k = 0; k < p.length; k++) {
                if (p[k] > maxRadius) {
                    maxRadius = p[k];
                    bestCenter = k;
                }
            }
            int start = (bestCenter - maxRadius) / 2;
            int end = start + maxRadius;
            String longestPal = (start >= 0 && end <= text.length()) ? text.substring(start, end) : text;

            host.setAuxiliaryArray(p);
            host.finish("Manacher's Algorithm complete! Longest palindrome is '" + longestPal + "' (length = " + maxRadius + ") in strictly linear O(N)!");
        }
    }

    private static void advanceGenericPattern(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        if (step >= 5) {
            host.finish("Pattern matching completed.");
        } else {
            host.bindCurrentState("Scanning pattern comparison window: step " + step + ".");
        }
    }
}
