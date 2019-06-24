package com.zsh.mvpframeapplication.base.mvp.factory;

import com.zsh.mvpframeapplication.base.mvp.presenter.BaseMvpPresenter;
import com.zsh.mvpframeapplication.base.mvp.view.IBaseView;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:Presenter工厂接口
 */
public interface PresenterMvpFactory <V extends IBaseView, P extends BaseMvpPresenter<V>> {
    /**
     * 创建Presenter的接口方法
     *
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
