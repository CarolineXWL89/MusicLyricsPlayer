package com.example.caroline.musiclyricsplayer;

/**
 * Created by princ on 24/10/2017.
 */

public class TitleSpotifyURI {
    private String title,URI;
    private int start;

    /*
    Example Google search links to compare
    https://www.google.com/search?safe=strict&q=i+whistle+a+happy+tune+spotify&oq=i+whistle+a+happy+tune+spotify&gs_l=psy-ab.3...5657.9742.0.9975.8.8.0.0.0.0.284.488.2-2.2.0....0...1.1.64.psy-ab..6.2.487...0j35i39k1j0i67k1j0i22i30k1.0.mc7rWBhsHzs
    https://www.google.com/search?safe=strict&source=hp&q=don%27t+stop+me+now+spotify&oq=don%27t+stop+me+now+spotify&gs_l=psy-ab.3...4676.10661.0.10906.32.22.1.0.0.0.411.2824.2-8j0j2.10.0....0...1.1.64.psy-ab..21.11.2828.0..0j35i39k1j0i131k1j0i20i264k1j0i67k1j0i10i67k1j0i22i30k1.0.t05TmiK9U14
    https://www.google.com/search?safe=strict&source=hp&q=think+of+me+spotify --> directly into the URL bar;
    ^^top two are obtained when gotten from search bar directly
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
