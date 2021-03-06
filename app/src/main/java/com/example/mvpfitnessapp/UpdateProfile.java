package com.example.mvpfitnessapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class UpdateProfile extends AppCompatActivity {

    private EditText newUserName, newUserPhoneNumber, newUserEmail , newUserWeight, newUserHeight,newUserAge;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView updateProfilePic;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private Spinner userGenderUpdate;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        userGenderUpdate = (Spinner) findViewById(R.id.etGenderUpdate);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userGenderUpdate.setAdapter(adapter);

        newUserName = findViewById(R.id.etNameUpdate);
        newUserPhoneNumber = findViewById(R.id.etPhoneNumberUpdate);
        newUserEmail = findViewById(R.id.etEmailUpdate);
        save = findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.ivProfileUpdate);
        newUserHeight = findViewById(R.id.etheightUpdate);
        newUserAge = findViewById(R.id.etageUpdate);
        newUserWeight = findViewById(R.id.etWeightUpdate);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");
        firebaseStorage = FirebaseStorage.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference("User Info").child(firebaseAuth.getUid());
        storageReference = firebaseStorage.getReference();

        StorageReference mImageRef = FirebaseStorage.getInstance().getReference().child(firebaseAuth.getUid()).child("Images").child("Profile Picture");
        final long ONE_MEGABYTE = 1024 * 1024;

        mImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                updateProfilePic.setMinimumHeight(dm.heightPixels);
                updateProfilePic.setMinimumWidth(dm.widthPixels);
                updateProfilePic.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);


                newUserAge.setText(userProfile.getUserAge());
                newUserName.setText(userProfile.getUserName());
                 newUserHeight.setText(userProfile.getUserHeight());
                newUserWeight.setText(userProfile.getUserWeight());

                newUserPhoneNumber.setText(userProfile.getUserPhoneNumber());
                newUserEmail.setText(userProfile.getUserEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newUserName.getText().toString();
                String number = newUserPhoneNumber.getText().toString();
                String email = newUserEmail.getText().toString();
                String gender =  userGenderUpdate.getSelectedItem().toString();
                String age = newUserAge.getText().toString();
                String weight = newUserWeight.getText().toString();
                String height = newUserHeight.getText().toString();
                UserProfile userProfile = new UserProfile(email, name, number, age, gender, weight, height);

                databaseReference.setValue(userProfile);

                StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Picture"); //User Id/Images/Profile_pic
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, "Photo exceeds 1mb", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UpdateProfile.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateProfile.this, UpdateProfile.class));
                    }
            });
        }
    });
        }

    public Boolean validate(){
        boolean result = false;

        String name = newUserName.getText().toString();
        String number = newUserPhoneNumber.getText().toString();
        String email = newUserEmail.getText().toString();
        String weight = newUserWeight.getText().toString();
        String height = newUserHeight.getText().toString();
        String age = newUserAge.getText().toString();


        if(name.isEmpty()) {
            Toast.makeText(this, "Name can't be empty.", Toast.LENGTH_SHORT).show();
        }else if(number.isEmpty()){
            Toast.makeText(this, "Phone number can't be empty.", Toast.LENGTH_SHORT).show();
        }else if(email.isEmpty()){
            Toast.makeText(this, "Email can't be empty.", Toast.LENGTH_SHORT).show();
        }else if(weight.isEmpty()){
            Toast.makeText(this, "Weight can't be empty.", Toast.LENGTH_SHORT).show();
        }else if(height.isEmpty()){
            Toast.makeText(this, "Height can't be empty.", Toast.LENGTH_SHORT).show();
        }else if(age.isEmpty()){
            Toast.makeText(this, "Age can't be empty.", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    }