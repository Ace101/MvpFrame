package com.zsh.mvpframeapplication.base.mvp.factory;

import com.zsh.mvpframeapplication.base.mvp.presenter.BaseMvpPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:标注创建Presenter的注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<? extends BaseMvpPresenter> value();
}
