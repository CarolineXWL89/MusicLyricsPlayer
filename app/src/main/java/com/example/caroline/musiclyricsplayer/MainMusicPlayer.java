package com.example.caroline.musiclyricsplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainMusicPlayer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String lyricPhrase, googleSearchURL, title, artist;
    private String lyricsComURL; //lyricsComURL is for teh search results on lyrics.com
    private String lyricsUrl; //lyricsUrl is the url we will need to parse for the actual lyrics to the song
    private TextView text;
    private Button goodLyrics;
    private ArrayList<String> lyrics;
    public static final String TAG = "main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lyricPhrase = "";
        lyrics = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music_player);
        wireWidgets();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void letsGO() throws IOException, ExecutionException, InterruptedException {
        lyricPhrase = "Risin up back on the street "; //normal set by speaking
        tokenize(); //takes phrase set by speaking and returns arraylist of the words
        create1stURL(); //creates the lyrics.com searching url
        String lyricsComHTMLBasic = new URLPinger().execute(lyricsComURL).get(); //gets the html from search results
        HTMLReader htmlReader = new HTMLReader(lyricsComHTMLBasic); //creates html parser

        //if nothing, rickrolls them
        if (htmlReader != null) {
            artist = htmlReader.findComposer();
            title = htmlReader.findTitle();
            lyricsUrl = htmlReader.findLyricsURL();
        } else {
            artist = "Rick Astley";
            title = "Never Gonna Give You Up";
            lyricsUrl = "https://www.lyrics.com/lyric/1906428/Rick+Astley/Never+Gonna+Give+You+Up";
        }

        //Gets URI from google, gets lyrics and album art from second website
        String uri = getURI();
        String lyricsText = getSongLyrics();
        Log.d(TAG, "letsGO: Image Url:"+getImageURL());
        String imageURL = getImageURL();
        Log.d(TAG, "letsGO: "+ uri);

        //sends data over to spotify activity
        //writes to shared prefrences

        Context context = MainMusicPlayer.this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Title",title);
        editor.putString("Artist", artist);
        editor.putString("URI", uri);
        editor.putString("URL", lyricsUrl);
        editor.putString("Lyrics", lyricsText);
        editor.putString("Image", imageURL);
        editor.commit();
        //TODO load fragment instad of startign intent

        Intent i = new Intent(this, MainLyricsActivity.class);
        startActivity(i);
        /*
        Fragment fragment = null;
        fragment = MainLyricsActivity();
                */
    }

    private String getImageURL() throws ExecutionException, InterruptedException {
        String htmlForLyrics = new URLPinger().execute(lyricsUrl).get(); //gets the html from search results
        HTMLReader htmlReader = new HTMLReader(htmlForLyrics); //creates html parser
        return htmlReader.findAlbumArt();
    }

    private String getSongLyrics() throws ExecutionException, InterruptedException {
        String htmlForLyrics = new URLPinger().execute(lyricsUrl).get(); //gets the html from search results
        HTMLReader htmlReader = new HTMLReader(htmlForLyrics); //creates html parser
        return htmlReader.getLyrics();
    }

    private String getURI() throws ExecutionException, InterruptedException {
        TitleToSpotifyURI titleToSpotifyURI = new TitleToSpotifyURI(title, artist); //creates an object to get the uri
        googleSearchURL = titleToSpotifyURI.constructSearchURL(); //gets the google search url
        Log.d(TAG, "getURI: "+ googleSearchURL);
        String googleSearchHTMLCode = new URLPinger().execute(googleSearchURL).get(); //gets the results
        return titleToSpotifyURI.getURIFromHTML(googleSearchHTMLCode); //returns the uri
    }

    private void create1stURL() {

        lyricsComURL = "https://www.lyrics.com/lyrics/";
        int j = 0;
        while(j < lyrics.size()){
            lyricsComURL = lyricsComURL + "%20" + lyrics.get(j);
            j++;
        }
        Log.d(TAG, "create1stURL: "+lyricsComURL);
    }

    private void tokenize() {
        int len = lyricPhrase.length();
        int last = 0;
        for(int i =0; i < len ; i++){
            if(i != len-1) {
                if (lyricPhrase.substring(i, i + 1).equals(" ")) { //if a space
                    Log.d(TAG, "letsGO: " + lyricPhrase.substring(last, i)); //goes from beginng to end
                    lyrics.add(lyricPhrase.substring(last, i)); //add word to array list
                    last = i+1;
                } //TODO work with '
                    /* else if(lyricPhrase.substring(i, i + 1).equals("\'")){
                        lyricPhrase = lyricPhrase.substring(0, i) + lyricPhrase.substring(i+1, len);
                    }*/
            } else {
                lyrics.add(lyricPhrase.substring(last, len));
            }
        }
    }

    private void wireWidgets() {
       text  = (TextView)findViewById(R.id.show_text);
       goodLyrics = (Button) findViewById(R.id.lyrics_good);
       goodLyrics.setText("Go");
       goodLyrics.setVisibility(View.GONE);
    }

    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.speech_img:
                Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                getIntent().putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                if (i.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(i, 10);
                }
                else{
                    Toast.makeText(this, "Your device doesn't speech input.", Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 10:
                if (resultCode==RESULT_OK&& data!=null) {
                    ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText(result.get(0));
                    lyricPhrase = (String) text.getText();
                    Log.d(TAG, "onActivityResult: "+lyricPhrase);
                    goodLyrics.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void letsGO(View view) throws IOException, ExecutionException, InterruptedException {
        letsGO();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class URLPinger extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URLReader urlReader = new URLReader(strings[0]);
            try {
                return urlReader.readerReturn();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: "+e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}