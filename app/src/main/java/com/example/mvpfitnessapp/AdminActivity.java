package com.example.mvpfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private EditText AName;
    private EditText APassword;
    private Button ALogin;
   Credentials credentials = new Credentials("Admin", "123456");
    boolean isValid = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    AName =(EditText) findViewById(R.id.etAdminName);
        APassword =(EditText) findViewById(R.id.etAdminPassword);
        ALogin = (Button) findViewById(R.id.btnAdminLogin);
        credentials.setUsername("A");
        credentials.setUserpassword("1");

        ALogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputName = AName.getText().toString();
                String inputPassword = APassword.getText().toString();

                if (inputName.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(AdminActivity.this, "Please enter all the details correctly!", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = validate(inputName, inputPassword);
                    if (!isValid) {
                        Toast.makeText(AdminActivity.this, "Incorrect detail, please try again", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(AdminActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this, SecondAdminActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });
    }


    private boolean validate (String UserName, String UserPassword){
        if (UserName.equals(credentials.getUsername()) && UserPassword.equals(credentials.getUserpassword())){
           return true;
        }
        return false;
    }
        }