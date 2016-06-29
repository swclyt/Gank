package com.example.swchalu.gank.presenter;

import com.example.swchalu.gank.ui.view.BaseView;

/**
 * Created by swchalu on 2016/6/23.
 */
public interface Presenter<V extends BaseView> {
    void attachView(V mvpView);

    void detachView();
}
