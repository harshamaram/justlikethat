package com.justlikethat.mp3.bean;

import org.jaudiotagger.tag.FieldKey;

public class TrackMetaData {

	private String album;
	private String title;
	private String track;
	private String artist;
	private String genre;
	private String year;
	private String originalFileName;
	private String originalFolderName;

	public String getAlbum() {
		return album;
	}

	public String getOriginalFolderName() {
		return originalFolderName;
	}

	public void setOriginalFolderName(String originalFolderName) {
		this.originalFolderName = originalFolderName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "ExistingTrackData [album=" + album + ", title=" + title + ", track=" + track + ", artist=" + artist
				+ ", genre=" + genre + ", year=" + year + "]";
	}

}
