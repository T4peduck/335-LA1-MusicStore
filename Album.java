import java.util.ArrayList;
import java.util.Hashtable;

public class Album {
	
	private Hashtable<String, Song> songs;
	private ArrayList<String> songNames;
	public final String artist;
	public final String name;
	public final String genre;
	public final String year;
	
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
	
	public Song getSong(String songName) {
		return songs.get(songName.toLowerCase());
	}
	
	public ArrayList<Song> getAlbum() {
		ArrayList<Song> list = new ArrayList<Song>();
		for(String s : songNames) {
			list.add(new Song(songs.get(s)));
		}
		return list;
	}
}
