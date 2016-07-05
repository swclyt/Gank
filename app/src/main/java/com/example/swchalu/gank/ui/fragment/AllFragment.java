package com.example.swchalu.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    @Bind(R.id.fab)
    FloatingActionButton fab;
    List<SearchResultEntity> lists = new ArrayList<SearchResultEntity>();
    private RecyclerView.LayoutManager mLayoutManager;
    private AllFragmentPresenterImpl presenter = null;
    private AllRecyclerAdapter mAdapter;
    private View rootView;
    //当前页码，默认初始值为1，每页10条数据
    private int currentpage = 1;
    private boolean FLAG_CAN_LOAD = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(Tag, "enter onCreateView...");
        rootView = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, rootView);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.getItemCount() > 0) {
                    recyclerView.smoothScrollToPosition(0);
                    fab.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isSlideToBottom(recyclerView) && newState == RecyclerView.SCROLL_STATE_IDLE && FLAG_CAN_LOAD) {
                    loadMore(currentpage);
                }
                if (((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() == 0) {
//                    Toast.makeText(rootView.getContext(), "第一行显示...", Toast.LENGTH_SHORT).show();
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
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
        if (FLAG_CAN_LOAD) {
            FLAG_CAN_LOAD = false;
            presenter.loadData(1);
        }
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
                    if (type == Constants.LOADING_TYPE_INIT) {
                        lists.clear();
                    }
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
                    if (type == Constants.LOADING_TYPE_INIT)
                        currentpage = 1;
                    else
                        currentpage++;
                    hideLoading();
                } else {
                    showError(entity.getMsg());
                }
            }
        }
    }

    public void loadMore(int page) {
        Log.i(Tag, "enter loadMore...page = " + page);
        if (presenter == null) {
            presenter = new AllFragmentPresenterImpl(getActivity());
            presenter.attachView(this);
        }
        int nextpage = page + 1;

        if (FLAG_CAN_LOAD) {
            FLAG_CAN_LOAD = false;
            presenter.loadData(nextpage);
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void startLoading(int type) {
        if (type == Constants.LOADING_TYPE_INIT)
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
        else
            Snackbar.make(fab, "loading...", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
        FLAG_CAN_LOAD = true;
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        hideLoading();
        FLAG_CAN_LOAD = true;
//        super.showError(msg, onClickListener);
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
