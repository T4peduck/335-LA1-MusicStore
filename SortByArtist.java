import java.util.Comparator;

public class SortByArtist implements Comparator<Song> {
		
	public int compare(Song a, Song b) {
		return a.artist.compareTo(b.artist);
	}
}