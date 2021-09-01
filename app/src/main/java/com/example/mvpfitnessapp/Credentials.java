package com.example.mvpfitnessapp;

public class Credentials {

    private String  Username ;
     private  String  Userpassword;

    Credentials(String username, String userpassword){
        this.Username = username;
        this.Userpassword = userpassword;}

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserpassword() {
        return Userpassword;
    }

    public void setUserpassword(String userpassword) {
        Userpassword = userpassword;
    }


     }

