/*
 * simulates the program
 */
import java.util.ArrayList;
import java.util.Scanner;


public class main {
	
	private static final String helpMenu = 	"Help Menu\nCommands:\nadd,<type>,<argument>,<argument2>\n	-adds an item to your library\n	"
			+ "-<type> should contain either A for album or S for song\n	-<argument> should be replaced by the appropriate name for the item you are trying to add\n"
			+ "add,<type>,<argument>,<argument2>\n	-first two arguments function same as for above add command\n	-<argument2> should be replaced with the artist of the song or album if you're"
			+ "trying to add a song for which there are multiple in the music store with the same name but different artists\n"
			+ "addPL,<name>,<argument>\n	-adds a song in your library to a given playlist\n	-<name> should be replaced with the name of the playlist to be added to\n	"
			+ "-<argument> should be replaced with the name of the song to be added\ncreatePL,<argument>\n	-<argument> should be replaced with your desired name for the created playlist\n"
			+ "exit\n	-exits the program\n"
			+ "fav,<argument>\n	-<argument> should be replaced with the name of the song that you wish to favorite\nlist,<type>\n	"
			+ "-prints a list of the each given item of the given type in your library\n	-<type> should be replaced with S for songs, AR for artists, AL for albums, P for playlists, or F for favorited songs\n"
			+ "rate,<argument>,<rating>\n	-<argument> should be replaced with the name of the song you wish to rate\n	-<rating> should be replaced with your desired rating (1 - 5)\n"
			+ "removePL,<name>,<argument>\n	-removes a song from a playlist\n	-<name> should be replaced with the name of the playlist from which you wish to remove a song\n	"
			+ "-<argument> should be replaced with the name of the song you wish to remove\nsearch,<location>,<search type>,<argument>\n	"
			+ "-<location> should have either MS for searching the music store or UL for searching your library\n	"
			+ "-<search type> should have ST for searching for a song by its title, SA for a song by artist, AT for an album by its title,\n"
			+ "		AA for an album by its artist, or P (only applies for your library) for a playlist by name\n	-<argument> should be replaced by the proper argument for your search (song name, song artist, etc.)\n";
	
	private static MusicStore ms = new MusicStore();
	private static LibraryModel ul = new LibraryModel(ms);

	public static void main(String args[]) {
		System.out.println("Welcome to the Music Store\nType help for the list of commands");
		boolean exit = false;
		Scanner keyboard = new Scanner(System.in);
		while(!exit) {
			String[] input = keyboard.nextLine().strip().split(",");
			String command = input[0].toLowerCase();
			if(command.equals("help")) {
				System.out.println(helpMenu);
			}
			else if(command.equals("exit")) {
				System.exit(0);
			}
			else if(command.equals("add")) {
				try{
					add(input[1], input[2], input[3]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					try{
						add(input[1], input[2]);
					}
					catch(ArrayIndexOutOfBoundsException e1) {
						System.out.println("Error: Invalid Input");
					}
				}
			}
			else if(command.equals("addpl")) {
				try{
					addPL(input[1], input[2]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}			
			}
			else if(command.equals("createpl")) {
				try{
					createPL(input[1]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}			
			}
			else if(command.equals("fav")) {
				try{
					favorite(input[1]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}			
			}
			else if(command.equals("list")) {
				try{
					list(input[1]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}			
			}
			else if(command.equals("removepl")) {
				try{
					removePL(input[1], input[2]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}		
			}
			else if(command.equals("search")) {
				try{
					search(input[1], input[2], input[3]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}	
			}
			else
				System.out.println("Error: Invalid Command. Type help for a list of commands");
		}
	}
	
	public static void add(String type, String argument) {
		if(type.toUpperCase().equals("A")) {
			boolean alreadyAdded = false;
			ArrayList<String> albums = ul.getAlbums();
			for(String s : albums) {
				if(s.toLowerCase().equals(argument.toLowerCase()))
					alreadyAdded = true;
			}
			if(alreadyAdded) {
				System.out.println("Error: Album already added to library");
				return;
			}
			if(ms.searchAlbumWithTitle(argument).size() > 0)
				ul.addAlbum(argument);
			else
				System.out.println("Error: Album not in Music Store");
		}
		else if(type.toUpperCase().equals("S")) {
			boolean alreadyAdded = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {
				if(s.toLowerCase().equals(argument.toLowerCase()))
					alreadyAdded = true;
			}
			if(alreadyAdded) {
				System.out.println("Error: Song already added to library");
				return;
			}
			if(ms.searchSongWithTitle(argument).size() > 0)
				ul.addSong(argument);
			else
				System.out.println("Error: Song not in Music Store");
		}
		else
			System.out.println("Error: Invalid Type. Please input S or A for type");
	}
	
	public static void add(String type, String argument, String argument2) {
		if(type.toUpperCase().equals("A")) {
			boolean alreadyAdded = false;
			ArrayList<String> albums = ul.getAlbums();
			for(String s : albums) {
				if(s.toLowerCase().equals(argument.toLowerCase()))
					alreadyAdded = true;
			}
			if(alreadyAdded && ms.searchAlbumWithTitle(argument).size() == ul.searchAlbumWithTitle(argument).size()) {
				System.out.println("Error: Album already added to library");
				return;
			}
			for(Album a : ul.searchAlbumWithTitle(argument)) {
				if(a.artist.toLowerCase().equals(argument2)) {
					System.out.println("Error: Song already added to library");
				}
			}
			if(ms.searchAlbumWithTitle(argument).size() > 0) {
				boolean albumExists = false;
				for(Album a : ms.searchAlbumWithTitle(argument))
					if(a.artist.toLowerCase().equals(argument2.toLowerCase())) {
						albumExists = true;
					}
				if(albumExists)
					ul.addAlbum(argument, argument2);
				else
					System.out.println("Error: No album with that name and artist exists in Music Store");
			}
			else
				System.out.println("Error: Album not in Music Store");
		}
		else if(type.toUpperCase().equals("S")) {
			boolean alreadyAdded = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {
				if(s.toLowerCase().equals(argument.toLowerCase()))
					alreadyAdded = true;
			}
			if(alreadyAdded && ms.searchSongWithTitle(argument).size() == ul.searchSongWithTitle(argument).size()) {
				System.out.println("Error: Song already added to library");
				return;
			}
			for(Song s : ul.searchSongWithTitle(argument)) {
				if(s.artist.toLowerCase().equals(argument2)) {
					System.out.println("Error: Song already added to library");
				}
			}
			if(ms.searchSongWithTitle(argument).size() > 0) {
				boolean songExists = false;
				for(Song s : ms.searchSongWithTitle(argument))
					if(s.artist.toLowerCase().equals(argument2.toLowerCase())) {
						songExists = true;
					}
				if(songExists)
					ul.addSong(argument, argument2);
				else 
					System.out.println("Error: No song with that name and artist in Music Store");
			}
			else
				System.out.println("Error: Song not in Music Store");
		}
		else
			System.out.println("Error: Invalid Type. Please input S or A for type");
	}
	
	public static void addPL(String name, String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		boolean playlistExists = false;
		for(String s : playlists) {
			if(s.toLowerCase().equals(name.toLowerCase()))
				playlistExists = true;
		}
		if(playlistExists) {
			boolean songExists = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {
				if(s.toLowerCase().equals(argument.toLowerCase()))
					songExists = true;
			}
			if(songExists) {
				ul.addSongToPlaylist(name, argument);
			}
			else
				System.out.println("Error: Song not in your library");
		}
		else
			System.out.println("Error: Playlist not created within your library");
	}
	
	public static void createPL(String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		for(String s : playlists) {
			if(s.toLowerCase().equals(argument.toLowerCase())) {
				System.out.println("Error: Playlist already exists in your library");
				return;
			}
		}
		ul.createPlayList(argument);
	}
	
	public static void favorite(String argument) {
		ArrayList<String> songs = ul.getSongs();
		for(String s : songs) {
			if(s.toLowerCase().equals(argument.toLowerCase())) {
				ul.markFavorite(argument);
				return;
			}
		}
		System.out.println("Error: Song not in your library");
	}
	
	public static void list(String type) {
		if(type.toUpperCase().equals("S")) {
			ArrayList<String> songs = ul.getSongs();
			System.out.println("Songs in your library:");
			for(String s : songs)
				System.out.println(s);
		}
		else if(type.toUpperCase().equals("AR")) {
			ArrayList<String> artists = ul.getArtists();
			System.out.println("Artists in your library:");
			for(String s : artists)
				System.out.println(s);
		}
		else if(type.toUpperCase().equals("AL")) {
			ArrayList<String> albums = ul.getAlbums();
			System.out.println("Albums in your library:");
			for(String s : albums)
				System.out.println(s);
		}
		else if(type.toUpperCase().equals("P")) {
			ArrayList<String> playlists = ul.getPlaylists();
			System.out.println("Playlists in your library:");
			for(String s : playlists)
				System.out.println(s);
		}
		else if(type.toUpperCase().equals("F")) {
			ArrayList<String> favorites = ul.getFavorites();
			System.out.println("Favorited songs in your library:");
			for(String s : favorites)
				System.out.println(s);
		}
		else
			System.out.println("Error: Invalid Type. Input S for songs, AL for albums, AR for artists, P for playlists, or F for favorites");
	}
	public static void rate(String argument, int rating) {
		if(rating < 1 || rating > 5) {
			System.out.println("Error: Invalid Rating. Ratings should be between 1 and 5");
			return;
		}
		ArrayList<String> songs = ul.getSongs();
		for(String s : songs) {
			if(s.toLowerCase().equals(argument.toLowerCase())) {
				ul.rateSong(argument, rating);
				return;
			}
		}
		System.out.println("Error: Song not in your library");
	}
	public static void removePL(String name, String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		boolean playlistExists = false;
		for(String s : playlists) {
			if(s.toLowerCase().equals(name.toLowerCase()))
				playlistExists = true;
		}
		if(playlistExists) {
			boolean songExists = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {
				if(s.toLowerCase().equals(argument.toLowerCase()))
					songExists = true;
			}
			if(songExists) {
				ArrayList<Song> playlistSongs = ul.searchPlaylist(name);
				boolean songInPlaylist = false;
				for(Song s : playlistSongs) {
					if(s.name.toLowerCase().equals(argument.toLowerCase()))
						songInPlaylist = true;
				}
				if(songInPlaylist)
					ul.removeSongFromPlaylist(name, argument);
				else
					System.out.println("Error: Song not in playlist");
			}
			else
				System.out.println("Error: Song not in your library");
		}
		else
			System.out.println("Error: Playlist not created within your library");
	}
	
	public static void search(String location, String searchType, String argument) {
		if(location.toUpperCase().equals("MS")) {
			if(searchType.toUpperCase().equals("ST")) {
				ArrayList<Song> songs = ms.searchSongWithTitle(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.toUpperCase().equals("SA")) {
				ArrayList<Song> songs = ms.searchSongWithArtist(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.toUpperCase().equals("AT")) {
				ArrayList<Album> albums = ms.searchAlbumWithTitle(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else if(searchType.toUpperCase().equals("AA")) {
				ArrayList<Album> albums = ms.searchAlbumWithArtist(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else
				System.out.println("Error: Invalid Search Type. Input ST to search for a song by title, SA for a song by artist, AT for an album by title, AA for an album by artist");
		}
		else if(location.toUpperCase().equals("UL")) {
			if(searchType.toUpperCase().equals("ST")) {
				ArrayList<Song> songs = ul.searchSongWithTitle(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.toUpperCase().equals("SA")) {
				ArrayList<Song> songs = ul.searchSongWithArtist(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.toUpperCase().equals("AT")) {
				ArrayList<Album> albums = ul.searchAlbumWithTitle(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else if(searchType.toUpperCase().equals("AA")) {
				ArrayList<Album> albums = ul.searchAlbumWithArtist(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else if(searchType.toUpperCase().equals("P")) {
				ArrayList<Song> songs = ul.searchPlaylist(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else
				System.out.println("Error: Invalid Search Type. Input ST to search for a song by title, SA for a song by artist, AT for an album by title, AA for an album by artist, or P for a playlist");
		}
		else
			System.out.println("Error: Invalid Location. Input UL to search your library and MS to search the music store");
	}
}
