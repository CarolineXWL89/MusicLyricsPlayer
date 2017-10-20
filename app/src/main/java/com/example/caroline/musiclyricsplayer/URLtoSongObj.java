package com.example.caroline.musiclyricsplayer;

import java.net.URL;

/**
 * Created by maylisw on 10/19/17.
 */

//link is to lyrics url is to search result

public class URLtoSongObj {
    private String url, title, artist;
    private URL link;

    public URLtoSongObj(String url) {
        this.url = url;
    }

    public SongObject toObject(){
        return new SongObject(title, artist, link);
    }
}
