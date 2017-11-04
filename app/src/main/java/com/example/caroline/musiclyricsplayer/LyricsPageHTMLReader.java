package com.example.caroline.musiclyricsplayer;

import java.util.ArrayList;

/**
 * Created by princ on 04/11/2017.
 */

public class LyricsPageHTMLReader {
    private int lyRefL;
    private int lyEndRefL;
    private static final String LYRIC_REF = "<pre id=\"lyric-body-text\" class=\"lyric-body\" dir=\"ltr\" data-lang=\"en\">";
    private static final String LYRIC_END = "</div></div><div class=\"xpdxpnd kno-fb-ctx _Rtn _ECr\" data-mh=\"-1\" data-ved=\"";
    private String HTMLCode, lyrics, googleSearchForLyricsLink;
    private ArrayList<String> words = new ArrayList<>();

    public LyricsPageHTMLReader(String HTMLCode){
        this.HTMLCode = HTMLCode;
        int firstBeforeLyrics = HTMLCode.indexOf(LYRIC_REF);
        lyRefL = LYRIC_REF.length();
        int firstAfterLyrics = HTMLCode.indexOf(LYRIC_END);
        lyEndRefL = LYRIC_END.length();
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
        for(int i = 0; i < titleWords.size(); i++){
            String word = titleWords.get(i);
            googleSearchForLyricsLink = "https://www.google.com/search?q=" + word;
        }
        return googleSearchForLyricsLink;
    }

    public String findLyrics(){
        return "";
    }
}
