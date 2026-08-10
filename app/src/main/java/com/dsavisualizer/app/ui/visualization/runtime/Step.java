package com.dsavisualizer.app.ui.visualization.runtime;

/**
 * Immutable playback step result shared by every algorithm family.
 * The playback controller uses only this object to decide whether to
 * advance the timeline, pause at the end, or disable previous at start.
 */
public final class Step {

    public final boolean moved;
    public final boolean atStart;
    public final boolean atEnd;

    private Step(boolean moved, boolean atStart, boolean atEnd) {
        this.moved = moved;
        this.atStart = atStart;
        this.atEnd = atEnd;
    }

    public static Step moved(boolean atStart, boolean atEnd) {
        return new Step(true, atStart, atEnd);
    }

    public static Step idle(boolean atStart, boolean atEnd) {
        return new Step(false, atStart, atEnd);
    }
}
