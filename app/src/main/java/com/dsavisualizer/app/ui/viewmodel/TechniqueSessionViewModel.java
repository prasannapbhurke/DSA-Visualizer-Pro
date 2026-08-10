package com.dsavisualizer.app.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dsavisualizer.app.learning.DifficultyLevel;
import com.dsavisualizer.app.learning.StepState;

/**
 * Shared screen state between the visualization tab and the code tab.
 * This keeps traversal choices and current highlighted code line in sync.
 */
public class TechniqueSessionViewModel extends ViewModel {

    private final MutableLiveData<Integer> highlightedCodeLine = new MutableLiveData<>(-1);
    private final MutableLiveData<Integer> highlightedAlgorithmLine = new MutableLiveData<>(-1);
    private final MutableLiveData<String> graphMode = new MutableLiveData<>("BFS");
    private final MutableLiveData<String> treeMode = new MutableLiveData<>("Inorder");
    private final MutableLiveData<StepState> currentLearningStep = new MutableLiveData<>();
    private final MutableLiveData<DifficultyLevel> difficultyLevel = new MutableLiveData<>(DifficultyLevel.GUIDED);

    public LiveData<Integer> getHighlightedCodeLine() {
        return highlightedCodeLine;
    }

    public void setHighlightedCodeLine(int line) {
        highlightedCodeLine.setValue(line);
    }

    public LiveData<Integer> getHighlightedAlgorithmLine() {
        return highlightedAlgorithmLine;
    }

    public void setHighlightedAlgorithmLine(int line) {
        highlightedAlgorithmLine.setValue(line);
    }

    public LiveData<String> getGraphMode() {
        return graphMode;
    }

    public void setGraphMode(String mode) {
        graphMode.setValue(mode);
    }

    public LiveData<String> getTreeMode() {
        return treeMode;
    }

    public void setTreeMode(String mode) {
        treeMode.setValue(mode);
    }

    public LiveData<StepState> getCurrentLearningStep() {
        return currentLearningStep;
    }

    public void setCurrentLearningStep(StepState stepState) {
        currentLearningStep.setValue(stepState);
    }

    public LiveData<DifficultyLevel> getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel level) {
        difficultyLevel.setValue(level == null ? DifficultyLevel.GUIDED : level);
    }
}
