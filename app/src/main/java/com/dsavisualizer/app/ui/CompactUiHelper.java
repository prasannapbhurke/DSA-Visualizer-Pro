package com.dsavisualizer.app.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public final class CompactUiHelper {

    private CompactUiHelper() {
    }

    public static boolean isCompactPhone(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration.smallestScreenWidthDp < 600 && configuration.screenWidthDp <= 360;
    }

    public static void stackButtonsIfCompact(Context context, @Nullable LinearLayout row, int gapDp, View... children) {
        if (row == null || !isCompactPhone(context)) {
            return;
        }
        row.setOrientation(LinearLayout.VERTICAL);
        int gapPx = Math.round(gapDp * context.getResources().getDisplayMetrics().density);
        for (int i = 0; i < children.length; i++) {
            View child = children[i];
            if (child == null) {
                continue;
            }
            ViewGroup.LayoutParams rawParams = child.getLayoutParams();
            if (!(rawParams instanceof LinearLayout.LayoutParams)) {
                continue;
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rawParams;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.weight = 0f;
            params.setMargins(0, i == 0 ? 0 : gapPx, 0, 0);
            child.setLayoutParams(params);
        }
    }
}
