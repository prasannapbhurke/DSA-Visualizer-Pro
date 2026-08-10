package com.dsavisualizer.app.learning;

public class QuestionOption {

    public final String id;
    public final ActionType actionType;
    public final String label;
    public final boolean correct;

    public QuestionOption(String id, ActionType actionType, String label, boolean correct) {
        this.id = id == null ? "" : id;
        this.actionType = actionType == null ? ActionType.COMPLETE : actionType;
        this.label = label == null ? "" : label;
        this.correct = correct;
    }
}
