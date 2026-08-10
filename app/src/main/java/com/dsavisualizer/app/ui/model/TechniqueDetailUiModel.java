package com.dsavisualizer.app.ui.model;

import com.dsavisualizer.app.model.ContentMetadata;

public class TechniqueDetailUiModel {
    public final String techniqueId;
    public final String title;
    public final String definition;
    public final String example;
    public final String bestTimeLabel;
    public final String averageTimeLabel;
    public final String worstTimeLabel;
    public final String spaceLabel;
    public final String sourceLabel;
    public final String versionLabel;
    public final ContentMetadata contentMetadata;

    public TechniqueDetailUiModel(
        String techniqueId,
        String title,
        String definition,
        String example,
        String bestTimeLabel,
        String averageTimeLabel,
        String worstTimeLabel,
        String spaceLabel,
        String sourceLabel,
        String versionLabel,
        ContentMetadata contentMetadata
    ) {
        this.techniqueId = techniqueId;
        this.title = title;
        this.definition = definition;
        this.example = example;
        this.bestTimeLabel = bestTimeLabel;
        this.averageTimeLabel = averageTimeLabel;
        this.worstTimeLabel = worstTimeLabel;
        this.spaceLabel = spaceLabel;
        this.sourceLabel = sourceLabel;
        this.versionLabel = versionLabel;
        this.contentMetadata = contentMetadata;
    }
}
