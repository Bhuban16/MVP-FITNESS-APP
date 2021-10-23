package com.example.mvpfitnessapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ViewWeight extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;
    private WeightAdapter mAdapter;
    String weight;
    private ProgressBar mProgressCircle;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    private List<UploadWeight> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_weight);

        mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Weight Track").child(firebaseAuth.getUid());
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<UploadWeight> options =
                new FirebaseRecyclerOptions.Builder<UploadWeight>()
                        .setQuery(mDatabaseRef,UploadWeight.class)
                        .build();

        FirebaseRecyclerAdapter<UploadWeight, WeightAdapter> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UploadWeight, WeightAdapter>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull WeightAdapter holder, int position, @NonNull UploadWeight model) {
                        holder.setExoPlayer(getApplication(),model.getWeightDate(),model.getTrackWeight());
                       final  DatabaseReference itemRef = getRef(position);
                       final  String myKey = itemRef.getKey();
                        holder.setOnClicklistener(new WeightAdapter.Clicklistener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String[] sortOptions = { "Delete"};
                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewWeight.this);
                              builder.setTitle("Action")
                                        .setItems(sortOptions, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                  if (which == 0){
                                                 mDatabaseRef.child(myKey).removeValue();

                                                }
                                            }
                                        });

                                builder.show();


                            }


                        });
                    }
                    @NonNull
                    @Override
                    public WeightAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.weight_item,parent, false);
                        return new WeightAdapter(view);
                    }



                };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}



