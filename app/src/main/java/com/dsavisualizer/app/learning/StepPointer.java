package com.dsavisualizer.app.learning;

public class StepPointer {

    public final String label;
    public final int index;

    public StepPointer(String label, int index) {
        this.label = label == null ? "" : label;
        this.index = index;
    }
}
