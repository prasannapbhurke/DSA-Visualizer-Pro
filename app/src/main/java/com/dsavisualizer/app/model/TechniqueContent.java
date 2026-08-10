package com.dsavisualizer.app.model;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TechniqueContent {
    public final String techniqueId;
    public final String title;
    public final String definition;
    public final String realLifeExample;
    public final String bestTime;
    public final String averageTime;
    public final String worstTime;
    public final String spaceComplexity;
    public final int[] sampleInput;
    public final int sampleTarget;
    public int contentVersion = 1;
    public int structuredFormatVersion = 1;
    public String sourceType = "Internal";
    public String sourceTitle = "";
    public String sourceChapter = "";
    public String sourceReference = "";
    public String updatedAt = "";
    public String overrideOf = "";
    public String visualizationType = "";
    public final List<String> explanationSteps = new ArrayList<>();
    public final Map<String, String> algorithmByStyle = new LinkedHashMap<>();
    public final Map<String, String> codeByStyle = new LinkedHashMap<>();
    public final Map<String, Map<String, String>> codeByLanguageAndStyle = new LinkedHashMap<>();
    public final Map<String, Map<String, String>> commentedCodeByLanguageAndStyle = new LinkedHashMap<>();

    public TechniqueContent(
        String techniqueId,
        String title,
        String definition,
        String realLifeExample,
        String bestTime,
        String averageTime,
        String worstTime,
        String spaceComplexity,
        int[] sampleInput,
        int sampleTarget
    ) {
        this.techniqueId = techniqueId;
        this.title = title;
        this.definition = definition;
        this.realLifeExample = realLifeExample;
        this.bestTime = bestTime;
        this.averageTime = averageTime;
        this.worstTime = worstTime;
        this.spaceComplexity = spaceComplexity;
        this.sampleInput = sampleInput;
        this.sampleTarget = sampleTarget;
    }

    public void putCode(String language, String style, String code, boolean commented) {
        if (language == null || style == null || code == null) {
            return;
        }
        Map<String, Map<String, String>> target = commented
            ? commentedCodeByLanguageAndStyle
            : codeByLanguageAndStyle;
        Map<String, String> byStyle = target.get(language);
        if (byStyle == null) {
            byStyle = new LinkedHashMap<>();
            target.put(language, byStyle);
        }
        byStyle.put(style, code);
    }

    public String getCode(String language, String style, boolean commented) {
        Map<String, Map<String, String>> target = commented
            ? commentedCodeByLanguageAndStyle
            : codeByLanguageAndStyle;
        Map<String, String> byStyle = target.get(language);
        if (byStyle == null) {
            return null;
        }
        return byStyle.get(style);
    }
}
