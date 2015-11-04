package com.application.sample.selectcardviewprototype.app.singleton;

import android.app.Activity;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/**
 * Created by davide on 04/11/15.
 */
public class RetrieveAssetsSingleton {
    private final WeakReference<Activity> activity;
    private final AssetManager assetManager;
    private static RetrieveAssetsSingleton singletonRef;

    public static RetrieveAssetsSingleton getInstance(WeakReference<Activity> activity) {
        return singletonRef == null ?
                singletonRef = new RetrieveAssetsSingleton(activity) :
                singletonRef;
    }

    private RetrieveAssetsSingleton(WeakReference<Activity> activityWeakReference) {
        activity = activityWeakReference;
        assetManager = activity.get().getAssets();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String getJsonDataFromAssets() throws IOException {
        String line;
        String result = "";
        InputStreamReader is = new InputStreamReader(assetManager.open("data.json"));
        BufferedReader bufferedReader = new BufferedReader(is);
        while ((line = bufferedReader.readLine()) != null) {
            result = result.concat(line);
        }
        is.close();
        return result.equals("") ? null : result;
    }
}
