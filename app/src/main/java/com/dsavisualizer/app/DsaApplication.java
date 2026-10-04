package com.dsavisualizer.app;

import android.app.Application;

import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.ui.ThemeHelper;
import com.google.android.material.color.DynamicColors;

/**
 * Lightweight app entry point.
 * Heavy content loading is deferred to SplashActivity so launch can draw immediately.
 */
public class DsaApplication extends Application {

    private static DsaApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ThemeHelper.applyThemeMode(new AppPreferences(this).getThemeMode());
        DynamicColors.applyToActivitiesIfAvailable(this);
        RemoteSyncManager.getInstance(this).ensureGuestSession();
    }

    public static DsaApplication getInstance() {
        return sInstance;
    }
}
