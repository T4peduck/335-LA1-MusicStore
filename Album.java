import java.util.ArrayList;

public class Album {
	
	private ArrayList<Song> songs;
	public final String artist;
	public final String name;
	public final String genre;
	public final String year;
	
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
	
	public Album(Album a) {
		this.name = a.name;
		this.artist = a.artist;
		this.genre = a.genre;
		this.year = a.year;
		songs = new ArrayList<Song>();
		for(Song song : a.songs) {
			songs.add(new Song(song));
		}
	}
	
	public String toString() {
		String s = name + " by " + artist + ", a " + genre + " album released in " + year + ".\n";
		for(Song song : songs) {
			s += song.name + "\n";
		}
		return s;
	}
	
	public Song getSong(String songName) {
		for(Song song : songs) {
			if(song.name.equals(songName))
				return new Song(song);
		}
		return null;
	}
	
	public ArrayList<Song> getAlbum() {
		ArrayList<Song> list = new ArrayList<Song>();
		for(Song song : songs) {
			list.add(new Song(song));
		}
		return list;
	}
}
