package com.example.swchalu.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swchalu.gank.R;
import com.example.swchalu.gank.entities.SearchResultEntity;
import com.example.swchalu.gank.ui.view.DataView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by swchalu on 2016/7/5.
 */
public class FuliFragment extends BaseFragment implements DataView, SwipeRefreshLayout.OnRefreshListener {

    private final String Tag = "FuliFragment";
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    List<SearchResultEntity> lists = new ArrayList<SearchResultEntity>();
    private RecyclerView.LayoutManager mLayoutManager;
    private View rootView;
    //当前页码，默认初始值为1，每页10条数据
    private int currentpage = 1;
    private boolean FLAG_CAN_LOAD = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fuli, container, false);
        ButterKnife.bind(this, rootView);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.toolbarColor));
        return rootView;
    }

    @Override
    public void loadData(Object o, int type) {

    }

    @Override
    public void startLoading(int type) {
        super.startLoading(type);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {

    }
}
