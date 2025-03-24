/*
 * File: PlayList.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Models a user's playlist of music
 */

import java.util.ArrayList;
import java.util.Collections;

public class PlayList {
	
	private ArrayList<Song> playlist;
	public final String name;
	
	public PlayList(String name) {
		this.name = name;
		playlist = new ArrayList<Song>();
	}
	
	/*
	 * void addSong(Song s) -- adds Song s to the playlist, unless it is
	 * already in the playlist.
	 */
	public void addSong(Song s) {
		if(playlist.contains(s))
			return;
		playlist.add(s);
	}
	
	/*
	 * void removeSong(Song s) -- if s is in the playlist, removes it.
	 */
	public void removeSong(Song s) {
		if(playlist.contains(s))
			playlist.remove(s);
	}
	
	/*
	 * ArrayList<Song> getPlaylist() -- returns a copy of the playlist
	 */
	public ArrayList<Song> getPlaylist() {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : playlist) {
			songs.add(new Song(s));
		}
		return songs;
	}
	
	public void shuffle() {
		Collections.shuffle(playlist);
	}
	
	@Override
	public int hashCode() {
		return name.toLowerCase().hashCode();
	}
}
