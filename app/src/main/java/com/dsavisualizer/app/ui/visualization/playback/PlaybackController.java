package com.dsavisualizer.app.ui.visualization.playback;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dsavisualizer.app.ui.visualization.runtime.StepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.Step;

public class PlaybackController {

    private static final long BASE_DELAY_MS = 1000L;
    private static final long MIN_STEP_INTERVAL_MS = 120L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final StepEngine stepEngine;
    private final MutableLiveData<PlaybackState> playbackState = new MutableLiveData<>(PlaybackState.initial());

    private boolean released = false;
    private boolean dispatchInProgress = false;

    private final Runnable playbackRunnable = new Runnable() {
        @Override
        public void run() {
            PlaybackState state = playbackState.getValue();
            if (released || state == null || !state.isPlaying) {
                return;
            }
            dispatchNextFromPlaybackLoop();
        }
    };

    public PlaybackController(StepEngine stepEngine) {
        this.stepEngine = stepEngine;
    }

    public LiveData<PlaybackState> getPlaybackState() {
        return playbackState;
    }

    public void play() {
        if (released) {
            return;
        }
        PlaybackState state = stateOrDefault();
        if (state.isAtEnd) {
            reset();
            state = stateOrDefault();
        }
        if (state.isPlaying) {
            return;
        }
        updateState(new PlaybackState(true, state.currentStepIndex, state.playbackSpeed, state.isAtStart, state.isAtEnd));
        scheduleNext();
    }

    public void pause() {
        if (released) {
            return;
        }
        handler.removeCallbacks(playbackRunnable);
        PlaybackState state = stateOrDefault();
        updateState(new PlaybackState(false, state.currentStepIndex, state.playbackSpeed, state.isAtStart, state.isAtEnd));
    }

    public void stop() {
        pause();
    }

    public void reset() {
        if (released) {
            return;
        }
        handler.removeCallbacks(playbackRunnable);
        stepEngine.reset();
        updateState(PlaybackState.initial());
    }

    public void restoreState(PlaybackState state) {
        if (released || state == null) {
            return;
        }
        handler.removeCallbacks(playbackRunnable);
        updateState(new PlaybackState(
            false,
            Math.max(0, state.currentStepIndex),
            state.playbackSpeed,
            state.isAtStart,
            state.isAtEnd
        ));
    }

    public void nextStep() {
        if (released) {
            return;
        }
        pause();
        applyStep(stepEngine.next(), true);
    }

    public void prevStep() {
        if (released) {
            return;
        }
        pause();
        applyStep(stepEngine.prev(), false);
    }

    public void previousStep() {
        prevStep();
    }

    public void setSpeed(float speed) {
        if (released) {
            return;
        }
        float normalizedSpeed = normalizeSpeed(speed);
        PlaybackState state = stateOrDefault();
        updateState(new PlaybackState(state.isPlaying, state.currentStepIndex, normalizedSpeed, state.isAtStart, state.isAtEnd));
    }

    public void release() {
        released = true;
        handler.removeCallbacksAndMessages(null);
    }

    private void scheduleNext() {
        handler.removeCallbacks(playbackRunnable);
        PlaybackState state = stateOrDefault();
        long adjustedDelay = Math.max(MIN_STEP_INTERVAL_MS, Math.round(BASE_DELAY_MS / state.playbackSpeed));
        handler.postDelayed(playbackRunnable, adjustedDelay);
    }

    private void dispatchNextFromPlaybackLoop() {
        if (dispatchInProgress) {
            return;
        }
        dispatchInProgress = true;
        try {
            Step step = stepEngine.next();
            PlaybackState state = applyStep(step, true);
            if (state.isPlaying && !state.isAtEnd) {
                scheduleNext();
            }
        } finally {
            dispatchInProgress = false;
        }
    }

    private PlaybackState applyStep(Step step, boolean forward) {
        PlaybackState previous = stateOrDefault();
        int nextIndex = previous.currentStepIndex;

        if (step != null && step.moved) {
            nextIndex = forward ? previous.currentStepIndex + 1 : Math.max(0, previous.currentStepIndex - 1);
        }

        boolean atStart = step == null ? previous.isAtStart : step.atStart || nextIndex == 0;
        boolean atEnd = step != null && step.atEnd;
        boolean keepPlaying = previous.isPlaying && step != null && step.moved && !atEnd;

        PlaybackState updated = new PlaybackState(keepPlaying, nextIndex, previous.playbackSpeed, atStart, atEnd);
        updateState(updated);
        return updated;
    }

    private PlaybackState stateOrDefault() {
        PlaybackState state = playbackState.getValue();
        return state == null ? PlaybackState.initial() : state;
    }

    private void updateState(PlaybackState state) {
        playbackState.setValue(state);
    }

    private float normalizeSpeed(float speed) {
        if (speed <= 0.75f) {
            return 0.5f;
        }
        if (speed >= 1.5f) {
            return 2f;
        }
        return 1f;
    }
}
