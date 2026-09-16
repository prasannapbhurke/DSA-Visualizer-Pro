package com.dsavisualizer.app.data;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Manages smart store rating prompts and links for Amazon Appstore and Google Play.
 */
public final class RateAppManager {

    private static final String PACKAGE_NAME = "com.dsavisualizer.app";

    private RateAppManager() {}

    public static void openStorePage(@NonNull Context context) {
        // 1. Try Amazon Appstore market scheme
        try {
            Uri amazonUri = Uri.parse("amzn://apps/android?p=" + PACKAGE_NAME);
            Intent amazonIntent = new Intent(Intent.ACTION_VIEW, amazonUri);
            amazonIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(amazonIntent);
            return;
        } catch (ActivityNotFoundException ignored) {}

        // 2. Try Google Play market scheme
        try {
            Uri playUri = Uri.parse("market://details?id=" + PACKAGE_NAME);
            Intent playIntent = new Intent(Intent.ACTION_VIEW, playUri);
            playIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(playIntent);
            return;
        } catch (ActivityNotFoundException ignored) {}

        // 3. Fallback to web browser
        try {
            Uri webUri = Uri.parse("https://www.amazon.com/gp/mas/dl/android?p=" + PACKAGE_NAME);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(webIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Unable to open app store", Toast.LENGTH_SHORT).show();
        }
    }

    public static void showRateDialog(@NonNull Activity activity) {
        AppPreferences prefs = new AppPreferences(activity);
        new MaterialAlertDialogBuilder(activity)
            .setTitle("⭐ Enjoying DSA Visualizer Pro?")
            .setMessage("Your 5-star review helps fellow students and engineers discover interactive algorithm animations. Would you take 10 seconds to support us on the Appstore?")
            .setPositiveButton("Rate 5 Stars ⭐", (dialog, which) -> {
                prefs.setRatedStore(true);
                openStorePage(activity);
            })
            .setNegativeButton("No, Thanks", (dialog, which) -> {
                prefs.setRatedStore(true);
            })
            .setNeutralButton("Remind Later", null)
            .show();
    }

    public static void maybePromptRating(@NonNull Activity activity) {
        AppPreferences prefs = new AppPreferences(activity);
        if (prefs.hasRatedStore()) {
            return;
        }
        int count = prefs.incrementExploredActions();
        if (count == 4 || count == 10) {
            showRateDialog(activity);
        }
    }
}
