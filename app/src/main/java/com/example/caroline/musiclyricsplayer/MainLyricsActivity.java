package com.example.caroline.musiclyricsplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

public class MainLyricsActivity extends Activity implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback

    {
        private ImageButton pauseButton;
        private TextView songNameView;


        // TODO: Replace with your client ID
        private static final String CLIENT_ID = "af779a6467964225b9b369ec9bc7f330";
        // TODO: Replace with your redirect URI
        private static final String REDIRECT_URI = "http://spotifyplayer1.com/callback";
        private static final int REQUEST_CODE = 497;


        private Player mPlayer;
        private boolean paused = false;
        private String uri, url;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_lyrics);
            AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                    AuthenticationResponse.Type.TOKEN,
                    REDIRECT_URI);
            builder.setScopes(new String[]{"user-read-private", "streaming"});
            AuthenticationRequest request = builder.build();
            AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
            Intent i = getIntent();
            uri = i.getStringExtra("URI");
            url = i.getStringExtra("URL");
            wireWidgets();

        }

        private void wireWidgets() {
            pauseButton = (ImageButton) findViewById(R.id.button_pause);
            pauseButton.setBackgroundResource(R.drawable.green_button);
            songNameView = (TextView) findViewById(R.id.song_name_view);
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MainLyricsActivity.this);
                        mPlayer.addNotificationCallback(MainLyricsActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainLyricsActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

        @Override
        public void onPlaybackEvent(PlayerEvent playerEvent) {

        }

        @Override
    public void onPlaybackError(Error error) {
        Log.d("MainLyricsActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainLyricsActivity", "User logged in");
        mPlayer.playUri(null, "spotify:track:"+uri, 0, 0);
    }

    public void pause(View view){
        if(!paused){
            mPlayer.pause(null);
            paused = true;
            pauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
        else{
            mPlayer.resume(null);
            paused = false;
            pauseButton.setImageResource(R.drawable.ic_pause_black_24dp);

        }
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainLyricsActivity", "User logged out");
    }

        @Override
        public void onLoginFailed(Error error) {
            Log.d("MainLyricsActivity", "Login Failed");
        }



    @Override
    public void onTemporaryError() {
        Log.d("MainLyricsActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainLyricsActivity", "Received connection message: " + message);
    }


}