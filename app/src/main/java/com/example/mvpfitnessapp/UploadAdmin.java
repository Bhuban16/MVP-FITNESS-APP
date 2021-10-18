package com.example.mvpfitnessapp;

import com.google.firebase.database.Exclude;

public class UploadAdmin {
    private String mName;
    private String mImageuri;
    private String mImageuri2;
    private String mImageuri3;
    private String mName2;
    private String mName3;
    private String mDesc , mDesc2 , mDesc3;
    private String mKey;

    public UploadAdmin(){}

    public UploadAdmin(String name,String imageuri,String name2,String imageuri2,String name3,String imageuri3 , String mdesc , String mdesc2 , String mdesc3){

        mName = name;
        mName2 = name2;
        mName3 = name3;
        mImageuri = imageuri;
        mImageuri2 = imageuri2;
        mImageuri3 = imageuri3;

        mDesc= mdesc;
        mDesc2 = mdesc2;
        mDesc3=mdesc3;


    }




    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmName2() {
        return mName2;
    }

    public void setmName2(String mName2) {
        this.mName2 = mName2;
    }

    public String getmName3() {
        return mName3;
    }

    public void setmName3(String mName3) {
        this.mName3 = mName3;
    }

    public String getmImageuri() {
        return mImageuri;
    }

    public void setmImageuri(String mImageuri) {
        this.mImageuri = mImageuri;
    }


    public String getmImageuri2() {
        return mImageuri2;
    }

    public void setmImageuri2(String mImageuri2) {
        this.mImageuri2 = mImageuri2;
    }

public String getmImageuri3() {
    return mImageuri3;
}

    public void setmImageuri3(String mImageuri3) {
        this.mImageuri3 = mImageuri3;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmDesc2() {
        return mDesc2;
    }

    public void setmDesc2(String mDesc2) {
        this.mDesc2 = mDesc2;
    }

    public String getmDesc3() {
        return mDesc3;
    }

    public void setmDesc3(String mDesc3) {
        this.mDesc3 = mDesc3;
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

