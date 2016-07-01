package com.example.swchalu.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.swchalu.gank.Constants;
import com.example.swchalu.gank.R;
import com.example.swchalu.gank.entities.SearchEntity;
import com.example.swchalu.gank.entities.SearchResultEntity;
import com.example.swchalu.gank.presenter.AllFragmentPresenterImpl;
import com.example.swchalu.gank.ui.adapter.AllRecyclerAdapter;
import com.example.swchalu.gank.ui.view.DataView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by swchalu on 2016/6/23.
 */
public class AllFragment extends BaseFragment implements DataView, SwipeRefreshLayout.OnRefreshListener {

    private final String Tag = "AllFragment";
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    List<SearchResultEntity> lists = new ArrayList<SearchResultEntity>();
    private RecyclerView.LayoutManager mLayoutManager;
    private AllFragmentPresenterImpl presenter = null;
    private AllRecyclerAdapter mAdapter;
    private View rootView;
    //当前页码，默认初始值为1，每页10条数据
    private int currentpage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Tag, "enter onCreateView...");
        rootView = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, rootView);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.toolbarColor));
//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//            }
//        });
        initData();
        return rootView;
    }

    public void initData() {
        Log.i(Tag, "enter initData...");
        if (presenter == null) {
            presenter = new AllFragmentPresenterImpl(getActivity());
            presenter.attachView(this);
        }
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            // recyclerView.setItemAnimator(new SlideInLeftAnimator());
            // recyclerView.getItemAnimator().setMoveDuration(1000);
        }
        if (mAdapter == null) {
            mAdapter = new AllRecyclerAdapter(lists, getActivity());
            recyclerView.setAdapter(mAdapter);
        }
        presenter.loadData(1);
    }

    @Override
    public void loadData(Object object, int type) {
        Log.i(Tag, "enter loadData...");
        if (object != null) {
            Log.i(Tag, "enter object != null...");
            if (object instanceof SearchEntity) {
                SearchEntity entity = (SearchEntity) object;
                if (!entity.isError()) {
                    Log.i(Tag, "enter !entity.isError()...");
                    SearchResultEntity[] res = entity.getResults();
                    if (type == Constants.LOADING_TYPE_INIT)
                        lists.clear();
                    for (int i = 0; i < res.length; i++) {
                        Log.i(Tag, "enter lists.add(res[" + i + "])...");
                        Document document = Jsoup.parse(res[i].getReadability());
                        Elements es = document.getElementsByTag("img");
                        if (es.size() > 0) {
                            Log.i(Tag, "res[" + i + "].getReadability()的img url = " + es.get(0).absUrl("src"));
                            res[i].setPic(es.get(0).absUrl("src"));
                        }
                        String date = res[i].getPublishedAt().split("T")[0];
                        String time = res[i].getPublishedAt().split("T")[1];
                        time = time.substring(0, 8);
                        res[i].setPublishedAt(date + " " + time);
                        lists.add(res[i]);
                    }
                    mAdapter.notifyDataSetChanged();
                    hideLoading();
                } else {
                    showError(entity.getMsg());
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void startLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        hideLoading();
//        super.showError(msg, onClickListener);
    }
}
