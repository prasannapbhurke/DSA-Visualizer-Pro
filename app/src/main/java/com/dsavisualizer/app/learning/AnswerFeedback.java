package com.dsavisualizer.app.learning;

public class AnswerFeedback {

    public final boolean correct;
    public final String title;
    public final String explanation;

    public AnswerFeedback(boolean correct, String title, String explanation) {
        this.correct = correct;
        this.title = title == null ? "" : title;
        this.explanation = explanation == null ? "" : explanation;
    }
}
