package com.example.mvpfitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Report extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseAuth firebaseAuth;
    private TextView showcalories, showwater, showbmi ,showGaincalories , showLosscalories , showIdeal;
    private FirebaseDatabase firebaseDatabase;
    private ImageView setWeight;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        showbmi = findViewById(R.id.showbmi);
        showcalories = findViewById(R.id.showcalories);
        showwater = findViewById(R.id.showwater);
        showGaincalories = findViewById(R.id.showGaincalories);
        showLosscalories = findViewById(R.id.showLosscalories);
        showIdeal = findViewById(R.id.showIdeal);
        firebaseAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.report);
        setWeight = findViewById(R.id.setWeight);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");


        final DatabaseReference databaseReference = firebaseDatabase.getReference("User Info").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String gender = dataSnapshot.child("userGender").getValue().toString();
                String age = dataSnapshot.child("userAge").getValue().toString();
                String weight = dataSnapshot.child("userWeight").getValue().toString();
                String height = dataSnapshot.child("userHeight").getValue().toString();

                int weightCal = Integer.parseInt(weight) * 10;
                int heightCal = Integer.parseInt(height) * 25 / 4;
                int ageCal = Integer.parseInt(age) * 5;

                //int genderValue =Integer.parseInt(gender);
                int weightint = Integer.parseInt(weight) * 125;
                float weightValue = Float.parseFloat(weight);
                float heightValue = Float.parseFloat(height) / 100;

                int weightMenIdeal = Integer.parseInt(weight);
                int heightMenIdeal = Integer.parseInt(height) - 152;

                int MaleIdeal = calculateIdealMale(heightMenIdeal);
                int MaleMinIdeal = calculateIdealMinMale(heightMenIdeal);
                int FemaleMinIdeal = calculateIdealMinFemale(heightMenIdeal);
                int FemaleIdeal = calculateIdealFemale(heightMenIdeal);

                int DiffMaleIdeal = calculateMaleDiff(weightMenIdeal,MaleIdeal);
                int DiffMaxMaleIdeal = calculateMinMaleDiff(weightMenIdeal,MaleMinIdeal);
                int DiffFemaleIdeal = calculateFemaleDiff(weightMenIdeal,FemaleIdeal);
                int DiffMaxFemaleIdeal = calculateMinFemaleDiff(weightMenIdeal,FemaleMinIdeal);


                float bmi = calculateBMI(weightValue, heightValue);
                int calMale = calculateCalMale(weightCal, heightCal, ageCal);
                int calFemale = calculateCalFemale(weightCal, heightCal, ageCal);
                int calLossFemale = calculateFemaleLoss(weightCal, heightCal, ageCal);
                int callGainFemale = calculateFemaleGain(weightCal, heightCal, ageCal);
                int water = calculateWater(weightint);
                int calGainMale = calculateMaleGain(weightCal, heightCal, ageCal);
                int calLossMale = calculateMaleLoss(weightCal, heightCal, ageCal);
                String bmiInterpretation = interpretBMI(bmi);

                showbmi.setText(String.valueOf( bmi + "\n" + bmiInterpretation));
                showwater.setText(String.valueOf(water + "ml"));

                if (gender.equals("Male") && bmiInterpretation.equals("Severely Under Weight")) {
                    showcalories.setText(String.valueOf( calMale +  "cal"));
                    showGaincalories.setText(String.valueOf( calGainMale + "cal"));
                    showLosscalories.setText(String.valueOf("You are not recommended to loss weight" ));

                } else if (gender.equals("Male") && bmiInterpretation.equals("Under Weight")) {
                    showcalories.setText(String.valueOf( calMale +  "cal "));
                    showGaincalories.setText(String.valueOf(calGainMale + "cal"));
                    showLosscalories.setText(String.valueOf("You are not recommended to loss weight" ));

                } else if (gender.equals("Male") && bmiInterpretation.equals("Normal Weight")) {
                    showcalories.setText(String.valueOf( calMale +  "cal "));
                    showGaincalories.setText(String.valueOf(+calGainMale + "cal"));
                    showLosscalories.setText(String.valueOf( + calLossMale + "cal"));

                } else if (gender.equals("Male") && bmiInterpretation.equals("Overweight")) {
                    showcalories.setText(String.valueOf( calMale +  "cal "));
                    showGaincalories.setText(String.valueOf("You are not recommended to gain weight"));
                    showLosscalories.setText(String.valueOf(calLossMale + "cal" ));
                } else if (gender.equals("Male") && bmiInterpretation.equals("Obese")) {
                    showcalories.setText(String.valueOf(calMale +  "cal"));
                    showGaincalories.setText(String.valueOf("You are not recommended to gain weight" ));
                    showLosscalories.setText(String.valueOf(calLossMale + "cal" ));

                } else if (gender.equals("Female") && bmiInterpretation.equals("Severely Under Weight")) {
                    showcalories.setText(String.valueOf(calFemale+  "cal" ));
                    showGaincalories.setText(String.valueOf( +callGainFemale + "cal" ));
                    showLosscalories.setText(String.valueOf("You are not recommended to loss weight" ));

                } else if (gender.equals("Female") && bmiInterpretation.equals("Under Weight")) {

                    showcalories.setText(String.valueOf(calFemale+  "cal"));
                    showGaincalories.setText(String.valueOf(callGainFemale + "cal"));
                    showLosscalories.setText(String.valueOf("You are not recommended to Loss weight" ));

                } else if (gender.equals("Female") && bmiInterpretation.equals("Normal Weight")) {

                    showcalories.setText(String.valueOf(calFemale+  "cal"));
                    showGaincalories.setText(String.valueOf(callGainFemale + "cal"));
                    showLosscalories.setText(String.valueOf(calLossFemale + "cal"));

                }else if (gender.equals("Female") && bmiInterpretation.equals("Overweight")) {

                    showcalories.setText(String.valueOf(calFemale+  "cal"));
                    showGaincalories.setText(String.valueOf("You are not recommended to gain weight" ));
                    showLosscalories.setText(String.valueOf(calLossFemale + "cal"));

                }else if (gender.equals("Female") && bmiInterpretation.equals("Obese")) {
                    showcalories.setText(String.valueOf(calFemale+  "cal"));
                    showGaincalories.setText(String.valueOf("You are not recommended to gain weight" ));
                    showLosscalories.setText(String.valueOf(calLossFemale + "cal"));

                }else {
                    showcalories.setText(String.valueOf("Please update your weight"));
                    showGaincalories.setText(String.valueOf( "Please update your height"));
                    showLosscalories.setText(String.valueOf( "Please update your age"));
                }

                if(gender.equals("Male") &&  weightMenIdeal > MaleMinIdeal && weightMenIdeal > MaleIdeal){
                    showIdeal.setText(String.valueOf(  MaleIdeal+" - " +MaleMinIdeal + "\nYou need to loss " + DiffMaxMaleIdeal + "-" + DiffMaleIdeal + "Kg"));
                }
                else if(gender.equals("Male") && weightMenIdeal < MaleMinIdeal && weightMenIdeal < MaleIdeal){
                    showIdeal.setText(String.valueOf( MaleIdeal+" - " + MaleMinIdeal + "\nYou need to gain " + DiffMaxMaleIdeal + "-" + DiffMaleIdeal + "Kg"));
                }
                else if(gender.equals("Male") && weightMenIdeal <= MaleMinIdeal && weightMenIdeal >= MaleIdeal){
                    showIdeal.setText(String.valueOf(  MaleIdeal+" - " + MaleMinIdeal + "\nYou need to maintain this body weight" ));
                }

                else if(gender.equals("Female") &&  weightMenIdeal > FemaleMinIdeal && weightMenIdeal > FemaleIdeal){
                    showIdeal.setText(String.valueOf(  FemaleIdeal+" - " +FemaleMinIdeal + "\nYou need to loss " + DiffMaxFemaleIdeal + "-" + DiffFemaleIdeal + "kg"));
                }
                else if(gender.equals("Female") && weightMenIdeal < FemaleMinIdeal && weightMenIdeal < FemaleIdeal){
                    showIdeal.setText(String.valueOf(  FemaleIdeal+" - " + FemaleMinIdeal + "\nYou need to gain " + DiffMaxFemaleIdeal + "-" + DiffFemaleIdeal + "Kg"));
                }
                else if(gender.equals("Female") && weightMenIdeal <= FemaleMinIdeal && weightMenIdeal >= FemaleIdeal){
                    showIdeal.setText(String.valueOf(  FemaleIdeal+" - " + FemaleMinIdeal + "\nYou need to maintain this body weight" ));
                }else
                {
                    showIdeal.setText(String.valueOf("Please update your gender"));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Report.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

setWeight.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openDialog();
    }
});
    }
    public void openDialog(){

    }
    public int calculateMaleDiff(int weightMenIdeal, int MaleIdeal ) {

        if (weightMenIdeal > MaleIdeal) {
            return (int) (weightMenIdeal - MaleIdeal);
        }else {
            return (int) (MaleIdeal - weightMenIdeal);
        }
    }

    public int calculateMinMaleDiff(int weightMenIdeal, int MaleMinIdeal ) {

        if (weightMenIdeal > MaleMinIdeal) {
            return (int) (weightMenIdeal - MaleMinIdeal);
        }else {
            return (int) (MaleMinIdeal - weightMenIdeal);
        }
    }

    public int calculateMinFemaleDiff(int weightMenIdeal, int FemaleMinIdeal ) {

        if (weightMenIdeal > FemaleMinIdeal) {
            return (int) (weightMenIdeal - FemaleMinIdeal);
        }else {
            return (int) (FemaleMinIdeal - weightMenIdeal);
        }
    }

    public int calculateFemaleDiff(int weightMenIdeal, int FemaleIdeal ) {

        if (weightMenIdeal > FemaleIdeal) {
            return (int) (weightMenIdeal - FemaleIdeal);
        }else {
            return (int) (FemaleIdeal - weightMenIdeal);
        }
    }




    public int calculateIdealMale(int heightMenIdeal ){
        return (int) (50 +(( 91 * heightMenIdeal )/100));
    }

    public int calculateIdealMinMale(int heightMenIdeal ){
        return (int) (56 +(( 141 * heightMenIdeal )/254));
    }

    public int calculateIdealFemale(int heightMenIdeal ){
        return (int) (46+(( 91 * heightMenIdeal )/100));
    }
    public int calculateIdealMinFemale(int heightMenIdeal ){
        return (int) (53+(( 27 * heightMenIdeal )/50));
    }


    public int calculateWater(int waterint){
        return (int) (waterint / 3 );
    }

    public float calculateBMI(float weightValue, float heightValue) {
        return (float) (weightValue / (heightValue * heightValue));
    }

    public int calculateCalMale( int weightCal,int heightCal , int ageCal) {

        return (int) (weightCal + heightCal - ageCal + 210 );

    }

    public int calculateMaleGain(int weightCal,int heightCal , int ageCal) {
        return (int) (weightCal + heightCal - ageCal + 810 );
    }

    public int calculateFemaleGain(int weightCal,int heightCal , int ageCal) {
        return (int) (weightCal + heightCal - ageCal +580 );
    }

    public int calculateMaleLoss(int weightCal,int heightCal , int ageCal) {
        return (int) (weightCal + heightCal - ageCal - 410 );
    }

    public int calculateFemaleLoss(int weightCal,int heightCal , int ageCal) {
        return (int) (weightCal + heightCal - ageCal - 480 );
    }

    public int calculateCalFemale( int weightCal,int heightCal , int ageCal) {

        return (int) (weightCal + heightCal - ageCal - 20 );

    }

    private String interpretBMI(float bmi) {

        if (bmi < 16) {
            return "Severely Under Weight";
        } else if (bmi < 18.5) {
            return "Under Weight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            return "Normal Weight";
        } else if (bmi >= 25 && bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}