/*
 * MIT License
 *
 * Copyright (c) 2022 Thales DIS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * IMPORTANT: This source code is intended to serve training information purposes only.
 *            Please make sure to review our IdCloud documentation, including security guidelines.
 */

package com.thalesgroup.BancoNextSample.Helpers;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IdCloudHelper {

    private static final String JSON_REQUEST = "{\n" +
            "    \"name\": \"Enroll_OTP\",\n" +
            "    \"version\": 1,\n" +
            "    \"input\": {\n" +
            "        \"userId\": \"%s\"\n" +
            "    }\n" +
            "}";

    public interface GenericEnrollmentHandler {
        void onFinished(boolean success, final String registrationCode, final String pin, final String error);
    }

    public static void enrollUser(final String tokenName,
                                  final GenericEnrollmentHandler callback) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format(Locale.US, "Bearer %s", Configuration.CFG_BASIC_AUTH_JWT));
        headers.put("X-API-KEY", Configuration.CFG_BASIC_AUTH_API_KEY);
        headers.put("Content-Type", "application/json");

        final String jsonRequest = String.format(Locale.US, JSON_REQUEST, tokenName);

        ExecutionService.getExecutionService().runOnBackgroundThread(() -> {
            doPostRequest(Configuration.CFG_IDCLOUD_URL, headers, jsonRequest, (success, result) -> {
                final boolean valid = success && result.contains("\"status\":\"Success\",");
                try {
                    final JSONObject jsonObject = new JSONObject(result).getJSONObject("state").getJSONObject("result").getJSONObject("object");
                    final String regCode = jsonObject.getString("registrationCode");
                    final String regPin = jsonObject.getString("pin");

                    callback.onFinished(valid, regCode, regPin, null);

                } catch (final JSONException error) {
                    callback.onFinished(false, null, null, error.getMessage());
                }
            });
        });
    }

    private static HttpURLConnection createConnection(@NonNull final String hostUrl,
                                                      @NonNull final Map<String, String> headers) throws IOException {
        final URL url = new URL(hostUrl);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            connection.setRequestProperty(key, value);
        }

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setReadTimeout(30000);
        connection.setConnectTimeout(30000);

        return connection;
    }

    private static String convertStreamToString(final InputStream inputStream) throws IOException {
        String response = "";

        do {
            if (inputStream == null) {
                break;
            }

            final Writer writer = new StringWriter();

            final char[] buffer = new char[1024];
            try (final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), 1024)) {
                int numberOfCharacters = reader.read(buffer);
                while (numberOfCharacters != -1) {
                    writer.write(buffer, 0, numberOfCharacters);
                    numberOfCharacters = reader.read(buffer);
                }
            }

            response = writer.toString();
        } while (false);

        return response;
    }

    /**
     * Does a POST request.
     *
     * @param hostUrl  URL.
     * @param headers  Headers.
     * @param body     Body.
     * @param callback Callback back to the application.
     */
    private static void doPostRequest(@NonNull final String hostUrl,
                                      @NonNull final Map<String, String> headers,
                                      @NonNull final String body,
                                      @NonNull final SdkLogic.GenericHandler callback) {
        final ExecutionService service = ExecutionService.getExecutionService();

        try {
            final HttpURLConnection connection = createConnection(hostUrl, headers);

            try (final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(body);
                writer.flush();
            }

            final String responseBody = convertStreamToString(connection.getInputStream());
            service.runOnMainUiThread(() -> callback.onFinished(true, responseBody));
        } catch (final IOException exception) {
            service.runOnMainUiThread(() -> callback.onFinished(false, exception.getMessage()));
        }
    }
}
