package com.example.swchalu.gank.entities;

/**
 * Created by swchalu on 2016/6/28.
 */
public class Entity {
    private boolean error;
    private int count;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
}
