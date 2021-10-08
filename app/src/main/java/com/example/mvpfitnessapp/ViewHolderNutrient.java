package com.example.mvpfitnessapp;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolderNutrient extends RecyclerView.ViewHolder {

    View view;

    public ViewHolderNutrient(@NonNull View itemView) {
        super(itemView);

    view = itemView;

        }


    public void setdetails(Context context,String mName,String mlmageuri){

        TextView mtitletv = view.findViewById(R.id.rTitleView);
        ImageView mImagetv = view.findViewById(R.id.rImageView);

        mtitletv.setText(mName);
        Picasso.get().load(mlmageuri).into(mImagetv);

    }

}
