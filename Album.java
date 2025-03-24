/*
 * File: Album.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Models a music album
 */

import java.util.ArrayList;
import java.util.Hashtable;

public class Album {
	
	private Hashtable<String, Song> songs;
	private ArrayList<String> songNames;
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
		songs = new Hashtable<String, Song>();
		this.songNames = new ArrayList<String>();
		for(String songName : songNames) {
			songs.put(songName, new Song(songName, artist, name));
			this.songNames.add(songName.toLowerCase());
		}
	}
	
	// Copy Constructor
	public Album(Album a) {
		this.name = a.name;
		this.artist = a.artist;
		this.genre = a.genre;
		this.year = a.year;
		songs = new Hashtable<String, Song>();
		this.songNames = new ArrayList<String>(a.songNames);
		
		for(String song : a.songNames) {
			songs.put(song, new Song(a.songs.get(song)));
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
			if(song.name.equals(songName))
				songs.add(new Song(song));
		}
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
		for(String song : songNames) {
			s += song + "\n";
		}
		return s;
	}
	
	/*
	 * Song getSong(String songName) -- returns a copy of the song with name songName from this album. If no
	 * such song exists, returns null
	 */
	public Song getSong(String songName) {
		return songs.get(songName.toLowerCase());
	}
	
	/*
	 * ArrayList<Song> getAlbum() -- returns a copy of the ArrayList of Songs that comprises the album.
	 */
	public ArrayList<Song> getAlbum() {
		ArrayList<Song> list = new ArrayList<Song>();
		for(String s : songNames) {
			list.add(new Song(songs.get(s)));
		}
		return list;
	}
	
	public int hashCodeArtist() {
		return this.artist.toLowerCase().hashCode();
	}
	
	public int hashCodeName() {
		return this.name.toLowerCase().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		Album otherAlbum = (Album) other;
		return this.name.equals(otherAlbum.name) && this.artist.equals(otherAlbum.artist);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.artist.hashCode();
	}
}
