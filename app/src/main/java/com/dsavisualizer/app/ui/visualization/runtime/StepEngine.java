package com.dsavisualizer.app.ui.visualization.runtime;

public interface StepEngine {
    Step next();
    Step prev();
    void reset();
}
