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

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gemalto.idp.mobile.core.IdpCore;
import com.gemalto.idp.mobile.core.passwordmanager.PasswordManagerException;
import com.gemalto.idp.mobile.fasttrack.FastTrack;
import com.gemalto.idp.mobile.fasttrack.FastTrackException;
import com.gemalto.idp.mobile.fasttrack.MobileFingerprintSource;
import com.gemalto.idp.mobile.fasttrack.protector.MobileProtector;
import com.gemalto.idp.mobile.fasttrack.protector.oath.OathMobileProtector;
import com.gemalto.idp.mobile.fasttrack.protector.oath.OathTokenDevice;
import com.gemalto.idp.mobile.fasttrack.protector.oath.OathTokenDeviceCreationCallback;
import com.gemalto.idp.mobile.fasttrack.protector.oath.TotpSettings;
import com.thalesgroup.BancoNextSample.Helpers.DataStructures.OtpValue;
import com.thalesgroup.gemalto.securelog.SecureLogConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class SdkLogic {

    private static OathMobileProtector sOathTokenManager;

    public interface GenericHandler {
        void onFinished(boolean success, final String result);
    }

    /**
     * Setups Mobile Protector SDK.
     */
    public static void setup(final Context context) {
        if (FastTrack.isConfigured()) {
            return;
        }

        FastTrack.configureSecureLog(new SecureLogConfig.Builder(context)
                .publicKey(Configuration.CFG_SLOG_MODULUS, Configuration.CFG_SLOG_EXPONENT)
                .build());

        new FastTrack.Builder(context)
//                .withActivationCode(Configuration.CFG_ACTIVATION_CODE)
                .build();

        // In order to use secure storage we need to log in the password manager.
        try {
            IdpCore.getInstance().getPasswordManager().login();
        } catch (final PasswordManagerException exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Provisions asynchronously a new token.
     *
     * @param userId           User id.
     * @param registrationCode Registration code.
     * @param callback         Callback back to the application - called on Main UI Thread.
     */
    public static void provisionWithUserId(@NonNull final String userId,
                                           @NonNull final String registrationCode,
                                           @NonNull final GenericHandler callback) {
        final OathMobileProtector oathMobileProtector = getOathMobileProtector();
        oathMobileProtector.withDeviceFingerprintSource(
                new MobileFingerprintSource(
                        Configuration.CFG_CUSTOM_FINGERPRINT_DATA,
                        MobileFingerprintSource.Type.SOFT
                )
        );

        final TotpSettings totpSettings = new TotpSettings();
        totpSettings.setTimeStepSize(Configuration.CFG_TOTP_TIME_STEP);
        totpSettings.setLength(Configuration.CFG_TOTP_LENGTH);

        oathMobileProtector.provision(userId,
                registrationCode,
                totpSettings,
                new OathTokenDeviceCreationCallback() {
                    @Override
                    public void onSuccess(final OathTokenDevice oathTokenDevice,
                                          final Map<String, String> map) {

                        callback.onFinished(true, "Provisioning was successfull.");
                    }

                    @Override
                    public void onError(final FastTrackException exception) {
                        callback.onFinished(false, exception.getMessage());
                    }
                });
    }

    /**
     * Retrieves the first token.
     *
     * @return Token, or {@code null} if no Token available.
     */
    @Nullable
    public static OathTokenDevice getToken() {
        OathTokenDevice retValue = null;
        try {
            final Set<String> tokenNames = getOathMobileProtector().getTokenDeviceNames();
            if (!tokenNames.isEmpty()) {
                retValue = getOathMobileProtector().getTokenDevice(tokenNames.iterator().next(), Configuration.CFG_CUSTOM_FINGERPRINT_DATA);
            }
        } catch (final FastTrackException exception) {
            // Application might want to handle invalid token in specific way. For example remove old one and ask for new provision.
            // Sample code will simple throw exception and crash.
            // Error here usually mean wrong password or SDK configuration.
            throw new IllegalStateException(exception.getMessage());
        }

        return retValue;
    }

    public static OtpValue generateOtp() throws FastTrackException {
        if (FastTrack.getInstance().isRooted()) {
            // Handle root status according to app policy.
        }

        final OathTokenDevice token = SdkLogic.getToken();
        return new OtpValue(token.getOtp(SecureStorageHelper.readPin()), token.getLastOtpLifeSpan(), Configuration.CFG_TOTP_TIME_STEP);
    }

    private static synchronized OathMobileProtector getOathMobileProtector() {
        if (sOathTokenManager == null) {
            try {
                sOathTokenManager = FastTrack.getInstance().getOathMobileProtectorBuilder(
                                new URL(Configuration.CFG_PROVISIONING_URL),
                                Configuration.CFG_DOMAIN,
                                MobileProtector.ProvisioningProtocol.PROTOCOL_V5,
                                Configuration.CFG_RSA_KEY_ID,
                                Configuration.CFG_RSA_KEY_EXPONENT,
                                Configuration.CFG_RSA_KEY_MODULUS)
                        .withProtectorRootPolicy(MobileProtector.RootPolicy.IGNORE)
                        .build();
            } catch (final MalformedURLException e) {
                // This should not happen.
                throw new IllegalStateException(e.getMessage());
            }
        }

        return sOathTokenManager;
    }
}
