package com.example.mvpfitnessapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class Fullscreen extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    TextView textView;
    private String url;
    private boolean playwhenready = true;
    private int currentWindow = 0;
    private long playbackposition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);





        playerView = findViewById(R.id.exoplayer_fullscreen);
        textView = findViewById(R.id.tv_fullscreen);

        Intent intent = getIntent();
        url = intent.getExtras().getString("ur");
        String title = intent.getExtras().getString("nam");


        textView.setText(title);
    }

    private MediaSource buildMediaSource(Uri uri){
        DataSource.Factory datasourcefactory =
                new DefaultHttpDataSourceFactory("video");
        return new ProgressiveMediaSource.Factory(datasourcefactory)
               .createMediaSource(uri);
    }


    private void initializeplayer(){
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        Uri uri = Uri.parse(url);
        MediaSource mediasource = buildMediaSource(uri);
        player.setPlayWhenReady(playwhenready);
        player.seekTo(currentWindow,playbackposition);
        player.prepare(mediasource, false, false);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Util.SDK_INT >= 26 ) {
           initializeplayer();
        }

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

}