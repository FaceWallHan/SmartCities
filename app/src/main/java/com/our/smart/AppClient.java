package com.our.smart;

import android.app.Application;

import com.our.smart.utils.LocalKeyUtil;

public class AppClient extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalKeyUtil.initSingle(this);
    }
}
