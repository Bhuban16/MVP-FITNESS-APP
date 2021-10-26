package com.example.mvpfitnessapp;

import android.content.Intent;
import android.os.Bundle;
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

public class userCategoryVideoActivity extends AppCompatActivity implements userCategoryVideoAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private userCategoryVideoAdapter mAdapter;
    String name , url, Child ;


    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;
    private List<UploadCategoryVideo> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category_video);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        mUploads = new ArrayList<>();
        mAdapter = new userCategoryVideoAdapter(userCategoryVideoActivity.this, mUploads);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("video");
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(userCategoryVideoActivity.this);
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadCategoryVideo upload = postSnapshot.getValue(UploadCategoryVideo.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(userCategoryVideoActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onItemClick(int position) {
        UploadCategoryVideo uploadCurrent = mUploads.get(position);
        name = uploadCurrent.getmTitle();
        url = uploadCurrent.getmBanner();
        String child = uploadCurrent.getmChild();
        Intent intent = new Intent(userCategoryVideoActivity.this,UserShowVideo.class);
        intent.putExtra("child",child);
        intent.putExtra("nam1",name);
        intent.putExtra("ur1",url);
        startActivity(intent);

    }
    @Override
    public void onWhatEverClick(int position) {
        UploadCategoryVideo uploadCurrent = mUploads.get(position);



    }

    @Override
    public void onDeleteClick(int position) {

    }
}