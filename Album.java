/*
 * File: Album.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Models a music album
 */

import java.util.ArrayList;

public class Album {
	
	private ArrayList<Song> songs;
	public final String artist;
	public final String name;
	public final String genre;
	public final String year;
	
	// Regular Constructor
	public Album(String name, String artist, String genre, String year, ArrayList<String> songNames) {
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		songs = new ArrayList<Song>();
		for(String songName : songNames) {
			songs.add(new Song(songName, artist, name));
		}
	}
	
	// Copy Constructor
	public Album(Album a) {
		this.name = a.name;
		this.artist = a.artist;
		this.genre = a.genre;
		this.year = a.year;

		songs = new ArrayList<>();

		for(Song song : a.songs) {
			songs.add(new Song(song));
		}
	}
	
	
	// Copy Constructor 2
	public Album(Album a, String songName) {
		this.name = a.name;
		this.artist = a.artist;
		this.genre = a.genre;
		this.year = a.year;
		songs = new ArrayList<Song>();
		for(Song song : a.songs) {
			if(song.name.equals(songName)) {
				songs.add(new Song(song));
			}
		}
	}
	
	// Copy Constructor 3
	public Album(Album a, Song s, boolean add) {
		this.name = a.name;
		this.artist = a.artist;
		this.genre = a.genre;
		this.year = a.year;
		songs = new ArrayList<Song>();
		for(Song song : a.songs) {
			songs.add(new Song(song));
		}
		if(add) {songs.add(s);}
		else {songs.remove(s);}
	}
	
	/*
	 * String toString() -- returns a string that represents the album. Contains the name, artist, genre, and year of release, as
	 * well as a list of all songs in the album.
	 */
	public String toString() {
		String s;
		char firstCharOfGenre = genre.charAt(0);
		if(firstCharOfGenre == 'A' || firstCharOfGenre == 'E' || firstCharOfGenre == 'I' || firstCharOfGenre == 'O' || firstCharOfGenre == 'U')
			s = name + " by " + artist + ", an " + genre.toLowerCase() + " album released in " + year + ".\n";
		else	
			s = name + " by " + artist + ", a " + genre.toLowerCase() + " album released in " + year + ".\n";
		for(Song song : songs) {
			s += song.name + "\n";
		}
		return s;
	}
	
	/*
	 * Song getSong(String songName) -- returns a copy of the song with name songName from this album. If no
	 * such song exists, returns null
	 */
	public Song getSong(String songName) {
		for(Song song :songs) {
			if(song.name.toLowerCase().equals(songName.toLowerCase())) {
				return new Song(song);
			}
		}
		return null;
	}
	
	/*
	 * ArrayList<Song> getAlbum() -- returns a copy of the ArrayList of Songs that comprises the album.
	 */
	public ArrayList<Song> getAlbum() {
		ArrayList<Song> list = new ArrayList<Song>();
		for(Song song: songs) {
			list.add(new Song(song));
		}
		return list;
	}
	
	/*
	 * int hashCodeArtist() -- returns an integer hash code based on the artist of the song
	 */
	public int hashCodeArtist() {
		return this.artist.toLowerCase().hashCode();
	}
	
	/*
	 * int hashCodeName() -- returns an integer hash code based on the name of the song
	 */
	public int hashCodeName() {
		return this.name.toLowerCase().hashCode();
	}
	
	/*
	 * boolean equals(Object other) -- returns true if the two songs have the same name and artist, false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		Album otherAlbum = (Album) other;
		return this.name.equals(otherAlbum.name) && this.artist.equals(otherAlbum.artist);
	}
	
	/*
	 * int hashCode() -- returns an integer hash code based on both the artist and name of the song
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.artist.hashCode();
	}
}
