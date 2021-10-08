package com.example.mvpfitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class NutrientAdmin extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChoose;
    private Button mButtonUpload;
    private EditText mEditText;
    private ImageView mImageView;
    private ProgressBar mProgressbar;

    private Uri mImageUri;

    private StorageReference mStoragerefernce;
    private DatabaseReference mDatabaserefernce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_admin);

        mButtonChoose = findViewById(R.id.Choose_image_btn);
        mButtonUpload = findViewById(R.id.upload_image_btn);
        mEditText = findViewById(R.id.Image_file_name);
        mProgressbar = findViewById(R.id.progress_bar);
        mImageView = findViewById(R.id.image_view);

        mStoragerefernce = FirebaseStorage.getInstance().getReference("Images");
        mDatabaserefernce = FirebaseDatabase.getInstance().getReference("Image");

        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImagechooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFile();
                mProgressbar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void openImagechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST || resultCode == RESULT_OK
        || data != null || data.getData() != null
        ){
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);

        }
    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
     private void UploadFile(){
        if (mImageUri != null){
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
            +"."+getFileExt(mImageUri));

            filerefernce.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(NutrientAdmin.this,"Upload Successful",Toast.LENGTH_LONG).show();
                            UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(NutrientAdmin.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

        }else {
            Toast.makeText(NutrientAdmin.this,"No file Selected", Toast.LENGTH_SHORT).show();
        }
     }
}