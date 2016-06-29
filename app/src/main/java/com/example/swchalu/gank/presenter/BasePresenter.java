package com.example.swchalu.gank.presenter;

import com.example.swchalu.gank.ui.view.BaseView;

/**
 * Created by swchalu on 2016/6/23.
 */
public class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T mBaseView;

    @Override
    public void attachView(T baseView) {
        this.mBaseView = baseView;
    }

    @Override
    public void detachView() {
        this.mBaseView = null;
    }

    public boolean isViewAttached() {
        return this.mBaseView != null;
    }

    public T getmBaseView() {
        return this.mBaseView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new BaseViewNotAttachedException();
    }

    public static class BaseViewNotAttachedException extends RuntimeException {
        public BaseViewNotAttachedException() {
            super("Please call Presenter.attachView(BaseView) before requesting data to the Presenter");
        }
    }
}
