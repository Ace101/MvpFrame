package com.zsh.mvpframeapplication.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.zsh.mvpframeapplication.R;
import com.zsh.mvpframeapplication.inter.IHandlerCallBack;
import com.zsh.mvpframeapplication.utils.commondialog.DialogFragmentHelper;

import java.lang.ref.SoftReference;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author:zhangshihao
 * @date:2019-06-04
 * @description:Activity基类
 */
public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private long updateTime = -1;
    public BaseActivityHandler handler;
    protected Toolbar mIdToolbar;
    private DialogFragment mDialogFragment = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.handler = new BaseActivityHandler(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        apply();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        apply();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        apply();
    }

    private void apply() {
        initToolBar();
        updateTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (isFinishing()) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 将处理结果托管给EasyPermissions进行处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // (Optional) 检查用户拒绝权限的时候是否选择了“不再提醒”的情况
        // 这里将弹出对话框引导用户去系统设置
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            handler.postDelayed(() -> finish(), 2000);
        }
    }

    /**
     * @param ：[]
     * @return : void
     * @methodName ：initToolBar created by liujiyuan on 2018/8/15 上午11:13.
     * @description ：所有activity初始化toolbar的方法
     */
    protected void initToolBar() {
        try {
            mIdToolbar = findViewById(R.id.id_toolbar);
            if (mIdToolbar != null) {
                setSupportActionBar(mIdToolbar);
                mIdToolbar.setNavigationIcon(R.drawable.ic_back_white);
                mIdToolbar.setNavigationOnClickListener(v -> BaseActivity.this.onBackPressed());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ：[msg, isCancelable, isDismiss]
     * @return : void
     * @methodName ：showProgressDialog created by luozhipeng on 22/11/17 17:25.
     * @description ：显示加载框
     */
    public void showProgressDialog(String msg, boolean isCancelable, boolean isDismiss) {
        try {
            if (!isFinishing()) {
                mDialogFragment = DialogFragmentHelper.showProgress(getSupportFragmentManager(), msg, isCancelable);
                if (mDialogFragment.getDialog() != null) {
                    mDialogFragment.getDialog().setCanceledOnTouchOutside(isDismiss);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @parms
     * @return
     * @methodNama
     * @author zhangshihao
     * @desc 停止加载框
     */
    public void stopProgressDialog() {
        try {
            if (mDialogFragment != null) {
                mDialogFragment.dismissAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDialogFragment = null;
        }
    }


    /**
     * @author zhangshihao
     * @parms
     * @return
     * @methodNama
     * @desc 初始化Handler对象, 采用静态内部类的实现方式
     */
    public final static class BaseActivityHandler extends Handler {
        private final SoftReference<BaseActivity> mBaseActivityWeakReference;
        private IHandlerCallBack mHandlerCallBack;

        public BaseActivityHandler(BaseActivity mBaseActivity) {
            mBaseActivityWeakReference = new SoftReference<>(mBaseActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity baseActivity = mBaseActivityWeakReference.get();
            if (baseActivity != null && mHandlerCallBack != null) {
                mHandlerCallBack.handleMsg(msg);
            }
        }

        public void setHandlerCallBack(IHandlerCallBack handlerCallBack) {
            mHandlerCallBack = handlerCallBack;
        }
    }

    /**
     * @return
     * @parms
     * @methodNama
     * @author zhangshihao
     * @desc 横竖屏切换时调用，需要在manifest中声明
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            KMReaderConfigs.IS_PORTRAIT = false;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            KMReaderConfigs.IS_PORTRAIT = true;
        }
    }
}
