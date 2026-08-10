package com.dsavisualizer.app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.model.ClassResourceItem;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.ClassResourceAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class ClassResourcesActivity extends AppCompatActivity {
    public static final String EXTRA_CLASS_ID = "extra_class_id";
    public static final String EXTRA_CLASS_NAME = "extra_class_name";

    private static final long MAX_UPLOAD_BYTES = 500L * 1024L * 1024L;

    private final List<ClassResourceItem> items = new ArrayList<>();

    private AppPreferences appPreferences;
    private ClassResourceAdapter adapter;
    private ProgressBar loadingIndicator;
    private TextView emptyText;
    private MaterialButton uploadButton;
    private View uploadStatusCard;
    private ProgressBar uploadProgressBar;
    private TextView uploadStatusTitle;
    private TextView uploadStatusMessage;
    private MaterialButton cancelUploadButton;
    private MaterialButton retryUploadButton;
    private ActivityResultLauncher<String[]> documentPickerLauncher;
    private volatile boolean cancelUploadRequested = false;
    private volatile HttpURLConnection activeUploadConnection;
    private Uri lastUploadUri;
    private String lastUploadTitle = "";
    private String lastUploadType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_resources);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.class_resources_root), 20, 20, 20, 20);

        appPreferences = new AppPreferences(this);
        findViewById(R.id.class_resources_back_button).setOnClickListener(v -> finish());
        ((TextView) findViewById(R.id.class_resources_title_text)).setText(
            getIntent().getStringExtra(EXTRA_CLASS_NAME) == null
                ? getString(R.string.class_resources_title)
                : getIntent().getStringExtra(EXTRA_CLASS_NAME)
        );

        loadingIndicator = findViewById(R.id.class_resources_loading);
        emptyText = findViewById(R.id.class_resources_empty_text);
        uploadButton = findViewById(R.id.class_resources_upload_button);
        uploadStatusCard = findViewById(R.id.class_resources_upload_status_card);
        uploadProgressBar = findViewById(R.id.class_resources_upload_progress);
        uploadStatusTitle = findViewById(R.id.class_resources_upload_status_title);
        uploadStatusMessage = findViewById(R.id.class_resources_upload_status_message);
        cancelUploadButton = findViewById(R.id.class_resources_upload_cancel_button);
        retryUploadButton = findViewById(R.id.class_resources_upload_retry_button);
        uploadButton.setVisibility(isTeacherView() ? View.VISIBLE : View.GONE);
        uploadButton.setOnClickListener(v -> documentPickerLauncher.launch(new String[] {
            "application/pdf",
            "text/plain",
            "image/png",
            "image/jpeg"
        }));
        cancelUploadButton.setOnClickListener(v -> cancelCurrentUpload());
        retryUploadButton.setOnClickListener(v -> retryLastUpload());
        showUploadIdleState();

        RecyclerView recyclerView = findViewById(R.id.class_resources_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassResourceAdapter(this::openResource);
        recyclerView.setAdapter(adapter);

        documentPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(),
            uri -> {
                if (uri != null) {
                    openUploadMetadataDialog(uri);
                }
            }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchResources();
    }

    private boolean isTeacherView() {
        String role = appPreferences.getAuthRole();
        return "teacher".equalsIgnoreCase(role) || "owner".equalsIgnoreCase(role);
    }

    private void fetchResources() {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        setLoading(true);
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes/" + classId + "/resources");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(7000);
                connection.setReadTimeout(7000);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

                int statusCode = connection.getResponseCode();
                String responseBody = readStream(statusCode >= 200 && statusCode < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream());
                if (statusCode < 200 || statusCode >= 300) {
                    throw new IllegalStateException(responseBody);
                }

                JSONObject payload = new JSONObject(responseBody);
                JSONObject data = payload.optJSONObject("data");
                JSONArray resources = data == null ? null : data.optJSONArray("resources");
                List<ClassResourceItem> loaded = new ArrayList<>();
                if (resources != null) {
                    for (int i = 0; i < resources.length(); i++) {
                        JSONObject item = resources.optJSONObject(i);
                        if (item == null) {
                            continue;
                        }
                        loaded.add(new ClassResourceItem(
                            item.optString("id", ""),
                            item.optString("title", ""),
                            item.optString("type", ""),
                            item.optString("fileName", ""),
                            item.optString("mimeType", ""),
                            item.optInt("sizeBytes", 0),
                            item.optString("createdAt", ""),
                            item.optString("downloadPath", ""),
                            item.optString("uploaderName", "")
                        ));
                    }
                }

                runOnUiThread(() -> {
                    items.clear();
                    items.addAll(loaded);
                    adapter.submitList(items);
                    emptyText.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
                    setLoading(false);
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    emptyText.setText(R.string.class_resources_load_failed);
                    emptyText.setVisibility(View.VISIBLE);
                    setLoading(false);
                    Toast.makeText(this, R.string.class_resources_load_failed, Toast.LENGTH_LONG).show();
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private void openUploadMetadataDialog(@NonNull Uri uri) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(20);
        container.setPadding(padding, dpToPx(8), padding, 0);

        EditText titleInput = new EditText(this);
        titleInput.setHint(R.string.class_resources_title_hint);
        titleInput.setText(resolveDisplayName(uri));
        titleInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        titleInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        EditText typeInput = new EditText(this);
        LinearLayout.LayoutParams typeParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        typeParams.topMargin = dpToPx(12);
        typeInput.setLayoutParams(typeParams);
        typeInput.setHint(R.string.class_resources_type_hint);
        typeInput.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        typeInput.setHintTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));

        container.addView(titleInput);
        container.addView(typeInput);

        new AlertDialog.Builder(this)
            .setTitle(R.string.class_resources_upload_title)
            .setView(container)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.class_resources_upload_button, (dialog, which) -> {
                String title = titleInput.getText() == null ? "" : titleInput.getText().toString().trim();
                String type = typeInput.getText() == null ? "" : typeInput.getText().toString().trim();
                if (title.isEmpty()) {
                    Toast.makeText(this, R.string.class_resources_title_required, Toast.LENGTH_SHORT).show();
                    return;
                }
                long sizeBytes = resolveSizeBytes(uri);
                if (sizeBytes > 10L * 1024L * 1024L) {
                    Toast.makeText(this, R.string.class_resources_large_mobile_warning, Toast.LENGTH_LONG).show();
                }
                uploadResource(uri, title, type.isEmpty() ? "resource" : type);
            })
            .show();
    }

    private void uploadResource(@NonNull Uri uri, @NonNull String title, @NonNull String type) {
        String classId = getIntent().getStringExtra(EXTRA_CLASS_ID);
        setLoading(true);
        cancelUploadRequested = false;
        lastUploadUri = uri;
        lastUploadTitle = title;
        lastUploadType = type;
        runOnUiThread(() -> showUploadPreparingState());
        new Thread(() -> {
            try {
                String fileName = resolveDisplayName(uri);
                String mimeType = resolveMimeType(uri, fileName);
                long sizeBytes = resolveSizeBytes(uri);
                if (sizeBytes <= 0L) {
                    throw new IllegalStateException("Could not determine file size");
                }
                if (sizeBytes > MAX_UPLOAD_BYTES) {
                    throw new IllegalStateException("File exceeds the 500 MB app limit");
                }
                if (cancelUploadRequested) {
                    throw new InterruptedException("Upload cancelled");
                }
                JSONObject uploadSession = createDirectUploadSession(classId, title, type, fileName, mimeType, sizeBytes);
                String signedUploadUrl = uploadSession.optString("signedUploadUrl", "");
                String uploadToken = uploadSession.optString("uploadToken", "");
                String resourceId = uploadSession.optString("resourceId", "");
                if (signedUploadUrl.trim().isEmpty() || resourceId.trim().isEmpty()) {
                    throw new IllegalStateException("Upload session is incomplete");
                }
                uploadBinaryToSignedUrl(uri, signedUploadUrl, uploadToken, mimeType, sizeBytes);
                if (cancelUploadRequested) {
                    throw new InterruptedException("Upload cancelled");
                }
                runOnUiThread(() -> updateUploadStatus(getString(R.string.class_resources_upload_finalizing), getString(R.string.class_resources_upload_finalizing_detail), true, 100, false, false));
                completeDirectUpload(classId, resourceId, title, type, fileName, mimeType, sizeBytes);

                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.class_resources_upload_success, Toast.LENGTH_SHORT).show();
                    showUploadSuccessState(fileName, sizeBytes);
                    fetchResources();
                });
            } catch (InterruptedException cancelled) {
                runOnUiThread(() -> {
                    setLoading(false);
                    showUploadCancelledState();
                    Toast.makeText(this, R.string.class_resources_upload_cancelled, Toast.LENGTH_SHORT).show();
                });
            } catch (Exception error) {
                runOnUiThread(() -> {
                    setLoading(false);
                    String message = resolveUploadErrorMessage(error);
                    showUploadFailedState(message);
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                });
            } finally {
                activeUploadConnection = null;
            }
        }).start();
    }

    private JSONObject createDirectUploadSession(
        @NonNull String classId,
        @NonNull String title,
        @NonNull String type,
        @NonNull String fileName,
        @NonNull String mimeType,
        long sizeBytes
    ) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes/" + classId + "/resources/upload-url");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

            JSONObject body = new JSONObject();
            body.put("title", title);
            body.put("type", type);
            body.put("fileName", fileName);
            body.put("mimeType", mimeType);
            body.put("sizeBytes", sizeBytes);

            try (OutputStream outputStream = connection.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                writer.write(body.toString());
            }

            int statusCode = connection.getResponseCode();
            String responseBody = readStream(statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream());
            if (statusCode < 200 || statusCode >= 300) {
                throw buildServerException(responseBody, statusCode, "Upload session failed");
            }

            JSONObject payload = new JSONObject(responseBody);
            JSONObject data = payload.optJSONObject("data");
            return data == null ? new JSONObject() : data;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void uploadBinaryToSignedUrl(
        @NonNull Uri uri,
        @NonNull String signedUploadUrl,
        @NonNull String uploadToken,
        @NonNull String mimeType,
        long sizeBytes
    ) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(appendUploadTokenIfMissing(signedUploadUrl, uploadToken));
            connection = (HttpURLConnection) url.openConnection();
            activeUploadConnection = connection;
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(120000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", mimeType);
            connection.setRequestProperty("x-upsert", "true");
            if (!uploadToken.trim().isEmpty()) {
                connection.setRequestProperty("authorization", "Bearer " + uploadToken);
                connection.setRequestProperty("x-signature", uploadToken);
            }
            if (sizeBytes > 0L && sizeBytes <= Integer.MAX_VALUE) {
                connection.setFixedLengthStreamingMode((int) sizeBytes);
            } else {
                connection.setChunkedStreamingMode(1024 * 1024);
            }

            try (InputStream inputStream = getContentResolver().openInputStream(uri);
                 OutputStream outputStream = connection.getOutputStream()) {
                if (inputStream == null) {
                    throw new IllegalStateException("Could not open selected file");
                }
                byte[] chunk = new byte[1024 * 1024];
                int read;
                long uploadedBytes = 0L;
                runOnUiThread(() -> updateUploadStatus(getString(R.string.class_resources_upload_progress_title), formatUploadProgress(0L, sizeBytes), true, 0, true, false));
                while ((read = inputStream.read(chunk)) != -1) {
                    if (cancelUploadRequested) {
                        throw new InterruptedException("Upload cancelled");
                    }
                    outputStream.write(chunk, 0, read);
                    uploadedBytes += read;
                    final long progressBytes = uploadedBytes;
                    final int percent = sizeBytes > 0L ? (int) Math.min(100L, (progressBytes * 100L) / sizeBytes) : 0;
                    runOnUiThread(() -> updateUploadStatus(
                        getString(R.string.class_resources_upload_progress_title),
                        formatUploadProgress(progressBytes, sizeBytes),
                        true,
                        percent,
                        true,
                        false
                    ));
                }
            }

            int statusCode = connection.getResponseCode();
            String responseBody = readStream(statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream());
            if (statusCode < 200 || statusCode >= 300) {
                throw buildServerException(responseBody, statusCode, "Binary upload failed");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void completeDirectUpload(
        @NonNull String classId,
        @NonNull String resourceId,
        @NonNull String title,
        @NonNull String type,
        @NonNull String fileName,
        @NonNull String mimeType,
        long sizeBytes
    ) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ApiConfig.getBaseUrl() + "/api/classes/" + classId + "/resources/complete");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + appPreferences.getAuthToken());

            JSONObject body = new JSONObject();
            body.put("resourceId", resourceId);
            body.put("title", title);
            body.put("type", type);
            body.put("fileName", fileName);
            body.put("mimeType", mimeType);
            body.put("sizeBytes", sizeBytes);

            try (OutputStream outputStream = connection.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                writer.write(body.toString());
            }

            int statusCode = connection.getResponseCode();
            String responseBody = readStream(statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream());
            if (statusCode < 200 || statusCode >= 300) {
                throw buildServerException(responseBody, statusCode, "Upload finalize failed");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String resolveDisplayName(@NonNull Uri uri) {
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index >= 0) {
                    return cursor.getString(index);
                }
            }
        } catch (Exception ignored) {
        }
        return "resource";
    }

    private String resolveMimeType(@NonNull Uri uri, @NonNull String fileName) {
        String mimeType = getContentResolver().getType(uri);
        if (mimeType != null && !mimeType.trim().isEmpty() && !"application/octet-stream".equalsIgnoreCase(mimeType.trim())) {
            return mimeType.trim();
        }
        String lowerName = fileName.toLowerCase();
        if (lowerName.endsWith(".pdf")) {
            return "application/pdf";
        }
        if (lowerName.endsWith(".txt")) {
            return "text/plain";
        }
        if (lowerName.endsWith(".png")) {
            return "image/png";
        }
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        return mimeType == null || mimeType.trim().isEmpty() ? "application/octet-stream" : mimeType.trim();
    }

    private long resolveSizeBytes(@NonNull Uri uri) {
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (index >= 0 && !cursor.isNull(index)) {
                    return cursor.getLong(index);
                }
            }
        } catch (Exception ignored) {
        }
        return -1L;
    }

    private String appendUploadTokenIfMissing(@NonNull String signedUploadUrl, @NonNull String uploadToken) {
        if (uploadToken.trim().isEmpty() || signedUploadUrl.contains("token=")) {
            return signedUploadUrl;
        }
        return signedUploadUrl + (signedUploadUrl.contains("?") ? "&" : "?") + "token=" + Uri.encode(uploadToken);
    }

    private void openResource(@NonNull ClassResourceItem item) {
        if (item.downloadPath.trim().isEmpty()) {
            Toast.makeText(this, R.string.class_resources_open_failed, Toast.LENGTH_SHORT).show();
            return;
        }
        String absoluteUrl = item.downloadPath.startsWith("http")
            ? item.downloadPath
            : ApiConfig.getBaseUrl() + item.downloadPath;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(absoluteUrl));
        startActivity(intent);
    }

    private void setLoading(boolean loading) {
        loadingIndicator.setVisibility(loading ? View.VISIBLE : View.GONE);
        uploadButton.setEnabled(!loading);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private int resolveThemeColor(int attrResId, int fallbackColorResId) {
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(attrResId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return ContextCompat.getColor(this, typedValue.resourceId);
            }
            return typedValue.data;
        }
        return ContextCompat.getColor(this, fallbackColorResId);
    }

    private String readStream(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }

    private Exception buildServerException(String responseBody, int statusCode, String fallback) {
        try {
            JSONObject payload = new JSONObject(responseBody);
            JSONObject error = payload.optJSONObject("error");
            String message = error == null ? "" : error.optString("message", "");
            if (message.trim().isEmpty()) {
                message = payload.optString("message", "");
            }
            if (message.trim().isEmpty()) {
                message = payload.optString("error", "");
            }
            if (!message.trim().isEmpty()) {
                return new IllegalStateException(message);
            }
        } catch (Exception ignored) {
        }
        return new IllegalStateException(fallback + " (" + statusCode + ")");
    }

    private String resolveUploadErrorMessage(@NonNull Exception error) {
        String message = error.getMessage() == null ? "" : error.getMessage().trim();
        String lower = message.toLowerCase();
        if (lower.contains("row-level security policy")) {
            return getString(R.string.class_resources_upload_policy_blocked);
        }
        if (lower.contains("authorization")) {
            return getString(R.string.class_resources_upload_auth_failed);
        }
        if (!message.isEmpty()) {
            return message;
        }
        return getString(R.string.class_resources_upload_failed);
    }

    private void cancelCurrentUpload() {
        cancelUploadRequested = true;
        HttpURLConnection connection = activeUploadConnection;
        if (connection != null) {
            connection.disconnect();
        }
        updateUploadStatus(
            getString(R.string.class_resources_upload_cancelling),
            getString(R.string.class_resources_upload_cancelling_detail),
            true,
            uploadProgressBar.getProgress(),
            false,
            false
        );
    }

    private void retryLastUpload() {
        if (lastUploadUri == null || lastUploadTitle.trim().isEmpty()) {
            return;
        }
        uploadResource(lastUploadUri, lastUploadTitle, lastUploadType.isEmpty() ? "resource" : lastUploadType);
    }

    private void showUploadIdleState() {
        uploadStatusCard.setVisibility(View.GONE);
        uploadProgressBar.setProgress(0);
        cancelUploadButton.setVisibility(View.GONE);
        retryUploadButton.setVisibility(View.GONE);
    }

    private void showUploadPreparingState() {
        updateUploadStatus(
            getString(R.string.class_resources_upload_preparing),
            getString(R.string.class_resources_upload_plan_hint),
            true,
            0,
            true,
            false
        );
    }

    private void showUploadSuccessState(@NonNull String fileName, long sizeBytes) {
        updateUploadStatus(
            getString(R.string.class_resources_upload_done),
            getString(R.string.class_resources_upload_done_detail, fileName, formatBytes(sizeBytes)),
            true,
            100,
            false,
            false
        );
    }

    private void showUploadFailedState(@NonNull String message) {
        updateUploadStatus(
            getString(R.string.class_resources_upload_failed_title),
            message,
            false,
            uploadProgressBar.getProgress(),
            false,
            true
        );
    }

    private void showUploadCancelledState() {
        updateUploadStatus(
            getString(R.string.class_resources_upload_cancelled_title),
            getString(R.string.class_resources_upload_cancelled_detail),
            false,
            uploadProgressBar.getProgress(),
            false,
            true
        );
    }

    private void updateUploadStatus(
        @NonNull String title,
        @NonNull String message,
        boolean showProgress,
        int progress,
        boolean showCancel,
        boolean showRetry
    ) {
        uploadStatusCard.setVisibility(View.VISIBLE);
        uploadStatusTitle.setText(title);
        uploadStatusMessage.setText(message);
        uploadProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        uploadProgressBar.setProgress(Math.max(0, Math.min(100, progress)));
        cancelUploadButton.setVisibility(showCancel ? View.VISIBLE : View.GONE);
        retryUploadButton.setVisibility(showRetry ? View.VISIBLE : View.GONE);
    }

    private String formatUploadProgress(long uploadedBytes, long totalBytes) {
        return getString(R.string.class_resources_upload_progress_detail, formatBytes(uploadedBytes), formatBytes(totalBytes));
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024L) {
            return bytes + " B";
        }
        double kb = bytes / 1024.0;
        if (kb < 1024.0) {
            return String.format(java.util.Locale.US, "%.1f KB", kb);
        }
        double mb = kb / 1024.0;
        if (mb < 1024.0) {
            return String.format(java.util.Locale.US, "%.1f MB", mb);
        }
        double gb = mb / 1024.0;
        return String.format(java.util.Locale.US, "%.2f GB", gb);
    }
}
