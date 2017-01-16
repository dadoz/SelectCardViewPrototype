package com.application.sample.selectcardviewprototype.app.application;

import android.app.Application;

import com.application.sample.selectcardviewprototype.app.R;
import com.flurry.android.FlurryAgent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SelectCardviewApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ShadowsIntoLight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        new FlurryAgent.Builder()
                .withLogEnabled(false)
                .build(this, getString(R.string.FLURRY_API_KEY));
    }
}
