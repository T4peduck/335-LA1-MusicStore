/*
 * Song.java
 * Authors: Ethan Cushner & Joseph Hill
 * Models a Song
 * 
 */
public class Song {
	
	public final String name;
	public final String artist;
	public final String album;
	private int rating;
	private boolean favorite;
	
	public Song(String name, String artist, String album) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		favorite = false;
	}
	
	public Song(Song s) {
		this(s.name, s.artist, s.album);
	}
	
	public String toString() {
		return name + " by " + artist + " on " + album + "."; 
	}
	
	public void setRating(int rating) {
		this.rating = rating;
		if(rating == 5)
			favorite = true;
	}
	
	public void setFavorite() {
		favorite = true;
	}
	
	public boolean getFavorite() {
		return favorite;
	}
	
	public int getRating() {
		return rating;
	}
}
