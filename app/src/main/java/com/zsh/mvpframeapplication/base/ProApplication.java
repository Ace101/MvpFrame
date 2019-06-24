package com.zsh.mvpframeapplication.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import com.zsh.mvpframeapplication.R;
import com.zsh.mvpframeapplication.utils.GlobalConfigs;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:
 */
public class ProApplication extends Application {

    private static Context context;
    private volatile boolean isBackgroundRunning = false;

    public static Context getContext() {
        return context;
    }

    public boolean isBackgroundRunning() {
        return isBackgroundRunning;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        context = getApplicationContext();

        /****** 初始化全局变量 ******/
        initConfigs();
        /****** activity的生命周期监听接口 ******/
        onMonitorSwitchState();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initConfigs() {
        int themeColor = ContextCompat.getColor(this, R.color.theme_color_cyan);

        GlobalConfigs globalConfigs = new GlobalConfigs(this);
        globalConfigs.initGlobalConfigs();
    }

    private void onMonitorSwitchState() {
        ActivityLifecycleManager.init(this).setOnTaskSwitchListener(new ActivityLifecycleManager.OnTaskSwitchListener() {
            @Override
            public void onTaskSwitchToForeground() {
                isBackgroundRunning = false;
            }

            @Override
            public void onTaskSwitchToBackground() {
                isBackgroundRunning = true;
            }
        });
    }

    @Override
    public void onTerminate() {
        ActivityLifecycleManager.unregister(this);
        super.onTerminate();
    }
}
