package com.example.mvpfitnessapp;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeightAdapter extends RecyclerView.ViewHolder {

    public WeightAdapter(@NonNull View itemView){
        super(itemView);
    }
    public void setExoPlayer (Application application, String weightDate , String trackWeight ){

        TextView text = itemView.findViewById(R.id.date21);
        TextView text2 = itemView.findViewById(R.id.weight12);


        text.setText("Date : "+weightDate);
        text2.setText("Weight : "+trackWeight);


    }
}
