package com.example.swchalu.gank.ui.view;

import android.view.View;

/**
 * Created by swchalu on 2016/6/23.
 */
public interface BaseView {

    void startLoading();

    void hideLoading();

    void showError(String msg, View.OnClickListener onClickListener);
}
