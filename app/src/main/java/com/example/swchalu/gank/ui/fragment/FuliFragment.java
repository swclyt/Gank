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
import com.example.swchalu.gank.presenter.FuliFragmentPresenterImpl;
import com.example.swchalu.gank.ui.adapter.FuliRecyclerAdapter;
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
    private FuliFragmentPresenterImpl presenter = null;
    private FuliRecyclerAdapter mAdapter;
    private int currentpage = 1;
    private boolean FLAG_CAN_LOAD = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(Tag, "enter onCreateView...");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fuli, container, false);
        }
        ButterKnife.bind(this, rootView);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.toolbarColor));
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.getItemCount() > 0) {
                    if (((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() > 10)
                        recyclerView.scrollToPosition(10);
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
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
        initView();
        if (savedInstanceState == null)
            initData();
        return rootView;
    }

    public void initView() {
        Log.i(Tag, "enter initView...");
        if (presenter == null) {
            presenter = new FuliFragmentPresenterImpl(getActivity());
            presenter.attachView(this);
        }
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            // recyclerView.setItemAnimator(new SlideInLeftAnimator());
            // recyclerView.getItemAnimator().setMoveDuration(1000);
        }
        if (mAdapter == null) {
            mAdapter = new FuliRecyclerAdapter(lists, getActivity());
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void initData() {
        Log.i(Tag, "enter initData...");
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
                        String date = res[i].getPublishedAt().split("T")[0];
                        String time = res[i].getPublishedAt().split("T")[1];
                        time = time.substring(0, 8);
                        res[i].setPublishedAt(date + " " + time);
                        lists.add(res[i]);
                    }
                    mAdapter.notifyDataSetChanged();
                    Log.i(Tag, "enter mAdapter.notifyDataSetChanged()...");
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
            presenter = new FuliFragmentPresenterImpl(getActivity());
            presenter.attachView(this);
        }
        int nextpage = page + 1;

        if (FLAG_CAN_LOAD) {
            FLAG_CAN_LOAD = false;
            presenter.loadData(nextpage);
        }
    }

    @Override
    public void startLoading(int type) {
        Log.i(Tag, "enter startLoading...");
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
        Log.i(Tag, "enter hideLoading...");
        refreshLayout.setRefreshing(false);
        FLAG_CAN_LOAD = true;
    }

    @Override
    public void showError(String msg) {
        Log.i(Tag, "enter showError...");
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        hideLoading();
        FLAG_CAN_LOAD = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(Tag, "onSaveInstanceState...outState = " + outState);
        super.onSaveInstanceState(outState);
        if (lists.size() > 0) {
            outState.putParcelableArrayList("lists", (ArrayList) lists);
            outState.putInt("position", ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.i(Tag, "onViewStateRestored...savedInstanceState = " + (savedInstanceState == null));
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            List<SearchResultEntity> savedList = savedInstanceState.getParcelableArrayList("lists");
            if (savedList.size() > 0) {
                lists.clear();
                for (SearchResultEntity entity : savedList) {
                    lists.add(entity);
                }
                Log.i(Tag, "onViewStateRestored...lists.size() = " + lists.size());
                mAdapter.notifyDataSetChanged();
                Log.i(Tag, "onViewStateRestored...notifyDataSetChanged()... mAdapter.getItemCount():" + mAdapter.getItemCount());
                recyclerView.scrollToPosition(savedInstanceState.getInt("position"));
                Log.i(Tag, "onViewStateRestored...position = " + savedInstanceState.getInt("position"));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        initData();
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
