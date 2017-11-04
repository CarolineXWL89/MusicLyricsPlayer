package com.example.caroline.musiclyricsplayer;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by princ on 24/10/2017.
 * Runs through the HTML code and finds the div classes to access title, composer, and URL for lyrics page
 */

public class HTMLReader {
    private String title, artist, HTMLCode, lyricsURL;
    private ArrayList<Integer> positions = new ArrayList<>();
    private int tRefL;
    private int aRefL;
    private int lRefL;
    private int iRefL;
    private static final String TITLE_REF = "<a href=\"/lyric";
    private static final String ARTIST_REF = "artists\"><a href=\"artist/";
    private static final String LYRIC_LINK_REF = "lyric-body\" onclick=\"location.href='https://www.lyrics.com/";
    private static final String IMAGE_LINK_REF="<a href=\"/album";

    //private static final String MY_TAG = "testActivity";
    //private int firstSearched;

    public HTMLReader(String HTMLCode){
        this.HTMLCode = HTMLCode;
        int firstSearchedBeforeTitle = HTMLCode.indexOf(TITLE_REF);
        positions.add(firstSearchedBeforeTitle);
        tRefL = TITLE_REF.length();
        int firstBeforeArtist = HTMLCode.indexOf(ARTIST_REF);
        positions.add(firstBeforeArtist);
        aRefL = ARTIST_REF.length();
        int firstBeforeLyricLink = HTMLCode.indexOf(LYRIC_LINK_REF);
        positions.add(firstBeforeLyricLink);
        lRefL = LYRIC_LINK_REF.length();
        int firstBeforeAlbumArtLink = HTMLCode.indexOf(IMAGE_LINK_REF);
        positions.add(firstBeforeAlbumArtLink);
        iRefL = IMAGE_LINK_REF.length();
    }

    public String findTitle(){
        int firstTitle = positions.get(0);
        String shortenedSearch = HTMLCode.substring(firstTitle + tRefL);
        int sideCarrotTitleStart = shortenedSearch.indexOf(">");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("<");
        title = shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd);
        return title;
    }

    public String findComposer(){
        int firstComposer = positions.get(1);
        String shortenedSearch = HTMLCode.substring(firstComposer + aRefL);
        int sideCarrotTitleStart = shortenedSearch.indexOf(">");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("<");
        artist = shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd);
        return artist;
    }

    public String findLyricsURL(){
        int firstLyricLink = positions.get(2);
        String shortenedSearch = HTMLCode.substring(firstLyricLink + lRefL);
        int sideCarrotTitleStart = shortenedSearch.indexOf("/");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("'");
        lyricsURL = "https://www.lyrics.com/" + shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd);
        return lyricsURL;
    }

    public String findAlbumArt(){
        int firstImgLink = positions.get(3);
        String shortenedSearch = HTMLCode.substring(firstImgLink + iRefL);
        int sideCarrotTitleStart = shortenedSearch.indexOf(">");
        int sideCarrotTitleEnd = shortenedSearch.indexOf("<");
        artist = shortenedSearch.substring(sideCarrotTitleStart + 1, sideCarrotTitleEnd); //WHY are we returning a string?
        return artist; //NO??? TODO write a CORRECT method that returns albumArt --> how do you set?

    }

    public String getLyrics() {
        //TODO write this method properly

        return "NEED to write a html reader method to get lyrics";
    }
}
