/*
 * File: LibraryModel.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Models a user's library of music
 */

import java.util.ArrayList;

public class LibraryModel {
	private ArrayList<Song> library;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playlists;
	private MusicStore ms;
	private Song currentPlay;
	
	public LibraryModel(MusicStore ms) {
		library = new ArrayList<Song>();
		albums = new ArrayList<Album>();
		playlists = new ArrayList<PlayList>();
		this.ms = ms;
		currentPlay = null;
	}
	
	
	public void playSong(String songName, String artistName) {
		//TODO: need to add way to search if there are songs with multiple writers
	}
	
	public void playSong(String songName) {
		for(Song s: library) {
			if(s.name.equals(songName)) {
				currentPlay = s;
				currentPlay.play();
			}
		}
	}
	
	/*
	 * void addSong(String songName) -- adds a song with title songName from the music
	 * store to the library.
	 */
	public void addSong(String songName) {
		ArrayList<Song> songs = ms.searchSongWithTitle(songName);
		for(Song s : songs)
			library.add(s);
	}
	
	/*
	 * void addSong(String songName, String artist) -- adds a song with title SongName and artist <artist>
	 * from the music store to the library
	 */
	public void addSong(String songName, String artist) {
		ArrayList<Song> songs = ms.searchSongWithTitle(songName);
		for(Song s : songs)
			if(s.artist.toLowerCase().equals(artist.toLowerCase()))
				library.add(s);
	}
	
	//TODO: may not work and needs .equals instead
	public void addSong(Song s) {
		if(!library.contains(s)) {
			library.add(new Song(s));
		}
	}
	
	/*
	 * void addAlbum(String albumName) -- adds an album with title albumName from the 
	 * music store to the library
	 */

	public void addAlbum(String albumName) {
		ArrayList<Album> foundAlbums = ms.searchAlbumWithTitle(albumName);
		for(Album a : foundAlbums) {
			albums.add(a);
			for(Song s : a.getAlbum()) {
				boolean songAlreadyAdded = false;
				for(Song s1: library) {
					if(s.name.equals(s1.name) && s.artist.equals(s1.artist)) {
						songAlreadyAdded = true;
						break;
					}
				}
				if(songAlreadyAdded)
					continue;
				library.add(s);
			}
		}
	}
	
	
	/*
	 * void addAlbum(String albumName, String artist) -- adds an album with title albumName and artist <artist>
	 * to the library
	 */
	public void addAlbum(String albumName, String artist) {
		ArrayList<Album> foundAlbums = ms.searchAlbumWithTitle(albumName);
		for(Album a : foundAlbums) {
			if(a.artist.toLowerCase().equals(artist.toLowerCase())) {
				albums.add(a);
				for(Song s : a.getAlbum())
					library.add(s);
			}
		}
	}
	

	public void addAlbum(Album a) {
		albums.add(new Album(a));
	}
	
  
	/*
	 * void createPlaylist(String playListName) -- creates a new Playlist with name playListName.
	 */

	public void createPlayList(String playListName) {
		playlists.add(new PlayList(playListName));
	}
	
	/*
	 * void addSongToPlaylist(String playListName, String songName) -- adds song with title songName to 
	 * the playlist with name playListName
	 */
	public void addSongToPlaylist(String playListName, String songName) {
		Song song = null;
		for(Song s : library) {
			if(s.name.toLowerCase().equals(songName.toLowerCase()))
				song = s;
		}
		for(PlayList p : playlists) {
			if(p.name.toLowerCase().equals(playListName.toLowerCase()))
				p.addSong(song);
		}
	}
	
	/*
	 * void removeSongFromPlaylist(String playListName, String songName) -- remove song with title songName from
	 * the playlist with name playListName
	 */
	public void removeSongFromPlaylist(String playListName, String songName) {
		Song song = null;
		for(Song s : library) {
			if(s.name.toLowerCase().equals(songName.toLowerCase()))
				song = s;
		}
		for(PlayList p : playlists) {
			if(p.name.toLowerCase().equals(playListName.toLowerCase()))
				p.removeSong(song);
		}
	}
	
	/*
	 * ArrayList<Song> searchSongWithTitle(String songName) -- returns an ArrayList of Song items containing every
	 * song in the library with title songName.  Returns an empty list if no such song exists.
	 */
	public ArrayList<Song> searchSongWithTitle(String songName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : library) {
			if(s.name.toLowerCase().equals(songName.toLowerCase()))
				songs.add(new Song(s));
		}
		return songs;
	}
	
	/*
	 * ArrayList<Song> searchSongWithArtist(String artistName) -- returns an ArrayList of Song items containing every
	 * song in the library with artist artistName. Returns an empty list if no such song exists.
	 */
	public ArrayList<Song> searchSongWithArtist(String artistName) {
		ArrayList<Song> songs = new ArrayList<Song>();
		for(Song s : library) {
			if(s.artist.toLowerCase().equals(artistName.toLowerCase()))
				songs.add(new Song(s));
		}
		return songs;
	}
	
	/*
	 * ArrayList<Album> searchAlbumWithTitle(String albumName) -- returns an ArrayList of Album items containing every
	 * album in the library with title albumName. Returns an empty list if no such album exists.
	 */
	public ArrayList<Album> searchAlbumWithTitle(String albumName) {
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for(Album a : albums) {
			if(a.name.toLowerCase().equals(albumName.toLowerCase()))
				searchedAlbums.add(new Album(a));
		}
		return searchedAlbums;
	}
	
	/*
	 * ArrayList<Album> searchSongWithArtist(String artistName) -- returns an ArrayList of Album items containing every
	 * album in the library with artist artistName. Returns an empty list if no such album exists.
	 */
	public ArrayList<Album> searchAlbumWithArtist(String artistName) {
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for(Album a : albums) {
			if(a.artist.toLowerCase().equals(artistName.toLowerCase()))
				searchedAlbums.add(new Album(a));
		}
		return searchedAlbums;
	}
	
	/*
	 * ArrayList<Song> searchPlaylist(String playlistName) -- returns a list of songs from the playlist with name playListName
	 */
	public ArrayList<Song> searchPlaylist(String playlistName) {
		for(PlayList p : playlists) {
			if(p.name.toLowerCase().equals(playlistName.toLowerCase())) {
				return p.getPlaylist();
			}
		}
		return new ArrayList<Song>();
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every song in the user's library
	 */
	public ArrayList<String> getSongs() {
		ArrayList<String> titles = new ArrayList<String>();
		for(Song s : library) {
			titles.add(s.name);
		}
		return titles;
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every artist present in the user's library
	 */
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
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every playlist in the user's library
	 */
	public ArrayList<String> getPlaylists() {
		ArrayList<String> playlistList = new ArrayList<String>();
		for(PlayList p : playlists) {
			playlistList.add(p.name);
		}
		return playlistList;
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every song that is favorited in the user's library
	 */
	public ArrayList<String> getFavorites() {
		ArrayList<String> favorites = new ArrayList<String>();
		for(Song s : library) {
			if(s.isFavorite())
				favorites.add(s.name);
		}
		return favorites;
	}
	
	/*
	 * void markFavorite(String songName) -- marks every song with name songName within the library as a favorite.
	 */
	public void markFavorite(String songName) {
		for(Song s : library)
			if(s.name.toLowerCase().equals(songName.toLowerCase()))
				s.setFavorite();
	}
	
	/*
	 * void rateSong(String songName, int rating) -- changes the rating of every song with name songName to <rating>
	 */
	public void rateSong(String songName, int rating) {
		for(Song s : library)
			if(s.name.toLowerCase().equals(songName.toLowerCase()))
				s.setRating(rating);
	}
	
	//TODO add artist getRating
	public int getRating(String songName) {
		for(Song s: library) {
			if(s.name.toLowerCase().equals(songName.toLowerCase())) {
				return s.getRating();
			}
		}
		return -1;
	}
}
