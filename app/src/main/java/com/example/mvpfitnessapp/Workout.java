package com.example.mvpfitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class Workout extends AppCompatActivity {

    RecyclerView Mrecyclerview;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Mrecyclerview = findViewById(R.id.recyclerview_video);
        Mrecyclerview.setHasFixedSize(true);
        Mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference = database.getReference("videos");

    }

    //@Override
    protected void onStart() {
        super.onStart();

        //FirebaseRecyclerAdapter<User,ViewHolder> firebaseRecyclerAdapter=
                //new FirebaseRecyclerAdapter<User, ViewHolder>(
                       // User.class,
                       // R.layout.row,
                        //ViewHolder.class,
                        //reference
               // ) {
                    //@Override
                  //  protected void populateViewHolder(ViewHolder viewHolder, User user, int i) {
                   //     viewHolder.setVideo(getApplication(),user.getVideoName(),user.getVideoUri());
                    //}
                //};

       // Mrecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}