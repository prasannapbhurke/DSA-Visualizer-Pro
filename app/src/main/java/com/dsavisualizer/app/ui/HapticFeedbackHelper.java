package com.dsavisualizer.app.ui;

import android.view.HapticFeedbackConstants;
import android.view.View;

public final class HapticFeedbackHelper {

    private HapticFeedbackHelper() {
    }

    public static void performSelection(View view) {
        if (view != null) {
            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK);
        }
    }

    public static void performConfirm(View view) {
        if (view != null) {
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
        }
    }
}
