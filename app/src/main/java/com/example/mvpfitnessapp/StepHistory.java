package com.example.mvpfitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StepHistory extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;

    private ProgressBar mProgressCircle;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_history);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User Step Record").child(firebaseAuth.getUid());
    }
        @Override
        protected void onStart() {
            super.onStart();

            FirebaseRecyclerOptions<UploadStep> options =
                    new FirebaseRecyclerOptions.Builder<UploadStep>()
                            .setQuery(mDatabaseRef,UploadStep.class)
                            .build();

            FirebaseRecyclerAdapter<UploadStep, StepAdapter> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<UploadStep, StepAdapter>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull StepAdapter holder, int position, @NonNull UploadStep model) {

                            holder.setExoPlayer(getApplication(),model.getUserDate(),model.getUserCalories(), model.getUserSteps(), model.getUserDistance());

                        }

                        @NonNull
                        @Override
                        public StepAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.step_item,parent, false);
                            return new StepAdapter(view);
                        }



                    };
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }


    }
