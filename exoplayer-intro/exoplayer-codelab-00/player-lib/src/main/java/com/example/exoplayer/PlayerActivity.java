/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
* limitations under the License.
 */
package com.example.exoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivity extends AppCompatActivity {


    private PlayerView playerView;
    private SimpleExoPlayer player;

    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);
    playerView = findViewById(R.id.video_view);
  }

  @Override
  protected  void onStart(){
        super.onStart();

        player = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultTrackSelector());

        playerView.setPlayer(player);

      DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,
              Util.getUserAgent(this, "exo-demo"));
      ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
              .createMediaSource(Samples.MP4_URI);

      player.prepare(mediaSource);
      player.setPlayWhenReady(true);
  }

    @Override
    protected void onStop() {
        super.onStop();

        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private void initializePlayer(){

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),new DefaultLoadControl()
        );

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(getString(R.string.media_url_mp3));
    MediaSource mediaSource = buildMediaSource(uri);
    player.prepare(mediaSource,true,false);

    Uri uri = Uri.parse(getString(R.string.media_url_mp3));
    MediaSource mediaSource = buildMediaSource(uri);
    player.prepare(mediaSource, true, false);
}
private MediaSource buildMediaSource(Uri uri){
    return new ExtractorMediaSource.Factory(
            new DefaultHttpDataSourceFactory("exoplayer-codelab")
    ).createMediaSource(uri);
}

}
