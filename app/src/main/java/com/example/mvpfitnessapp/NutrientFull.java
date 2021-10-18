package com.example.mvpfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NutrientFull extends AppCompatActivity {
    private TextView name, name2, name3;
    private ImageView image , image2 , image3;
    private TextView desc , desc2, desc3;
    private String urlI , urlI2 , urlI3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_full);

        name = findViewById(R.id.name);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        image = findViewById(R.id.url);
        image2= findViewById(R.id.url2);
        image3 = findViewById(R.id.url3);
        desc = findViewById(R.id.desc);
        desc2= findViewById(R.id.desc2);
        desc3 = findViewById(R.id.desc3);


        Intent intent = getIntent();
        urlI = intent.getExtras().getString("ur1");
         urlI2 = intent.getExtras().getString("ur2");
         urlI3 = intent.getExtras().getString("ur3");

        String description = intent.getExtras().getString("desc1");
        String description2 = intent.getExtras().getString("desc2");
        String description3 = intent.getExtras().getString("desc3");

        String title = intent.getExtras().getString("nam1");
        String title2 = intent.getExtras().getString("nam2");
        String title3 = intent.getExtras().getString("nam3");

        desc.setText(description);
        desc2.setText(description2);
        desc3.setText(description3);

        name.setText(title);
        name2.setText(title2);
        name3.setText(title3);

        Picasso.get().load(urlI).fit().into(image);
        Picasso.get().load(urlI2).fit().into(image2);
        Picasso.get().load(urlI3).fit().into(image3);
    }
}