/*
 * File: SortByTitle.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Implements the Comparator Interface to compare two
 * songs by their titles.
 */
import java.util.Comparator;


public class SortByTitle implements Comparator<Song> {
	
	public int compare(Song a, Song b) {
		return a.name.compareTo(b.name);
	}
}
