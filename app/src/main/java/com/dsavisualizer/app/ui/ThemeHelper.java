package com.dsavisualizer.app.ui;

import androidx.appcompat.app.AppCompatDelegate;

import com.dsavisualizer.app.data.AppPreferences;

/**
 * Maps the saved app theme preference to AppCompat night modes.
 */
public final class ThemeHelper {

    private ThemeHelper() {
    }

    public static void applyThemeMode(String mode) {
        AppCompatDelegate.setDefaultNightMode(resolveNightMode(mode));
    }

    public static int resolveNightMode(String mode) {
        if (AppPreferences.THEME_LIGHT.equals(mode)) {
            return AppCompatDelegate.MODE_NIGHT_NO;
        }
        if (AppPreferences.THEME_DARK.equals(mode)) {
            return AppCompatDelegate.MODE_NIGHT_YES;
        }
        return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    }
}
