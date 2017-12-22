package com.example.caroline.musiclyricsplayer;

import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by maylisw on 10/19/17.
 */

public class SongObject {
    //TODO some redundant code and commented out stuff
    private String title, artist, link, lyricsPageURL;
    private ArrayList<String> wordsFinal = new ArrayList<>();

    public SongObject(String title, String artist, String link) {
        this.title = title;
        this.artist = artist;
        this.link = link;
        lyricsPageURL = "https://www.azlyrics.com/lyrics/"; //"http://www.metrolyrics.com/";
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //gets the lyrics page link from MetroLyrics

    /**
     * Creates an URL for Metro Lyrics to get the HTML of the lyrics page
     * @param titleWords Array list of strings of words words in the title of song
     * @param artistWords Array list of strings of words words in the artist of song
     * @return working URL
     */
    public String createLyricsPageURL(ArrayList<String> titleWords, ArrayList<String> artistWords){
        for(int i = 0; i < artistWords.size(); i++){ //title first for Metrolyrics
            String word = artistWords.get(i);
            if(!(i == 0 && word.equals("The"))){
                lyricsPageURL += word.toLowerCase();
            }

//            lyricsPageURL += word + "-";

        }
//        lyricsPageURL += "lyrics";
        lyricsPageURL += "/";
        int i = 0;
        for(i = 0; i < titleWords.size(); i++){ //artist second for Metrolyrics
            String word = titleWords.get(i);
//            lyricsPageURL += "-" + word;
            lyricsPageURL += word.toLowerCase();
        }
        //TODO: figure out why there's no i+1
        lyricsPageURL += ".html";
        return lyricsPageURL;
    }

    /**
     * separates through words in title, artist
     * @param phrase either the title or artist
     * @return Array list of strings of the individual words in the title or artist
     */
    int startPosition2 = 0;
    public ArrayList<String> separateWords(String phrase){
        int startPosition1 = 0;
        ArrayList<String> wordsFinal = new ArrayList<>();
        int numLetters = 0;
        int phraseLength = phrase.length();
        //System.out.println(phraseLength);
        for(int i = 0; i < phraseLength; i++){
            String currentCharacter = phrase.substring(i, i + 1);
            //System.out.println(currentCharacter + " " + numLetters);
            if(!(currentCharacter.equals(" ")) && !(currentCharacter.equals("  "))/*i = phraseLength*/){
                if(i != phraseLength - 1){
                    numLetters++;
                }

                //System.out.print("startPosition1: " + startPosition1 + " ");
                //System.out.println(currentCharacter.equals(" "));
                //System.out.println("startPosition2: " + startPosition2 + " ");

            }
            else{
                /*System.out.print("There was a space: check --> ");
                System.out.print("startPosition1: " + startPosition1 + " ");
                System.out.println("numLetters: " + numLetters);*/
                Log.d("separateWords", "startPosition1: " + startPosition1);
                Log.d("separateWords", "numLetters: " + numLetters);
                String currentWord = phrase.substring(startPosition1, startPosition1 + numLetters);
                //phrase = phrase.substring(numLetters);
                //System.out.println(phrase);
                //System.out.println("" + startPosition1 + " " + numLetters);
                //System.out.println("Current Word: " + currentWord);
//                startPosition1 += numLetters + 1;
                startPosition1 = i+1;
                wordsFinal.add(currentWord);
                numLetters = 0;
                //numLetters++;
            }
        }
        String lastWord = phrase.substring(startPosition1, startPosition1 + numLetters + 1);
        wordsFinal.add(lastWord);
        //System.out.println(lastWord);
        return wordsFinal;
    }
}
