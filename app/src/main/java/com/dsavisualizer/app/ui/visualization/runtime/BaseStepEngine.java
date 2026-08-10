package com.dsavisualizer.app.ui.visualization.runtime;

public abstract class BaseStepEngine implements StepEngine {

    protected final VisualizationRuntimeHost host;

    protected BaseStepEngine(VisualizationRuntimeHost host) {
        this.host = host;
    }

    @Override
    public Step prev() {
        if (host == null) {
            return Step.idle(true, false);
        }
        if (!host.canRestorePreviousSnapshot()) {
            return Step.idle(true, host.isPlaybackComplete());
        }
        host.restorePreviousSnapshot();
        return Step.moved(!host.canRestorePreviousSnapshot(), host.isPlaybackComplete());
    }

    @Override
    public void reset() {
        if (host == null) {
            return;
        }
        host.resetVisualizationState();
        host.captureCurrentSnapshot();
    }
}
