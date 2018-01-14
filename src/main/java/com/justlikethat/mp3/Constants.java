package com.justlikethat.mp3;

public interface Constants {	
	
	enum METADATA {
		ALBUM ("ALBUM"),
		TITLE ("TITLE"),
		TRACK ("TRACK"),
		ARTIST ("ARTIST"),
		GENRE ("GENRE"),
		YEAR ("YEAR"),
		ORIGINAL_FILE_NAME("ORIGINAL FILE NAME"),
		ORIGINAL_FOLDER_NAME("ORIGINAL FOLDER NAME");
		
		String name;
		METADATA(String name) {
			this.name = name;
		}
	}
	
	final int FILE_NAME = 0;
	final int FOLDER_NAME = 1;
	final int ALBUM = 2; 
	final int TITLE = 3;
	final int TRACK = 4;
	final int ARTIST = 5;
	final int GENRE = 6;
	final int YEAR = 7;
	
	final String MP3 = "MP3";
	final String mp3 = "mp3";
	
	final String SLASH = "\\";
}
