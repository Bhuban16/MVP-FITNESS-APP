package com.example.mvpfitnessapp;

public class UploadWorkoutHistory {

   private String timeStart;
   private String timeEnd;
   private String workoutDate;
   private String workoutName;

    public  UploadWorkoutHistory() {}

    public String getWorkoutName() {
        return workoutName;
    }
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
    public String getWorkoutDate() {
        return workoutDate;
    }
    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }




}

