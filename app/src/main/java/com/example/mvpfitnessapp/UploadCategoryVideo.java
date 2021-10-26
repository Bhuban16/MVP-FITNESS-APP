package com.example.mvpfitnessapp;

import com.google.firebase.database.Exclude;

public class UploadCategoryVideo {
    private String mTitle;
    private String mBanner;
    private String mChild;
    private String mKey;

    public UploadCategoryVideo(){ }

    public UploadCategoryVideo(String title,String banner , String child){

        mTitle =title;
        mBanner = banner;
        mChild = child;
    }
    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmChild() {
        return mChild;
    }

    public void setmChild(String mChild) {
        this.mChild = mChild;
    }
    public String getmBanner() {
        return mBanner;
    }

    public void setmBanner(String mBanner) {
        this.mBanner = mBanner;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
