package com.dsavisualizer.app.data;

import com.dsavisualizer.app.BuildConfig;

public final class ApiConfig {

    private static final String DEFAULT_REMOTE_BASE_URL = "https://dsa-visualizer-api-nu.vercel.app";

    private ApiConfig() {
    }

    public static String getBaseUrl() {
        if (BuildConfig.REMOTE_API_BASE_URL != null && !BuildConfig.REMOTE_API_BASE_URL.trim().isEmpty()) {
            return BuildConfig.REMOTE_API_BASE_URL;
        }
        if (BuildConfig.DEBUG) {
            return DEFAULT_REMOTE_BASE_URL;
        }
        return BuildConfig.API_BASE_URL;
    }
}
