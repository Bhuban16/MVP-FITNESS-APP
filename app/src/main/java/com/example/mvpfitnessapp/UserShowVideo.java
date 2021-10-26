package com.example.mvpfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserShowVideo extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    String name,url,des , timer , set , child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show_video);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        child = intent.getExtras().getString("child");
        recyclerView = findViewById(R.id.recyclerview_ShowVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database= FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");

        databaseReference = database.getReference("video").child(child).child("Menu");

    }
    private void firebaseSearch(String searchtext){

        String query = searchtext.toLowerCase();

        Query firebaseQuery = databaseReference.orderByChild("search").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerOptions<Member> options =
                new FirebaseRecyclerOptions.Builder<Member>()
                        .setQuery(firebaseQuery,Member.class)
                        .build();

        FirebaseRecyclerAdapter<Member, UserViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Member, UserViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Member model) {

                        holder.setExoPlayer(getApplication(),model.getName(),model.getVideourl(), model.getDescription());
                        holder.setOnClicklistener(new UserViewHolder.Clicklistener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                name = getItem(position).getName();
                                url = getItem(position).getVideourl();
                                des = getItem(position).getDescription();
                                timer= getItem(position).getTimer();
                                set = getItem(position).getWorkoutSet();
                                Intent intent = new Intent(UserShowVideo.this,UserFullscreen.class);
                                intent.putExtra("nam",name);
                                intent.putExtra("ur",url);
                                intent.putExtra("des",des);
                                intent.putExtra("time",timer);
                                intent.putExtra("set",set);
                                startActivity(intent);


                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }


                        });
                    }

                    @NonNull
                    @Override
                    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.userrow,parent, false);
                        return new UserViewHolder(view);
                    }



                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Member> options =
                new FirebaseRecyclerOptions.Builder<Member>()
                        .setQuery(databaseReference,Member.class)
                        .build();

        FirebaseRecyclerAdapter<Member, UserViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Member, UserViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Member model) {

                        holder.setExoPlayer(getApplication(),model.getName(),model.getVideourl(), model.getDescription());
                        holder.setOnClicklistener(new UserViewHolder.Clicklistener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            name = getItem(position).getName();
                            url = getItem(position).getVideourl();
                            des = getItem(position).getDescription();
                            timer= getItem(position).getTimer();
                            set = getItem(position).getWorkoutSet();
                            Intent intent = new Intent(UserShowVideo.this,UserFullscreen.class);
                            intent.putExtra("nam",name);
                            intent.putExtra("ur",url);
                            intent.putExtra("des",des);
                            intent.putExtra("time",timer);
                            intent.putExtra("set",set);
                            startActivity(intent);


                        }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }


                        });
                    }

                    @NonNull
                    @Override
                    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.userrow,parent, false);
                        return new UserViewHolder(view);
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
}