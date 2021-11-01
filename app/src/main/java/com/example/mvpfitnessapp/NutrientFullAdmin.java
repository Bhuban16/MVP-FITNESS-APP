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

public class NutrientFullAdmin extends AppCompatActivity {
    private Button mButtonChoose, mButtonChoose2, mButtonChoose3;
    private Button mButtonUpload;
    private EditText mEditText, mEditText2, mEditText3;
    private EditText Desc, Desc2, Desc3;
    private ImageView mImageView, mImageView2, mImageView3;
    private ProgressBar mProgressbar;
    private Uri mImageUri, mImageUri2, mImageUri3, mImage, mImage2, mImage3, mImageN;
    Uri image, image2, image3;
    private String urlI, urlI2, urlI3 , child;
    private StorageTask mUploadTask;
    private StorageTask mUploadTask1;
    private StorageTask mUploadTask2;
    String title;


    private StorageReference mStoragerefernce;
    private DatabaseReference mDatabaserefernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_full_admin);
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
        urlI = intent.getExtras().getString("ur1");
        urlI2 = intent.getExtras().getString("ur2");
        urlI3 = intent.getExtras().getString("ur3");
        mImage = Uri.parse(urlI);
        mImage2 = Uri.parse(urlI2);
        mImage3 = Uri.parse(urlI3);
        String description = intent.getExtras().getString("desc1");
        String description2 = intent.getExtras().getString("desc2");
        String description3 = intent.getExtras().getString("desc3");

        title = intent.getExtras().getString("nam1");
        String title2 = intent.getExtras().getString("nam2");
        String title3 = intent.getExtras().getString("nam3");
        child = intent.getExtras().getString("child");
        Desc.setText(description);
        Desc2.setText(description2);
        Desc3.setText(description3);

        mEditText.setText(title);
        mEditText2.setText(title2);
        mEditText3.setText(title3);


        Picasso.get().load(mImage2).fit().into(mImageView2);
        Picasso.get().load(mImage3).fit().into(mImageView3);

        mStoragerefernce = FirebaseStorage.getInstance().getReference("Images");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
        Query query2 = databaseReference.orderByChild("mName").equalTo(title);

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String value = snapshot1.child("mImageuri").getValue().toString();
                    Picasso.get().load(value).fit().into(mImageView);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, 1);

            }
        });
        mButtonChoose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");

                startActivityForResult(in, 2);
            }
        });

        mButtonChoose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(Intent.ACTION_GET_CONTENT);
                int1.setType("image/*");

                startActivityForResult(int1, 3);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                mImageUri = data.getData();

                mImageView.setImageURI(mImageUri);

            }

            if (requestCode == 2) {
                mImageUri2 = data.getData();


                mImageView2.setImageURI(mImageUri2);

            }
            if (requestCode == 3) {

                mImageUri3 = data.getData();

                mImageView3.setImageURI(mImageUri3);

            }
        }


    }


    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void UploadFile() {
        String name = mEditText.getText().toString();
        String name2 = mEditText2.getText().toString();
        String name3 = mEditText3.getText().toString();
        String desc = Desc.getText().toString();
        String desc2 = Desc2.getText().toString();
        String desc3 = Desc3.getText().toString();


        if (mImageUri != null && mImageUri2 != null && mImageUri3 != null) {
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri));
            StorageReference filerefernce2 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri2));
            StorageReference filerefernce3 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri3));

            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
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

                                                            Toast.makeText(NutrientFullAdmin.this, "Upload Successful 2", Toast.LENGTH_LONG).show();
                                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                            while (!urlTask.isSuccessful()) ;

                                                            Uri downloadUrl2 = urlTask.getResult();

                                                            mUploadTask2 =
                                                                    filerefernce3.putFile(mImageUri3)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful 3", Toast.LENGTH_LONG).show();
                                                                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                    while (!urlTask.isSuccessful())
                                                                                        ;

                                                                                    Uri downloadUrl3 = urlTask.getResult();
                                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                                                                    Query query = databaseReference.orderByChild("mName").equalTo(title);

                                                                                    String url = downloadUrl.toString();
                                                                                    String url2 = downloadUrl2.toString();
                                                                                    String url3 = downloadUrl3.toString();

                                                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                                                ds.getRef().child("mName").setValue(name);
                                                                                                ds.getRef().child("mName2").setValue(name2);
                                                                                                ds.getRef().child("mName3").setValue(name3);
                                                                                                ds.getRef().child("mDesc").setValue(desc);
                                                                                                ds.getRef().child("mDesc2").setValue(desc2);
                                                                                                ds.getRef().child("mDesc3").setValue(desc3);
                                                                                                ds.getRef().child("mImageuri").setValue(url);
                                                                                                ds.getRef().child("mImageuri2").setValue(url2);
                                                                                                ds.getRef().child("mImageuri3").setValue(url3);
                                                                                            }
                                                                                            Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                                                            mProgressbar.setVisibility(View.INVISIBLE);
                                                                                            Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                                                                            intent.putExtra("child",child);
                                                                                            startActivity(intent);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                                        }
                                                                                    });


                                                                                }
                                                                            });
                                                        }
                                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(NutrientFullAdmin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

        }

        else if (mImageUri != null && mImageUri2 != null) {
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri));
            StorageReference filerefernce2 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri2));
            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
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

                                                            Toast.makeText(NutrientFullAdmin.this, "Upload Successful 2", Toast.LENGTH_LONG).show();
                                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                            while (!urlTask.isSuccessful()) ;

                                                            Uri downloadUrl2 = urlTask.getResult();

                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                                            Query query = databaseReference.orderByChild("mName").equalTo(title);

                                                            String url = downloadUrl.toString();
                                                            String url2 = downloadUrl2.toString();


                                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                                        ds.getRef().child("mName").setValue(name);
                                                                        ds.getRef().child("mName2").setValue(name2);
                                                                        ds.getRef().child("mName3").setValue(name3);
                                                                        ds.getRef().child("mDesc").setValue(desc);
                                                                        ds.getRef().child("mDesc2").setValue(desc2);
                                                                        ds.getRef().child("mDesc3").setValue(desc3);
                                                                        ds.getRef().child("mImageuri").setValue(url);
                                                                        ds.getRef().child("mImageuri2").setValue(url2);

                                                                    }
                                                                    Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                                    mProgressbar.setVisibility(View.INVISIBLE);
                                                                    Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                                                    intent.putExtra("child",child);
                                                                    startActivity(intent);
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });


                                                        }
                                                    });
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgressbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(NutrientFullAdmin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

        }
        else if(mImageUri != null && mImageUri3 != null){
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri));

            StorageReference filerefernce3 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri3));
            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl = urlTask.getResult();

                                                            mUploadTask2 =
                                                                    filerefernce3.putFile(mImageUri3)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful 3", Toast.LENGTH_LONG).show();
                                                                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                    while (!urlTask.isSuccessful())
                                                                                        ;

                                                                                    Uri downloadUrl3 = urlTask.getResult();
                                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                                                                    Query query = databaseReference.orderByChild("mName").equalTo(title);

                                                                                    String url = downloadUrl.toString();

                                                                                    String url3 = downloadUrl3.toString();

                                                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                                                ds.getRef().child("mName").setValue(name);
                                                                                                ds.getRef().child("mName2").setValue(name2);
                                                                                                ds.getRef().child("mName3").setValue(name3);
                                                                                                ds.getRef().child("mDesc").setValue(desc);
                                                                                                ds.getRef().child("mDesc2").setValue(desc2);
                                                                                                ds.getRef().child("mDesc3").setValue(desc3);
                                                                                                ds.getRef().child("mImageuri").setValue(url);

                                                                                                ds.getRef().child("mImageuri3").setValue(url3);
                                                                                            }
                                                                                            Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                                                            mProgressbar.setVisibility(View.INVISIBLE);
                                                                                            Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                                                                            intent.putExtra("child",child);
                                                                                            startActivity(intent);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                                        }
                                                                                    });


                                                                                }
                                                                            });
                                                        }
                                                    });

        }

        else if(mImageUri2 != null && mImageUri3 != null){

            StorageReference filerefernce2 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri2));
            StorageReference filerefernce3 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri3));
                                    mUploadTask1 =
                                            filerefernce2.putFile(mImageUri2)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            Toast.makeText(NutrientFullAdmin.this, "Upload Successful 2", Toast.LENGTH_LONG).show();
                                                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                            while (!urlTask.isSuccessful()) ;

                                                            Uri downloadUrl2 = urlTask.getResult();

                                                            mUploadTask2 =
                                                                    filerefernce3.putFile(mImageUri3)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful 3", Toast.LENGTH_LONG).show();
                                                                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                    while (!urlTask.isSuccessful())
                                                                                        ;

                                                                                    Uri downloadUrl3 = urlTask.getResult();
                                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                                                                    Query query = databaseReference.orderByChild("mName").equalTo(title);


                                                                                    String url2 = downloadUrl2.toString();
                                                                                    String url3 = downloadUrl3.toString();

                                                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                                                ds.getRef().child("mName").setValue(name);
                                                                                                ds.getRef().child("mName2").setValue(name2);
                                                                                                ds.getRef().child("mName3").setValue(name3);
                                                                                                ds.getRef().child("mDesc").setValue(desc);
                                                                                                ds.getRef().child("mDesc2").setValue(desc2);
                                                                                                ds.getRef().child("mDesc3").setValue(desc3);

                                                                                                ds.getRef().child("mImageuri2").setValue(url2);
                                                                                                ds.getRef().child("mImageuri3").setValue(url3);
                                                                                            }
                                                                                            Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                                                            mProgressbar.setVisibility(View.INVISIBLE);
                                                                                            Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                                                                            intent.putExtra("child",child);
                                                                                            startActivity(intent);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                                        }
                                                                                    });


                                                                                }
                                                                            });
                                                        }
                                                    });



        }

        else if(mImageUri != null){
            StorageReference filerefernce = mStoragerefernce.child(System.currentTimeMillis()
                    + "." + getFileExt(mImageUri));

            mUploadTask =
                    filerefernce.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl = urlTask.getResult();


                                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                                                                    Query query = databaseReference.orderByChild("mName").equalTo(title);

                                                                                    String url = downloadUrl.toString();


                                                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                                                ds.getRef().child("mName").setValue(name);
                                                                                                ds.getRef().child("mName2").setValue(name2);
                                                                                                ds.getRef().child("mName3").setValue(name3);
                                                                                                ds.getRef().child("mDesc").setValue(desc);
                                                                                                ds.getRef().child("mDesc2").setValue(desc2);
                                                                                                ds.getRef().child("mDesc3").setValue(desc3);
                                                                                                ds.getRef().child("mImageuri").setValue(url);

                                                                                            }
                                                                                            Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                                                                            mProgressbar.setVisibility(View.INVISIBLE);
                                                                                            Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                                                                            intent.putExtra("child",child);
                                                                                            startActivity(intent);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                                        }
                                                                                    });


                                                                                }
                                                                            });


        }

        else if (mImageUri2 != null){

            StorageReference filerefernce2 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri2));

            mUploadTask1 =
                    filerefernce2.putFile(mImageUri2)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl2 = urlTask.getResult();


                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                    Query query = databaseReference.orderByChild("mName").equalTo(title);

                                    String url2 = downloadUrl2.toString();


                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                ds.getRef().child("mName").setValue(name);
                                                ds.getRef().child("mName2").setValue(name2);
                                                ds.getRef().child("mName3").setValue(name3);
                                                ds.getRef().child("mDesc").setValue(desc);
                                                ds.getRef().child("mDesc2").setValue(desc2);
                                                ds.getRef().child("mDesc3").setValue(desc3);
                                                ds.getRef().child("mImageuri2").setValue(url2);

                                            }
                                            Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                            mProgressbar.setVisibility(View.INVISIBLE);
                                            Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                            intent.putExtra("child",child);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }
                            });

        }

        else if (mImageUri3 != null){

            StorageReference filerefernce3 = mStoragerefernce.child(System.currentTimeMillis() + "." + getFileExt(mImageUri3));
            mUploadTask2 =
                    filerefernce3.putFile(mImageUri3)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(NutrientFullAdmin.this, "Upload Successful", Toast.LENGTH_LONG).show();
                           /* UploadAdmin upload = new UploadAdmin(mEditText.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadId = mDatabaserefernce.push() .getKey();
                            mDatabaserefernce.child(uploadId).setValue(upload);*/

                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;

                                    Uri downloadUrl3 = urlTask.getResult();


                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
                                    Query query = databaseReference.orderByChild("mName").equalTo(title);

                                    String url3 = downloadUrl3.toString();


                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                ds.getRef().child("mName").setValue(name);
                                                ds.getRef().child("mName2").setValue(name2);
                                                ds.getRef().child("mName3").setValue(name3);
                                                ds.getRef().child("mDesc").setValue(desc);
                                                ds.getRef().child("mDesc2").setValue(desc2);
                                                ds.getRef().child("mDesc3").setValue(desc3);
                                                ds.getRef().child("mImageuri3").setValue(url3);

                                            }
                                            Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                                            mProgressbar.setVisibility(View.INVISIBLE);
                                            Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                                            intent.putExtra("child",child);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }
                            });

        }

        else{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Image").child(child).child("Menu");
            Query query = databaseReference.orderByChild("mName").equalTo(title);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ds.getRef().child("mName").setValue(name);
                        ds.getRef().child("mName2").setValue(name2);
                        ds.getRef().child("mName3").setValue(name3);
                        ds.getRef().child("mDesc").setValue(desc);
                        ds.getRef().child("mDesc2").setValue(desc2);
                        ds.getRef().child("mDesc3").setValue(desc3);

                    }
                    Toast.makeText(NutrientFullAdmin.this, "Data saved", Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(NutrientFullAdmin.this,ImagesActivity.class);
                    intent.putExtra("child",child);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}




