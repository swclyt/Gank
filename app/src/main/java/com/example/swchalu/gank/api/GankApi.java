package com.example.swchalu.gank.api;

import com.example.swchalu.gank.entities.SearchEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by swchalu on 2016/6/23.
 */
public interface GankApi {

    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Observable<SearchEntity> getSearch(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
