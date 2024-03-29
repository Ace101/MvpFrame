package com.zsh.mvpframeapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.zsh.mvpframeapplication.utils.threadpools.MainPoolUtils;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:键盘操作工具类
 */
public class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /*
      避免输入法面板遮挡
      <p>在 manifest.xml 中 activity 中设置</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */

    /**
     * @return void
     * @parms [Activity]
     * @methodNama showSoftInput
     * @author zhangshihao
     * @desc 动态显示软键盘
     */
    public static void showSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * @return void
     * @parms [view]
     * @methodNama showKeyboard
     * @author zhangshihao
     * @desc 动态显示软键盘
     * 注意：布局必须加载完成。
     * 在 onCreate() 中，如果立即调用 showSoftInput() 是不会生效的。想要在页面一启动的时候就弹出键盘，可以在 Activity 上，设置 android:windowSoftInputMode 属性来完成，或者做一个延迟加载，View.postDelayed() 也是一个解决方案。
     */
    public static void showKeyboard(final View view) {
        MainPoolUtils.getInstance().onMainRuningDelay(view, new Runnable() {
            @Override
            public void run() {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                }
            }
        }, 300);
    }

    /**
     * @parms [view]
     * @return void
     * @methodNama hideKeyboard
     * @author zhangshihao
     * @desc 隐藏软键盘
     */
    public static void hideKeyboard(final View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * @parms [activity]
     * @return void
     * @methodNama hideKeyboard
     * @author zhangshihao
     * @desc 隐藏软键盘
     */
    public static void hideKeyboard(Activity activity) {
        // 找到当前获得焦点的 view，从而可以获得正确的窗口 token
        View view = activity.getCurrentFocus();
        // 如果没有获得焦点的 view，创建一个新的，从而得到一个窗口的 token
        if (view == null) {
            view = new View(activity);
            view.requestFocus();
        }

        InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (null != imm) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * @parms [view]
     * @return void
     * @methodNama toggleSoftInput
     * @author zhangshihao
     * @desc 切换键盘显示与否状态,切换键盘的弹出和隐藏(隐藏 -》 显示； 显示 -》隐藏 切换)
     */
    public static void toggleSoftInput(Context context) {
        if (null != context) {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }
}
