package com.example.caroline.musiclyricsplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//import de.l3s.boilerpipe.BoilerpipeProcessingException;
//import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class MainMusicPlayer extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {
    private String lyricPhrase, googleSearchURL, title, artist;
    private String lyricsComURL; //lyricsComURL is for the search results on lyrics.com
    private String lyricsUrl; //lyricsUrl is the url we will need to parse for the actual lyrics to the song
    //private TextView text;
    private EditText editText;
    private Button goodLyrics;
    private ArrayList<String> lyrics;
    private String fullLyrics = "";
    public static final String TAG = "main";
    private SongObject songObject = new SongObject("", "", "");
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    protected void onCreate(Bundle savedInstanceState) {
        lyricPhrase = "";
        lyrics = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music_player);
        wireWidgets();
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void letsGO() throws IOException, ExecutionException, InterruptedException/*, BoilerpipeProcessingException*/ {
        lyricPhrase = editText.getText().toString();
        //lyricPhrase = "Risin' up back on the street "; //normal set by speaking
        tokenize(); //takes phrase set by speaking and returns arraylist of the words
        create1stURL(); //creates the lyrics.com searching url
        String lyricsComHTMLBasic = new URLPinger().execute(lyricsComURL).get(); //gets the html from search results
        Log.d("lyricsComURL", lyricsComURL);
        HTMLReader htmlReader = new HTMLReader(lyricsComHTMLBasic); //creates html parser

        Log.d(TAG, "letsGO: "+lyricsComHTMLBasic);
        if (lyricsComHTMLBasic != null) {
            artist = htmlReader.findComposer();
            Log.d(TAG, "letsGO: artist: "+artist);
            title = htmlReader.findTitle();
            Log.d(TAG, "letsGO: title: "+title);
            lyricsUrl = htmlReader.findLyricsURL();
            Log.d(TAG, "letsGO: lyricsURL: "+lyricsUrl);
        } else {
            artist = getArtist();
            title = getSongTitle();
            lyricsUrl = getLyricsURL();
        }

        //Gets URI from google, gets lyrics and album art from second website
        String uri = getURI();
        String lyricsText = getSongLyrics();
        Log.d(TAG, "letsGO: Image Url:"+getImageURL());
        String imageURL = getImageURL();
        Log.d(TAG, "letsGO: "+ uri);

        //sends data over to spotify activity
        Intent i = new Intent(this, MainLyricsActivity.class);
        i.putExtra("Title",title);
        i.putExtra("Artist", artist);
        i.putExtra("URI", uri);
        i.putExtra("URL", lyricsUrl);
        i.putExtra("Lyrics", lyricsText);
        i.putExtra("Image", imageURL);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private String getImageURL() throws ExecutionException, InterruptedException {
        String htmlForLyrics = new URLPinger().execute(lyricsUrl).get(); //gets the html from search results
        HTMLReader htmlReader = new HTMLReader(htmlForLyrics); //creates html parser
        return htmlReader.findAlbumArt();
    }

    private String getSongLyrics() throws ExecutionException, InterruptedException, IOException/*, BoilerpipeProcessingException*/ {
        String htmlForLyrics = new URLPinger().execute(lyricsUrl).get(); //gets the html from search results
        //HTMLReader htmlReader = new HTMLReader(htmlForLyrics); //creates html parser
        return getLyrics();

        /*
        songObject.setTitle(title);
        songObject.setArtist(artist);
        ArrayList<String> titleWords = new ArrayList<>();
        ArrayList<String> artistWords = new ArrayList<>();
        titleWords = songObject.separateWords(title);
        for(String wordsInTitle : titleWords){
            Log.d("title words:", wordsInTitle);
        }
        artistWords = songObject.separateWords(artist);
        for(String wordsForArtist : artistWords){
            Log.d("artist words:", wordsForArtist);
        }

        lyricsUrl =  songObject.createLyricsPageURL(titleWords, artistWords);
        //URL lyricsRealURLObject = new URL(lyricsUrl); //TODO need this later
        //URLReaderTrial urlReaderTrial = new URLReaderTrial(lyricsRealURLObject); //TODO need this later
//        URLReader urlReaderLyrics = new URLReader(lyricsUrl); //TODO need this later
        //Log.d("URL for lyrics: ", lyricsUrl);
//        String htmlCodeFull = urlReaderLyrics.readerReturn(); //TODO need this later
        String htmlCodeFull = new URLPinger().execute(lyricsUrl).get();
        //String htmlCodeFull = urlReaderTrial.getHTMLLyrics(); TODO need this later
        //ArrayList<String> lyricWords = new ArrayList<>();
        /*try {
            URL lyricsURLObject = new URL(lyricsUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        //URL lyricsURLObject = new URL(lyricsUrl);
        //String htmlCode = ArticleExtractor.INSTANCE.getText(lyricsURLObject);
        //DON'T USE OTHERS
        /*URLReader urlReaderLyrics = new URLReader(lyricsUrl);
        try {
            htmlCode = urlReaderLyrics.readerReturn();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //todo below was noraml
        /*LyricsPageHTMLReader lyricsPageHTMLReader = new LyricsPageHTMLReader(htmlCodeFull, title, artistWords);
        String reducedHTMLCode = lyricsPageHTMLReader.getHTMLCode();
        int numberOfSections = lyricsPageHTMLReader.numberSections(reducedHTMLCode);
        String[] stanzaSections = lyricsPageHTMLReader.stanzaLocators(reducedHTMLCode, numberOfSections);
        ArrayList<ArrayList<String>> arrayOfLyricArraysFromStanzas = new ArrayList<>();
        int numSections = numberOfSections;
        while(numSections > 0){
            ArrayList<String> lyrics = lyricsPageHTMLReader.separateLyricsWords(stanzaSections[numberOfSections - numberOfSections]);
            arrayOfLyricArraysFromStanzas.add(lyrics);
        }*/
        //todo below was commented out
        /*lyrics = lyricsPageHTMLReader.findLyrics(htmlCode);
        int l = lyrics.size();
        for(int i = 0; i < l; i++){
            fullLyrics += lyrics.get(i);
        }
        return fullLyrics;*/
        //todo below was normal
        /*for(ArrayList<String> lyricSets : arrayOfLyricArraysFromStanzas){
            for(String lyric : lyricSets){
                if(lyric.equals("<br>") || lyric.equals("</p><p class='verse'>")){
                    fullLyrics += "\n";
                }
                else{
                    fullLyrics += lyric;
                }
            }
        }
        return fullLyrics;*/
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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
        int last = 0; //fkjdhFJKASN OG
        for(int i =0; i < len ; i++){
            if(i != len-1) {
                if (lyricPhrase.substring(i, i + 1).equals(" ")) { //if a space
                    Log.d(TAG, "letsGO: " + lyricPhrase.substring(last, i)); //goes from beginng to end
                    lyrics.add(lyricPhrase.substring(last, i)); //add word to array list
                    last = i+1;
                } else if(lyricPhrase.substring(i, i + 1).equals("'")){
                    Log.d(TAG, "tokenize: "+lyricPhrase.substring(i, i + 1));
                    lyricPhrase = lyricPhrase.substring(0, i) + lyricPhrase.substring(i+1, len);
                    i--;
                    len--;
                }
            } else {
                lyrics.add(lyricPhrase.substring(last, len));
            }
        }
    }

    private void wireWidgets() {
       //text  = (TextView)findViewById(R.id.show_text);
       goodLyrics = (Button) findViewById(R.id.lyrics_good);
       editText = (EditText) findViewById(R.id.editable_text);
       goodLyrics.setText("Go");
//       goodLyrics.setVisibility(View.GONE);
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
                    editText.setText(result.get(0));
                    lyricPhrase = editText.getText().toString();
                    Log.d(TAG, "onActivityResult: "+lyricPhrase);
                    goodLyrics.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void letsGO(View view) throws IOException, ExecutionException, InterruptedException/*, BoilerpipeProcessingException*/ {
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

    public String getLyrics() {
        String lyrics = "We're no strangers to love\nYou know the rules and so do I\n" +
                "A full commitment's what I'm thinking of\n" +
                "You wouldn't get this from any other guy\n" +
                "I just wanna tell you how I'm feeling\nGotta make you understand\nNever gonna give you up\n" +
                "Never gonna let you down\n" +
                "Never gonna run around and desert you\n" +
                "Never gonna make you cry\n" +
                "Never gonna say goodbye\nNever gonna tell a lie and hurt you\n" +
                "We've known each other for so long\n" +
                "Your heart's been aching but\n" +
                "You're too shy to say it\n" +
                "Inside we both know what's been going on\nWe know the game and we're gonna play it\n" +
                "And if you ask me how I'm feeling\n" +
                "Don't tell me you're too blind to see\n" +
                "Never gonna give you up\n" +
                "Never gonna let you down\n" +
                "Never gonna run around and desert you\nNever gonna make you cry\n" +
                "Never gonna say goodbye\n" +
                "Never gonna tell a lie and hurt you\n" +
                "Never gonna give you up\n" +
                "Never gonna let you down\n" +
                "Never gonna run around and desert you\nNever gonna make you cry\n" +
                "Never gonna say goodbye\n" +
                "Never gonna tell a lie and hurt you\n" +
                "(Ooh, give you up)\n" +
                "(Ooh, give you up)\n" +
                "(Ooh)\n" +
                "Never gonna give, never gonna give\n" +
                "(Give you up)\n(Ooh)\n" +
                "Never gonna give, never gonna give\n(Give you up)\n" +
                "We've know each other for so long\n" +
                "Your heart's been aching but\n" +
                "You're too shy to say it\n" +
                "Inside we both know what's been going on\nWe know the game and we're gonna play it\n" +
                "I just wanna tell you how I'm feeling\n" +
                "Gotta make you understand\n" +
                "Never gonna give you up\nNever gonna let you down\n" +
                "Never gonna run around and desert you\n" +
                "Never gonna make you cry\n" +
                "Never gonna say goodbye\n" +
                "Never gonna tell a lie and hurt you\n" +
                "Never gonna give you up\n" +
                "Never gonna let you down\n" +
                "Never gonna run around and desert you\n" +
                "Never gonna make you cry\n" +
                "Never gonna say goodbye\n" +
                "Never gonna tell a lie and hurt you\n" +
                "Never gonna give you up\n" +
                "Never gonna let you down\n" +
                "Never gonna run around and desert you\n" +
                "Never gonna make you cry\n" +
                "Never gonna say goodbye\n" +
                "Never gonna tell a lie and hurt you\n";
        return lyrics;
    }

    public String getArtist() {
        //random cmmet
        return "Rick Astley";
    }

    public String getSongTitle() {
        //random line
        return "Never Gonna Give You Up";
    }

    public String getLyricsURL() {
        //random cmment
        return  "https://www.lyrics.com/lyric/1906428/Rick+Astley/Never+Gonna+Give+You+Up";
    }

    @SuppressLint("StaticFieldLeak")
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class URLPinger extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {

//            create a url object
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