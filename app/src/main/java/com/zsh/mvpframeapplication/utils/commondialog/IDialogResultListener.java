package com.zsh.mvpframeapplication.utils.commondialog;

/**
 * @author:zhangshihao
 * @date:2019-06-05
 * @description:对话框返回结果回调
 */
public interface IDialogResultListener<T> {
    void onDataResult(T result);
}
