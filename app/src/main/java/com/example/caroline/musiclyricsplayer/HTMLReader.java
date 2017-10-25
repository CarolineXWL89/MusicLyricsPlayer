package com.example.caroline.musiclyricsplayer;

import java.util.ArrayList;

/**
 * Created by princ on 24/10/2017.
 * Runs through the HTML code and finds the div classes to access title, composer, and URL for lyrics page
 */

public class HTMLReader {
    private String title, artist, HTMLCode, lyricsURL;
    private ArrayList<Integer> positions = new ArrayList<>();
    //private int firstSearched;

    public HTMLReader(String HTMLCode){
        this.HTMLCode = HTMLCode;
    }

    public void findFirstOccurances(){
        int firstSearchedBeforeTitle = HTMLCode.indexOf("<a href=\"/lyric");
        positions.add(firstSearchedBeforeTitle);
        int firstBeforeArtist = HTMLCode.indexOf("artists\"><a href=\"artist/");
        positions.add(firstBeforeArtist);
        int firstBeforeLyricLink = HTMLCode.indexOf("lyric-body\" onclick=\"location.href='https://www.lyrics.com/");
        positions.add(firstBeforeLyricLink);
    }


    public String findTitle(){
        int firstTitle = positions.get(0);
        String shortenedSearch = HTMLCode.substring(firstTitle);
        int sideCarrotTitleStart = shortenedSearch.indexOf(">");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("<");
        title = shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd);
        return title;
    }

    public String findComposer(){
        int firstComposer = positions.get(1);
        String shortenedSearch = HTMLCode.substring(firstComposer);
        int sideCarrotTitleStart = shortenedSearch.indexOf(">");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("<");
        artist = shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd);
        return artist;
    }

    public String findLyricsURL(){
        int firstLyricLink = positions.get(2);
        String shortenedSearch = HTMLCode.substring(firstLyricLink);
        int sideCarrotTitleStart = shortenedSearch.indexOf(">");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("<");
        lyricsURL = shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd);
        return lyricsURL;
    }
}
