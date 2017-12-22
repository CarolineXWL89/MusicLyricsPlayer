package com.example.caroline.musiclyricsplayer;


import android.util.Log;

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
    private static final String TAG = "urlreader";
    private static final String RICK_ROLL = "We're no strangers to love\nYou know the rules and so do I\n" +
            "A full commitment's what I'm thinking of\n" +
            "You wouldn't get this from any other guy\n" +
            "I just wanna tell you how I'm feeling\nGotta make you understand\nNever gonna give you up\n" +
            "Never gonna let you down\n" +
            "Never gonna run around and desert you\n" +
            "Never gonna make you cry\n" +
            "Never gonna say goodbye\nNever gonna tell a lie and hurt you\n" +
            "We've known each other for so long\n" +
            "Your heart's been aching but\n" +
            "You're too shy to say it\n" +
            "Inside we both know what's been going on\nWe know the game and we're gonna play it\n" +
            "And if you ask me how I'm feeling\n" +
            "Don't tell me you're too blind to see\n" +
            "Never gonna give you up\n" +
            "Never gonna let you down\n" +
            "Never gonna run around and desert you\nNever gonna make you cry\n" +
            "Never gonna say goodbye\n" +
            "Never gonna tell a lie and hurt you\n" +
            "Never gonna give you up\n" +
            "Never gonna let you down\n" +
            "Never gonna run around and desert you\nNever gonna make you cry\n" +
            "Never gonna say goodbye\n" +
            "Never gonna tell a lie and hurt you\n" +
            "(Ooh, give you up)\n" +
            "(Ooh, give you up)\n" +
            "(Ooh)\n" +
            "Never gonna give, never gonna give\n" +
            "(Give you up)\n(Ooh)\n" +
            "Never gonna give, never gonna give\n(Give you up)\n" +
            "We've know each other for so long\n" +
            "Your heart's been aching but\n" +
            "You're too shy to say it\n" +
            "Inside we both know what's been going on</p><p class='verse'>We know the game and we're gonna play it\n" +
            "I just wanna tell you how I'm feeling\n" +
            "Gotta make you understand\n" +
            "Never gonna give you up\nNever gonna let you down\n" +
            "Never gonna run around and desert you\n" +
            "Never gonna make you cry\n" +
            "Never gonna say goodbye\n" +
            "Never gonna tell a lie and hurt you\n" +
            "Never gonna give you up\n" +
            "Never gonna let you down\n" +
            "Never gonna run around and desert you\n" +
            "Never gonna make you cry\n" +
            "Never gonna say goodbye\n" +
            "Never gonna tell a lie and hurt you\n" +
            "Never gonna give you up\n" +
            "Never gonna let you down\n" +
            "Never gonna run around and desert you\n" +
            "Never gonna make you cry\n" +
            "Never gonna say goodbye\n" +
            "Never gonna tell a lie and hurt you\n";

    public URLReader(String url){
        this.urlString = url;
    }

    /**
     * Takes the URL input and returns an HTML code
     * @return HTML code for webpage
     */
    public String readerReturn() throws IOException {

//        StringBuilder content = new StringBuilder(2000);
        String content = "";

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
                content += line + "\n";
                Log.d("Appended Line:", line);
            }
            Log.d("Content: ", content.toString());
            bufferedReader.reset();
            bufferedReader.close();
        }
        catch(Exception e)
        {
            content += RICK_ROLL;
            Log.d("urlreader", "readerReturn: "+ e);
            e.printStackTrace();
        }
        HTMLCode = content.toString();
        return HTMLCode;
    }


}
