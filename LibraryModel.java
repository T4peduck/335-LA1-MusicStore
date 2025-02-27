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
	
	public ArrayList<Song> searchSongWithTitle(String songName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : library) {
			if(s.name.equals(songName))
				songs.add(new Song(s));
		}
		return songs;
	}
	
	public ArrayList<Song> searchSongWithArtist(String artistName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : library) {
			if(s.artist.equals(artistName))
				songs.add(new Song(s));
		}
		return songs;
	}
	
	public ArrayList<Album> searchAlbumWithTitle(String albumName) {
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for(Album a : albums) {
			if(a.name.equals(albumName))
				searchedAlbums.add(new Album(a));
		}
		return searchedAlbums;
	}
	
	public ArrayList<Album> searchAlbumWithArtist(String artistName) {
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for(Album a : albums) {
			if(a.artist.equals(artistName))
				searchedAlbums.add(new Album(a));
		}
		return searchedAlbums;
	}
	
	public ArrayList<Song> searchPlaylist(String playlistName) {
		for(PlayList p : playlists) {
			if(p.name.equals(playlistName)) {
				return p.getPlaylist();
			}
		}
		return null;
	}
	
	public ArrayList<String> getSongs() {
		ArrayList<String> titles = new ArrayList<String>();
		for(Song s : library) {
			titles.add(s.name);
		}
		return titles;
	}
	
	public ArrayList<String> getArtists() {
		ArrayList<String> artists = new ArrayList<String>();
		for(Song s : library) {
			if(!artists.contains(s.artist))
				artists.add(s.artist);
		}
		return artists;
	}
	
	/*
	 * ArrayList<String> getAlbums() -- returns the names of every full album
	 * included in the user's library
	 * Note: We interpreted the ability to list albums to be specific to full albums
	 * in the library as opposed to every album from which a song in the library comes
	 * from.
	 */
	public ArrayList<String> getAlbums() {
		ArrayList<String> albumsList = new ArrayList<String>();
		for(Album a : albums) {
			albumsList.add(a.name);
		}
		return albumsList;
	}
	
	public ArrayList<String> getPlaylists() {
		ArrayList<String> playlistList = new ArrayList<String>();
		for(PlayList p : playlists) {
			playlistList.add(p.name);
		}
		return playlistList;
	}
	
	public ArrayList<Song> getFavorites() {
		ArrayList<Song> favorites = new ArrayList<Song>();
		for(Song s : library) {
			if(s.isFavorite())
				favorites.add(new Song(s));
		}
		return favorites;
	}
	
	public void markFavorite(String songName) {
		for(Song s : library)
			if(s.name.equals(songName))
				s.setFavorite();
	}
	
	public void rateSong(String songName, int rating) {
		for(Song s : library)
			if(s.name.equals(songName))
				s.setRating(rating);
	}
}
