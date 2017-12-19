package com.example.caroline.musiclyricsplayer;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by princ on 30/11/2017.
 * This is also wholly unnecessary just keeping it for keeping it's sake.
 */

public class BoilerpipeGenerator {
    private String lyricsWebsiteURL, boilerPipeURL, realLyrics;
    URL realBoilerPipeURL;
    public BoilerpipeGenerator(String lyricsWebsiteURL){
        this.lyricsWebsiteURL = lyricsWebsiteURL;
        boilerPipeURL = "http://boilerpipe-web.appspot.com/extract?url=" + lyricsWebsiteURL + "&output=text";
        try {
            realBoilerPipeURL = new URL(boilerPipeURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } //why is there a MalformedURLException now???
    }

    //makes Boilerpipe URL --> read by URLReader?
    public String getBoilerPipeURL(String urlToBoilerpipe){
        boilerPipeURL = "http://boilerpipe-web.appspot.com/extract?url=" + lyricsWebsiteURL + "&output=text";
        /*try {
            realBoilerPipeURL = new URL(boilerPipeURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } //why is there a MalformedURLException now???
        try {
            realLyrics = ArticleExtractor.INSTANCE.getText(realBoilerPipeURL);
        } catch (BoilerpipeProcessingException e) {
            e.printStackTrace();
        }*/
        return boilerPipeURL;
    }
}
