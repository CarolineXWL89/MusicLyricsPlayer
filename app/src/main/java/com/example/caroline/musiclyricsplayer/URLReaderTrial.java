package com.example.caroline.musiclyricsplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by princ on 20/12/2017.
 */

public class URLReaderTrial {
    private URL lyricsURL;
    private String htmlCode, inputLine;

    public URLReaderTrial(URL lyricsURL){
        this.lyricsURL = lyricsURL;

    }

    public String getHTMLLyrics() throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        lyricsURL.openStream()));
        inputLine = in.readLine();
        while(inputLine != null) {
            //System.out.println(inputLine);
            htmlCode += inputLine;
        }
        in.close();

        return htmlCode;
    }
}
