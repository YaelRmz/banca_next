package com.thalesgroup.BancoNextSample;

import android.app.Application;

import com.thalesgroup.BancoNextSample.Helpers.SdkLogic;

public class BancoNextSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SdkLogic.setup(this);
    }
}