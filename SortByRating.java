import java.util.Comparator;

public class SortByRating implements Comparator<Song> {
		
	public int compare(Song a, Song b) {
		return a.getRating() - b.getRating();
	}
}