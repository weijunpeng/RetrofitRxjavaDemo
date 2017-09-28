package com.kevin.retrofitrxjavademo;

import android.app.Application;

import com.kevin.retrofitrxjavademo.app.GlobalConfig;

import timber.log.Timber;

/**
 * Created by zhouwenkai on 2017/9/27.
 */

public class DomoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalConfig.init(this)
                .withApiHost("http://123.57.31.11/androidnet/")
                .withIsReleased(false)
                .configure();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
