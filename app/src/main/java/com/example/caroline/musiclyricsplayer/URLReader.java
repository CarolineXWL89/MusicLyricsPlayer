package com.example.caroline.musiclyricsplayer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.*;
import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by princ on 19/10/2017.
 */

public class URLReader {
    private String HTMLCode = "";
    private String urlString = "";

    public URLReader(String url){

        this.urlString = url;
    }

    /**
     * Takes the URL input and returns an HTML code.
     * //@param URL
     * @return HTML code for webpage
     */
    public String readerReturn() throws IOException {
        /*URL oracle = null;
        BufferedReader in = null;
        String inputLine;
        try {
            oracle = new URL(url);
            in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));
            while ((inputLine = in.readLine()) != null)
                //System.out.println(inputLine);
                HTMLCode += inputLine;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
           if(in != null) {
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
        return HTMLCode;*/

        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(urlString);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

}
