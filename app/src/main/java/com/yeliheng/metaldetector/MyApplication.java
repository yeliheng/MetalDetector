package com.yeliheng.metaldetector;

import android.app.Application;
import android.content.Context;

/**
 * 创建者: Yeliheng
 * 时间: 2019/1/29 - 18:32
 * 功能: 全局获取Context
 */
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext(){
        return context;
    }
}
