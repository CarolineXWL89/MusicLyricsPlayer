package com.example.caroline.musiclyricsplayer;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainMusicPlayer extends AppCompatActivity {
    private String lyricPhrase, url;
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

    private void letsGO() {
        ArrayList<String> lyrics = new ArrayList<>();
        Log.d(TAG, "letsGO: "+lyricPhrase.length());
        int len = lyricPhrase.length();
        int last = 0;
            for(int i =0; i < len ; i++){
                if(i != len-1) {
                    if (lyricPhrase.substring(i, i + 1).equals(" ")) { //if a space
                        Log.d(TAG, "letsGO: " + lyricPhrase.substring(last, i)); //goes from beginng to end
                        lyrics.add(lyricPhrase.substring(last, i)); //add word to array list
                        last = i+1;
                    } else if(lyricPhrase.substring(i, i + 1).equals("'")){
                        lyricPhrase = lyricPhrase.substring(0, i) + lyricPhrase.substring(i+1, len);
                    }
                    //TODO check links with ' cause in logcat they show up weird...
                } else {
                    lyrics.add(lyricPhrase.substring(last, i+1));
                }
            }
            Log.d(TAG, "letsGO: out of loop");
            url = "https://www.lyrics.com/lyrics/";
            int j = 0;
            while(j < lyrics.size()){
                url= url + "%20" + lyrics.get(j);
                j++;
                Log.d(TAG, "letsGO: creating url");
            }
            Log.d("main class", "letsGO: " + url);
            URLReader HTMLCodeobj = new URLReader(url);
            String htmlCode = HTMLCodeobj.readerReturn(url); //TODO fix me so i can return html code
            HTMLReader htmlReader = new HTMLReader(htmlCode);
            String artist = htmlReader.findComposer();
            String title = htmlReader.findTitle();
            String url = htmlReader.findLyricsURL();
            SongObject song = new SongObject(title, artist, url);
            //TODO use SongObject song to get uri from google
            String uri = "71X7bPDljJHrmEGYCe7kQ8"; //something you get
            url = "https://www.lyrics.com/lyric/32212242/Lin-Manuel+Miranda/Alexander+Hamilton";
            Intent i = new Intent(this, MainLyricsActivity.class);
            i.putExtra("Title",song.getTitle());
            i.putExtra("Artist",song.getArtist());
            i.putExtra("URI", uri);
            i.putExtra("URL", url); //TODO use picasso image library
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

    public void letsGO(View view) {
        letsGO();
    }
}
