package com.example.mvpfitnessapp;

public class Member {

    private String name;
    private String Videourl;
    private String search;
    private String description;

    public Member() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideourl() {
        return Videourl;
    }

    public void setVideourl(String videourl) {
        Videourl = videourl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}