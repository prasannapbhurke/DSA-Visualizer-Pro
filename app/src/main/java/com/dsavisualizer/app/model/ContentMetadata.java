package com.dsavisualizer.app.model;

public class ContentMetadata {

    public final String sourceType;
    public final String sourceTitle;
    public final String sourceChapter;
    public final String sourceReference;
    public final String versionLabel;
    public final String lastUpdated;

    public ContentMetadata(
        String sourceType,
        String sourceTitle,
        String sourceChapter,
        String sourceReference,
        String versionLabel,
        String lastUpdated
    ) {
        this.sourceType = sourceType == null ? "" : sourceType;
        this.sourceTitle = sourceTitle == null ? "" : sourceTitle;
        this.sourceChapter = sourceChapter == null ? "" : sourceChapter;
        this.sourceReference = sourceReference == null ? "" : sourceReference;
        this.versionLabel = versionLabel == null ? "" : versionLabel;
        this.lastUpdated = lastUpdated == null ? "" : lastUpdated;
    }
}
