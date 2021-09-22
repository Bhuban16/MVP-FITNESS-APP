package com.example.mvpfitnessapp.daily_water;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mvpfitnessapp.R;
import com.example.mvpfitnessapp.TypefaceManager;

import java.io.PrintStream;
import java.util.ArrayList;


public class Daily_WaterIntake_Calculator extends Activity {
    String TAG = getClass().getSimpleName();

    ArrayAdapter<String> adapter_weight;
    ArrayList<String> arraylist_weight = new ArrayList<>();
    EditText et_weight;

    ImageView iv_back;
    ListView listViewWeight;
    private PopupWindow popupWindowWeight;

    TextView tv_calculate_waterintake;
    TextView tv_water_intake;
    TextView tv_weight;
    TypefaceManager typefaceManager;
    double water_intake;
    double weight;
    String weight_unit;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.waterintake_calculator);

        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.et_weight = (EditText) findViewById(R.id.et_weight);
        this.tv_weight = (TextView) findViewById(R.id.tv_weight);
        this.tv_calculate_waterintake = (TextView) findViewById(R.id.tv_calculate_waterintake);
        this.tv_water_intake = (TextView) findViewById(R.id.tv_water_intake);
       // this.tv_water_intake.setTypeface(this.typefaceManager.getBold());
        //this.tv_calculate_waterintake.setTypeface(this.typefaceManager.getBold());
        this.tv_weight.setOnClickListener(showPopupWindowWeight());
        this.arraylist_weight.clear();
        this.arraylist_weight.add(getString(R.string.lbs));
        this.arraylist_weight.add(getString(R.string.kg));
        this.adapter_weight = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_weight);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.tv_calculate_waterintake.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                if (Daily_WaterIntake_Calculator.this.et_weight.getText().toString().trim().equals("") || Daily_WaterIntake_Calculator.this.et_weight.getText().toString().trim().equals(".")) {
                    Toast.makeText(Daily_WaterIntake_Calculator.this.getApplicationContext(), Daily_WaterIntake_Calculator.this.getString(R.string.Enter_Weight),Toast.LENGTH_SHORT).show();
                    return;
                }
                Daily_WaterIntake_Calculator.this.weight = Double.parseDouble(Daily_WaterIntake_Calculator.this.et_weight.getText().toString().trim());
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Daily_WaterIntake_Calculator.this.get_waterintake();
                } else {
                    Daily_WaterIntake_Calculator.this.get_waterintake();
                }
            }
        });
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Daily_WaterIntake_Calculator.this.onBackPressed();
            }
        });
    }



    public void get_waterintake() {
        this.weight_unit = this.tv_weight.getText().toString().trim();
        if (this.weight_unit.equalsIgnoreCase(getString(R.string.lbs))) {
            this.weight /= 2.204622d;
        }
        this.water_intake = this.weight / 0.024d;
        StringBuilder sb = new StringBuilder();
        sb.append("water_intake");
        sb.append(this.water_intake);
        Log.d("water_intake", sb.toString());
        Intent intent = new Intent(this, Daily_WaterIntake_Result.class);
        intent.putExtra("water_intake", this.water_intake);
        startActivity(intent);
    }

    private OnClickListener showPopupWindowWeight() {
        return new OnClickListener() {
            public void onClick(View view) {
                Daily_WaterIntake_Calculator.this.popupWindowWeight().showAsDropDown(view, 0, 0);
            }
        };
    }



    @SuppressLint("ResourceType")
    public PopupWindow popupWindowWeight() {
        this.popupWindowWeight = new PopupWindow(this);
        this.listViewWeight = new ListView(this);
        this.listViewWeight.setDividerHeight(0);
        this.listViewWeight.setAdapter(this.adapter_weight);
        this.listViewWeight.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Daily_WaterIntake_Calculator.this.tv_weight.setText((CharSequence) Daily_WaterIntake_Calculator.this.arraylist_weight.get(i));
                Daily_WaterIntake_Calculator.this.dismissPopupWeight();
            }
        });
        this.popupWindowWeight.setFocusable(true);
        this.popupWindowWeight.setWidth(this.tv_weight.getMeasuredWidth());
        this.popupWindowWeight.setHeight(-2);
        this.popupWindowWeight.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowWeight.setContentView(this.listViewWeight);
        return this.popupWindowWeight;
    }


    public void dismissPopupWeight() {
        if (this.popupWindowWeight != null) {
            this.popupWindowWeight.dismiss();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }




}
