import java.util.Comparator;


public class SortByTitle implements Comparator<Song> {
	
	public int compare(Song a, Song b) {
		return a.name.compareTo(b.name);
	}
}
