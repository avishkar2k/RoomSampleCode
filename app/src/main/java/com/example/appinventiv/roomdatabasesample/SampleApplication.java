package com.example.appinventiv.roomdatabasesample;

import android.app.Application;
import android.content.Context;


import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


/**
 * Created by admin1 on 6/6/17.
 * Subclass of Application class
 * Base class for maintaining global application state
 * Twitter and Facebook sdks are initiaize here
 * ACRA is initialize
 */


@ReportsCrashes(mailTo = "avishkar.ramjeet@appinventiv.com,rupinder.kaur@appinventiv.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.app_name)
public class SampleApplication extends Application {

    private static final String TWITTER_KEY = "SFH5DFOVgP0cmhFIPq6p28Uof";
    private static final String TWITTER_SECRET = "c41NdNuyHKkBrVGJae1atmRkJpMs8JuCAJ9Jl4SokAAgep3ky6";

    private static SampleApplication application;

    public static SampleApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
