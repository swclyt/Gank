package com.example.swchalu.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swchalu.gank.R;
import com.example.swchalu.gank.entities.SearchEntity;
import com.example.swchalu.gank.entities.SearchResultEntity;
import com.example.swchalu.gank.presenter.AllFragmentPresenterImpl;
import com.example.swchalu.gank.ui.adapter.AllRecyclerAdapter;
import com.example.swchalu.gank.ui.view.DataView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by swchalu on 2016/6/23.
 */
public class AllFragment extends BaseFragment implements DataView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Bind(R.id.swipe)
    SwipeRefreshLayout refreshLayout;

    List<SearchResultEntity> lists;
    private RecyclerView.LayoutManager mLayoutManager;
    private AllFragmentPresenterImpl presenter;
    private AllRecyclerAdapter mAdapter;
    private View rootView;
    private int currentpage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, rootView);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.toolbarColor));
        initData();
        return rootView;
    }

    public void initData() {
        presenter = new AllFragmentPresenterImpl(getActivity());
        presenter.attachView(this);
        presenter.loadData(1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void loadData(Object object) {
        if (object != null) {
            if (object instanceof SearchEntity) {
                SearchEntity entity = (SearchEntity) object;
                if (!entity.isError()) {
                    SearchResultEntity[] res = entity.getResults();
                    for (int i = 0; i < res.length; i++)
                        lists.add(res[i]);
                    mAdapter = new AllRecyclerAdapter(lists, getActivity());
                    recyclerView.setAdapter(mAdapter);
                    refreshLayout.setRefreshing(false);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
