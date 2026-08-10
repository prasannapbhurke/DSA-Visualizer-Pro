package com.dsavisualizer.app.ui;

import android.content.res.Configuration;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Applies system bar insets so content does not overlap with the camera cutout or notification bar.
 */
public final class SafeInsetHelper {

    private SafeInsetHelper() {
    }

    public static void applyTopAndBottomInsets(View root, int leftPadding, int topPadding, int rightPadding, int bottomPadding) {
        if (root == null) {
            return;
        }
        Configuration configuration = root.getResources().getConfiguration();
        boolean compactPhone = configuration.smallestScreenWidthDp < 600 && configuration.screenWidthDp <= 360;
        if (compactPhone) {
            leftPadding = Math.max(12, Math.round(leftPadding * 0.75f));
            topPadding = Math.max(12, Math.round(topPadding * 0.8f));
            rightPadding = Math.max(12, Math.round(rightPadding * 0.75f));
            bottomPadding = Math.max(12, Math.round(bottomPadding * 0.8f));
        }
        float density = root.getResources().getDisplayMetrics().density;
        final int baseLeft = Math.round(leftPadding * density);
        final int baseTop = Math.round(topPadding * density);
        final int baseRight = Math.round(rightPadding * density);
        final int baseBottom = Math.round(bottomPadding * density);
        ViewCompat.setOnApplyWindowInsetsListener(root, (view, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                baseLeft + systemBars.left,
                baseTop + systemBars.top,
                baseRight + systemBars.right,
                baseBottom + systemBars.bottom
            );
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(root);
    }
}
