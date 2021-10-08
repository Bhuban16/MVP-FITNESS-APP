package com.example.mvpfitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NutrientUser extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_user);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference = firebaseDatabase.getReference("Image");


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MemberNutrient> options =
                new FirebaseRecyclerOptions.Builder<MemberNutrient>()
                        .setQuery(reference,MemberNutrient.class)
                        .build();
        FirebaseRecyclerAdapter<MemberNutrient,ViewHolderNutrient>firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<MemberNutrient, ViewHolderNutrient>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolderNutrient holder, int position, @NonNull MemberNutrient model) {
                        holder.setdetails(getApplicationContext(), model.getMName(), model.getMlmageuri());


                    }

                    @NonNull
                    @Override
                    public ViewHolderNutrient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.image,parent, false);


                        return new ViewHolderNutrient(view);
                    }

                };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}




