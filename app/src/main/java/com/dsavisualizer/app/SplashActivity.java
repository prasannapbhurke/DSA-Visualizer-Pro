package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DURATION_MS = 900L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Professional Fade-in Animation for Logo and Name
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1000);
        findViewById(R.id.logo_container).startAnimation(fadeIn);
        findViewById(R.id.app_name).startAnimation(fadeIn);

        new Handler(Looper.getMainLooper()).postDelayed(this::openHome, SPLASH_DURATION_MS);
    }

    private void openHome() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
