package com.example.mvpfitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CategoryVideoActivity extends AppCompatActivity implements CategoryVideoAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private CategoryVideoAdapter mAdapter;
    String name , url, Child ;


    private FirebaseStorage mStorage;
    private ValueEventListener mDBListener;
    private DatabaseReference mDatabaseRef;
    private List<UploadCategoryVideo> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_video);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        mUploads = new ArrayList<>();
        mAdapter = new CategoryVideoAdapter(CategoryVideoActivity.this, mUploads);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("video");
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CategoryVideoActivity.this);
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
                Toast.makeText(CategoryVideoActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onItemClick(int position) {
       UploadCategoryVideo uploadCurrent = mUploads.get(position);
        name = uploadCurrent.getmTitle();
        url = uploadCurrent.getmBanner();
       Child = uploadCurrent.getmChild();
        Intent intent = new Intent(CategoryVideoActivity.this,ShowVideo.class);
        intent.putExtra("child",Child);
        intent.putExtra("nam1",name);
        intent.putExtra("ur1",url);
        startActivity(intent);

    }
    @Override
    public void onWhatEverClick(int position) {
        UploadCategoryVideo uploadCurrent = mUploads.get(position);

        name = uploadCurrent.getmTitle();
        Child = uploadCurrent.getmChild();
        url = uploadCurrent.getmBanner();
        Intent intent = new Intent(CategoryVideoActivity.this,CategoryVideoEdit.class);
        intent.putExtra("child",Child);
        intent.putExtra("nam1",name);
        intent.putExtra("ur1",url);
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryVideoActivity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this data");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mStorage =FirebaseStorage.getInstance();
                mDatabaseRef =FirebaseDatabase.getInstance().

                        getReference("video");

                UploadCategoryVideo selectedItem = mUploads.get(position);
                final String selectedKey = selectedItem.getKey();
                StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmBanner());
                imageRef.delete().


                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess (Void aVoid){
                                mDatabaseRef.child(selectedKey).removeValue();
                                Toast.makeText(CategoryVideoActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}