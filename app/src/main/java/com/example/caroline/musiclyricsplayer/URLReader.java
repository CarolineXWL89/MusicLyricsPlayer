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
    private static final String RICK_ROLL = "\n" +
            "<div id=\"lyrics-body-text\" class=\"js-lyric-text\">\n" +
            "<!-- First Section -->\n" +
            "<p class='verse'>We're no strangers to love</p><p class='verse'>You know the rules and so do I<br>\n" +
            "A full commitment's what I'm thinking of<br>\n" +
            "You wouldn't get this from any other guy<br>\n" +
            "I just wanna tell you how I'm feeling</p><p class='verse'>Gotta make you understand</p><p class='verse'>Never gonna give you up<br>\n" +
            "Never gonna let you down<br>\n" +
            "Never gonna run around and desert you<br>\n" +
            "Never gonna make you cry<br>\n" +
            "Never gonna say goodbye</p><p class='verse'>Never gonna tell a lie and hurt you<br>\n" +
            "We've known each other for so long<br>\n" +
            "Your heart's been aching but<br>\n" +
            "You're too shy to say it</p>\n" +
            "<!--WIDGET - RELATED-->\n" +
            "<div class=\"driver-related\">\n" +
            "<h4>Related</h4>\n" +
            "<div class=\"related-container\">\n" +
            "<ul>\n" +
            "<li>\n" +
            "<div class=\"bt-container\">\n" +
            "<div id='div-gpt-ad-strnative'></div> </div>\n" +
            "</li>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/news-gallery-18-nontraditional-yet-perfect-wedding-songs.html\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://netstorage.metrolyrics.com/drivers/a5716/johnlegend-driver1-55ca4def-image.jpg\" pagespeed_url_hash=\"539329017\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "<span class=\"title\">18 Non-Traditional Yet Perfect Wedding Songs</span>\n" +
            "</a>\n" +
            "</li>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/news-story-listen-to-taylor-swifts-new-song-call-it-what-you-want.html\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://netstorage.metrolyrics.com/editorials/listen-to-taylor-swifts-new-song-call-it-what-you-want/small-image.jpg\" pagespeed_url_hash=\"1983955683\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "<span class=\"title\">Listen To Taylor Swift's New Song 'Call It What You Want'</span>\n" +
            "</a>\n" +
            "</li>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/news-story-watch-sam-smith-and-fifth-harmony-carpool-karaoke-james-corden.html\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://netstorage.metrolyrics.com/editorials/watch-sam-smith-and-fifth-harmony-carpool-karaoke-james-corden/small-image.jpg\" pagespeed_url_hash=\"816784286\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "<span class=\"title\">Watch Sam Smith And Fifth Harmony Join James Corden For Carpool Karaoke</span>\n" +
            "</a>\n" +
            "</li>\n" +
            "</ul>\n" +
            "</div>\n" +
            "<!-- BIG BOX AD - middle POSITION-->\n" +
            "<div class=\"big-box\" style=\"margin-bottom:28px\">\n" +
            "<div id='div-gpt-ad-mpu_middle'></div> </div>\n" +
            "</div> <!--END WIDGET - RELATED-->\n" +
            "<!-- Second Section -->\n" +
            "<p class='verse'>Inside we both know what's been going on</p><p class='verse'>We know the game and we're gonna play it<br>\n" +
            "And if you ask me how I'm feeling<br>\n" +
            "Don't tell me you're too blind to see<br>\n" +
            "Never gonna give you up<br>\n" +
            "Never gonna let you down<br>\n" +
            "Never gonna run around and desert you</p><p class='verse'>Never gonna make you cry<br>\n" +
            "Never gonna say goodbye<br>\n" +
            "Never gonna tell a lie and hurt you<br>\n" +
            "Never gonna give you up<br>\n" +
            "Never gonna let you down<br>\n" +
            "Never gonna run around and desert you</p><p class='verse'>Never gonna make you cry<br>\n" +
            "Never gonna say goodbye<br>\n" +
            "Never gonna tell a lie and hurt you<br>\n" +
            "(Ooh, give you up)</p>\n" +
            "<!--WIDGET - PHOTOS-->\n" +
            "<div class=\"\">\n" +
            "<div class=\"driver-photos\">\n" +
            "<h4>Photos</h4>\n" +
            "<div class=\"related-container\">\n" +
            "<ul>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/rick-astley-pictures.html#pic-239156390\" alt=\"Rick Astley - artist photos\" title=\"Rick Astley - artist photos\" onmousedown=\"ev('Artist Photos Module','picURL:http://www.metrolyrics.com/rick-astley-pictures.html#pic-239156390');\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://img2-ak.lst.fm/i/u/arO/ba8cda020295d4535b2c83273a55fe1e\" alt=\"hhRick Astley - artist photos\" pagespeed_url_hash=\"1931108168\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "</a>\n" +
            "</li>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/rick-astley-pictures.html#pic-162772938\" alt=\"Rick Astley - artist photos\" title=\"Rick Astley - artist photos\" onmousedown=\"ev('Artist Photos Module','picURL:http://www.metrolyrics.com/rick-astley-pictures.html#pic-162772938');\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://img2-ak.lst.fm/i/u/arO/756d8e7cd39c47a1873a5e72fb940db4\" alt=\"hhRick Astley - artist photos\" pagespeed_url_hash=\"761484394\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "</a>\n" +
            "</li>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/rick-astley-pictures.html#pic-162772977\" alt=\"Rick Astley - artist photos\" title=\"Rick Astley - artist photos\" onmousedown=\"ev('Artist Photos Module','picURL:http://www.metrolyrics.com/rick-astley-pictures.html#pic-162772977');\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://img2-ak.lst.fm/i/u/arO/5caed1e11edd4d119ce9e1ea0e1c6e66\" alt=\"hhRick Astley - artist photos\" pagespeed_url_hash=\"4231686583\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "</a>\n" +
            "</li>\n" +
            "<li>\n" +
            "<a href=\"http://www.metrolyrics.com/rick-astley-pictures.html#pic-162772962\" alt=\"Rick Astley - artist photos\" title=\"Rick Astley - artist photos\" onmousedown=\"ev('Artist Photos Module','picURL:http://www.metrolyrics.com/rick-astley-pictures.html#pic-162772962');\">\n" +
            "<div class=\"img-wrap\">\n" +
            "<img pagespeed_lazy_src=\"http://img2-ak.lst.fm/i/u/arO/50df881d16cc49e3a988907c90e9d0e9\" alt=\"hhRick Astley - artist photos\" pagespeed_url_hash=\"2831094606\" src=\"/img/1.gif\" onload=\"pagespeed.lazyLoadImages.loadIfVisible(this);\"/>\n" +
            "</div>\n" +
            "</a>\n" +
            "</li>\n" +
            "</ul>\n" +
            "</div>\n" +
            "<!-- BIG BOX AD - middle2 POSITION-->\n" +
            "<div class=\"big-box\">\n" +
            "<div id='div-gpt-ad-mpu_middle2'></div> </div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<!--END WIDGET - PHOTOS-->\n" +
            "<!-- Third Section -->\n" +
            "<p class='verse'>(Ooh, give you up)<br>\n" +
            "(Ooh)<br>\n" +
            "Never gonna give, never gonna give<br>\n" +
            "(Give you up)</p><p class='verse'>(Ooh)<br>\n" +
            "Never gonna give, never gonna give</p><p class='verse'>(Give you up)<br>\n" +
            "We've know each other for so long<br>\n" +
            "Your heart's been aching but<br>\n" +
            "You're too shy to say it<br>\n" +
            "Inside we both know what's been going on</p><p class='verse'>We know the game and we're gonna play it<br>\n" +
            "I just wanna tell you how I'm feeling<br>\n" +
            "Gotta make you understand<br>\n" +
            "Never gonna give you up</p><p class='verse'>Never gonna let you down<br>\n" +
            "Never gonna run around and desert you<br>\n" +
            "Never gonna make you cry<br>\n" +
            "Never gonna say goodbye<br>\n" +
            "Never gonna tell a lie and hurt you<br>\n" +
            "Never gonna give you up<br>\n" +
            "Never gonna let you down<br>\n" +
            "Never gonna run around and desert you<br>\n" +
            "Never gonna make you cry<br>\n" +
            "Never gonna say goodbye<br>\n" +
            "Never gonna tell a lie and hurt you<br>\n" +
            "Never gonna give you up<br>\n" +
            "Never gonna let you down<br>\n" +
            "Never gonna run around and desert you<br>\n" +
            "Never gonna make you cry<br>\n" +
            "Never gonna say goodbye<br>\n" +
            "Never gonna tell a lie and hurt you</p>\n" +
            "<!--BOTTOM MPU-->\n" +
            "<div class=\"bottom-mpu\">\n" +
            "<!-- BIG BOX AD - BOTTOM POSITION-->\n" +
            "<div class=\"big-box bottom\">\n" +
            "<div id='div-gpt-ad-mpu_bottom'></div>\t</div>\n" +
            "</div>\n" +
            "<!--END BOTTOM MPU-->\n";

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            
            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content += line + "\n";
                Log.d("Appended Line:", line);
            }
            Log.d("Content: ", content.toString());
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
