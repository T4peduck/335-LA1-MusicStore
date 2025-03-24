/*
 * Song.java
 * Authors: Ethan Cushner & Joseph Hill
 * Models a Song
 */
public class Song {
	
	public final String name;
	public final String artist;
	public final String album;
	private int rating;
	private boolean favorite;
	private int plays;
	
	// Regular Constructor
	public Song(String name, String artist, String album) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		favorite = false;
		rating = 0;
		plays = 0;
	}
	
	// Copy Constructor
	public Song(Song s) {
		this(s.name, s.artist, s.album);
		this.rating = s.rating;
		this.favorite = s.favorite;
		this.plays = s.plays;
	}
	
	/*
	 * String toString() -- returns a string that explains the string object with its
	 * name, artist, and album
	 */
	public String toString() {
		return name + " by " + artist + " on " + album + "."; 
	}
	
	public void play() {
		plays++;
	}
	
	public void play(int n) {
		plays = n;
	}
	

	/*
   * @Pre: rating is 1 to 5 inclusive
	 * void setRating(int rating) -- sets the rating of the song
	 * to <rating>. Also sets to favorite if the rating is set to 5.
	 */
	public void setRating(int rating) {
		this.rating = rating;
		if(rating == 5)
			favorite = true;
	}
	
	/*
	 * void setFavorite() -- sets this.favorite to true
	 */
	public void setFavorite() {
		favorite = true;
	}
	
	public int getPlays() {
		return plays;
	}
	
	// returns this.favorite
	public boolean isFavorite() {
		return favorite;
	}
	
	// returns this.rating
	public int getRating() {
		return rating;
	}
	
	public int hashCodeArtist() {
		return this.artist.toLowerCase().hashCode();
	}
	
	public int hashCodeName() {
		return this.name.toLowerCase().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		Song otherSong = (Song) other;
		return this.name.equals(otherSong.name) && this.artist.equals(otherSong.artist);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.artist.hashCode();
	}
}
