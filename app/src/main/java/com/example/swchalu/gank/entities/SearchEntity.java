package com.example.swchalu.gank.entities;

/**
 * Created by swchalu on 2016/6/23.
 */
public class SearchEntity extends Entity {
    private SearchResultEntity[] results;

    public SearchResultEntity[] getResults() {
        return results;
    }

    public void setResults(SearchResultEntity[] results) {
        this.results = results;
    }
}
