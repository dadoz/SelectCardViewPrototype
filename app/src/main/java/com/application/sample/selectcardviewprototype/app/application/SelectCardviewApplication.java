package com.application.sample.selectcardviewprototype.app.application;

import android.app.Application;

import com.application.sample.selectcardviewprototype.app.R;
import com.flurry.android.FlurryAgent;

public class SelectCardviewApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(this, getString(R.string.FLURRY_API_KEY));
    }
}
