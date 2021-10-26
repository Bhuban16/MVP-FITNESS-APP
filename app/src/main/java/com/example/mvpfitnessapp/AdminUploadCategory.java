package com.example.mvpfitnessapp;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AdminUploadCategory extends AppCompatActivity {
    private Button mButtonChoose;
    private Button mButtonUpload , mTextViewUploads;
    private EditText mEditText  ;

    private ImageView mImageView ;
    private ProgressBar mProgressbar;
    private Uri mImageUri ;

    private StorageTask mUploadTask;
    private StorageReference mStoragerefernce;
    private DatabaseReference mDatabaserefernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload_category);
        mButtonChoose = findViewById(R.id.Choose_image_btn);
        mButtonUpload = findViewById(R.id.upload_image_btn);
        mEditText = findViewById(R.id.Image_file_name);
        mImageView = findViewById(R.id.image_view);

        mProgressbar = findViewById(R.id.progress_bar);
        mImageView = findViewById(R.id.image_view);
        mTextViewUploads= findViewById(R.id.text_view_show_uploads);
        mStoragerefernce = FirebaseStorage.getInstance().getReference("Video");
        mDatabaserefernce = FirebaseDatabase.getInstance().getReference("video");

        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent,1);

            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = mEditText.getText().toString();
                if(m.isEmpty() ) {
                    Toast.makeText(AdminUploadCategory.this,"Please type title", Toast.LENGTH_SHORT).show();
                }else if (mImageUri == null){
                    Toast.makeText(AdminUploadCategory.this, "Please choose image",Toast.LENGTH_SHORT).show();
                }else{UploadFile();}
            }
        });
        mTextViewUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageActivity();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 1 ) {
                mImageUri = data.getData();
                mImageView.setImageURI(mImageUri); }
        } }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    private void openImageActivity(){
        Intent intent = new Intent(this,CategoryVideoActivity.class);
        startActivity(intent);

    }
    private void UploadFile(){

        if (mImageUri != null && mEditText != null) {
            mProgressbar.setVisibility(View.VISIBLE);
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri));
            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(AdminUploadCategory.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl = urlTask.getResult();

                                    UploadCategoryVideo upload = new UploadCategoryVideo(mEditText.getText().toString().trim(), downloadUrl.toString(), mEditText.getText().toString().trim());
                                    String title = mEditText.getText().toString();
                                    mDatabaserefernce.child(title).setValue(upload);
                                    mProgressbar.setVisibility(View.GONE);
                                }
                            })


                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgressbar.setVisibility(View.GONE);
                                    Toast.makeText(AdminUploadCategory.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

        }
        else{
            mProgressbar.setVisibility(View.GONE);
            Toast.makeText(AdminUploadCategory.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        }
    }

}