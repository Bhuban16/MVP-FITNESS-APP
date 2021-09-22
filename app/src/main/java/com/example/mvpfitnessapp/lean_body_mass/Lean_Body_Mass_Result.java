package com.example.mvpfitnessapp.lean_body_mass;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mvpfitnessapp.R;
import com.example.mvpfitnessapp.TypefaceManager;


public class Lean_Body_Mass_Result extends Activity {
    String TAG = getClass().getSimpleName();

    Bundle extras;

    ImageView iv_close;
    double lean_body_mass;
    LinearLayout rl_main;

    TextView tv_ans_bmr;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_bmr);
      //  this.typefaceManager = new TypefaceManager(getAssets(), this);

        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.rl_main = (LinearLayout) findViewById(R.id.rl_main);
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient4);

        this.extras = getIntent().getExtras();
        this.lean_body_mass = this.extras.getDouble("lean_body_mass");
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
       // this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.lean_body_mass);
        Log.d("lean_body_mass->", sb.toString());
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.your_lean_body_mass));
        sb2.append(" : \n");
        sb2.append(String.format("%.2f", new Object[]{Double.valueOf(this.lean_body_mass)}));
        textView.setText(String.valueOf(sb2.toString()));
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Lean_Body_Mass_Result.this.onBackPressed();
            }
        });
    }



}
