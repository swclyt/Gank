package com.example.swchalu.gank;

import android.app.Application;

import com.example.swchalu.gank.utils.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by swchalu on 2016/6/29.
 */
public class App extends Application {
    public static Picasso mPicasso;
    private static App instace;

    public static App getInstace() {
        return instace;
    }

    public static Picasso getPicasso() {
        return mPicasso;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instace = this;
        setUpPicasso();
    }

    public void setUpPicasso() {
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(new OkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);
        mPicasso = picasso;
    }
}
