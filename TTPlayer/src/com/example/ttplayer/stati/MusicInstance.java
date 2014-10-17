package com.example.ttplayer.stati;

public class MusicInstance {
	private int id;
	private String title;
	private String artist;	
	private String album;
	private String isfav;
	private String path;
	private String mp3AlbumImage;
	public String getMp3AlbumImage() {
		return mp3AlbumImage;
	}
	public void setMp3AlbumImage(String mp3AlbumImage) {
		this.mp3AlbumImage = mp3AlbumImage;
	}
	private long size;
	private long duration;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIsfav() {
		return isfav;
	}
	public void setIsfav(String isfav) {
		this.isfav = isfav;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
