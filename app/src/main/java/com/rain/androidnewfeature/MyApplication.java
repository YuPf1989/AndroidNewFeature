package com.rain.androidnewfeature;

import android.app.Application;

/**
 * Author:rain
 * Date:2018/6/11 11:00
 * Description:
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }

    public static Application getInstance() {
        return instance;
    }
}
