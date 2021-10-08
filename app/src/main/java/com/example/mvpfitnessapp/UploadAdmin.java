package com.example.mvpfitnessapp;

public class UploadAdmin {
    private String mName;
    private String mImageuri;

    public UploadAdmin(){}

    public UploadAdmin(String name,String imageuri){
        if (name.trim().equals("")){
            name = "no name";
        }
        mName = name;
        mImageuri = imageuri;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageuri() {
        return mImageuri;
    }

    public void setmImageuri(String mImageuri) {
        this.mImageuri = mImageuri;
    }
}
