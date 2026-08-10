package com.dsavisualizer.app.ui.visualization.playback;

/**
 * Immutable playback snapshot consumed by the UI.
 * The fragment observes this state and only renders controls.
 */
public final class PlaybackState {

    public final boolean isPlaying;
    public final int currentStepIndex;
    public final float playbackSpeed;
    public final boolean isAtStart;
    public final boolean isAtEnd;

    public PlaybackState(
        boolean isPlaying,
        int currentStepIndex,
        float playbackSpeed,
        boolean isAtStart,
        boolean isAtEnd
    ) {
        this.isPlaying = isPlaying;
        this.currentStepIndex = Math.max(0, currentStepIndex);
        this.playbackSpeed = playbackSpeed <= 0f ? 1f : playbackSpeed;
        this.isAtStart = isAtStart;
        this.isAtEnd = isAtEnd;
    }

    public static PlaybackState initial() {
        return new PlaybackState(false, 0, 1f, true, false);
    }

    public PlaybackState withPlaying(boolean playing) {
        return new PlaybackState(playing, currentStepIndex, playbackSpeed, isAtStart, isAtEnd);
    }

    public PlaybackState withStepIndex(int stepIndex, boolean atStart, boolean atEnd) {
        return new PlaybackState(isPlaying, stepIndex, playbackSpeed, atStart, atEnd);
    }

    public PlaybackState withSpeed(float speed) {
        return new PlaybackState(isPlaying, currentStepIndex, speed, isAtStart, isAtEnd);
    }
}
