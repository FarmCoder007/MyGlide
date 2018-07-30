package com.example.xu.myglide;

import android.app.Application;
import android.content.Context;

/**
 * Created by xu on 2018/6/12.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
