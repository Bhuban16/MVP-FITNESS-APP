package com.example.mvpfitnessapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.annotation.Nullable;

public class Fullscreen extends AppCompatActivity {

    private  static final int PICK_VIDEO= 1;
    private CountDownTimer countDownTimer;
    private boolean mTimerRunning;
    private  long mStartTimeInMillis ;
    private long mTimeLeftInMillis ;
    private SimpleExoPlayer player;
    private VideoView playerView;
    private EditText textView , description ;
    boolean fullscreen = false;
   private Button Continue , Pause;
    private Uri videoUri;
    private Uri videoUr;
   private Button Final, choose ,  save;
    private  EditText time , set , his;
    private DatabaseReference myRef;
   private ImageView fullscreenButton;
    private String url ;
    private  FirebaseAuth firebaseAuth;
    private boolean playwhenready = true;
    private int currentWindow = 0;
    private long playbackposition = 0;
    private UploadWorkoutHistory workoutHistory ;
    long date1;
    Member member;
    UploadTask uploadTask;
    String currentTime2;
    MediaController mediaController;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String title , child;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        workoutHistory = new UploadWorkoutHistory();
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Fullscreen");
        Intent intent = getIntent();
        child = intent.getExtras().getString("child");
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
       choose =findViewById(R.id.updatevideo);

        storageReference = FirebaseStorage.getInstance().getReference("Video");
        databaseReference = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("video").child(child).child("Menu");
        mProgress = findViewById(R.id.prog);
         time = findViewById( R.id.time0);
        set = findViewById(R.id.set);
        playerView = findViewById(R.id.exoplayer_fullscreen);
        textView = findViewById(R.id.tv_fullscreen);
        description=findViewById(R.id.description);
        save = findViewById(R.id.update);
        fullscreenButton = playerView.findViewById(R.id.exoplayer_fullscreen_icon);
        UploadTask uploadTask;

       url = intent.getExtras().getString("ur");

        title = intent.getExtras().getString("nam");
        String Description = intent.getExtras().getString("des");
        String Set = intent.getExtras().getString("set");
        String Timer = intent.getExtras().getString("time");

        videoUr = Uri.parse(intent.getExtras().getString("ur"));
        playerView.setVideoURI(videoUr);
        description.setText(Description);
        textView.setText(title);
        time.setText(Timer);
        set.setText(Set);
        mediaController = new MediaController(this);
        playerView.setMediaController(mediaController);
        mediaController.setAnchorView(playerView);
        playerView.start();


    choose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_VIDEO);
        }
    });



save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        UploadVideo();
        mProgress.setVisibility(View.VISIBLE);

    }
});

    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO || resultCode == RESULT_OK || data != null || data.getData()  !=null){

            try {
                videoUri = data.getData();
                playerView.setVideoURI(videoUri);
            }catch (Exception e){
                Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
            }

        }



    }
    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private MediaSource buildMediaSource(Uri uri){
        DataSource.Factory datasourcefactory =
                new DefaultHttpDataSourceFactory("video");
        return new ProgressiveMediaSource.Factory(datasourcefactory)
               .createMediaSource(uri);
    }







    @Override
    protected void onResume() {
        super.onResume();

        if (Util.SDK_INT >= 26 || player == null ) {
            //initializeplayer();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (Util.SDK_INT > 26 ) {
            releasePlayer();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (Util.SDK_INT >= 26) {
            releasePlayer();
        }
    }

    private void releasePlayer(){
        if (player !=null){
             playwhenready = player.getPlayWhenReady();
             playbackposition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player = null;
        }
    }



    private  void UploadVideo() {
        String videoName = textView.getText().toString();
        String Description = description.getText().toString();
        String search = textView.getText().toString().toLowerCase();
        String Timer = time.getText().toString();
        String Set = set.getText().toString();


        if (videoUri != null) {

            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExt(videoUri));
            uploadTask = reference.putFile(videoUri);

            Task<Uri> uritask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                Query query = databaseReference.orderByChild("name").equalTo(title);


                               query.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       for (DataSnapshot ds: snapshot.getChildren()){
                                          ds.getRef().child("videourl").setValue(downloadUrl.toString());
                                           ds.getRef().child("name").setValue(videoName);
                                           ds.getRef().child("search").setValue(search);
                                           ds.getRef().child("timer").setValue(Timer);
                                           ds.getRef().child("workoutSet").setValue(Set);
                                           ds.getRef().child("description").setValue(Description);
                                       }
                                       Toast.makeText(Fullscreen.this, "Data saved", Toast.LENGTH_SHORT).show();
                                       mProgress.setVisibility(View.INVISIBLE);
                                       startActivity(new Intent(Fullscreen.this,ShowVideo.class ));
                                       finish();
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });

                            } else {
                               Toast.makeText(Fullscreen.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
                        Query query = databaseReference.orderByChild("name").equalTo(title);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds: snapshot.getChildren()){
                                    ds.getRef().child("name").setValue(videoName);
                                    ds.getRef().child("search").setValue(search);
                                    ds.getRef().child("timer").setValue(Timer);
                                    ds.getRef().child("workoutSet").setValue(Set);
                                    ds.getRef().child("description").setValue(Description);
                                }
                                Toast.makeText(Fullscreen.this, "Data saved", Toast.LENGTH_SHORT).show();
                                mProgress.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(Fullscreen.this,ShowVideo.class ));
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }


        }



