package com.example.admin.basic.application;

import android.app.Application;

/**
 * @author Xinxin Shi
 */

public class BaseApplication extends Application {
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
