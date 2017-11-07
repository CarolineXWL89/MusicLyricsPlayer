package com.example.caroline.musiclyricsplayer;

import java.util.ArrayList;

/**
 * Created by princ on 04/11/2017.
 */

public class LyricsPageHTMLReader {
    private int lyRefL;
    private int lyEndRefL;
    //private static final String LYRIC_REF = "<pre id=\"lyric-body-text\" class=\"lyric-body\" dir=\"ltr\" data-lang=\"en\">";
    //private static final String LYRIC_END = "</div></div><div class=\"xpdxpnd kno-fb-ctx _Rtn _ECr\" data-mh=\"-1\" data-ved=\"";
    private static final String LYRIC_REF = "<!-- Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our licensing agreement. Sorry about that. -->\n";
    private static final String LYRIC_END = "<!-- MxM banner -->\n";
    private static final int SIZE_REF_BR = "<br>".length();
    private static final String BREAK_REF = "<br>";
    private String HTMLCode, lyrics, aToZLyricsURL;
    private ArrayList<String> words = new ArrayList<>();

    public LyricsPageHTMLReader(/*String HTMLCode*/){
        /*this.HTMLCode = HTMLCode;
        int firstBeforeLyrics = HTMLCode.indexOf(LYRIC_REF);
        lyRefL = LYRIC_REF.length();
        int firstAfterLyrics = HTMLCode.indexOf(LYRIC_END);
        lyEndRefL = LYRIC_END.length();*/
    }

    public ArrayList<String> separateWords(String phrase){
        int numLetters = 0;
        int startPosition = 0;
        int phraseLength = phrase.length();
        for(int i = 0; i < phraseLength; i++){
            String currentCharacter = phrase.substring(i, i + 1);
            if(!currentCharacter.equals(" ") || i != phraseLength){
                numLetters++;
            }
            else{
                String currentWord = phrase.substring(startPosition, startPosition + numLetters);
                words.add(currentWord);
                startPosition += numLetters;
            }
        }
        return words;
    }

    public String createLyricsGoogleSearch(ArrayList<String> titleWords, ArrayList<String> artistWords){
        for(int i = 0; i < artistWords.size(); i++){
            String word = artistWords.get(i);
            aToZLyricsURL = "https://www.azlyrics.com/lyrics/" + word;
        }
        aToZLyricsURL += "/";
        for(int i = 0; i < titleWords.size(); i++){
            String word = titleWords.get(i);
            aToZLyricsURL += word;
        }
        aToZLyricsURL += ".html";
        return aToZLyricsURL;
    }

    public ArrayList<String> findLyrics(String lyricsPageHTML){
        int firstBeforeLyrics = lyricsPageHTML.indexOf(LYRIC_REF);
        ArrayList<String> lyricLines = new ArrayList<>();
        lyRefL = LYRIC_REF.length();
        int firstAfterLyrics = lyricsPageHTML.indexOf(LYRIC_END);
        lyEndRefL = LYRIC_END.length();
        String lyricSection = lyricsPageHTML.substring(firstBeforeLyrics + lyRefL, firstAfterLyrics);
        int numCharacters = lyricSection.length();

        int firstPosition = 0;
        for(int i = 0; i < numCharacters; i++){
            String letterChecked = lyricSection.substring(i, i+1);
            String line = "";
            if(letterChecked.equals("<")){
                line = lyricSection.substring(firstPosition, i);
                lyricLines.add(line);
                firstPosition = i + SIZE_REF_BR;
            }
        }


        return lyricLines;
    }
}
