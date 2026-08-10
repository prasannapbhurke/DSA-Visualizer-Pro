package com.dsavisualizer.app.ui.view;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AlgoSoundHapticEngine – Multi-sensory feedback for algorithm visualization.
 *
 * Audio: Generates real-time sine-wave tones mapped to element values.
 *        Higher values → higher pitch. Uses AudioTrack in streaming mode.
 *
 * Haptics: Uses VibrationEffect predefined constants (API 29+) or amplitude
 *          patterns (API 26+) for compare/swap ticks, error buzzes, and
 *          a triumphant completion sequence.
 */
public final class AlgoSoundHapticEngine {

    private static final String TAG = "AlgoSoundHapticEngine";

    // Pitch range for sonification: 220 Hz (low A) to 1760 Hz (high A)
    private static final float MIN_FREQ_HZ = 220f;
    private static final float MAX_FREQ_HZ = 1760f;
    private static final int SAMPLE_RATE = 44100;
    // Short tone duration in samples (≈30ms)
    private static final int TONE_SAMPLES = (int) (SAMPLE_RATE * 0.030f);

    private static final int MAX_VALUE_RANGE = 200; // Expected max element value

    private final Vibrator vibrator;
    private final ExecutorService audioExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "AlgoAudioThread");
        t.setDaemon(true);
        t.setPriority(Thread.NORM_PRIORITY - 1);
        return t;
    });

    private final AtomicBoolean muted = new AtomicBoolean(false);
    private final AtomicBoolean hapticsEnabled = new AtomicBoolean(true);

    // Throttle: min ms between audio tones to avoid audio queue overload
    private volatile long lastToneMs = 0L;
    private static final long TONE_COOLDOWN_MS = 35L;

    @SuppressWarnings("deprecation")
    public AlgoSoundHapticEngine(Context context) {
        Context appCtx = context.getApplicationContext();
        Vibrator v = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                VibratorManager vm = (VibratorManager) appCtx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
                if (vm != null) v = vm.getDefaultVibrator();
            }
            if (v == null) {
                v = (Vibrator) appCtx.getSystemService(Context.VIBRATOR_SERVICE);
            }
        } catch (Exception e) {
            Log.w(TAG, "Could not acquire Vibrator", e);
        }
        vibrator = v;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Called when two elements are compared.
     * Plays a short mid-pitch tone based on the compared value.
     * Fires a light click haptic.
     */
    public void onCompare(int value, int maxValue) {
        playTone(value, maxValue);
        hapticClick();
    }

    /**
     * Called when a swap occurs between two positions.
     * Plays a gliding two-tone "swoosh" (low→high or high→low).
     * Fires a double click haptic.
     */
    public void onSwap(int valueA, int valueB, int maxValue) {
        playSwapTone(valueA, valueB, maxValue);
        hapticDoubleClick();
    }

    /**
     * Called when an element is found / algorithm completes successfully.
     * Plays an ascending triumphant arpeggio.
     * Fires a success haptic sequence.
     */
    public void onFound(int value, int maxValue) {
        playFoundTone(value, maxValue);
        hapticSuccess();
    }

    /**
     * Called when the entire algorithm completes (all sorted / traversal done).
     * Plays a full ascending chord sweep.
     * Fires a rhythmic completion haptic.
     */
    public void onComplete() {
        playCompletionFanfare();
        hapticCompletion();
    }

    /**
     * Called on error (stack overflow, queue underflow, etc.).
     * Plays a descending dissonant tone.
     * Fires a heavy double buzz.
     */
    public void onError() {
        playErrorTone();
        hapticError();
    }

    public void setMuted(boolean muted) {
        this.muted.set(muted);
    }

    public boolean isMuted() {
        return muted.get();
    }

    public void setHapticsEnabled(boolean enabled) {
        this.hapticsEnabled.set(enabled);
    }

    public void release() {
        audioExecutor.shutdownNow();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Audio: Sine-wave synthesis via AudioTrack
    // ─────────────────────────────────────────────────────────────────────────

    /** Maps an element value [0, maxValue] to a frequency [MIN_FREQ_HZ, MAX_FREQ_HZ]. */
    private float valueToFreq(int value, int maxValue) {
        int safeMax = Math.max(1, maxValue);
        float normalized = Math.max(0f, Math.min(1f, (float) value / safeMax));
        // Logarithmic mapping sounds more musical than linear
        return MIN_FREQ_HZ * (float) Math.pow(MAX_FREQ_HZ / MIN_FREQ_HZ, normalized);
    }

    private void playTone(int value, int maxValue) {
        if (muted.get()) return;
        long now = System.currentTimeMillis();
        if (now - lastToneMs < TONE_COOLDOWN_MS) return;
        lastToneMs = now;
        float freq = valueToFreq(value, maxValue);
        audioExecutor.execute(() -> synthesizeAndPlay(new float[]{freq}, TONE_SAMPLES, 0.35f));
    }

    private void playSwapTone(int valueA, int valueB, int maxValue) {
        if (muted.get()) return;
        float freqA = valueToFreq(valueA, maxValue);
        float freqB = valueToFreq(valueB, maxValue);
        // Play both freqs back-to-back: first the outgoing, then the incoming
        audioExecutor.execute(() -> {
            synthesizeAndPlay(new float[]{freqA}, TONE_SAMPLES, 0.45f);
            synthesizeAndPlay(new float[]{freqB}, TONE_SAMPLES, 0.45f);
        });
    }

    private void playFoundTone(int value, int maxValue) {
        if (muted.get()) return;
        float base = valueToFreq(value, maxValue);
        // Play a mini ascending triad
        float[] freqs = {base, base * 1.25f, base * 1.5f};
        int noteLen = (int) (SAMPLE_RATE * 0.055f);
        audioExecutor.execute(() -> {
            for (float f : freqs) {
                synthesizeAndPlay(new float[]{f}, noteLen, 0.5f);
            }
        });
    }

    private void playCompletionFanfare() {
        if (muted.get()) return;
        // Ascending pentatonic scale sweep: C D E G A C'
        float[] freqs = {261.6f, 293.7f, 329.6f, 392.0f, 440.0f, 523.3f};
        int noteLen = (int) (SAMPLE_RATE * 0.065f);
        audioExecutor.execute(() -> {
            for (float f : freqs) {
                synthesizeAndPlay(new float[]{f}, noteLen, 0.55f);
            }
        });
    }

    private void playErrorTone() {
        if (muted.get()) return;
        // Descending dissonant buzz: two slightly detuned low freqs
        audioExecutor.execute(() -> {
            synthesizeAndPlay(new float[]{280f, 195f}, (int)(SAMPLE_RATE * 0.12f), 0.6f);
            synthesizeAndPlay(new float[]{210f, 145f}, (int)(SAMPLE_RATE * 0.12f), 0.4f);
        });
    }

    /**
     * Core synthesis: generates a sine wave (sum of multiple frequencies if provided)
     * with a quick attack-decay envelope, then plays it via AudioTrack.
     */
    private void synthesizeAndPlay(float[] freqsHz, int sampleCount, float amplitude) {
        try {
            short[] samples = new short[sampleCount];
            int freqCount = freqsHz.length;
            float perFreqAmp = amplitude / freqCount;

            for (int i = 0; i < sampleCount; i++) {
                float sample = 0f;
                for (float freq : freqsHz) {
                    sample += (float) Math.sin(2.0 * Math.PI * freq * i / SAMPLE_RATE);
                }
                sample *= perFreqAmp;

                // Smooth attack (first 10% of samples) + decay (last 30%)
                float attackLen = sampleCount * 0.10f;
                float decayStart = sampleCount * 0.70f;
                if (i < attackLen) {
                    sample *= i / attackLen;
                } else if (i > decayStart) {
                    sample *= 1f - ((i - decayStart) / (sampleCount - decayStart));
                }

                samples[i] = (short) (sample * Short.MAX_VALUE);
            }

            int bufferSize = AudioTrack.getMinBufferSize(
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
            );
            bufferSize = Math.max(bufferSize, sampleCount * 2);

            AudioTrack track = new AudioTrack.Builder()
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
                    .setAudioFormat(new AudioFormat.Builder()
                            .setSampleRate(SAMPLE_RATE)
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build())
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .setBufferSizeInBytes(sampleCount * 2)
                    .build();

            track.write(samples, 0, sampleCount);
            track.play();
            // Wait for tone to finish, then release
            long waitMs = (long) (sampleCount * 1000L / SAMPLE_RATE) + 10L;
            Thread.sleep(waitMs);
            track.stop();
            track.release();

        } catch (Exception e) {
            Log.w(TAG, "Audio synthesis failed", e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Haptics
    // ─────────────────────────────────────────────────────────────────────────

    /** Light click — compare/step. */
    private void hapticClick() {
        if (!hapticsEnabled.get() || vibrator == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(12, 80));
            }
        } catch (Exception e) {
            Log.w(TAG, "hapticClick failed", e);
        }
    }

    /** Double click — swap. */
    private void hapticDoubleClick() {
        if (!hapticsEnabled.get() || vibrator == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 15, 40, 15}, -1));
            }
        } catch (Exception e) {
            Log.w(TAG, "hapticDoubleClick failed", e);
        }
    }

    /** Heavy click — element found. */
    private void hapticSuccess() {
        if (!hapticsEnabled.get() || vibrator == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(40, 200));
            }
        } catch (Exception e) {
            Log.w(TAG, "hapticSuccess failed", e);
        }
    }

    /** Rhythmic sequence — algorithm complete. */
    private void hapticCompletion() {
        if (!hapticsEnabled.get() || vibrator == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Three quick pulses then a longer final beat
                vibrator.vibrate(VibrationEffect.createWaveform(
                        new long[]{0, 30, 60, 30, 60, 30, 80, 80},
                        new int[]  {0, 180,  0, 180,  0, 180,  0, 255},
                        -1
                ));
            }
        } catch (Exception e) {
            Log.w(TAG, "hapticCompletion failed", e);
        }
    }

    /** Double heavy buzz — error. */
    private void hapticError() {
        if (!hapticsEnabled.get() || vibrator == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(
                        new long[]{0, 80, 60, 80},
                        new int[] {0, 255,  0, 220},
                        -1
                ));
            }
        } catch (Exception e) {
            Log.w(TAG, "hapticError failed", e);
        }
    }
}
