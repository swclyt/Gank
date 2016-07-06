package com.example.swchalu.gank.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.swchalu.gank.R;
import com.example.swchalu.gank.ui.adapter.TabPagerAdapter;
import com.example.swchalu.gank.ui.fragment.AllFragment;
import com.example.swchalu.gank.ui.fragment.FuliFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tablayout)
    TabLayout mTablayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    private TabPagerAdapter mAdapter;
    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gank");
        setSupportActionBar(toolbar);

        AllFragment allFragment = new AllFragment();
        FuliFragment fuliFragment = new FuliFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(allFragment);
        fragmentList.add(fuliFragment);
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("福利");
        mAdapter = new TabPagerAdapter(this.getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
