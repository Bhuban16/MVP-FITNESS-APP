package com.example.mvpfitnessapp;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class workoutAdapter extends RecyclerView.ViewHolder{

    public workoutAdapter(@NonNull View itemView){
        super(itemView);
    }
    public void setExoPlayer (Application application, String workoutName, String workoutDate, String timeStart , String timeEnd  ){

        TextView textView = itemView.findViewById(R.id.date7);
        TextView textView1 = itemView.findViewById(R.id.name7);
        TextView textView2 = itemView.findViewById(R.id.time7);


        textView.setText("Date : "+ workoutDate);
        textView1.setText("Workout Name : "+ workoutName);
        textView2.setText("Time Started at : "+timeStart + "   " +"End at : " + timeEnd);



    }
}
