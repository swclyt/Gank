package com.example.swchalu.gank.presenter;

import android.content.Context;

import com.example.swchalu.gank.Constants;
import com.example.swchalu.gank.api.GankApi;
import com.example.swchalu.gank.api.GankService;
import com.example.swchalu.gank.entities.SearchEntity;
import com.example.swchalu.gank.ui.view.DataView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by swchalu on 2016/6/28.
 */
public class AllFragmentPresenterImpl extends BasePresenter<DataView> {

    private final String Tag = "AllFragmentPresenterImpl";
    private Context context;
    private SearchEntity entity;

    public AllFragmentPresenterImpl(Context context) {
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
        GankService.createApi(GankApi.class).getSearch("all", 10, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
