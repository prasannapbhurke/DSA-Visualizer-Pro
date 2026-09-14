package com.dsavisualizer.app.data;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Centralized, thread-safe HTTP client for the DSA Visualizer Android app.
 * Replaces boilerplate across activities, handles thread pooling, JSON I/O,
 * status code validation, and dispatches callbacks to the Android main thread.
 */
public final class ApiClient {

    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 10000;
    private static final int DEFAULT_READ_TIMEOUT_MS = 15000;

    private static final int AUTH_MAX_ATTEMPTS = 3;
    private static final long AUTH_RETRY_DELAY_MS = 1000L;

    private static volatile ApiClient sInstance;

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (sInstance == null) {
            synchronized (ApiClient.class) {
                if (sInstance == null) {
                    sInstance = new ApiClient();
                }
            }
        }
        return sInstance;
    }

    public interface Callback {
        void onSuccess(@NonNull JSONObject response);
        void onError(@NonNull Exception error);
    }

    public interface ApiCallback extends Callback {
    }

    public static class ApiException extends Exception {
        private final int statusCode;
        private final String rawResponseBody;

        public ApiException(int statusCode, String message, String rawResponseBody) {
            super(message);
            this.statusCode = statusCode;
            this.rawResponseBody = rawResponseBody;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getRawResponseBody() {
            return rawResponseBody;
        }
    }

    // --- Asynchronous API Methods (Callbacks invoked on Main Thread) ---

    public void get(@NonNull String endpoint, @Nullable String bearerToken, @NonNull Callback callback) {
        request("GET", endpoint, null, bearerToken, callback);
    }

    public void post(@NonNull String endpoint, @Nullable JSONObject body, @Nullable String bearerToken, @NonNull Callback callback) {
        request("POST", endpoint, body, bearerToken, callback);
    }

    public void put(@NonNull String endpoint, @Nullable JSONObject body, @Nullable String bearerToken, @NonNull Callback callback) {
        request("PUT", endpoint, body, bearerToken, callback);
    }

    public void patch(@NonNull String endpoint, @Nullable JSONObject body, @Nullable String bearerToken, @NonNull Callback callback) {
        request("PATCH", endpoint, body, bearerToken, callback);
    }

    public void delete(@NonNull String endpoint, @Nullable String bearerToken, @NonNull Callback callback) {
        request("DELETE", endpoint, null, bearerToken, callback);
    }

    public void request(
        @NonNull String method,
        @NonNull String endpoint,
        @Nullable JSONObject body,
        @Nullable String bearerToken,
        @NonNull Callback callback
    ) {
        executor.execute(() -> {
            try {
                JSONObject result = execute(method, endpoint, body, bearerToken);
                mainHandler.post(() -> callback.onSuccess(result));
            } catch (Exception error) {
                mainHandler.post(() -> callback.onError(error));
            }
        });
    }

    // --- Synchronous Execution (for Background Threads / Workers) ---

    @NonNull
    public JSONObject execute(
        @NonNull String method,
        @NonNull String endpoint,
        @Nullable JSONObject body,
        @Nullable String bearerToken
    ) throws IOException, JSONException, ApiException {
        HttpURLConnection connection = null;
        try {
            String fullUrl = endpoint.startsWith("http://") || endpoint.startsWith("https://")
                ? endpoint
                : ApiConfig.getBaseUrl() + (endpoint.startsWith("/") ? endpoint : "/" + endpoint);

            URL url = new URL(fullUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(DEFAULT_READ_TIMEOUT_MS);
            connection.setRequestProperty("Accept", "application/json");

            if (bearerToken != null && !bearerToken.trim().isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + bearerToken.trim());
            }

            if (body != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                try (OutputStream outputStream = connection.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                    writer.write(body.toString());
                }
            }

            int statusCode = connection.getResponseCode();
            InputStream stream = statusCode >= 200 && statusCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream();

            String responseBody = readStream(stream);

            if (statusCode < 200 || statusCode >= 300) {
                String friendlyMessage = parseErrorMessage(statusCode, responseBody);
                throw new ApiException(statusCode, friendlyMessage, responseBody);
            }

            if (responseBody == null || responseBody.trim().isEmpty()) {
                return new JSONObject();
            }
            return new JSONObject(responseBody);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Shared Google Auth Exchange with automatic retry and backoff.
     */
    @NonNull
    public JSONObject performGoogleAuthExchange(@NonNull String idToken) throws Exception {
        Exception lastError = null;
        JSONObject requestBody = new JSONObject();
        requestBody.put("idToken", idToken);

        for (int attempt = 1; attempt <= AUTH_MAX_ATTEMPTS; attempt++) {
            try {
                JSONObject response = execute("POST", "/api/auth/google", requestBody, null);
                JSONObject data = response.optJSONObject("data");
                return data != null ? data : response;
            } catch (ApiException apiError) {
                int code = apiError.getStatusCode();
                boolean shouldRetry = code >= 500 || code == 408 || code == 429;
                if (!shouldRetry || attempt == AUTH_MAX_ATTEMPTS) {
                    throw apiError;
                }
                lastError = apiError;
            } catch (Exception error) {
                lastError = error;
                if (attempt == AUTH_MAX_ATTEMPTS) {
                    throw error;
                }
            }

            try {
                Thread.sleep(AUTH_RETRY_DELAY_MS * attempt);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                throw interruptedException;
            }
        }

        if (lastError != null) {
            throw lastError;
        }
        throw new IllegalStateException("Google auth exchange failed after retries");
    }

    // --- Helper Methods ---

    @NonNull
    public static String readStream(@Nullable InputStream stream) throws IOException {
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

    @NonNull
    private static String parseErrorMessage(int statusCode, @Nullable String responseBody) {
        if (responseBody == null || responseBody.trim().isEmpty()) {
            return "HTTP " + statusCode;
        }
        try {
            JSONObject obj = new JSONObject(responseBody);
            if (obj.has("error")) {
                Object err = obj.get("error");
                if (err instanceof JSONObject) {
                    String msg = ((JSONObject) err).optString("message", "");
                    if (!msg.isEmpty()) {
                        return msg;
                    }
                } else if (err instanceof String) {
                    return (String) err;
                }
            }
            if (obj.has("message")) {
                return obj.getString("message");
            }
        } catch (Exception ignored) {
        }
        return "HTTP " + statusCode + ": " + responseBody;
    }
}
