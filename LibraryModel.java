/*
 * File: LibraryModel.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: Models a user's library of music
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class LibraryModel {
	private ArrayList<Song> library;
	private HashMap<Integer, ArrayList<Song>> libraryByArtist;
	private HashMap<Integer, ArrayList<Song>> libraryByTitle;
	private HashMap<Integer, ArrayList<Album>> albumsByArtist;
	private HashMap<Integer, ArrayList<Album>> albumsByTitle;
	private HashMap<Integer, PlayList> playlists;
	private ArrayList<Song> recentlyPlayed;
	private HashMap<Integer, ArrayList<Song>> frequentlyPlayed;
	private HashMap<String, ArrayList<Song>> genreLists;
	private MusicStore ms;
	
	public LibraryModel(MusicStore ms) {
		library = new ArrayList<Song>();
		libraryByArtist = new HashMap<Integer, ArrayList<Song>>();
		libraryByTitle = new HashMap<Integer, ArrayList<Song>>();
		albumsByArtist = new HashMap<Integer, ArrayList<Album>>();
		albumsByTitle = new HashMap<Integer, ArrayList<Album>>();
		playlists = new HashMap<Integer, PlayList>();
		recentlyPlayed = new ArrayList<Song>();
		frequentlyPlayed = new HashMap<Integer, ArrayList<Song>>();
		frequentlyPlayed.put(0, new ArrayList<Song>());
		genreLists = new HashMap<String, ArrayList<Song>>();
		this.ms = ms;
		playlists.put("favorites".hashCode(), new PlayList("Favorites"));
		playlists.put("top rated".hashCode(), new PlayList("Top Rated"));
	}
	
	/*
	 * void addSong(String songName) -- adds a song with title songName from the music
	 * store to the library.
	 */
	public void addSong(String songName) {
		ArrayList<Song> songs = ms.searchSongWithTitle(songName);
		for(Song s : songs) {
			s = new Song(s);
			ArrayList<Album> albums = ms.searchAlbumWithTitle(s.album);
			String genre = "";
			for(Album a : albums) {
				if(s.artist.equals(a.artist)) {
					if(genreLists.get(a.genre.toLowerCase()) == null) {
						genreLists.put(a.genre.toLowerCase(), new ArrayList<Song>());
					}
					genre = a.genre.toLowerCase();
					ArrayList<Album> alist = new ArrayList<Album>();
					if(albumsByTitle.get(a.hashCodeName()) != null) {
						alist.add(new Album(albumsByTitle.get(a.hashCodeName()).get(0), s, true));
					} else {
						alist.add(new Album(a, s.name));
					}
					albumsByArtist.put(a.hashCodeArtist(), alist);
					albumsByTitle.put(a.hashCodeName(), alist);
				}
			}

			genreLists.get(genre).add(s);
			frequentlyPlayed.get(0).add(s);
			if(libraryByTitle.get(s.hashCodeName()) == null) {
				ArrayList<Song> slist = new ArrayList<Song>();
				slist.add(s);
				libraryByTitle.put(s.hashCodeName(), slist);

			} else {
				libraryByTitle.get(s.hashCodeName()).add(s);
			}
			if(libraryByArtist.get(s.hashCodeArtist()) == null) {
				ArrayList<Song> slist = new ArrayList<Song>();
				slist.add(s);
				libraryByArtist.put(s.hashCodeArtist(), slist);
			} else {
				libraryByArtist.get(s.hashCodeArtist()).add(s);
			}
			library.add(s);
		}
	}
	
	/*
	 * void addSong(String songName, String artist) -- adds a song with title SongName and artist <artist>
	 * from the music store to the library
	 */
	public void addSong(String songName, String artist) {
		ArrayList<Song> songs = ms.searchSongWithTitle(songName);
		for(Song s : songs) {
			s = new Song(s);
			if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
				ArrayList<Album> albums = ms.searchAlbumWithTitle(s.album);
				String genre = "";
				for(Album a : albums) {
					if(s.artist.toLowerCase().equals(a.artist.toLowerCase())) {
						if(genreLists.get(a.genre.toLowerCase()) == null) {
							genreLists.put(a.genre.toLowerCase(), new ArrayList<Song>());
						}
						genre = a.genre.toLowerCase();
						ArrayList<Album> alist = new ArrayList<Album>();
						if(albumsByTitle.get(a.hashCodeName()) != null) {
							alist.add(new Album(albumsByTitle.get(a.hashCodeName()).get(0), s, true));
						} else {
							alist.add(new Album(a, s.name));
						}
						albumsByArtist.put(a.hashCodeArtist(), alist);
						albumsByTitle.put(a.hashCodeName(), alist);
					}
				}
				genreLists.get(genre).add(s);
				frequentlyPlayed.get(0).add(s);
				if(libraryByTitle.get(s.hashCodeName()) == null) {
					ArrayList<Song> slist = new ArrayList<Song>();
					slist.add(s);
					libraryByTitle.put(s.hashCodeName(), slist);
				} else {
					libraryByTitle.get(s.hashCodeName()).add(s);
				}
				if(libraryByArtist.get(s.hashCodeArtist()) == null) {
					ArrayList<Song> slist = new ArrayList<Song>();
					slist.add(s);
					libraryByArtist.put(s.hashCodeArtist(), slist);
				} else {
					libraryByArtist.get(s.hashCodeArtist()).add(s);
				}
				library.add(s);
			}
		}
	}
	
	/*
	 * void addAlbum(String albumName) -- adds an album with title albumName from the 
	 * music store to the library
	 */
	public void addAlbum(String albumName) {
		ArrayList<Album> foundAlbums = ms.searchAlbumWithTitle(albumName);
		for(Album a : foundAlbums) {
			a = new Album(a);
			if(genreLists.get(a.genre.toLowerCase()) == null) {
				genreLists.put(a.genre.toLowerCase(), new ArrayList<Song>());
			}
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
			if(libraryByArtist.get(a.hashCodeArtist()) == null) {
				libraryByArtist.put(a.hashCodeArtist(), new ArrayList<Song>());
			}
			for(Song s : songs) {
				if(libraryByTitle.get(s.hashCodeName()) != null) {
					if(libraryByTitle.get(s.hashCodeName()).contains(s)) {
						continue;
					}
					else {
						libraryByTitle.get(s.hashCodeName()).add(s);
						libraryByArtist.get(s.hashCodeArtist()).add(s);
						genreLists.get(a.genre.toLowerCase()).add(s);
						frequentlyPlayed.get(0).add(s);
					}
				}
				else {
					ArrayList<Song> slist = new ArrayList<Song>();
					slist.add(s);
					libraryByTitle.put(s.hashCodeName(), slist);
					libraryByArtist.get(s.hashCodeArtist()).add(s);
					genreLists.get(a.genre.toLowerCase()).add(s);
					frequentlyPlayed.get(0).add(s);
				}
				if(!library.contains(s)) {
					library.add(s);
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
			a = new Album(a);
			if(a.artist.toLowerCase().equals(artist.toLowerCase())) {
				if(genreLists.get(a.genre.toLowerCase()) == null) {
					genreLists.put(a.genre.toLowerCase(), new ArrayList<Song>());
				}
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
				if(libraryByArtist.get(a.hashCodeArtist()) == null) {
					libraryByArtist.put(a.hashCodeArtist(), new ArrayList<Song>());
				}
					for(Song s : songs) {
						if(libraryByTitle.get(s.hashCodeName()) != null) {
							if(libraryByTitle.get(s.hashCodeName()).contains(s)) {
								continue;
							}
							else {
								libraryByTitle.get(s.hashCodeName()).add(s);
								libraryByArtist.get(s.hashCodeArtist()).add(s);
								genreLists.get(a.genre.toLowerCase()).add(s);
								frequentlyPlayed.get(0).add(s);
							}
						}
						else {
							ArrayList<Song> slist = new ArrayList<Song>();
							slist.add(s);
							libraryByTitle.put(s.hashCodeName(), slist);
							libraryByArtist.get(s.hashCodeArtist()).add(s);
							genreLists.get(a.genre.toLowerCase()).add(s);
							frequentlyPlayed.get(0).add(s);
						}
						if(!library.contains(s)) {
							library.add(s);
						}
					}
				}
			}
		}

	/*
	 * void removeSong(String songName) -- removes the song with songName from the library
	 */
	public void removeSong(String songName) {
		ArrayList<Song> song = libraryByTitle.remove(songName.toLowerCase().hashCode());
		ArrayList<Song> artistsSongs = libraryByArtist.get(song.get(0).hashCodeArtist());
		artistsSongs.remove(song.get(0));
		library.remove(song.get(0));
		ArrayList<Album> album = albumsByTitle.get(song.get(0).album.toLowerCase().hashCode());
		Album a = album.remove(0);
		genreLists.get(a.genre.toLowerCase()).remove(song.get(0));
		Album newAlbum = new Album(a, song.get(0), false);
		album.add(newAlbum);
		ArrayList<Album> artistsAlbums = albumsByArtist.get(song.get(0).hashCodeArtist());
		artistsAlbums.remove(a);
		artistsAlbums.add(newAlbum);
	}
	
	/*
	 * void removeSong(String songName, String artist) -- removes the song titled songName and whose artist
	 * is artist
	 */
	public void removeSong(String songName, String artist) {
		ArrayList<Song> songs = libraryByTitle.get(songName.toLowerCase().hashCode());
		if(songs == null)
			return;
		Song songToDelete = null;
		for(int i = 0; i < songs.size(); i++) {
			Song s = songs.get(i);
			if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
				songs.remove(s);
				songToDelete = s;
				i--;
			}
		}
		if(songToDelete == null) {
			return;
		}
		library.remove(songToDelete);
		libraryByArtist.get(songToDelete.hashCodeArtist()).remove(songToDelete);
		Album a = albumsByTitle.get(songToDelete.album.toLowerCase().hashCode()).remove(0);
		genreLists.get(a.genre.toLowerCase()).remove(songToDelete);
		Album newAlbum = new Album(a, songToDelete, false);
		albumsByTitle.get(songToDelete.album.toLowerCase().hashCode()).add(newAlbum);
		albumsByArtist.get(songToDelete.hashCodeArtist()).remove(a);
		albumsByArtist.get(songToDelete.hashCodeArtist()).add(newAlbum);
	}
	
	/*
	 * void removeAlbum(String albumName) -- removes the album with name albumName from the library
	 */
	public void removeAlbum(String albumName) {
		Album a = albumsByTitle.get(albumName.toLowerCase().hashCode()).get(0);
		for(int i = 0; i < libraryByArtist.get(a.hashCodeArtist()).size(); i++) {
			Song s = libraryByArtist.get(a.hashCodeArtist()).get(i);
			if(s.album.equals(a.name)) {
				libraryByArtist.get(a.hashCodeArtist()).remove(s);
				ArrayList<Song> songs = libraryByTitle.get(s.hashCodeName());
				if(songs.size() > 1) {
					for(Song s1 : songs) {
						if(s1.artist.equals(a.artist)) {
							songs.remove(s1);
						}
					}
				}
				else
					libraryByTitle.remove(s.hashCodeName());
				library.remove(s);
				genreLists.get(a.genre.toLowerCase()).remove(s);
				i--;
			}
		}
		albumsByTitle.remove(a.hashCodeName());
		albumsByArtist.get(a.hashCodeArtist()).remove(a);
	}
	
	/*
	 * void shuffle() -- shuffles the user library
	 */
	public void shuffle() {
		Collections.shuffle(library);
	}
	
	/*
	 * void createPlaylist(String playListName) -- creates a new PlayList with name playListName.
	 */
	public void createPlayList(String playListName) {
		if(playListName.toLowerCase().equals("frequently played") || playListName.toLowerCase().equals("recently played") || genreLists.keySet().contains(playListName.toLowerCase()))
			return;
		playlists.put(playListName.toLowerCase().hashCode(), new PlayList(playListName));
	}
	
	/*
	 * void addSongToPlaylist(String playListName, String songName) -- adds song with title songName to 
	 * the playlist with name playListName
	 */
	public void addSongToPlaylist(String playListName, String songName) {
		if(playListName.toLowerCase().equals("recently played")) {
			ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
			for(Song s : slist) {
				recentlyPlayed.add(s);
			}
		}
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		if(p == null)
			return;
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		if(slist == null)
			return;
		for(Song s : slist) {
			p.addSong(s);
		}
	}
	
	/*
	 * void addSongToPlaylist(String playListName, String songName, String artist) -- adds song with title songName and artist artist to 
	 * the playlist with name playListName
	 */
	public void addSongToPlaylist(String playListName, String songName, String artist) {
		if(playListName.toLowerCase().equals("recently played")) {
			ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
			for(Song s : slist) {
				if(s.artist.toLowerCase().equals(artist.toLowerCase()))
					recentlyPlayed.add(s);
			}
		}
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		if(p == null)
			return;
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		if(slist == null)
			return;
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
		if(slist == null)
			return;
		for(Song s : slist) {
			p.removeSong(s);
		}
	}
	
	/*
	 * void removeSongFromPlaylist(String playListName, String songName) -- remove song with title songName and artist artist from
	 * the playlist with name playListName
	 */
	public void removeSongFromPlaylist(String playListName, String songName, String artist) {
		PlayList p = playlists.get(playListName.toLowerCase().hashCode());
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase()))
				p.removeSong(s);
		}
	}
	
	/*
	 * void shufflePlaylist(String playlistName) -- shuffles the playlist with given name
	 */
	public void shufflePlaylist(String playlistName) {
		PlayList p = playlists.get(playlistName.toLowerCase().hashCode());
		p.shuffle();
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
	 * ArrayList<Song> searchSongsWithGenre(String genre) -- returns all songs categorized into genre <genre>
	 */
	public ArrayList<Song> searchSongsWithGenre(String genre){
		ArrayList<Song> songs = new ArrayList<Song>();
		if(genreLists.get(genre.toLowerCase()) != null) {
			for(Song s : genreLists.get(genre.toLowerCase())) {
				songs.add(new Song(s));
			}
		}
		else
			return new ArrayList<Song>();
		return songs;
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
		if(playlistName.toLowerCase().equals("frequently played")) {
			Integer[] keys = frequentlyPlayed.keySet().toArray(new Integer[frequentlyPlayed.keySet().size()]);
			ArrayList<Song> songs = new ArrayList<Song>();
			while(songs.size() < 10) {
				Integer max = keys[0];
				int pos = 0;
				for(int i = 1; i < keys.length; i++) {
					if(keys[i] > max) {
						max = keys[i];
						pos = i;
					}
				}
				keys[pos] = 0;
				for(Song s : frequentlyPlayed.get(max)) {
					if(songs.size() == 10)
						break;
					songs.add(new Song(s));
				}
			}
			return songs;
		}
		else if(playlistName.toLowerCase().equals("recently played")) {
			ArrayList<Song> songs = new ArrayList<Song>();
			for(Song s : recentlyPlayed) {
				songs.add(new Song(s));
			}
			return songs;
		}
		else if(genreLists.get(playlistName.toLowerCase()) != null) {
			if(genreLists.get(playlistName.toLowerCase()).size() < 10)
				return null;
			ArrayList<Song> songs = new ArrayList<Song>();
			for(Song s : genreLists.get(playlistName.toLowerCase())) {
				songs.add(new Song(s));
			}
			return songs;
		}
		else {
			PlayList p = playlists.get(playlistName.toLowerCase().hashCode());
			if(p == null)
				return null;
			return p.getPlaylist();
		}
	}
	
	/*
	 * ArrayList<String> getSongsSortedByTitle() -- returns a list with the names of all songs sorted by their titles
	 * in ascending order
	 */
	public ArrayList<String> getSongsSortedByTitle() {
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<Song> sorted = new ArrayList<Song>();
		sorted.addAll(library);
		Collections.sort(sorted, new SortByTitle());
		for(Song s : sorted) {
			titles.add(s.name);
		}
		return titles;
	}
	
	/*
	 * ArrayList<String> getSongsSortedByArtist() -- returns a list with the names of all songs sorted by their artists
	 * in ascending order
	 */
	public ArrayList<String> getSongsSortedByArtist()  {
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<Song> sorted = new ArrayList<Song>();
		sorted.addAll(library);
		Collections.sort(sorted, new SortByArtist());
		for(Song s : sorted) {
			titles.add(s.name);
		}
		return titles;
	}
	
	/*
	 * ArrayList<String> getSongsSortedByRating() -- returns a list with the names of all songs sorted by their ratings
	 * in ascending order
	 */
	public ArrayList<String> getSongsSortedByRating() {
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<Song> sorted = new ArrayList<Song>();
		sorted.addAll(library);
		Collections.sort(sorted, new SortByRating());
		for(Song s : sorted) {
			titles.add(s.name);
		}
		return titles;
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
		playlistList.add("Recently Played");
		playlistList.add("Frequently Played");
		for(String s : genreLists.keySet()) {
			if(genreLists.get(s).size() >= 10) {
				playlistList.add(s.substring(0, 1).toUpperCase() + s.substring(1));
			}
		}
		for(Integer i : playlists.keySet()) {
			playlistList.add(playlists.get(i).name);
		}
		return playlistList;
	}
	
	/*
	 * ArrayList<String> getGenres() -- returns a list of all the genres present in the library
	 */
	public ArrayList<String> getGenres() {
		ArrayList<String> genres = new ArrayList<String>();
		for(String s : genreLists.keySet()) {
			genres.add(s.substring(0, 1).toUpperCase() + s.substring(1));
		}
		return genres;
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

	/*
	 * void rateSong(String songName, String artistName int rating) -- changes the rating of song with name songName and 
   	 * artsit artistName to <rating>
	 */
	public void rateSong(String songName, String artist, int rating) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
				s.setRating(rating);
				if(rating == 4)
					addSongToPlaylist("Top Rated", s.name, s.artist);
				else if(rating == 5) {
					addSongToPlaylist("Favorites", s.name, s.artist);
					addSongToPlaylist("Top Rated", s.name, s.artist);
				}
			}
		}
	}
	
	/*
	 * int getRating(String songName, String artistName) -- returns the rating of the song named songName whose artist is artistName,
	 * or -1 if the song is not in the library
	 */
	public int getRating(String songName, String artistName) {
		Song s = findSong(songName, artistName);
		if(s != null) {
			return s.getRating();
		} else {
			return -1;
		}
	}
	
	/*
	 * void playSong(String songName) -- simulates playing a song songName, adjusting its number of plays as well as
	 * frequently and recently played playlists
	 */
	public void playSong(String songName) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			int plays = s.getPlays();
			frequentlyPlayed.get(plays).remove(s);
			if(frequentlyPlayed.get(plays + 1) == null) {
				frequentlyPlayed.put(plays + 1, new ArrayList<Song>());
			}
			frequentlyPlayed.get(plays + 1).add(s);
			if(recentlyPlayed.size() == 10) {
				recentlyPlayed.remove(9);
				recentlyPlayed.add(0, s);
			}
			else {
				recentlyPlayed.add(0, s);
			}
			s.play();
		}
	}
	
	/*
	 * void playSong(String songName, String artist) -- simulates playing a song songName with artist artist, adjusting its number of plays as well as
	 * frequently and recently played playlists
	 */
	public void playSong(String songName, String artist) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s : slist) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
				int plays = s.getPlays();
				frequentlyPlayed.get(plays).remove(s);
				if(frequentlyPlayed.get(plays + 1) == null) {
					frequentlyPlayed.put(plays + 1, new ArrayList<Song>());
				}
				frequentlyPlayed.get(plays + 1).add(s);
				if(recentlyPlayed.size() == 10) {
					recentlyPlayed.remove(9);
					recentlyPlayed.add(0, s);
				}
				else {
					recentlyPlayed.add(0, s);
				}
				s.play();
			}
		}
	}
	
	/*
	 * void setPlays(String songName, String artistName, int n) -- sets the number of plays for a song songName with artist <artist> to n,
	 * adjusting frequently played accordingly
	 */
	public void setPlays(String songName, String artistName, int n) {
		findSong(songName, artistName).play(n);
		frequentlyPlayed.get(0).remove(findSong(songName, artistName));
		if(frequentlyPlayed.get(n) == null)
			frequentlyPlayed.put(n, new ArrayList<Song>());
		frequentlyPlayed.get(n).add(findSong(songName, artistName));
	}
	
	/*
	 * int getPlays(String songName, String artistName) -- returns the number of plays for a song songName with artist artistName
	 */
	public int getPlays(String songName, String artistName) {
		return findSong(songName, artistName).getPlays();
	}
	
	/*
	 * Song findSong(String songName, String artistName) -- returns the song of name songName and artist artistName, or null
	 * if the song is not in the library
	 */
	private Song findSong(String songName, String artistName) {
		ArrayList<Song> slist = libraryByTitle.get(songName.toLowerCase().hashCode());
		for(Song s: slist) {
			if(s.artist.toLowerCase().equals(artistName.toLowerCase())) {
				return s;
			}
		}
		return null;
	}
}
