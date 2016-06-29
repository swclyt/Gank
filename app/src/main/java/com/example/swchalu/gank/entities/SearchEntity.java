package com.example.swchalu.gank.entities;

/**
 * Created by swchalu on 2016/6/23.
 */
public class SearchEntity extends Entity{
    private int count;
    private boolean error;
    private SearchResultEntity[] results;
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public SearchResultEntity[] getResults() {
        return results;
    }

    public void setResults(SearchResultEntity[] results) {
        this.results = results;
    }
}
