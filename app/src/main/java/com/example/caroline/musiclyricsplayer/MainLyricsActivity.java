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
        private TextView songNameView, songLyrics, songArtist;


        // TODO: Replace with your client ID
        private static final String CLIENT_ID = "af779a6467964225b9b369ec9bc7f330";
        // TODO: Replace with your redirect URI
        private static final String REDIRECT_URI = "http://spotifyplayer1.com/callback";
        private static final int REQUEST_CODE = 497;


        private Player mPlayer;
        private boolean paused = false;
        private String uri, url, title, artist, lyrics;
        public static final String TAG = "lyrics";

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
            Log.d(TAG, "onCreate: intent worked");
            Intent i = getIntent();
            uri = i.getStringExtra("URI");
            url = i.getStringExtra("URL");
            title = i.getStringExtra("Title");
            artist = i.getStringExtra("Artist"); //TODO use picasso image library for images
            wireWidgets();
            getLyrics();
            setUpWidgets();
        }

        private void getLyrics() {
            //needs to use the url that was passed to get the lyrics off the webpage and store them in the lyrics variable which can tehen be used to display them
            lyrics = "There’s nothing rich folks love more\n" +
                    "Than going downtown and slummin’ it with the poor\n" +
                    "They pull up in their carriages and gawk at the students in the common \n" +
                    "Just to watch them talk take Philip Schuyler the man is loaded \n" +
                    "Uh oh, but little does he know that His daughters, Peggy, Angelica, Eliza  Sneak into the city just to watch all the guys at work, work\n" +
                    "\n" +
                    " Angelica \n" +
                    "\n" +
                    "Work, work\n" +
                    "\n" +
                    " Eliza and Peggy (work, work) the Schuyler sisters\n" +
                    "\n" +
                    "Angelica, Peggy, Eliza (work)\n" +
                    "\n" +
                    " Daddy said to be home by sundown \n" +
                    "\n" +
                    " Daddy doesn’t need to know\n" +
                    "\n" +
                    " Daddy said not to go downtown \n" +
                    "\n" +
                    "Like I said, you’re free to go\n" +
                    "But look around, look around, the revolution’s happening in New York New York\n" +
                    "\n" +
                    " Angelica work\n" +
                    "\n" +
                    "It’s bad enough daddy wants to go to war\n" +
                    "\n" +
                    " People shouting in the square \n" +
                    "\n" +
                    "It’s bad enough there’ll be violence on our shore \n" +
                    "\n" +
                    "New ideas in the air\n" +
                    "\n" +
                    "Look around, look around \n" +
                    "\n" +
                    "Angelica, remind me what we’re looking for\n" +
                    "\n" +
                    "She’s lookin' for me\n" +
                    "\n" +
                    "Eliza, I’m lookin’ for a mind at work, work, work\n" +
                    "I’m lookin’ for a mind at work, work, work\n" +
                    "I’m lookin’ for a mind at work, work, work\n" +
                    "Whoa, whoa work\t\n" +
                    "\n" +
                    "Wooh there’s nothin’ like summer in the city\n" +
                    " Someone in a rush next to someone lookin’ pretty  Excuse me, miss, I know it’s not funny \n" +
                    "But your perfume smells like your daddy’s got money \n" +
                    "Why you slummin’ in the city in your fancy heels\n" +
                    "You searchin' for an urchin who can give you ideals?\n" +
                    "\n" +
                    "Burr, you disgust me\n" +
                    "\n" +
                    "Ah, so you’ve discussed me\n" +
                    "I’m a trust fund, baby, you can trust me\n" +
                    "\n" +
                    "I’ve been reading common sense by Thomas Paine\n" +
                    "So men say that I’m intense or I’m insane \n" +
                    "You want a revolution? I want a revelation \n" +
                    "So listen to my declaration \n" +
                    "\n" +
                    "We hold these truths to be self-evident\n" +
                    "That all men are created equal\n" +
                    "\n" +
                    "And when I meet Thomas Jefferson (unh)\n" +
                    "\n" +
                    "I’m ‘a compel him to include women in the sequel (work)\n" +
                    "\n" +
                    "Look around, look around at how lucky we are to be alive right now\n" +
                    "Look around, look around at how lucky we are to be alive right now\n" +
                    "\n" +
                    " History is happening in Manhattan and we just happen to be in the greatest city in the world \n" +
                    "In the greatest city in the world \n" +
                    "\n" +
                    "'Cause I’ve been reading common sense by Thomas Paine\n" +
                    "So men say that I’m intense or I’m insane \n" +
                    "\n" +
                    "You want a revolution I want a revelation \n" +
                    "\n" +
                    "So listen to my declaration \n" +
                    "\n" +
                    "We hold these truths to be self evident that all men are created equal\n" +
                    "Look around, look around at how lucky we are to be alive right now\n" +
                    " History is happening in Manhattan and we just happen to be in the greatest city in the world \n" +
                    "In the greatest city in the world \n" +
                    "\n" +
                    "Work, work Angelica \n" +
                    "\n" +
                    "(Work, work) Eliza and Peggy (work, work) the Schuyler sisters\n" +
                    "\n" +
                    "(Work, work) we’re looking for a mind at work, work\n" +
                    "Hey (work, work) hey (work, work) whoa (work, work)\n" +
                    "\n" +
                    "In the greatest, in the greatest city in the world  \n" +
                    "\n" +
                    "In the greatest city in the world";
        }

        private void setUpWidgets() {
            songNameView.setText(title);
            songLyrics.setText(lyrics);
            songArtist.setText(artist);

        }

        private void wireWidgets() {
            pauseButton = (ImageButton) findViewById(R.id.button_pause);
            pauseButton.setBackgroundResource(R.drawable.grey_button);
            songNameView = (TextView) findViewById(R.id.song_title);
            songLyrics = (TextView) findViewById(R.id.lyrics);
            songArtist = (TextView) findViewById(R.id.artist);
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