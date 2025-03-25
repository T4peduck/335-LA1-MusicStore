/*
 * File: SortByArtist.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Implements the Comparator Interface to compare two
 * songs by their artists.
 */

import java.util.Comparator;

public class SortByArtist implements Comparator<Song> {
		
	public int compare(Song a, Song b) {
		return a.artist.compareTo(b.artist);
	}
}