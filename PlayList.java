import java.util.ArrayList;

public class PlayList {
	
	private ArrayList<Song> playlist;
	public final String name;
	
	public PlayList(String name) {
		this.name = name;
		playlist = new ArrayList<Song>();
	}
	
	public void addSong(Song s) {
		if(playlist.contains(s))
			return;
		playlist.add(s);
	}
	
	public void removeSong(Song s) {
		if(playlist.contains(s))
			playlist.remove(s);
	}
	
	public ArrayList<Song> getPlaylist() {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : playlist) {
			songs.add(new Song(s));
		}
		return songs;
	}
}
