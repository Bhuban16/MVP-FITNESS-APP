package com.example.mvpfitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowVideo extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    String name, url, des, timer, set, child;
    Button upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showvideo);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        child = intent.getExtras().getString("child");
        upload = findViewById(R.id.upload);
        recyclerView = findViewById(R.id.recyclerview_ShowVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");

        databaseReference = database.getReference("video").child(child).child("Menu");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowVideo.this, Upload.class);
                intent.putExtra("child", child);
                startActivity(intent);
            }
        });


        FirebaseRecyclerOptions<Member> options =
                new FirebaseRecyclerOptions.Builder<Member>()
                        .setQuery(databaseReference, Member.class)
                        .build();

        FirebaseRecyclerAdapter<Member, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Member model) {

                        holder.setExoPlayer(getApplication(), model.getName(), model.getVideourl(), model.getDescription());

                        holder.setOnClicklistener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                name = getItem(position).getName();
                                url = getItem(position).getVideourl();
                                des = getItem(position).getDescription();
                                timer = getItem(position).getTimer();
                                set = getItem(position).getWorkoutSet();
                                Intent intent = new Intent(ShowVideo.this, Fullscreen.class);
                                intent.putExtra("child", child);
                                intent.putExtra("nam", name);
                                intent.putExtra("ur", url);
                                intent.putExtra("des", des);
                                intent.putExtra("time", timer);
                                intent.putExtra("set", set);
                                startActivity(intent);


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                name = getItem(position).getName();
                                showDeleteDialog(name);

                            }
                        });


                    }


                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.row, parent, false);
                        return new ViewHolder(view);
                    }


                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    private void firebaseSearch(String searchtext){

        String query = searchtext.toLowerCase();

        Query firebaseQuery = databaseReference.orderByChild("search").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerOptions<Member> options =
                new FirebaseRecyclerOptions.Builder<Member>()
                        .setQuery(firebaseQuery,Member.class)
                        .build();

        FirebaseRecyclerAdapter<Member, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Member model) {

                        holder.setExoPlayer(getApplication(),model.getName(),model.getVideourl(), model.getDescription());
                        holder.setOnClicklistener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                name = getItem(position).getName();
                                url = getItem(position).getVideourl();
                                des = getItem(position).getDescription();
                                timer= getItem(position).getTimer();
                                set = getItem(position).getWorkoutSet();
                                Intent intent = new Intent(ShowVideo.this,Fullscreen.class);

                                intent.putExtra("child",child);
                                intent.putExtra("nam",name);
                                intent.putExtra("ur",url);
                                intent.putExtra("des",des);
                                intent.putExtra("time",timer);
                                intent.putExtra("set",set);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                name = getItem(position).getName();
                                showDeleteDialog(name);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.row,parent, false);
                        return new ViewHolder(view);
                    }



                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search_firebase);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void  showDeleteDialog(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowVideo.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this data");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query query = databaseReference.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot dataSnapshot1 : datasnapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();
                        }
                        Toast.makeText(ShowVideo.this, "Video Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}



