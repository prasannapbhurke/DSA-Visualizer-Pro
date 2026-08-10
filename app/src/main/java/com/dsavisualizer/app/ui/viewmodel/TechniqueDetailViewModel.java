package com.dsavisualizer.app.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dsavisualizer.app.data.TechniqueContentRepository;
import com.dsavisualizer.app.model.ContentMetadata;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.model.TechniqueDetailUiModel;

/**
 * ViewModel for the detail header/meta area.
 * Keeps the Activity focused on binding views only.
 */
public class TechniqueDetailViewModel extends ViewModel {

    private final TechniqueContentRepository repository;
    private final MutableLiveData<TechniqueDetailUiModel> detailUiModel = new MutableLiveData<>();

    public TechniqueDetailViewModel() {
        this(new TechniqueContentRepository());
    }

    TechniqueDetailViewModel(TechniqueContentRepository repository) {
        this.repository = repository;
    }

    public LiveData<TechniqueDetailUiModel> getDetailUiModel() {
        return detailUiModel;
    }

    public void load(String techniqueId) {
        TechniqueContent content = repository.getContent(techniqueId == null ? "linear_search" : techniqueId);
        ContentMetadata metadata = buildMetadata(content);
        String sourceLabel = buildSourceLabel(content);
        String versionLabel = buildVersionLabel(content);
        detailUiModel.setValue(new TechniqueDetailUiModel(
            content.techniqueId,
            content.title,
            content.definition,
            content.realLifeExample,
            "Best: " + content.bestTime,
            "Average: " + content.averageTime,
            "Worst: " + content.worstTime,
            "Space: " + content.spaceComplexity,
            sourceLabel,
            versionLabel,
            metadata
        ));
    }

    private ContentMetadata buildMetadata(TechniqueContent content) {
        return new ContentMetadata(
            content.sourceType,
            content.sourceTitle,
            content.sourceChapter,
            content.sourceReference,
            "v" + Math.max(1, content.contentVersion) + " / schema v" + Math.max(1, content.structuredFormatVersion),
            content.updatedAt
        );
    }

    private String buildSourceLabel(TechniqueContent content) {
        StringBuilder builder = new StringBuilder();
        if (content.sourceType != null && !content.sourceType.trim().isEmpty()) {
            builder.append(content.sourceType.trim());
        }
        if (content.sourceTitle != null && !content.sourceTitle.trim().isEmpty()) {
            if (builder.length() > 0) {
                builder.append(" | ");
            }
            builder.append("Source: ").append(content.sourceTitle.trim());
        }
        if (content.sourceChapter != null && !content.sourceChapter.trim().isEmpty()) {
            if (builder.length() > 0) {
                builder.append(" | ");
            }
            builder.append("Chapter: ").append(content.sourceChapter.trim());
        }
        if (content.sourceReference != null && !content.sourceReference.trim().isEmpty()) {
            if (builder.length() > 0) {
                builder.append(" | ");
            }
            builder.append("Ref: ").append(content.sourceReference.trim());
        }
        return builder.toString();
    }

    private String buildVersionLabel(TechniqueContent content) {
        StringBuilder builder = new StringBuilder();
        builder.append("Version: ").append("v").append(Math.max(1, content.contentVersion));
        builder.append(" | Schema v").append(Math.max(1, content.structuredFormatVersion));
        if (content.updatedAt != null && !content.updatedAt.trim().isEmpty()) {
            builder.append(" | Updated: ").append(content.updatedAt.trim());
        }
        return builder.toString();
    }
}
