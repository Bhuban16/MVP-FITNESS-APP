package com.example.mvpfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class userImagesActivity extends AppCompatActivity implements userImageAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private userImageAdapter mAdapter;
    String name , url , desc;
    String name2 , url2 , desc2;
    String name3 , url3 , desc3;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;
    private List<UploadAdmin> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_images);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mAdapter = new userImageAdapter(userImagesActivity.this, mUploads);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Image");
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(userImagesActivity.this);
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadAdmin upload = postSnapshot.getValue(UploadAdmin.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(userImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void onItemClick(int position) {
        UploadAdmin uploadCurrent = mUploads.get(position);
        name = uploadCurrent.getmName();
        url = uploadCurrent.getmImageuri();
        desc = uploadCurrent.getmDesc();
        name2 = uploadCurrent.getmName2();
        url2 = uploadCurrent.getmImageuri2();
        desc2 = uploadCurrent.getmDesc2();
        name3 = uploadCurrent.getmName3();
        url3 = uploadCurrent.getmImageuri3();
        desc3 = uploadCurrent.getmDesc3();
        Intent intent = new Intent(userImagesActivity.this,NutrientFull.class);
        intent.putExtra("nam1",name);
        intent.putExtra("ur1",url);
        intent.putExtra("nam2",name2);
        intent.putExtra("ur2",url2);
        intent.putExtra("nam3",name3);
        intent.putExtra("ur3",url3);
        intent.putExtra("desc1",desc);
        intent.putExtra("desc2",desc2);
        intent.putExtra("desc3",desc3);
        startActivity(intent);

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }


}