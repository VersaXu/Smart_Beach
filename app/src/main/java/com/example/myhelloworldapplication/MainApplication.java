package com.example.myhelloworldapplication;

import java.util.HashMap;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

public class MainApplication extends Application {
    private final static String TAG = "MainApplication";
    // 声明一个当前应用的静态实例
    private static MainApplication mApp;
    // 声明一个公共信息映射对象，可当作全局变量使用
    public HashMap<String, String> mInfoMap = new HashMap<String, String>();

    // 利用单例模式获取当前应用唯一实例
    public static MainApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 打开应用时对静态的应用实例赋值
        mApp = this;
        Log.d(TAG, "onCreate");
    }
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    // 声明一个公共的图标映射对象
    public HashMap<Long, Bitmap> mIconMap = new HashMap<Long, Bitmap>();
}