# MusicLyricsPlayer

###BASIC OUTLINE
- Audio file --> written text/words
- Parsed sentences/phrases --> separate words
- Tagged words --> categories for identifying keywords and filler words
- Keywords/filler --> identify important lines/phrases/lyrics
- Lyrics --> possible song(s) + artists from database
- Song + artist --> access song/video
- Show video/play song

###DETAILED
1. Audio → Lyrics
- STRETCH: Parsed sentences/phrases → categorised words (yay…)
- If too many filler words, redirect
- Identify important/keywords + filler words → know which part is more “important” 
2. Lyrics → Song (object)
3. Search using URL class
4. Access the info that pops up
- Artist
- Title
- STRETCH:  Lyrics → can access if chosen (use parser from last year)
- Use BufferedReader to access from HTML OR Boilerpipe (limited)
5. Store as strings in song object 
- STRETCH:
Save mutiple to continue to check if not using Spotify
Save as favorite)
song.getTitle + song.getArtist → search on X platform
Spotify → if no account redirect to creating an account (HAS TO BE PREMIUM)
- STRETCH: Settings for preferred playing platform
- Spotify
- Play Music
- YouTube
6. Search first result then next ones for x turns.
7. Search → Play

