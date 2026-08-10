package com.dsavisualizer.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.google.android.material.button.MaterialButton;

/**
 * A reusable activity for showing feature details and performing actions.
 */
public class ThirdActivity extends AppCompatActivity {

    public static final String EXTRA_FEATURE_TYPE = "FEATURE_TYPE";

    private String featureType;
    private TextView tvTitle;
    private TextView tvDescription;
    private MaterialButton btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.third_root), 20, 20, 20, 20);

        featureType = getIntent().getStringExtra(EXTRA_FEATURE_TYPE);
        if (featureType == null) featureType = "UNKNOWN";

        tvTitle = findViewById(R.id.tv_screen_title);
        tvDescription = findViewById(R.id.tv_display_data);
        btnAction = findViewById(R.id.btn_perform_action);

        configureFeature();
        setupButtonListeners();
    }

    private void configureFeature() {
        switch (featureType) {
            case "QR_SCAN":
                tvTitle.setText(R.string.button_scan);
                tvDescription.setText(R.string.desc_scan);
                btnAction.setText(R.string.btn_start_scanner);
                break;
            case "UPLOAD":
                tvTitle.setText(R.string.button_upload);
                tvDescription.setText(R.string.desc_upload);
                btnAction.setText(R.string.btn_select_file);
                break;
            case "LOCATION":
                tvTitle.setText(R.string.button_location);
                tvDescription.setText(R.string.desc_location);
                btnAction.setText(R.string.btn_get_location);
                break;
            default:
                tvTitle.setText(R.string.third_activity_title);
                tvDescription.setText(R.string.feature_description);
                btnAction.setVisibility(View.GONE);
                break;
        }
    }

    private void setupButtonListeners() {
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        
        btnAction.setOnClickListener(v -> {
            String message = getString(R.string.toast_executing, featureType);
            Toast.makeText(ThirdActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_cancel).setOnClickListener(v -> finish());
    }
}
