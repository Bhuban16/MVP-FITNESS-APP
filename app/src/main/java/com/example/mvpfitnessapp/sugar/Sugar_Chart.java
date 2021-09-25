package com.example.mvpfitnessapp.sugar;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mvpfitnessapp.R;


public class Sugar_Chart extends Activity {
    String TAG = getClass().getSimpleName();
    ImageView iv_back;
    TextView tv_athletes;
    TextView tv_avg_fat;
    TextView tv_bodayfat_classification;
    TextView tv_bodayfat_classification_man;
    TextView tv_bodayfat_classification_woman;
    TextView tv_bodayfat_percentage;
    TextView tv_bodyfat;
    TextView tv_essential_fat;
    TextView tv_fat_level;
    TextView tv_fitness;
    TextView tv_low_fat;
    TextView tv_men_fatlevel;
    TextView tv_title;
    TextView tv_vlow_fat;
    TextView tv_women_fatlevel;



    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sugar_chart);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_bodyfat = (TextView) findViewById(R.id.tv_bodyfat);
        this.tv_fat_level = (TextView) findViewById(R.id.tv_fat_level);
        this.tv_men_fatlevel = (TextView) findViewById(R.id.tv_men_fatlevel);
        this.tv_women_fatlevel = (TextView) findViewById(R.id.tv_women_fatlevel);
        this.tv_bodayfat_percentage = (TextView) findViewById(R.id.tv_bodayfat_percentage);
        this.tv_vlow_fat = (TextView) findViewById(R.id.tv_vlow_fat);
        this.tv_low_fat = (TextView) findViewById(R.id.tv_low_fat);
        this.tv_avg_fat = (TextView) findViewById(R.id.tv_avg_fat);
        this.tv_bodayfat_classification = (TextView) findViewById(R.id.tv_bodayfat_classification);
        this.tv_bodayfat_classification_man = (TextView) findViewById(R.id.tv_bodayfat_classification_man);
        this.tv_bodayfat_classification_woman = (TextView) findViewById(R.id.tv_bodayfat_classification_woman);
        this.tv_essential_fat = (TextView) findViewById(R.id.tv_essential_fat);
        this.tv_athletes = (TextView) findViewById(R.id.tv_athletes);
        this.tv_fitness = (TextView) findViewById(R.id.tv_fitness);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }



        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Sugar_Chart.this.onBackPressed();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
