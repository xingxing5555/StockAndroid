package com.example.admin.basic.application;

import android.app.Application;

import com.example.admin.basic.net.RequestManager;


/**
 * @author Xinxin Shi
 */

public class BaseApplication extends Application {
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RequestManager.init();
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
