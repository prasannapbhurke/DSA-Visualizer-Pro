package com.dsavisualizer.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlgorithmViewModel extends ViewModel {
    private final MutableLiveData<String> explanation = new MutableLiveData<>("Ready to visualize!");
    private final MutableLiveData<Integer> activeCodeLine = new MutableLiveData<>(-1);
    private final MutableLiveData<Boolean> isRunning = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> stepCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> operationLabel = new MutableLiveData<>("Ready");
    
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    
    public LiveData<String> getExplanation() { return explanation; }
    public LiveData<Integer> getActiveCodeLine() { return activeCodeLine; }
    public LiveData<Boolean> getIsRunning() { return isRunning; }
    public LiveData<Integer> getStepCount() { return stepCount; }
    public LiveData<String> getOperationLabel() { return operationLabel; }

    public void updateState(int line, String msg) {
        activeCodeLine.postValue(line);
        explanation.postValue(msg);
        Integer currentCount = stepCount.getValue();
        stepCount.postValue(currentCount == null ? 1 : currentCount + 1);
        operationLabel.postValue(inferOperation(msg));
    }

    public void setRunning(boolean running) {
        isRunning.postValue(running);
    }

    public void resetTrace() {
        activeCodeLine.postValue(-1);
        explanation.postValue("Ready to visualize!");
        stepCount.postValue(0);
        operationLabel.postValue("Ready");
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    private String inferOperation(String message) {
        String normalized = message == null ? "" : message.toLowerCase();

        if (normalized.contains("compare")) {
            return "Comparing";
        }
        if (normalized.contains("swap")) {
            return "Swapping";
        }
        if (normalized.contains("pivot")) {
            return "Partitioning";
        }
        if (normalized.contains("shift")) {
            return "Shifting";
        }
        if (normalized.contains("insert")) {
            return "Inserting";
        }
        if (normalized.contains("match")) {
            return "Matching";
        }
        if (normalized.contains("visit") || normalized.contains("travers")) {
            return "Visiting";
        }
        if (normalized.contains("heap")) {
            return "Heapifying";
        }
        if (normalized.contains("move")) {
            return "Moving";
        }
        if (normalized.contains("found") || normalized.contains("completed")) {
            return "Completed";
        }
        if (normalized.contains("reset")) {
            return "Reset";
        }
        return "Tracing";
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdownNow();
    }
}
