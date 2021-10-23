package com.example.mvpfitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
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
        setContentView(R.layout.activity_images);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Image");
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ImagesActivity.this);
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
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(ImagesActivity.this,NutrientFull.class);
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
            Intent intent = new Intent(ImagesActivity.this,NutrientFullAdmin.class);
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
        public void onDeleteClick(int position) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ImagesActivity.this);
            builder.setTitle("Delete");
            builder.setMessage("Are you sure to delete this data");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mStorage =FirebaseStorage.getInstance();
                    mDatabaseRef =FirebaseDatabase.getInstance().

                            getReference("Image");

                    UploadAdmin selectedItem = mUploads.get(position);
                    final String selectedKey = selectedItem.getKey();
                    StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageuri());
                    imageRef.delete().


                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess (Void aVoid){
                                    mDatabaseRef.child(selectedKey).removeValue();
                                    Toast.makeText(ImagesActivity.this, "item deleted", Toast.LENGTH_SHORT).show();
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

