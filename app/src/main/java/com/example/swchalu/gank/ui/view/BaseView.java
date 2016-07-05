package com.example.swchalu.gank.ui.view;

/**
 * Created by swchalu on 2016/6/23.
 */
public interface BaseView {

    void startLoading(int type);

    void hideLoading();

    void showError(String msg);
}
