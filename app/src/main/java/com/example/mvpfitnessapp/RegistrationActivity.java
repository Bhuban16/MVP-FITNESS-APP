package com.example.mvpfitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                //Upload data to the database
                String user_email = userEmail.getText().toString().trim();
                String user_password = userPassword.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                 Toast.makeText(RegistrationActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                } else {
                 Toast.makeText(RegistrationActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                 }
                 }
                 });
                }

                 }
                 });

                userLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));


                    }
                });


            }

            private void setupUIViews() {
                userName = (EditText) findViewById(R.id.etUserName);
                userPassword = (EditText) findViewById(R.id.etUserPassword);
                userEmail = (EditText) findViewById(R.id.etUserEmail);
                regButton = (Button) findViewById(R.id.btnRegister);
                userLogin = (TextView) findViewById(R.id.tvUserLogin);

            }


            private Boolean validate() {
            Boolean result = false;

            String name = userName.getText().toString();
            String password = userPassword.getText().toString();
             String email = userEmail.getText().toString();

            if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
             Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            } else {
            result = true;
             }
            return result;
<<<<<<< HEAD
             }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        sendUserData();
                        firebaseAuth.signOut();
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    private void sendUserData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = firebaseDatabase.getReference("User Info").child(firebaseAuth.getUid());

        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Picture"); //User Id/Images/Profile_pic
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Upload Failed! ", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(email, name, number );
        myRef.setValue(userProfile);
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
=======
             }}
>>>>>>> parent of b0645ea... backlog 2


