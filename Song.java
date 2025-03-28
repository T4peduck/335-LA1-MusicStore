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
	
	/*
	 * void play() -- increments the plays of the song by one
	 */
	public void play() {
		plays++;
	}
	
	/*
	 * void play(int n) -- sets the plays of the song to n
	 */
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
		rating = 5;
	}
	
	// returns this.plays
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
	
	/*
	 * int hashCodeArtist() -- returns an integer hash code corresponding to the artist of the song
	 */
	public int hashCodeArtist() {
		return this.artist.toLowerCase().hashCode();
	}
	
	/*
	 * int hashCodeName() -- returns an integer hash code corresponding to the name of the song
	 */
	public int hashCodeName() {
		return this.name.toLowerCase().hashCode();
	}
	
	/*
	 * boolean equals(Object other) -- returns true if supplied song is equivalent in both name and artist to this song,
	 * false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		Song otherSong = (Song) other;
		return this.name.equals(otherSong.name) && this.artist.equals(otherSong.artist);
	}
	
	/*
	 * int hashCode() -- returns an integer hash code corresponding to the artist and name of the song
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.artist.hashCode();
	}
}
