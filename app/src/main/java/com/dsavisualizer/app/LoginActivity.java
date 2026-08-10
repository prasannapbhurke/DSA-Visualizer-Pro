package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dsavisualizer.app.data.ApiConfig;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int AUTH_CONNECT_TIMEOUT_MS = 8000;
    private static final int AUTH_READ_TIMEOUT_MS = 10000;
    private static final int AUTH_MAX_ATTEMPTS = 2;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;
    private AppPreferences appPreferences;
    private MaterialButton googleSignInButton;
    private MaterialCardView setupCard;
    private TextView setupTitleView;
    private TextView setupMessageView;
    private boolean signInInFlight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.login_root), 0, 0, 0, 0);
        appPreferences = new AppPreferences(this);
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        setupCard = findViewById(R.id.login_setup_card);
        setupTitleView = findViewById(R.id.login_setup_title);
        setupMessageView = findViewById(R.id.login_setup_message);

        // Check if user is already authenticated
        if (isUserAuthenticated()) {
            proceedToMain();
            return;
        }

        initializeGoogleSignIn();
        renderGoogleSetupState();
        warmBackend();

        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

    }

    private void initializeGoogleSignIn() {
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail();
        if (isGoogleClientConfigured()) {
            builder.requestIdToken(BuildConfig.GOOGLE_CLIENT_ID);
        }
        GoogleSignInOptions gso = builder.build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    handleSignInResult(task);
                } else {
                    Log.w(TAG, "Google sign-in returned no data. resultCode=" + result.getResultCode());
                    signInInFlight = false;
                    setGoogleSignInLoading(false);
                    Toast.makeText(this, R.string.google_sign_in_cancelled, Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void signInWithGoogle() {
        if (signInInFlight) {
            return;
        }
        if (!isGoogleClientConfigured()) {
            Toast.makeText(this, R.string.google_sign_in_config_missing, Toast.LENGTH_LONG).show();
            return;
        }
        signInInFlight = true;
        setGoogleSignInLoading(true);

        GoogleSignInAccount cachedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (cachedAccount != null && isFreshIdToken(cachedAccount.getIdToken())) {
            Log.d(TAG, "Using cached Google account for fast sign-in: " + cachedAccount.getEmail());
            authenticateWithServer(cachedAccount.getIdToken(), cachedAccount);
            return;
        }

        Task<GoogleSignInAccount> silentTask = googleSignInClient.silentSignIn();
        if (silentTask.isComplete()) {
            handleSilentSignInResult(silentTask);
            return;
        }
        silentTask.addOnCompleteListener(this, this::handleSilentSignInResult);
    }

    private void handleSilentSignInResult(Task<GoogleSignInAccount> task) {
        if (task.isSuccessful()) {
            GoogleSignInAccount account = task.getResult();
            if (account != null && isFreshIdToken(account.getIdToken())) {
                Log.d(TAG, "Silent Google sign-in succeeded for: " + account.getEmail());
                authenticateWithServer(account.getIdToken(), account);
                return;
            }
        }
        launchInteractiveGoogleSignIn();
    }

    private void launchInteractiveGoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            if (isFreshIdToken(idToken)) {
                Log.d(TAG, "Google sign-in successful for: " + account.getEmail());
                authenticateWithServer(idToken, account);
            } else {
                Log.e(TAG, "Google sign-in failed: missing or expired ID token");
                signInInFlight = false;
                setGoogleSignInLoading(false);
                Toast.makeText(this, R.string.google_sign_in_failed_no_token, Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.e(TAG, "Google sign-in failed with statusCode=" + e.getStatusCode(), e);
            signInInFlight = false;
            setGoogleSignInLoading(false);
            Toast.makeText(
                this,
                getString(R.string.google_sign_in_failed_status, e.getStatusCode()),
                Toast.LENGTH_LONG
            ).show();
        }
    }

    private void authenticateWithServer(String idToken, GoogleSignInAccount account) {
        setGoogleSignInLoading(true);
        Toast.makeText(this, R.string.google_sign_in_authenticating, Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            try {
                JSONObject data = performGoogleAuthExchange(idToken);
                JSONObject user = data.optJSONObject("user");
                String token = data.optString("token", "");
                String refreshToken = data.optString("refreshToken", "");
                String userId = user == null ? "" : user.optString("id", "");
                String displayName = user == null
                    ? (account.getDisplayName() == null ? "" : account.getDisplayName())
                    : user.optString("displayName", user.optString("name", ""));
                String role = user == null ? "student" : user.optString("role", "student");

                if (token.trim().isEmpty()) {
                    throw new IllegalStateException("Missing app session token");
                }

                appPreferences.saveAuthSession(token, refreshToken, userId, displayName, role, "google");
                runOnUiThread(() -> {
                    signInInFlight = false;
                    proceedToMain();
                });
            } catch (Exception error) {
                Log.e(TAG, "Server auth failed", error);
                runOnUiThread(() -> {
                    signInInFlight = false;
                    setGoogleSignInLoading(false);
                    Toast.makeText(this, toReadableAuthError(error), Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private void warmBackend() {
        if (!isGoogleClientConfigured()) {
            return;
        }
        new Thread(() -> {
            warmUrl("/status");
            warmUrl("/api/auth/google-config");
        }).start();
    }

    private void warmUrl(String relativePath) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ApiConfig.getBaseUrl() + relativePath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2500);
            connection.setReadTimeout(2500);
            connection.setRequestProperty("Accept", "application/json");
            connection.getResponseCode();
        } catch (Exception ignored) {
            // Best-effort warm-up so serverless cold starts are less visible on sign-in.
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void setGoogleSignInLoading(boolean loading) {
        googleSignInButton.setEnabled(!loading && isGoogleClientConfigured());
        googleSignInButton.setAlpha(loading ? 0.7f : (isGoogleClientConfigured() ? 1f : 0.6f));
        googleSignInButton.setText(loading ? R.string.google_sign_in_authenticating : R.string.login_google_button);
    }

    private JSONObject performGoogleAuthExchange(String idToken) throws Exception {
        Exception lastError = null;
        for (int attempt = 1; attempt <= AUTH_MAX_ATTEMPTS; attempt++) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ApiConfig.getBaseUrl() + "/api/auth/google");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(AUTH_CONNECT_TIMEOUT_MS);
                connection.setReadTimeout(AUTH_READ_TIMEOUT_MS);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");

                JSONObject body = new JSONObject();
                body.put("idToken", idToken);

                try (OutputStream outputStream = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    writer.write(body.toString());
                }

                int statusCode = connection.getResponseCode();
                String responseBody = readStream(statusCode >= 200 && statusCode < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream());

                if (statusCode >= 200 && statusCode < 300) {
                    JSONObject payload = new JSONObject(responseBody);
                    JSONObject data = payload.optJSONObject("data");
                    return data == null ? new JSONObject() : data;
                }

                boolean shouldRetry = statusCode >= 500 || statusCode == 408 || statusCode == 429;
                IllegalStateException error = new IllegalStateException(
                    "Google auth exchange failed with status " + statusCode + ": " + responseBody
                );
                if (!shouldRetry || attempt == AUTH_MAX_ATTEMPTS) {
                    throw error;
                }
                lastError = error;
            } catch (Exception error) {
                lastError = error;
                if (attempt == AUTH_MAX_ATTEMPTS) {
                    throw error;
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            try {
                Thread.sleep(250L * attempt);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                throw interruptedException;
            }
        }
        throw lastError == null ? new IllegalStateException("Google auth exchange failed") : lastError;
    }

    private boolean isUserAuthenticated() {
        if (appPreferences.hasGuestSession()) {
            appPreferences.clearAuthSession();
            return false;
        }
        return appPreferences.hasGoogleSession();
    }

    private boolean isGoogleClientConfigured() {
        String clientId = BuildConfig.GOOGLE_CLIENT_ID;
        return clientId != null && !clientId.trim().isEmpty() && !clientId.contains("YOUR_");
    }

    private void renderGoogleSetupState() {
        boolean configured = isGoogleClientConfigured();
        googleSignInButton.setEnabled(configured);
        googleSignInButton.setAlpha(configured ? 1f : 0.6f);

        setupTitleView.setText(configured
            ? R.string.login_setup_ready
            : R.string.login_setup_missing_title);
        setupMessageView.setText(configured
            ? R.string.login_setup_ready_body
            : R.string.login_setup_missing_body);

        int strokeColor = ContextCompat.getColor(this,
            configured ? R.color.surface_toggle_algorithm_stroke : R.color.surface_stroke_muted);
        int backgroundColor = ContextCompat.getColor(this,
            configured ? R.color.surface_toggle_algorithm_active : R.color.surface_panel_muted);
        setupCard.setStrokeColor(strokeColor);
        setupCard.setCardBackgroundColor(backgroundColor);

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

    private String toReadableAuthError(Exception error) {
        if (error == null) {
            return getString(R.string.google_sign_in_failed_server);
        }
        String message = error.getMessage();
        if (message == null || message.trim().isEmpty()) {
            return getString(R.string.google_sign_in_failed_server);
        }

        int jsonStart = message.indexOf('{');
        if (jsonStart >= 0) {
            try {
                JSONObject payload = new JSONObject(message.substring(jsonStart));
                JSONObject errorObject = payload.optJSONObject("error");
                String serverMessage = errorObject == null ? "" : errorObject.optString("message", "");
                if (!serverMessage.trim().isEmpty()) {
                    return serverMessage;
                }
            } catch (Exception ignored) {
                // Fall back to the raw message below.
            }
        }

        return message;
    }

    private boolean isFreshIdToken(String idToken) {
        if (idToken == null || idToken.trim().isEmpty()) {
            return false;
        }
        try {
            String[] parts = idToken.split("\\.");
            if (parts.length < 2) {
                return false;
            }
            byte[] payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            JSONObject payload = new JSONObject(new String(payloadBytes, StandardCharsets.UTF_8));
            long expiresAtSeconds = payload.optLong("exp", 0L);
            long nowSeconds = System.currentTimeMillis() / 1000L;
            return expiresAtSeconds > nowSeconds + 60L;
        } catch (Exception error) {
            Log.w(TAG, "Could not inspect Google ID token expiry", error);
            return false;
        }
    }

    private void proceedToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close login activity
    }
}
