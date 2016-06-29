package com.example.swchalu.gank.api;

import com.example.swchalu.gank.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by swchalu on 2016/6/23.
 */
public class GankService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private GankService() {
    }

    public static <T> T createApi(Class<T> mClass) {
        return retrofit.create(mClass);
    }

}
