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
	private int numPlays;
	private boolean favorite;
	
	// Regular Constructor
	public Song(String name, String artist, String album) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		favorite = false;
		numPlays = 0;
	}
	
	// Copy Constructor
	public Song(Song s) {
		this(s.name, s.artist, s.album);
		this.rating = s.rating;
	}
	
	/*
	 * String toString() -- returns a string that explains the string object with its
	 * name, artist, and album
	 */
	public String toString() {
		return name + " by " + artist + " on " + album + "."; 
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
	
	public void play() {
		numPlays++;
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
  
	public int getNumPlays() {
		return numPlays;
	}
}
