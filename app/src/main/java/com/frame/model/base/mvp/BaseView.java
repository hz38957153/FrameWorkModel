package com.frame.model.base.mvp;

/**
 * Created by MrZ on 2017/12/22. 14:15
 * project delin
 * Explain
 */

public class BaseView extends BasePresenter implements Iview {
    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showLoading(String msg, int progress) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showErrorMsg(String msg, String content) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return isViewAttached();
    }

    @Override
    public void noNetWork() {

    }

    @Override
    public void attachView(Iview view) {

    }

    @Override
    public void subscribe() {

    }
}
