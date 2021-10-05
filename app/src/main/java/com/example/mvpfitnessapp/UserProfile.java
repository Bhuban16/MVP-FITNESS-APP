package com.example.mvpfitnessapp;

public class UserProfile  {

    public String userPhoneNumber;
    public String userEmail;
    public String userName;
    public String userAge;
    public String userHeight;
    public String userGender;
    public String userWeight;



    public UserProfile() {
    }

    public  UserProfile (String userEmail, String userName, String userPhoneNumber,String userAge, String userGender ,String userWeight, String userHeight ) {
             this.userPhoneNumber = userPhoneNumber;
             this.userEmail = userEmail;
             this.userName = userName;
             this.userGender= userGender;
             this.userAge = userAge;
             this.userHeight = userHeight;
             this.userWeight = userWeight;
         }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }


    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }


    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}