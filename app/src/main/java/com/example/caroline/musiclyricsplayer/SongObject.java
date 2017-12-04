package com.example.caroline.musiclyricsplayer;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by maylisw on 10/19/17.
 */

public class SongObject {
    private String title, artist, link, lyricsPageURL;
    private ArrayList<String> wordsFinal = new ArrayList<>();

    public SongObject(String title, String artist, String link) {
        this.title = title;
        this.artist = artist;
        this.link = link;
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
    public String createLyricsPageURL(ArrayList<String> titleWords, ArrayList<String> artistWords){
        for(int i = 0; i < titleWords.size(); i++){
            String word = artistWords.get(i);
            lyricsPageURL = "http://www.metrolyrics.com/" + word + "-";
        }
        lyricsPageURL += "lyrics";
        for(int i = 0; i < artistWords.size(); i++){
            String word = titleWords.get(i);
            lyricsPageURL += word;
        }
        lyricsPageURL += ".html";
        return lyricsPageURL;
    }

    public ArrayList<String> separateWords(String phrase){
        int startPosition1 = 0;
        int startPosition2 = 0;
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
                //System.out.print("There was a space: check --> ");
                //System.out.print("startPosition1: " + startPosition1 + " ");
                //System.out.println("numLetters: " + numLetters);
                String currentWord = phrase.substring(startPosition1, startPosition1 + numLetters);
                //phrase = phrase.substring(numLetters);
                //System.out.println(phrase);
                //System.out.println("" + startPosition1 + " " + numLetters);
                //System.out.println("Current Word: " + currentWord);
                startPosition1 += numLetters + 1;
                wordsFinal.add(currentWord);
                numLetters = 0;
                //numLetters++;
            }
        }
        String lastWord = phrase.substring(startPosition1, startPosition1 + numLetters + 1);
        System.out.println(lastWord);
        return wordsFinal;
    }
}
