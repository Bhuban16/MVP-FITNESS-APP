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

public class NutrientAdmin extends AppCompatActivity {



    private Button mButtonChoose , mButtonChoose2, mButtonChoose3;
    private Button mButtonUpload , mTextViewUploads;
    private EditText mEditText , mEditText2 , mEditText3 ;
    private EditText Desc , Desc2 , Desc3;
    private ImageView mImageView , mImageView2 , mImageView3;
    private ProgressBar mProgressbar;
    private Uri mImageUri , mImageUri2, mImageUri3;


    private StorageTask mUploadTask;
    private StorageTask mUploadTask1;
    private StorageTask mUploadTask2;

    private StorageReference mStoragerefernce;
    private DatabaseReference mDatabaserefernce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_admin);
        mButtonChoose2 = findViewById(R.id.Choose_image_btn2);
        mButtonChoose3 = findViewById(R.id.Choose_image_btn3);
        mButtonChoose = findViewById(R.id.Choose_image_btn);
        mButtonUpload = findViewById(R.id.upload_image_btn);
        mEditText = findViewById(R.id.Image_file_name);
        mEditText2 = findViewById(R.id.Image_file_name2);
        mEditText3 = findViewById(R.id.Image_file_name3);
        mImageView = findViewById(R.id.image_view);
        mImageView2 = findViewById(R.id.image_view2);
        mImageView3 = findViewById(R.id.image_view3);
        Desc = findViewById(R.id.desc);
        Desc2 = findViewById(R.id.desc2);
        Desc3 = findViewById(R.id.desc3);
        mProgressbar = findViewById(R.id.progress_bar);
        mImageView = findViewById(R.id.image_view);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("nam1");
        mTextViewUploads= findViewById(R.id.text_view_show_uploads);
        mStoragerefernce = FirebaseStorage.getInstance().getReference("Images");
        mDatabaserefernce = FirebaseDatabase.getInstance().getReference("Image").child(title).child("Menu");

        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent,1);

            }
        });
        mButtonChoose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");

                startActivityForResult(in,2);
            }
        });

        mButtonChoose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(Intent.ACTION_GET_CONTENT);
                int1.setType("image/*");

                startActivityForResult(int1,3);

            }
        });


        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFile();
                mProgressbar.setVisibility(View.VISIBLE);
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

                mImageView.setImageURI(mImageUri);

            }

            if (requestCode == 2

            ) {
                 mImageUri2 = data.getData();


                mImageView2.setImageURI(mImageUri2);

            }
            if  (requestCode == 3

            ) {

                mImageUri3 = data.getData();

                mImageView3.setImageURI(mImageUri3);

            }
        }


    }



    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    private void openImageActivity(){
        Intent intent = new Intent(this,ImagesActivity.class);
        startActivity(intent);

    }
     private void UploadFile(){
        if (mImageUri != null || mImageUri2 !=null|| mImageUri3!=null || mEditText !=null || mEditText2 != null || mEditText3 !=null || Desc != null || Desc2 != null || Desc3 !=null ) {
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri)  );
            StorageReference filerefernce2 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri2));
            StorageReference filerefernce3 = mStoragerefernce.child(System.currentTimeMillis() + "."+ getFileExt(mImageUri3));
            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl = urlTask.getResult();

                                    mUploadTask1 =
                                            filerefernce2.putFile(mImageUri2)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            Toast.makeText(NutrientAdmin.this, "Upload Successful 2", Toast.LENGTH_LONG).show();
                                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                            while (!urlTask.isSuccessful()) ;

                                                            Uri downloadUrl2 = urlTask.getResult();

                                                            mUploadTask2 =
                                                                    filerefernce3.putFile(mImageUri3)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                                    Toast.makeText(NutrientAdmin.this, "Upload Successful 3", Toast.LENGTH_LONG).show();
                                                                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                    while (!urlTask.isSuccessful()) ;

                                                                                    Uri downloadUrl3 = urlTask.getResult();


                                    UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(), downloadUrl.toString(),mEditText2.getText().toString().trim(), downloadUrl2.toString(),mEditText3.getText().toString().trim(),downloadUrl3.toString(),Desc.getText().toString().trim(),Desc2.getText().toString().trim(),Desc3.getText().toString().trim());
                                    String uploadId = mDatabaserefernce.push().getKey();
                                    mDatabaserefernce.child(uploadId).setValue(upload);
                                                        }
                                                    });
                                                        }
                                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(NutrientAdmin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

        }
        else  {
            Toast.makeText(NutrientAdmin.this,"Please fill all the details", Toast.LENGTH_SHORT).show();
        }
     }
}