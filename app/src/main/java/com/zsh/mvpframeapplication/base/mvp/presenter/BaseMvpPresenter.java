package com.zsh.mvpframeapplication.base.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zsh.mvpframeapplication.base.mvp.view.IBaseView;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:所有Presenter的基类，并不强制实现这些方法，有需要在重写
 */
public abstract class BaseMvpPresenter<V extends IBaseView> {
    /****** V层view ******/
    private V mView;

    /**
     * @parms: [savedState]
     * @return: void
     * @methodNama: onCreatePresenter
     * @author: zhangshihao
     * @desc: Presenter被创建后调用; savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePresenter(@Nullable Bundle savedState) {
        Log.e("perfect-mvp", "P onCreatePresenter = ");
    }

    /**
     * @parms: [outState]
     * @return: void
     * @methodNama: onSaveInstanceState
     * @author: zhangshihao
     * @desc: 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState 时机相同
     */
    public void onSaveInstanceState(Bundle outState) {
        Log.e("perfect-mvp", "P onSaveInstanceState = ");
    }

    /**
     * @parms: [mvpView]
     * @return: void
     * @methodNama: onAttachMvpView
     * @author: zhangshihao
     * @desc: 绑定View
     */
    public void onAttachMvpView(V mvpView) {
        mView = mvpView;
        Log.e("perfect-mvp", "P onResume");
    }

    /**
     * @parms:
     * @return:
     * @methodNama: onDetachMvpView
     * @author: zhangshihao
     * @desc: 解除绑定View
     */
    public void onDetachMvpView() {
        mView = null;
        Log.e("perfect-mvp", "P onDetachMvpView = ");
    }


    /**
     * @parms:
     * @return:
     * @methodNama: onDestroyPresenter
     * @author: zhangshihao
     * @desc: Presenter被销毁时调用
     */
    public void onDestroyPresenter() {
        Log.e("perfect-mvp", "P onDestroy = ");
    }

    /**
     * @parms:
     * @return:
     * @methodNama: getMvpView
     * @author: zhangshihao
     * @desc: 获取V层接口View; 返回当前MvpView
     */
    public V getMvpView() {
        return mView;
    }

    public boolean isAttached() {
        if (null != mView) {
            if (mView instanceof Activity) {
                return !((Activity) mView).isFinishing();
            }

            if (mView instanceof Fragment) {
                return ((Fragment) mView).isAdded();
            }

            if (mView instanceof android.app.Fragment) {
                return ((android.app.Fragment) mView).isAdded();
            }
        }
        return false;
    }

    public abstract void onInit(V mView);
}
