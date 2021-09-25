package com.example.mvpfitnessapp.ideal_body_weight;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mvpfitnessapp.R;


public class Ideal_Body_Weight_Result extends Activity {
    String TAG = getClass().getSimpleName();

    Bundle extras;

    Float ideal_body_weight;

    TextView tv_ideal_weight;
    TextView tv_ideal_weight_range;



    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_ideal_body_weight_result);

        this.tv_ideal_weight = (TextView) findViewById(R.id.tv_ideal_weight);
        this.tv_ideal_weight_range = (TextView) findViewById(R.id.tv_ideal_weight_range);

        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.extras = getIntent().getExtras();
        this.ideal_body_weight = Float.valueOf(this.extras.getFloat("ideal_body_weight"));
        if (this.ideal_body_weight.floatValue() <= 0.0f) {
            TextView textView = this.tv_ideal_weight;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.Your_ideal_body_weight_is));
            sb.append("0");
            sb.append(getString(R.string.kg));
            textView.setText(sb.toString());
            TextView textView2 = this.tv_ideal_weight_range;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getString(R.string.Your_ideal_body_weight_range_is));
            sb2.append("0-0");
            sb2.append(getString(R.string.kg));
            textView2.setText(sb2.toString());
            return;
        }
        TextView textView3 = this.tv_ideal_weight;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.Your_ideal_body_weight_is));
        sb3.append(" ");
        sb3.append(Math.round(this.ideal_body_weight.floatValue()));
        sb3.append(getString(R.string.kg));
        textView3.setText(sb3.toString());
        TextView textView4 = this.tv_ideal_weight_range;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.Your_ideal_body_weight_range_is));
        sb4.append(" ");
        sb4.append(Math.round(this.ideal_body_weight.floatValue()) - 5);
        sb4.append("-");
        sb4.append(Math.round(this.ideal_body_weight.floatValue()) + 5);
        sb4.append(getString(R.string.kg));
        textView4.setText(sb4.toString());
    }



}
