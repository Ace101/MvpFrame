package com.zsh.mvpframeapplication.base.mvp.proxy;

import com.zsh.mvpframeapplication.base.mvp.factory.PresenterMvpFactory;
import com.zsh.mvpframeapplication.base.mvp.presenter.BaseMvpPresenter;
import com.zsh.mvpframeapplication.base.mvp.view.IBaseView;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:代理接口
 */
public interface PresenterProxyInterface<V extends IBaseView, P extends BaseMvpPresenter<V>> {

    /**
     * 设置创建Presenter的工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory);

    /**
     * 获取Presenter的工厂类
     *
     * @return 返回PresenterMvpFactory类型
     */
    PresenterMvpFactory<V, P> getPresenterFactory();


    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     */
    P getMvpPresenter();
}
