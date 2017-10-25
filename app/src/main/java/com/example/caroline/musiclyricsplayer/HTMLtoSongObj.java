package com.example.caroline.musiclyricsplayer;

import java.net.URL;

/**
 * Created by maylisw on 10/19/17.
 */

//link is to lyrics url is to search result --> URL is from lyrics.com

public class HTMLtoSongObj {
    private String url, title, artist;

    public HTMLtoSongObj(String url) {
        this.url = url;
    }

    public SongObject toObject(){
        return new SongObject(title, artist, url);
    }
}
