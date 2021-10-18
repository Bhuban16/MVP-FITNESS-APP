package com.example.mvpfitnessapp;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StepAdapter extends RecyclerView.ViewHolder {

    public StepAdapter(@NonNull View itemView){
        super(itemView);
    }
    public void setExoPlayer (Application application, String userDate , String userCalories , String userSteps , String userDistance){

        TextView textView = itemView.findViewById(R.id.date);
        TextView textView1 = itemView.findViewById(R.id.calories);
        TextView textView2 = itemView.findViewById(R.id.step);
        TextView textView3 = itemView.findViewById(R.id.distance);

        textView.setText("Date : "+userDate);
        textView1.setText("Calories : "+userCalories);
        textView2.setText("Step : "+userSteps);
        textView3.setText("Distance : "+userDistance);

    }
}
