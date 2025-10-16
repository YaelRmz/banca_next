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

package com.thalesgroup.BancoNextSample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.gemalto.idp.mobile.fasttrack.FastTrackException;
import com.thalesgroup.BancoNextSample.Helpers.DataStructures.Lifespan;
import com.thalesgroup.BancoNextSample.Helpers.DataStructures.OtpValue;
import com.thalesgroup.BancoNextSample.Helpers.ExecutionService;
import com.thalesgroup.BancoNextSample.Helpers.IdCloudHelper;
import com.thalesgroup.BancoNextSample.Helpers.SdkLogic;
import com.thalesgroup.BancoNextSample.Helpers.SecureStorageHelper;
import com.thalesgroup.BancoNextSample.UiComponents.ResultFragment;

public class MainActivity extends AppCompatActivity {

    //region Defines

    private Button mButtonEnroll;
    private Button mButtonGenerateOTP;
    private ProgressDialog mProgressDialog = null;

    //endregion

    //region Life Cycle

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonEnroll = findViewById(R.id.activity_main_button_enroll);
        mButtonGenerateOTP = findViewById(R.id.activity_main_button_generate_otp);

        mButtonEnroll.setOnClickListener(this::onButtonPressedEnroll);
        mButtonGenerateOTP.setOnClickListener(this::onButtonPressedGenerateOtp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update UI availability
        updateGui();
    }

    //endregion

    //region Private Helpers

    private ResultFragment getResultFragment() {
        final FragmentContainerView view = findViewById(R.id.activity_main_fragment_result);
        return view.getFragment();
    }

    private void updateGui() {
        final boolean enabled = mProgressDialog == null;

        if (SdkLogic.getToken() != null) {
            mButtonEnroll.setEnabled(false);
            mButtonGenerateOTP.setEnabled(enabled);
        } else {
            mButtonEnroll.setEnabled(enabled);
            mButtonGenerateOTP.setEnabled(false);
        }
    }

    public void loadingBarShow(final String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(caption);
            mProgressDialog.show();
        } else {
            mProgressDialog.setMessage(caption);
        }

        updateGui();
    }

    public void loadingBarHide() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null; // NOPMD
        }

        updateGui();
    }

    /**
     * Common method to display information to user. Currently implemented as simple dialog with app name in caption.
     *
     * @param result Message to be displayed.
     */
    public void displayMessageDialog(final String result) {
        // Allow to display simple toast from any threads.
        ExecutionService.getExecutionService().runOnMainUiThread(() -> {
            Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_LONG).show();
        });
    }

    /**
     * Display description of error if it's not nil using displayMessageDialog method.
     *
     * @param exception Application error or nil.
     */
    public void displayMessageDialog(final Exception exception) {
        displayMessageDialog(exception.getMessage());
    }

    protected void displayMessageResult(final String message,
                                        final Lifespan lifespan) {
        getResultFragment().show(message, lifespan);
    }

    //endregion

    //region User Interface

    private void onButtonPressedEnroll(final View sender) {
        loadingBarShow("Provisioning...");

        // Hardcoded user id for demo purposes only!
        final String userId = "test01";
        final String registrationCode = "8327269845";
        final String pin = "9011";

        SecureStorageHelper.writePin(pin);

        SdkLogic.provisionWithUserId(userId, registrationCode, (successprovision, result) -> {
            loadingBarHide();
            displayMessageDialog(result);
        });

//        IdCloudHelper.enrollUser(userId, (successEnroll, registrationCode, pin, error) -> {
//            if (successEnroll) {
//                SecureStorageHelper.writePin(pin);
//
//                SdkLogic.provisionWithUserId(userId, registrationCode, (successProvision, result) -> {
//                    loadingBarHide();
//                    displayMessageDialog(result);
//                });
//            } else {
//                displayMessageDialog(error);
//                loadingBarHide();
//            }
//        });


    }

    private void onButtonPressedGenerateOtp(final View sender) {
        try {
            final OtpValue otpValue = SdkLogic.generateOtp();
            displayMessageResult(otpValue.getOtp(), otpValue.getLifespan());
        } catch (final FastTrackException exception) {
            displayMessageDialog(exception);
        }
    }

    //endregion
}