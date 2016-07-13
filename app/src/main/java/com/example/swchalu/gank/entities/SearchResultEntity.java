package com.example.swchalu.gank.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by swchalu on 2016/6/23.
 */
public class SearchResultEntity implements Parcelable {
    public static final Creator<SearchResultEntity> CREATOR = new Creator<SearchResultEntity>() {
        @Override
        public SearchResultEntity createFromParcel(Parcel in) {
            return new SearchResultEntity(in);
        }

        @Override
        public SearchResultEntity[] newArray(int size) {
            return new SearchResultEntity[size];
        }
    };
    private String desc;
    private String publishedAt;
    private String readability;
    private String type;
    private String url;
    private String who;
    private String pic;

    protected SearchResultEntity(Parcel in) {
        desc = in.readString();
        publishedAt = in.readString();
        readability = in.readString();
        type = in.readString();
        url = in.readString();
        who = in.readString();
        pic = in.readString();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getReadability() {
        return readability;
    }

    public void setReadability(String readability) {
        this.readability = readability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(desc);
        parcel.writeString(publishedAt);
        parcel.writeString(readability);
        parcel.writeString(type);
        parcel.writeString(url);
        parcel.writeString(who);
        parcel.writeString(pic);
    }
}
