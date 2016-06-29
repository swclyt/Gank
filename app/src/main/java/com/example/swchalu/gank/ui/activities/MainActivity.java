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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        AllFragment allFragment = new AllFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(allFragment);
        titleList = new ArrayList<>();
        titleList.add("全部");
        mAdapter = new TabPagerAdapter(this.getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);
    }

}
