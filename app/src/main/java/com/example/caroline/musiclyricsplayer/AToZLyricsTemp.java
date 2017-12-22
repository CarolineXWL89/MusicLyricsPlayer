package com.example.caroline.musiclyricsplayer;

import java.util.ArrayList;

/**
 * Created by princ on 22/12/2017.
 */

public class AToZLyricsTemp {
    private static String START_REF = "<!-- Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our licensing agreement. Sorry about that. -->";
    private static String END_REF = "<!-- MxM banner -->";
    private String lyrics = "";
    private static String BREAK_REF = "<br>";
    private static String DIV_REF = "</div>\n";
    private String HTMLCode = "";

    private String reducedSearch = "";
    private ArrayList<String> htmlLyrics = new ArrayList<>();
    private String plainLyrics = "";

    public AToZLyricsTemp(String HTMLCode){
        this.HTMLCode = HTMLCode;
        int startPoint = HTMLCode.indexOf(START_REF);
        int endPoint = HTMLCode.indexOf(END_REF);
        reducedSearch = HTMLCode.substring(startPoint, endPoint);
    }

//    public String getReducedSearch() {
//        return reducedSearch;
//    }

    public ArrayList<String> getHtmlLyrics(){
        int startPosition1 = 0;
        int numLetters = 0;
        int phraseLength = reducedSearch.length();
        for(int i = 0; i < phraseLength; i++){
            String currentCharacter = reducedSearch.substring(i, i + 1);
            if(!(currentCharacter.equals(" ")) && !(currentCharacter.equals("  "))/*i = phraseLength*/){
                if(i != phraseLength - 1){
                    numLetters++;
                }
            }
            else{
                String currentWord = reducedSearch.substring(startPosition1, startPosition1 + numLetters);
                startPosition1 = i + 1;
                htmlLyrics.add(currentWord);
                numLetters = 0;
            }
        }
        String lastWord = reducedSearch.substring(startPosition1, startPosition1 + numLetters + 1);
        htmlLyrics.add(lastWord);
        return htmlLyrics;
    }

    public String cutHTML(ArrayList<String> givenLyrics){
//        ArrayList<String> tempHolder = new ArrayList<>();
        int lyricsWordsNum = htmlLyrics.size();
        for(int i = 0; i < lyricsWordsNum; i++){
            String currentWord = htmlLyrics.get(i);
            int wordLength = currentWord.length();
            for(int k = 0; k < wordLength; k++){
                char c = currentWord.charAt(k);
                if(c == '<'){
                    String revisedWord = currentWord.substring(0, k);
//                    tempHolder.add(revisedWord);
                    plainLyrics += revisedWord + "\n";
                }
                else{
//                    tempHolder.add(currentWord);
                    plainLyrics += currentWord;
                }
            }
        }

        return plainLyrics;
    }
}
