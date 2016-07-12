package com.example.swchalu.gank.presenter;

import android.content.Context;

import com.example.swchalu.gank.Constants;
import com.example.swchalu.gank.api.GankApi;
import com.example.swchalu.gank.api.GankService;
import com.example.swchalu.gank.entities.SearchEntity;
import com.example.swchalu.gank.ui.view.DataView;
import com.example.swchalu.gank.utils.NetworkUtils;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by swchalu on 2016/7/12.
 */
public class AndroidFragmentPresenterImpl extends BasePresenter<DataView> {

    private final String Tag = "AndroidFragmentPresenterImpl";
    private Context context;
    private SearchEntity entity;

    public AndroidFragmentPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(DataView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadData(final int page) {
        if (!NetworkUtils.isNetworkConnected()) {
            getmBaseView().showError("请检查是否接入网络...");
            return;
        }
        GankService.createApi(GankApi.class).getSearch("Android", 10, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getmBaseView().startLoading(page > 1 ? Constants.LOADING_TYPE_PAGE : Constants.LOADING_TYPE_INIT);
                    }
                })
                .subscribe(new Observer<SearchEntity>() {
                               @Override
                               public void onCompleted() {
                               }

                               @Override
                               public void onError(Throwable e) {
                               }

                               @Override
                               public void onNext(SearchEntity searchEntity) {
                                   entity = searchEntity;
                                   getmBaseView().loadData(entity, page == 1 ? Constants.LOADING_TYPE_INIT : Constants.LOADING_TYPE_PAGE);
                               }
                           }
                );
    }
}
