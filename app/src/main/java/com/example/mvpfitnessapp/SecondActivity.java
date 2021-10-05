package com.example.mvpfitnessapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mvpfitnessapp.ui.tracker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private FirebaseAuth firebaseAuth;
    private ImageView profileMenu;
    private FirebaseDatabase firebaseDatabase;
    private TextView profileName;
    private TextView feedback;
    private TextView contactus;
    private CardView bmi;
    private CardView step;
    private CardView reminder;
    private CardView report;
    private CardView calculate;
    private ImageView lean_body_mass;
    private TextView showcalories, showwater, showbmi ,showGaincalories , showLosscalories , showIdeal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);



        firebaseAuth = FirebaseAuth.getInstance();
        profileMenu = findViewById(R.id.profileMenu);
        report = findViewById(R.id.report);
        profileName = findViewById(R.id.tvProfileName);
        feedback = findViewById(R.id.feedback);
        contactus = findViewById(R.id.contactus);
        calculate = findViewById(R.id.calculate);
        step = findViewById(R.id.step);
        reminder = findViewById(R.id.reminder);
        showbmi = findViewById(R.id.showbmi);
        showcalories = findViewById(R.id.showcalories);
        showwater = findViewById(R.id.showwater);
        showGaincalories = findViewById(R.id.showGaincalories);
        showLosscalories = findViewById(R.id.showLosscalories);
        showIdeal = findViewById(R.id.showIdeal);
        bmi = findViewById(R.id.bmi);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");


        final DatabaseReference databaseReference = firebaseDatabase.getReference("User Info").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, UserShowVideo.class));
            }
        });

        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, tracker.class));
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, Reminder.class));
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, Calculator.class));
            }
        });

       report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this, Report.class));
            }
        });
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

        @Override
        public void onBackPressed () {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        private void Logout () {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
        }


        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem){
            switch (menuItem.getItemId()) {

                case R.id.nav_home:
                    break;

                case R.id.profileMenu:
                    Intent intent = new Intent(SecondActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;

                case R.id.logoutMenu: {
                    Logout();
                    break;
                }

                case R.id.contactus: {
                    startActivity(new Intent(SecondActivity.this, ContactUs.class));
                    break;
                }

                case R.id.feedback: {
                    startActivity(new Intent(SecondActivity.this, Feedback.class));
                    break;
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

    }




