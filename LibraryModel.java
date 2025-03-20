/*
 * File: LibraryModel.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Models a user's library of music
 */

import java.util.ArrayList;
import java.util.HashMap;

public class LibraryModel {
	private HashMap<Integer, ArrayList<Song>> libraryByArtist;
	private HashMap<Integer, ArrayList<Song>> libraryByTitle;
	private HashMap<Integer, ArrayList<Album>> albumsByArtist;
	private HashMap<Integer, ArrayList<Album>> albumsByTitle;
	private HashMap<Integer, PlayList> playlists;
	private MusicStore ms;
	private Song currentPlay;
	
	public LibraryModel(MusicStore ms) {
		libraryByArtist = new HashMap<Integer, ArrayList<Song>>();
		libraryByTitle = new HashMap<Integer, ArrayList<Song>>();
		albumsByArtist = new HashMap<Integer, ArrayList<Album>>();
		albumsByTitle = new HashMap<Integer, ArrayList<Album>>();
		playlists = new HashMap<Integer, PlayList>();
		this.ms = ms;
		playlists.put("favorites".hashCode(), new PlayList("Favorites"));
		playlists.put("top rated".hashCode(), new PlayList("Top Rated"));
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
		for(Song s : songs) {
			ArrayList<Album> albums = ms.searchAlbumWithTitle(s.album);
			for(Album a : albums) {
				if(s.artist.equals(a.artist)) {
					ArrayList<Album> alist = new ArrayList<Album>();
					alist.add(new Album(a, s.name));
					albumsByArtist.put(a.hashCodeArtist(), alist);
					albumsByTitle.put(a.hashCodeName(), alist);
				}
			}
			ArrayList<Song> slist = new ArrayList<Song>();
			slist.add(s);
			libraryByArtist.put(s.hashCodeArtist(), slist);
			libraryByTitle.put(s.hashCodeName(), slist);
		}
	}
	
	/*
	 * void addSong(String songName, String artist) -- adds a song with title SongName and artist <artist>
	 * from the music store to the library
	 */
	public void addSong(String songName, String artist) {
		ArrayList<Song> songs = ms.searchSongWithTitle(songName);
		for(Song s : songs) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
				ArrayList<Album> albums = ms.searchAlbumWithTitle(s.album);
				for(Album a : albums) {
					if(s.artist.equals(a.artist)) {
						ArrayList<Album> alist = new ArrayList<Album>();
						alist.add(new Album(a, s.name));
						albumsByArtist.put(a.hashCodeArtist(), alist);
						albumsByTitle.put(a.hashCodeName(), alist);
					}
				}
				ArrayList<Song> slist = new ArrayList<Song>();
				slist.add(s);
				libraryByArtist.put(s.hashCodeArtist(), slist);
				libraryByTitle.put(s.hashCodeName(), slist);
			}
		}
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
			if(albumsByArtist.get(a.hashCodeArtist()) == null) {
				albumsByArtist.put(a.hashCodeArtist(), new ArrayList<Album>());
			}
			if(albumsByTitle.get(a.hashCodeName()) != null) {
				if(albumsByTitle.get(a.hashCodeName()).contains(a)) {
					albumsByTitle.get(a.hashCodeName()).remove(a);
					albumsByTitle.get(a.hashCodeName()).add(a);
					albumsByArtist.get(a.hashCodeArtist()).remove(a);
					albumsByArtist.get(a.hashCodeArtist()).add(a);
				}
				else {
					ArrayList<Album> alist = new ArrayList<Album>();
					alist.add(a);
					albumsByTitle.put(a.hashCodeName(), alist);
					albumsByArtist.get(a.hashCodeArtist()).add(a);
				}
			}
			else {
				ArrayList<Album> alist = new ArrayList<Album>();
				alist.add(a);
				albumsByTitle.put(a.hashCodeName(), alist);
				albumsByArtist.get(a.hashCodeArtist()).add(a);
			}
			ArrayList<Song> songs = a.getAlbum();
			if(libraryByArtist.get(a.hashCodeArtist()) == null)
				libraryByArtist.put(a.hashCodeArtist(), new ArrayList<Song>())
			for(Song s : songs) {
				if(libraryByTitle.get(s.hashCodeName()) != null) {
					if(libraryByTitle.get(s.hashCodeName()).contains(s))
						continue;
					else {
						libraryByTitle.get(s.hashCodeName()).add(s);
						libraryByArtist.get(s.hashCodeArtist()).add(s);
					}
				}
				else {
					ArrayList<Song> slist = new ArrayList<Song>();
					slist.add(s);
					libraryByTitle.put(s.hashCodeName(), slist);
					libraryByArtist.get(s.hashCodeArtist()).add(s);
				}
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
				if(albumsByArtist.get(a.hashCodeArtist()) == null) {
					albumsByArtist.put(a.hashCodeArtist(), new ArrayList<Album>());
				}
				if(albumsByTitle.get(a.hashCodeName()) != null) {
					if(albumsByTitle.get(a.hashCodeName()).contains(a)) {
						albumsByTitle.get(a.hashCodeName()).remove(a);
						albumsByTitle.get(a.hashCodeName()).add(a);
						albumsByArtist.get(a.hashCodeArtist()).remove(a);
						albumsByArtist.get(a.hashCodeArtist()).add(a);
					}
					else {
						ArrayList<Album> alist = new ArrayList<Album>();
						alist.add(a);
						albumsByTitle.put(a.hashCodeName(), alist);
						albumsByArtist.get(a.hashCodeArtist()).add(a);
					}
				}
				else {
					ArrayList<Album> alist = new ArrayList<Album>();
					alist.add(a);
					albumsByTitle.put(a.hashCodeName(), alist);
					albumsByArtist.get(a.hashCodeArtist()).add(a);
				}
				ArrayList<Song> songs = a.getAlbum();
				if(libraryByArtist.get(a.hashCodeArtist()) == null)
					libraryByArtist.put(a.hashCodeArtist(), new ArrayList<Song>())
					for(Song s : songs) {
						if(libraryByTitle.get(s.hashCodeName()) != null) {
							if(libraryByTitle.get(s.hashCodeName()).contains(s))
								continue;
							else {
								libraryByTitle.get(s.hashCodeName()).add(s);
								libraryByArtist.get(s.hashCodeArtist()).add(s);
							}
						}
						else {
							ArrayList<Song> slist = new ArrayList<Song>();
							slist.add(s);
							libraryByTitle.put(s.hashCodeName(), slist);
							libraryByArtist.get(s.hashCodeArtist()).add(s);
						}
					}
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
		playlists.put(playListName.toLowerCase().hashCode(), new PlayList(playListName));
	}
	
	/*
	 * void addSongToPlaylist(String playListName, String songName) -- adds song with title songName to 
	 * the playlist with name playListName
	 */
	public void addSongToPlaylist(String playListName, String songName) {
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			p.addSong(s);
		}
	}
	
	public void addSongToPlaylist(String playListName, String songName, String artist) {
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase()))
				p.addSong(s);
		}
	}
	
	/*
	 * void removeSongFromPlaylist(String playListName, String songName) -- remove song with title songName from
	 * the playlist with name playListName
	 */
	public void removeSongFromPlaylist(String playListName, String songName) {
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			p.removeSong(s);
		}
	}
	
	public void removeSongToPlaylist(String playListName, String songName, String artist) {
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase()))
				p.removeSong(s);
		}
	}
	
	/*
	 * ArrayList<Song> searchSongWithTitle(String songName) -- returns an ArrayList of Song items containing every
	 * song in the library with title songName.  Returns an empty list if no such song exists.
	 */
	public ArrayList<Song> searchSongWithTitle(String songName) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		if(slist == null)
			slist = new ArrayList<Song>();
		else
			for(int i = 0; i < slist.size(); i++) {
				slist.add(i, new Song(slist.remove(i)));
			}
		return slist;
	}
	
	/*
	 * ArrayList<Song> searchSongWithArtist(String artistName) -- returns an ArrayList of Song items containing every
	 * song in the library with artist artistName. Returns an empty list if no such song exists.
	 */
	public ArrayList<Song> searchSongWithArtist(String artistName) {
		ArrayList<Song> slist = libraryByArtist.get(artistName.toLowerCase().hashCode());
		if(slist == null)
			slist = new ArrayList<Song>();
		else
			for(int i = 0; i < slist.size(); i++) {
				slist.add(i, new Song(slist.remove(i)));
			}
		return slist;
	}
	
	/*
	 * ArrayList<Album> searchAlbumWithTitle(String albumName) -- returns an ArrayList of Album items containing every
	 * album in the library with title albumName. Returns an empty list if no such album exists.
	 */
	public ArrayList<Album> searchAlbumWithTitle(String albumName) {
		ArrayList<Album> alist = albumsByTitle.get(albumName.toLowerCase().hashCode());
		if(alist == null)
			alist = new ArrayList<Album>();
		else
			for(int i = 0; i < alist.size(); i++) {
				alist.add(i, new Album(alist.remove(i)));
			}
		return alist;
	}
	
	/*
	 * ArrayList<Album> searchSongWithArtist(String artistName) -- returns an ArrayList of Album items containing every
	 * album in the library with artist artistName. Returns an empty list if no such album exists.
	 */
	public ArrayList<Album> searchAlbumWithArtist(String artistName) {
		ArrayList<Album> alist = albumsByArtist.get(artistName.toLowerCase().hashCode());
		if(alist == null)
			alist = new ArrayList<Album>();
		else
			for(int i = 0; i < alist.size(); i++) {
				alist.add(i, new Album(alist.remove(i)));
			}
		return alist;
	}
	
	/*
	 * ArrayList<Song> searchPlaylist(String playlistName) -- returns a list of songs from the playlist with name playListName
	 */
	public ArrayList<Song> searchPlaylist(String playlistName) {
		PlayList p = playlists.get(playlistName.toLowerCase().hashCode());
		if(p == null)
			return null;
		return p.getPlaylist();
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every song in the user's library
	 */
	public ArrayList<String> getSongs() {
		ArrayList<String> titles = new ArrayList<String>();
		for(Integer i : libraryByTitle.keySet()) {
			for(Song s : libraryByTitle.get(i))
				titles.add(s.name);
		}
		return titles;
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every artist present in the user's library
	 */
	public ArrayList<String> getArtists() {
		ArrayList<String> artists = new ArrayList<String>();
		for(Integer i : albumsByArtist.keySet()) {
			artists.add(albumsByArtist.get(i).get(0).artist);
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
		for(Integer i : albumsByTitle.keySet()) {
			ArrayList<Album> alist = albumsByTitle.get(i);
			for(Album a : alist) {
				albumsList.add(a.name);
			}
		}
		return albumsList;
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every playlist in the user's library
	 */
	public ArrayList<String> getPlaylists() {
		ArrayList<String> playlistList = new ArrayList<String>();
		for(Integer i : playlists.keySet()) {
			playlistList.add(playlists.get(i).name);
		}
		return playlistList;
	}
	
	/*
	 * ArrayList<String> getSongs() -- returns the names of every song that is favorited in the user's library
	 */
	public ArrayList<Song> getFavorites() {
		return playlists.get("favorites".hashCode()).getPlaylist();
	}
	
	/*
	 * void markFavorite(String songName) -- marks every song with name songName within the library as a favorite.
	 */
	public void markFavorite(String songName) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			s.setFavorite();
			addSongToPlaylist("Favorites", s.name);
			addSongToPlaylist("Top Rated", s.name);
		}
	}
	
	/*
	 * void rateSong(String songName, int rating) -- changes the rating of every song with name songName to <rating>
	 */
	public void rateSong(String songName, int rating) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			s.setRating(rating);
			if(rating == 4)
				addSongToPlaylist("Top Rated", s.name);
			else if(rating == 5) {
				addSongToPlaylist("Favorites", s.name);
				addSongToPlaylist("Top Rated", s.name);
			}
		}
	}
}
