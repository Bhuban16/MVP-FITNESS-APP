package com.example.mvpfitnessapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;

public class StepHistory extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference databaseReference;
    private ProgressBar mProgressCircle;
    private DatePickerDialog datePickerDialog;
    FirebaseAuth firebaseAuth;
    String date;
    TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_history);
        ok = findViewById(R.id.ok);

        mProgressCircle = findViewById(R.id.progress_circle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle("Step History");
        setSupportActionBar(toolbar);

        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort","newest");
        if(mSorting.equals("newest")){
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);

        }else if(mSorting.equals("oldest")){
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User Step Record").child(firebaseAuth.getUid());
    }
    private void firebaseSearch() {

        String query = date;

        Query firebaseQuery = mDatabaseRef.orderByChild("userDate").equalTo(query);

            FirebaseRecyclerOptions<UploadStep> options =
                    new FirebaseRecyclerOptions.Builder<UploadStep>()
                            .setQuery(firebaseQuery, UploadStep.class)
                            .build();

            FirebaseRecyclerAdapter<UploadStep, StepAdapter> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<UploadStep, StepAdapter>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull StepAdapter holder, int position, @NonNull UploadStep model) {

                            holder.setExoPlayer(getApplication(), model.getUserDate(), model.getUserCalories(), model.getUserSteps(), model.getUserDistance());

                        }

                        @NonNull
                        @Override
                        public StepAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.step_item, parent, false);
                            return new StepAdapter(view);
                        }


                    };
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        ok.setVisibility(View.GONE);
        }

        @Override
        protected void onStart() {
            super.onStart();

            FirebaseRecyclerOptions<UploadStep> options =
                    new FirebaseRecyclerOptions.Builder<UploadStep>()
                            .setQuery(mDatabaseRef,UploadStep.class)
                            .build();
                           ok.setVisibility(View.GONE);
            FirebaseRecyclerAdapter<UploadStep, StepAdapter> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<UploadStep, StepAdapter>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull StepAdapter holder, int position, @NonNull UploadStep model) {

                            holder.setExoPlayer(getApplication(),model.getUserDate(),model.getUserCalories(), model.getUserSteps(), model.getUserDistance());

                        }

                        @NonNull
                        @Override
                        public StepAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.step_item,parent, false);
                            return new StepAdapter(view);
                        }



                    };
            firebaseRecyclerAdapter.startListening();
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_step,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_sort) {
            showSortDialog();

            return true;
        }
        if(id == R.id.action_other){
            showDatePickerDialog();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
  public void showSortDialog(){
        String[] sortOptions = {"Newest" , "Oldest"};

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Sort by")
              .setIcon(R.drawable.ic_baseline_sort_24)

              .setItems(sortOptions, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      if (which == 0){

                          SharedPreferences.Editor editor = mSharedPref.edit();
                          editor.putString("Sort", "newest");
                          editor.apply();
                          recreate();
                      }else if (which == 1){
                          SharedPreferences.Editor editor = mSharedPref.edit();
                          editor.putString("Sort", "oldest");
                          editor.apply();
                          recreate();
                      }
                  }
              });
      builder.show();
  }

  private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

  }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       int mon = month+1;
         date = dayOfMonth+ "/"+ mon + "/" + year;
        firebaseSearch();
    }
}
