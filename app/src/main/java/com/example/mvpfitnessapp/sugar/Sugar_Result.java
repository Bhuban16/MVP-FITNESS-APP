package com.example.mvpfitnessapp.sugar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvpfitnessapp.R;


import java.io.PrintStream;


public class Sugar_Result extends Activity {
    String TAG = getClass().getSimpleName();
    Bundle extras;
    Double final_bloodsugar_val;
    ImageView iv_close;
    TextView tv_ans_bloodsugar;
    TextView tv_bloodsugar_chart;



    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_sugar);


        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.extras = getIntent().getExtras();
        this.final_bloodsugar_val = Double.valueOf(this.extras.getDouble("final_bloodsugar_val"));
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        if (this.final_bloodsugar_val.doubleValue() < 0.0d) {
            TextView textView = this.tv_ans_bloodsugar;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.Blood_Sugar));
            sb.append(" : 0 ]");
            sb.append(getString(R.string.mmol_a));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = this.tv_ans_bloodsugar;
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getString(R.string.Blood_Sugar));
            sb3.append(" : %.2f");
            sb2.append(String.format(sb3.toString(), new Object[]{this.final_bloodsugar_val}));
            sb2.append(" ");
            sb2.append(getString(R.string.mmol_a));
            textView2.setText(sb2.toString());
        }
        this.tv_bloodsugar_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Sugar_Result.this.startActivity(new Intent(Sugar_Result.this, Sugar_Chart.class));
                    return;
                }
                Sugar_Result.this.startActivity(new Intent(Sugar_Result.this, Sugar_Chart.class));
            }
        });
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Sugar_Result.this.onBackPressed();
            }
        });
    }






}