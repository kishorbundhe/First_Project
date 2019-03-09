package com.example.first_project;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;


public class VideoPlaying extends AppCompatActivity {
    private PlayerView playerview;
    private SimpleExoPlayer player;
    private String url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private ProgressBar loading;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_playing);

        playerview = (PlayerView) findViewById(R.id.video_view);
        loading = (ProgressBar) findViewById(R.id.loading);
        url=getIntent().getStringExtra("url");
        try{
            url=getIntent().getStringExtra("url");
            Log.v("url Vlue******88**",url);
            if (url==null){
                throw new Exception();
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),
                    " Url is NULL ",
                    Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        playerview.setPlayer(null);
        player.release();
        player = null;
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }

    }


    private void hideSystemUi() {
        playerview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

   // @Override
//    public void onStart() {
//        super.onStart();
//        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
//        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
//                Util.getUserAgent(this, "my-application"), defaultBandwidthMeter);
//
//
//        playerview.setPlayer(player);
////        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "My-application"));
//        Uri uri = Uri.parse(url);
//        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//        player.prepare(mediaSource);
//        player.setPlayWhenReady(true);
//
//
//
//    }
    @Override
    public void onStart() {
        super.onStart();
        hideSystemUi();
        if (Util.SDK_INT > 23) {
            Intialize();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            Intialize();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
public void Intialize(){
    TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
    player = ExoPlayerFactory.newSimpleInstance(
            new DefaultRenderersFactory(this),
            new DefaultTrackSelector(adaptiveTrackSelection),
            new DefaultLoadControl());

    //init the player
    playerview.setPlayer(player);

    //-------------------------------------------------
    DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
    // Produces DataSource instances through which media data is loaded.
    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
            Util.getUserAgent(this, "Exo2"), defaultBandwidthMeter);

    //-----------------------------------------------
    //Create media source
    //String hls_url = "YOUR STREAMING URL HERE";

    url=getIntent().getStringExtra("url");
    Log.v("URL*******",url);
    Uri uri = Uri.parse(url);
    //Handler mainHandler = new Handler();


    ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);


    player.setPlayWhenReady(playWhenReady);
    player.addListener(new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case ExoPlayer.STATE_READY:
                    loading.setVisibility(View.GONE);
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    loading.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Toast.makeText(getApplicationContext(),
                    "Something Went Wrong ",
                    Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }

        @Override
        public void onSeekProcessed() {

        }
    });
    player.seekTo(currentWindow, playbackPosition);
    player.prepare(mediaSource, true, false);

}

}
