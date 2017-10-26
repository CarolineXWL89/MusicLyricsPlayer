package com.example.caroline.musiclyricsplayer;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainMusicPlayer extends AppCompatActivity {
    private String lyricPhrase, url;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music_player);
        wireWidgets();
        letsGO();
    }

    private void letsGO() {
        /*ArrayList<String> lyrics = new ArrayList<>();
        lyricPhrase = (String) text.getText();
        for(int i =0; i < lyricPhrase.length(); i++){
            if(lyricPhrase.substring(i, i+1).equals(" ")){ //if a space
                lyrics.add(lyricPhrase.substring(0, i)); //add word to array list
                lyricPhrase = lyricPhrase.substring(i+1,lyricPhrase.length()); //delete word from phrase
            }
        }
        url = "https://www.lyrics.com/lyrics/";
        int j = 0;
        while(j < lyrics.size()-1){
            url= url + "%20" + lyrics.get(j);
            j++;
        }
        Log.d("URL", "letsGO: "+url);
        URLReader HTMLCodeobj = new URLReader("http://www.themusicallyrics.com/h/351-hamilton-the-musical.html");
        String htmlCode = HTMLCodeobj.readerReturn(url);
        HTMLReader htmlReader = new HTMLReader(htmlCode);
        htmlReader.findFirstOccurances();
        String artist = htmlReader.findComposer();
        String title = htmlReader.findTitle();
        String url = htmlReader.findLyricsURL();
        SongObject song = new SongObject(title, artist, url);*/
        //TODO use SongObject song to get uri
        String uri = "4TTV7EcfroSLWzXRY6gLv6"; //something you get
        url = "https://www.lyrics.com/lyric/32212242/Lin-Manuel+Miranda/Alexander+Hamilton";
        SongObject2 song2 = new SongObject2(url,uri);
        Intent i = new Intent(this, MainLyricsActivity.class);
        i.putExtra("URI", song2.getUri());
        i.putExtra("URL", song2.getUrl());
        startActivity(i);

    }

    private void wireWidgets() {
       text  = (TextView)findViewById(R.id.show_text);
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
                }
                break;
        }
    }
}
