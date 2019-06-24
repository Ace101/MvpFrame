package com.zsh.mvpframeapplication.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:
 */
public class ActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {

    public int mCount = 0;
    private OnTaskSwitchListener mOnTaskSwitchListener;
    private static ActivityLifecycleManager mBaseLifecycle;

    private ActivityLifecycleManager() {
    }

    public static ActivityLifecycleManager init(Application application) {
        if (null == mBaseLifecycle) {
            mBaseLifecycle = new ActivityLifecycleManager();
            application.registerActivityLifecycleCallbacks(mBaseLifecycle);
        }
        return mBaseLifecycle;
    }

    public static void unregister(Application application) {
        if (null != mBaseLifecycle) {
            application.unregisterActivityLifecycleCallbacks(mBaseLifecycle);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        ProActivityManager.getInstance().pullActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        /****** 不统计启动页 ******/
        if (mCount++ == 0) {
            mOnTaskSwitchListener.onTaskSwitchToForeground();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        /****** 不统计启动页 ******/
        if ((0 == mCount) || (--mCount == 0)) {
            mOnTaskSwitchListener.onTaskSwitchToBackground();
        }

        if (mCount < 0) {
            mCount = 0;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //ProActivityManager.getInstance().destroyActivity(activity, false);
    }

    public void setOnTaskSwitchListener(OnTaskSwitchListener listener) {
        mOnTaskSwitchListener = listener;
    }

    public interface OnTaskSwitchListener {
        void onTaskSwitchToForeground();

        void onTaskSwitchToBackground();
    }
}
