package com.example.mvpfitnessapp

import com.google.firebase.database.Exclude

class UploadStep {
  private  var userSteps: String? = null
  private  var userCalories: String? = null
   private var userDistance: String? = null
    private var userDate: String? = null


    fun UploadStep() {}


    fun getUserDate(): String? {
        return userDate
    }

    fun setUserDate(userDate: String) {
        this.userDate = userDate
    }


    fun getUserCalories(): String? {
        return userCalories
    }

    fun setUserCalories(userCalories: String) {
        this.userCalories = userCalories
    }
    fun getUserDistance(): String? {
        return userDistance
    }

    fun setUserDistance(userDistance: String) {
        this.userDistance = userDistance
    }

    fun getUserSteps(): String? {
        return userSteps
    }

    fun setUserSteps(userSteps: String) {
        this.userSteps = userSteps
    }




}