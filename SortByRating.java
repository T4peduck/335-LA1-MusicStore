/*
 * File: SortByRating.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Implements the Comparator Interface to compare two
 * songs by their ratings.
 */

import java.util.Comparator;

public class SortByRating implements Comparator<Song> {
		
	public int compare(Song a, Song b) {
		return a.getRating() - b.getRating();
	}
}