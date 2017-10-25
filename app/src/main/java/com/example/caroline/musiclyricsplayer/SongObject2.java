package com.example.caroline.musiclyricsplayer;

/**
 * Created by maylisw on 10/24/17.
 */

public class SongObject2 {
    private String url, uri;

    public SongObject2(String url, String uri) {
        this.url = url;
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
