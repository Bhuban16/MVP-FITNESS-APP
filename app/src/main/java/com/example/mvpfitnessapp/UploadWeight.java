package com.example.mvpfitnessapp;

import com.google.firebase.database.Exclude;

public class UploadWeight {
    private String weightDate;
    private String trackWeight;
    private String mKey;

    public UploadWeight() {
    }

    public String getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(String weightDate) {
        this.weightDate = weightDate;
    }

    public String getTrackWeight() {
        return trackWeight;
    }

    public void setTrackWeight(String trackWeight) {
        this.trackWeight = trackWeight;
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
