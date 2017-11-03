package com.example.caroline.musiclyricsplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainMusicPlayer extends AppCompatActivity {
    private String lyricPhrase, googleSearchURL, title, artist;
    private String lyricsComURL; //lyricsComURL is for teh search results on lyrics.com
    private String lyricsUrl; //lyricsUrl is the url we will need to parse for the actual lyrics to the song
    private TextView text;
    private Button goodLyrics;
    public static final String TAG = "main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lyricPhrase = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music_player);
        wireWidgets();

    }

    private void letsGO() throws IOException, ExecutionException, InterruptedException {
        ArrayList<String> lyrics = new ArrayList<>();
        lyricPhrase = "i got the eye of the tiger";
        Log.d(TAG, "letsGO: "+lyricPhrase.length());
        int len = lyricPhrase.length();
        int last = 0;
            for(int i =0; i < len ; i++){
                if(i != len-1) {
                    if (lyricPhrase.substring(i, i + 1).equals(" ")) { //if a space
                        Log.d(TAG, "letsGO: " + lyricPhrase.substring(last, i)); //goes from beginng to end
                        lyrics.add(lyricPhrase.substring(last, i)); //add word to array list
                        last = i+1;
                    }
                    /* else if(lyricPhrase.substring(i, i + 1).equals("\'")){
                        lyricPhrase = lyricPhrase.substring(0, i) + lyricPhrase.substring(i+1, len);
                    }*/
                } else {
                    lyrics.add(lyricPhrase.substring(last, len));
                }
            }
        Log.d(TAG, "letsGO: out of loop");
        Log.d(TAG, "letsGO: lyric phrase "+lyricPhrase);
        lyricsComURL = "https://www.lyrics.com/lyrics/";
        int j = 0;
        while(j < lyrics.size()){
            lyricsComURL = lyricsComURL + "%20" + lyrics.get(j);
            j++;
            Log.d(TAG, "letsGO: creating lyricsComURL");
        }
        Log.d("main class", "letsGO: " + lyricsComURL);



        String lyricsComHTMLBasic = new URLPinger().execute(lyricsComURL).get();

        Log.d(TAG, "letsGO: "+ lyricsComHTMLBasic);
        HTMLReader htmlReader = new HTMLReader(lyricsComHTMLBasic);

        boolean working = true;

        if (working) {
            artist = htmlReader.findComposer();
            title = htmlReader.findTitle();
            lyricsUrl = htmlReader.findLyricsURL();
        } else {
            artist = "Hamilton";
            title = "The Schuyler Sisters";
            lyricsUrl = "https://www.lyrics.com/lyric/32212242/Lin-Manuel+Miranda/Alexander+Hamilton";
        }
        SongObject song = new SongObject(title, artist, lyricsUrl);

        //TODO use SongObject song to get uri from google
        //Gets URI from google
        TitleToSpotifyURI titleToSpotifyURI = new TitleToSpotifyURI(title);
        googleSearchURL = titleToSpotifyURI.constructSearchURL();
        String googleSearchHTMLCode = new URLPinger().execute(googleSearchURL).get();

        String uri = titleToSpotifyURI.getURIFromHTML(googleSearchHTMLCode);

        String lyricsText = "NEED to write a html reader method to get lyrics"; //new URLPinger().execute(lyricsUrl).get();


        //sends data over to spotify activity
        Intent i = new Intent(this, MainLyricsActivity.class);
        i.putExtra("Title",song.getTitle());
        i.putExtra("Artist",song.getArtist());
        i.putExtra("URI", uri);
        i.putExtra("URL", lyricsUrl); // TODO use picasso image library
        i.putExtra("Lyrics", lyricsText);
        startActivity(i);
    }

    private void wireWidgets() {
       text  = (TextView)findViewById(R.id.show_text);
       goodLyrics = (Button) findViewById(R.id.lyrics_good);
       goodLyrics.setText("Go");
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
                }
                break;
        }
    }

    public void letsGO(View view) throws IOException, ExecutionException, InterruptedException {
        letsGO();
    }

    private class URLPinger extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URLReader urlReader = new URLReader(strings[0]);
            try {
                return urlReader.readerReturn();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}