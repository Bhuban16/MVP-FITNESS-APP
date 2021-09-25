package com.example.mvpfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mvpfitnessapp.bmi.BMI_Calculator;
import com.example.mvpfitnessapp.body_fat.Bodyfat_Calculator;
import com.example.mvpfitnessapp.calories_intake.Daily_Calories_Intake_Calculator;
import com.example.mvpfitnessapp.daily_water.Daily_WaterIntake_Calculator;
import com.example.mvpfitnessapp.ideal_body_weight.Ideal_Body_Weight_Calculator;
import com.example.mvpfitnessapp.lean_body_mass.Lean_Body_Mass_Calculator;

public class Calculator extends AppCompatActivity {

    private CardView bmi;
    private CardView fat;
    private CardView weight;
    private CardView calorie;
    private CardView lean;
    private CardView water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        bmi = findViewById(R.id.bmi);
        fat = findViewById(R.id.fat);
        weight = findViewById(R.id.weight);
        water = findViewById(R.id.water);
        calorie= findViewById(R.id.calorie);
        lean= findViewById(R.id.lean);

        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calculator.this, BMI_Calculator.class));
            }
        });
        fat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calculator.this, Bodyfat_Calculator.class));
            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calculator.this, Ideal_Body_Weight_Calculator.class));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calculator.this, Daily_WaterIntake_Calculator.class));
            }
        });
        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calculator.this, Daily_Calories_Intake_Calculator.class));
            }
        });
        lean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calculator.this, Lean_Body_Mass_Calculator.class));
            }
        });
    }
}