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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CategoryEdit extends AppCompatActivity {

    private Button mButtonChoose;
    private Button mButtonUpload , mTextViewUploads;
    private EditText mEditText  ;
    String title , child;
    private ImageView mImageView ;
    private ProgressBar mProgressbar;
    private Uri mImageUri ;

    private StorageTask mUploadTask;
    private StorageReference mStoragerefernce;
    private DatabaseReference mDatabaserefernce;
    private Query query2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        mButtonChoose = findViewById(R.id.Choose_image_btn);
        mButtonUpload = findViewById(R.id.upload_image_btn);
        mEditText = findViewById(R.id.Image_file_name);
        mImageView = findViewById(R.id.image_view);
        Intent intent = getIntent();
         title = intent.getExtras().getString("nam1");
        String url = intent.getExtras().getString("ur1");
        child = intent.getExtras().getString("child");
        mProgressbar = findViewById(R.id.progress_bar);

       mEditText.setText(title);
        Picasso.get().load(url).fit().into(mImageView);
        mStoragerefernce = FirebaseStorage.getInstance().getReference("Images");
        mDatabaserefernce = FirebaseDatabase.getInstance().getReference("Image");
        query2 = mDatabaserefernce.orderByChild("mChild").equalTo(child);



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
                UploadFile();
                mProgressbar.setVisibility(View.VISIBLE);
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
    private void UploadFile(){
        if (mImageUri != null ) {
            DatabaseReference Databaserefernce = FirebaseDatabase.getInstance().getReference("Image");
            Query query = Databaserefernce.orderByChild("mChild").equalTo(child);
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri)  );
            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(CategoryEdit.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl = urlTask.getResult();
                                   query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                ds.getRef().child("mBanner").setValue(downloadUrl.toString());
                                                String Title = mEditText.getText().toString();
                                                ds.getRef().child("mTitle").setValue(Title);


                                            }
                                            Toast.makeText(CategoryEdit.this, "Data saved", Toast.LENGTH_SHORT).show();
                                            mProgressbar.setVisibility(View.INVISIBLE);
                                            startActivity(new Intent(CategoryEdit.this,CategoryActivities.class ));
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            })




                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgressbar.setVisibility(View.GONE);
                                    Toast.makeText(CategoryEdit.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

        }
        else  {

           DatabaseReference Databaserefernce = FirebaseDatabase.getInstance().getReference("Image");
            Query query = Databaserefernce.orderByChild("mChild").equalTo(child);
          query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                       String Title = mEditText.getText().toString();
                        ds.getRef().child("mTitle").setValue(Title);


                    }
                    Toast.makeText(CategoryEdit.this, "Data saved", Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(CategoryEdit.this,CategoryActivities.class ));
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mProgressbar.setVisibility(View.GONE);
        }
    }
}