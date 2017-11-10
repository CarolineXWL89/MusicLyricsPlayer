package com.example.caroline.musiclyricsplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

public class MainLyricsActivity extends Fragment implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback

    {
        private ImageButton pauseButton;
        private TextView songNameView, songLyrics, songArtist;
        private Toolbar toolbar;
        private ImageView img;

        //sets up spotify account
        private static final String CLIENT_ID = "af779a6467964225b9b369ec9bc7f330";
        private static final String REDIRECT_URI = "http://spotifyplayer1.com/callback";
        private static final int REQUEST_CODE = 497;
        private final Context context = getActivity();
        private View rootView;

        private Player mPlayer;
        private boolean paused = false;
        private String uri, url, title, artist, lyrics, albumArt;
        public static final String TAG = "lyrics";
        private int duration;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            super.onCreate(savedInstanceState);
            rootView = inflater.inflate(R.layout.activity_main_lyrics, container, false);
            AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                    AuthenticationResponse.Type.TOKEN,
                    REDIRECT_URI);
            builder.setScopes(new String[]{"user-read-private", "streaming"});
            AuthenticationRequest request = builder.build();
            AuthenticationClient.openLoginActivity((Activity) context, REQUEST_CODE, request);
            //gets info from shared prefrences
            Context context = getActivity();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            //todo set default values to be rickrolling not ""
            uri = sharedPref.getString("URI", "");
            url = sharedPref.getString("URL","");
            title = sharedPref.getString("Title","");
            artist = sharedPref.getString("Artist","");
            albumArt=sharedPref.getString("Image","");
            lyrics = sharedPref.getString("Lyrics","");
            wireWidgets();
            setUpWidgets();
            return rootView;
        }

        private void setUpWidgets() {
            //songNameView.setText(title);
            songLyrics.setText(lyrics);
            //songArtist.setText(artist);
            toolbar.setTitle(title);
            Picasso.with(context).load(albumArt).into(img);

        }

        private void wireWidgets() {
            pauseButton = (ImageButton) rootView.findViewById(R.id.button_pause);
            pauseButton.setBackgroundResource(R.drawable.grey_button);
            //songNameView = (TextView) findViewById(R.id.song_title);
            songLyrics = (TextView) rootView.findViewById(R.id.lyrics);
            //songArtist = (TextView) findViewById(R.id.artist);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            img = rootView.findViewById(R.id.album_art);
        }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(context, response.getAccessToken(), CLIENT_ID);
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
    public void onDestroy() {
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
        mPlayer.playUri(null, "spotify:track:" + uri, 0, 0);
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