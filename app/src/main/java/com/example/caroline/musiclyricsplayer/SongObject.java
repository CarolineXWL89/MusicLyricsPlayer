package com.example.caroline.musiclyricsplayer;

import java.net.URL;

/**
 * Created by maylisw on 10/19/17.
 */

public class SongObject {
    private String title, artist, uri;
    private URL link;

    public SongObject(String title, String artist, URL link, String uri) {
        this.title = title;
        this.artist = artist;
        this.link = link;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
