package com.srikanth.lyrics;

public interface LyricsDAO {

	String getLyricText(String artist, String title);
	
	String[] getTitlesbyArtist(String artist);
	
	String getArtist(String title);
	
	String[] searchLyrics(String[] words);
}
