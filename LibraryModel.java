import java.util.ArrayList;

public class LibraryModel {
	private ArrayList<Song> library;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playlists;
	private MusicStore ms;
	
	public LibraryModel(MusicStore ms) {
		library = new ArrayList<Song>();
		albums = new ArrayList<Album>();
		playlists = new ArrayList<PlayList>();
		this.ms = ms;
	}
	
	public void addSong(Song s) {
		library.add(s);
	}
	
	public void addAlbum(Album a) {
		for(Song s : a.getAlbum())
			library.add(s);
		albums.add(a);
	}
	
	public void createPlayList(String playListName) {
		playlists.add(new PlayList(playListName));
	}
	
	public void addSongToPlaylist(String playListName, String songName) {
		Song song = null;
		for(Song s : library) {
			if(s.name.equals(songName))
				song = s;
		}
		for(PlayList p : playlists) {
			if(p.name.equals(playListName))
				p.addSong(song);
		}
	}
	
	public void removeSongFromPlaylist(String playListName, String songName) {
		Song song = null;
		for(Song s : library) {
			if(s.name.equals(songName))
				song = s;
		}
		for(PlayList p : playlists) {
			if(p.name.equals(playListName))
				p.removeSong(song);
		}
	}
	
	public ArrayList<Song> searchSongByName(String songName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : library) {
			if(s.name.equals(songName))
				songs.add(new Song(s));
		}
		return songs;
	}
	
	public ArrayList<Song> searchSongByArtist(String artistName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : library) {
			if(s.artist.equals(artistName))
				songs.add(new Song(s));
		}
		return songs;
	}
	
	public ArrayList<Album> searchAlbumByTitle(String albumName) {
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for(Album a : albums) {
			if(a.name.equals(albumName))
				searchedAlbums.add(new Album(a));
		}
		return searchedAlbums;
	}
	
	public ArrayList<Album> searchAlbumByArtist(String artistName) {
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for(Album a : albums) {
			if(a.artist.equals(artistName))
				searchedAlbums.add(new Album(a));
		}
		return searchedAlbums;
	}
}
