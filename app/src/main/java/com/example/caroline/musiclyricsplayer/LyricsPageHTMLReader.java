package com.example.caroline.musiclyricsplayer;

import java.util.ArrayList;

/**
 * Created by princ on 07/11/2017.
 */
public class LyricsPageHTMLReader {
    private int lyRefL;
    private int lyEndRefL;
    //private static final String LYRIC_REF = "<pre id=\"lyric-body-text\" class=\"lyric-body\" dir=\"ltr\" data-lang=\"en\">";
    //private static final String LYRIC_END = "</div></div><div class=\"xpdxpnd kno-fb-ctx _Rtn _ECr\" data-mh=\"-1\" data-ved=\"";
    private static final String LYRIC_REF = "<div id=\"lyrics-body-text\" class=\"js-lyric-text\">";
    private static final String LYRIC_END = "<!--BOTTOM MPU-->";
    private static final int SIZE_REF_BR = "<br />".length();
    private static final String BREAK_REF = "<br />";
    private static final String LINE_BREAK_INDICATOR = " / ";
    private String HTMLCode, lyrics, lyricsPageURL;
    private ArrayList<String> wordsFinal = new ArrayList<>();
    //private ArrayList<String> brokenUpText = new ArrayList<>();

    public LyricsPageHTMLReader(String HTMLCodeLyrics){
        this.HTMLCode = HTMLCodeLyrics;
        /*int firstBeforeLyrics = HTMLCode.indexOf(LYRIC_REF);
        lyRefL = LYRIC_REF.length();
        int firstAfterLyrics = HTMLCode.indexOf(LYRIC_END);
        lyEndRefL = LYRIC_END.length();*/
    }


    public ArrayList<String> separateWords(String phrase){
        int startPosition1 = 0;
        int startPosition2 = 0;
        int numLetters = 0;
        int phraseLength = phrase.length();
        //System.out.println(phraseLength);
        for(int i = 0; i < phraseLength; i++){
            String currentCharacter = phrase.substring(i, i+SIZE_REF_BR);
            //System.out.println(currentCharacter + " " + numLetters);
            if(!(currentCharacter.equals(BREAK_REF)) && !(currentCharacter.equals(BREAK_REF))/*i = phraseLength*/){
                if(i != phraseLength - 1){
                    numLetters++;
                }

                //System.out.print("startPosition1: " + startPosition1 + " ");
                //System.out.println(currentCharacter.equals(" "));
                //System.out.println("startPosition2: " + startPosition2 + " ");

            }
            else{
                //TODO: be able to separate into stanzas with <!-- First Section -->
                String currentWord = phrase.substring(startPosition1, startPosition1 + numLetters);
                //System.out.print("There was a space: check --> ");
                //System.out.print("startPosition1: " + startPosition1 + " ");
                //System.out.println("numLetters: " + numLetters);
                String lineEndCheck = phrase.substring(startPosition1 + numLetters, startPosition1 + numLetters + 1);
                if(lineEndCheck.equals("<")){
                    String stuffToCheck = phrase.substring(startPosition1 + numLetters);
                    int endOfCheckPosit = stuffToCheck.indexOf(">");
                    wordsFinal.add(currentWord);
                    wordsFinal.add(LINE_BREAK_INDICATOR);
                }
                else{
                    wordsFinal.add(currentWord);
                }
                //String currentWord = phrase.substring(startPosition1, startPosition1 + numLetters);
                //phrase = phrase.substring(numLetters);
                //System.out.println(phrase);
                //System.out.println("" + startPosition1 + " " + numLetters);
                //System.out.println("Current Word: " + currentWord);
                startPosition1 += numLetters + SIZE_REF_BR;
                numLetters = 0;
                //numLetters++;
            }
        }
        String lastWord = phrase.substring(startPosition1, startPosition1 + numLetters + 1);
        System.out.println(lastWord);
        return wordsFinal;
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
