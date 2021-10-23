package com.example.mvpfitnessapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserFullscreen extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private TextView textView, description;
    boolean fullscreen = false;
   private Button Continue, Pause;
   private Button Final;
  private   TextView timer, set, his;
   private DatabaseReference myRef;
  private   ImageView fullscreenButton;
    private String url;
    private FirebaseAuth firebaseAuth;
    private boolean playwhenready = true;
    private int currentWindow = 0;
    private long playbackposition = 0;
    private UploadWorkoutHistory workoutHistory;
    long date1;
   private String currentTime2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_fullscreen);

        workoutHistory = new UploadWorkoutHistory();
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Fullscreen");

        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        Final = findViewById(R.id.Final12);
        Pause = findViewById(R.id.Pause1);
        Pause.setVisibility(View.GONE);
        myRef = FirebaseDatabase.getInstance().getReference("video");
        timer = findViewById(R.id.timer11);
        set = findViewById(R.id.set1);
        playerView = findViewById(R.id.exoplayer_fullscreen1);
        textView = findViewById(R.id.tv_fullscreen1);
        description = findViewById(R.id.description1);
        Continue = findViewById(R.id.Continue1);
        fullscreenButton = playerView.findViewById(R.id.exoplayer_fullscreen_icon);
        Final.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        url = intent.getExtras().getString("ur");
        String title = intent.getExtras().getString("nam");
        String Description = intent.getExtras().getString("des");
        String Set = intent.getExtras().getString("set");
        String Timer = intent.getExtras().getString("time");
        description.setText(Description);
        textView.setText(title);
        set.setText("Workout Set : " + Set);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Continue.setVisibility(View.GONE);

                long millisInput = Long.parseLong(Timer) * 1000;
                timer.setVisibility(View.VISIBLE);
                setTime(millisInput);


                Pause.setVisibility(View.VISIBLE);


            }
        });

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Final.setVisibility(View.VISIBLE);
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        Final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                firebaseAuth = FirebaseAuth.getInstance();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef2 = firebaseDatabase.getReference("Workout History").child((firebaseAuth.getUid()));
                workoutHistory.setTimeEnd(currentTime);
                workoutHistory.setTimeStart(currentTime2);
                workoutHistory.setWorkoutDate(getDateToday());
                workoutHistory.setWorkoutName(title);


                String i = myRef2.push().getKey();

                myRef2.child(i).setValue(workoutHistory);

                Toast.makeText(UserFullscreen.this, "Data saved", Toast.LENGTH_SHORT).show();


            }
        });
        updateCountDownText();

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(UserFullscreen.this, R.drawable.ic_fullscreen_expand));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);
                    fullscreen = false;
                    textView.setVisibility(View.VISIBLE);
                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(UserFullscreen.this, R.drawable.ic_fullscreen_skrink));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);
                    fullscreen = true;
                    textView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory datasourcefactory =
                new DefaultHttpDataSourceFactory("video");
        return new ProgressiveMediaSource.Factory(datasourcefactory)
                .createMediaSource(uri);
    }


    private void initializeplayer() {
        player = ExoPlayerFactory.newSimpleInstance(this);


        Uri uri = Uri.parse(url);
        MediaSource mediasource = buildMediaSource(uri);
        LoopingMediaSource loopingMediaSource = new LoopingMediaSource(buildMediaSource(uri), 50);


        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
        player.seekTo(currentWindow, playbackposition);
        // player.prepare(mediasource, false, false);
        player.prepare(loopingMediaSource, false, false);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Util.SDK_INT >= 26) {
            initializeplayer();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (Util.SDK_INT >= 26 || player == null) {
            //initializeplayer();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (Util.SDK_INT > 26) {
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

    private void releasePlayer() {
        if (player != null) {
            playwhenready = player.getPlayWhenReady();
            playbackposition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        player.stop();
        releasePlayer();

        final Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }

    private void setTime(long milliseconds) {

        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                Pause.setVisibility(View.INVISIBLE);

            }
        }.start();
        mTimerRunning = true;
        Pause.setText("Pause");
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        mTimerRunning = false;
        Pause.setText("Resume");

    }


    private void resetTimer() {

        timer.setVisibility(View.VISIBLE);
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        Pause.setVisibility(View.GONE);

    }

    private void updateCountDownText() {

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;

        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        timer.setText(timeLeftFormatted);
    }

    private String getDateToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        return today;
    }

}



