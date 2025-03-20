/*
 * File: view.java
 * Authors: Ethan Cushner and Joseph Hill
 * Purpose: provides a user interface that allows a user to interact
 * with the music store and with their music library
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;


public class view {
	
	private static final String helpMenu = 	"Help Menu\nNote: Commands and arguments are not case sensitive\nCommands:\nadd,<type>,<argument>\n	-adds an item to your library\n	-Note: For songs for which there are multiple with same name in music store, use below add command\n	"
			+ "-<type> should contain either A for album or S for song\n	-<argument> should be replaced by the appropriate name for the item you are trying to add\n"
			+ "add,<type>,<argument>,<argument2>\n	-first two arguments function same as for above add command\n	-<argument2> should be replaced with the artist of the song or album you're trying to add\n"
			+ "addPL,<name>,<argument>\n	-adds a song in your library to a given playlist\n	-<name> should be replaced with the name of the playlist to be added to\n	"
			+ "-<argument> should be replaced with the name of the song to be added\ncreatePL,<argument>\n	-<argument> should be replaced with your desired name for the created playlist\n"
			+ "exit\n	-exits the program\n"
			+ "fav,<argument>\n	-<argument> should be replaced with the name of the song that you wish to favorite\nlist,<type>\n	"
			+ "-prints a list of the each given item of the given type in your library\n	-<type> should be replaced with S for songs, AR for artists, AL for albums, P for playlists, or F for favorited songs\n"
			+ "login,<username>,<password>\n	-<username> should be replaced with your username\n	-<password> should be replaced with your password\n"
			+ "rate,<argument>,<rating>\n	-<argument> should be replaced with the name of the song you wish to rate\n	-<rating> should be replaced with your desired rating (1 - 5)\n"
			+ "removePL,<name>,<argument>\n	-removes a song from a playlist\n	-<name> should be replaced with the name of the playlist from which you wish to remove a song\n	"
			+ "-<argument> should be replaced with the name of the song you wish to remove\nsearch,<location>,<search type>,<argument>\n	"
			+ "-<location> should have either MS for searching the music store or UL for searching your library\n	"
			+ "-<search type> should have ST for searching for a song by its title, SA for a song by artist, AT for an album by its title,\n"
			+ "		AA for an album by its artist, or P (only applies for your library) for a playlist by name\n	-<argument> should be replaced by the proper argument for your search (song name, song artist, etc.)\n";
	
	private static MusicStore ms = new MusicStore();
	private static LibraryModel ul = new LibraryModel(ms);
	private static HashMap<String, String> logins = new HashMap<String, String>();
	

	public static void main(String args[]) {
		//startup();
		System.out.println("Welcome to the Music Store\nType help for the list of commands");
		boolean exit = false;
		Scanner keyboard = new Scanner(System.in);
		//boolean loggedIn = false;
		while(!exit) {
			String[] input = keyboard.nextLine().strip().split(",");
			String command = input[0].toLowerCase();
			if(command.equals("help")) {
				System.out.println(helpMenu);
			}
			else if(command.equals("exit")) {
				keyboard.close();
				System.exit(0);
			}
			/*else if(!loggedIn) {
				System.out.println("Please login before using this command");
			}*/
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
			/*else if(command.equals("login")) {
				try{
					login(input[1], input[2]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}
			}*/
			else if(command.equals("rate")) {
				try{
					String secondInput = input[2];
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}
				try {
					rate(input[1], Integer.parseInt(input[2]));
				}
				catch(NumberFormatException e) {
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
	
	/*
	 * void add(String type, String argument) -- given the type (S for song or A for album),
	 * adds the corresponding item whose name is argument from the music store to the user library. 
	 * If the item is already in the library, will not add and prints an error message. If the type
	 * or argument are invalid, prints appropriate error messages.
	 */
	public static void add(String type, String argument) {
		if(type.toUpperCase().equals("A")) {
			boolean alreadyAdded = false;
			for(String s : ul.getAlbums()) {	// Check if the album is already added
				if(s.toLowerCase().equals(argument.toLowerCase()))
					alreadyAdded = true;
			}
			if(alreadyAdded) {	// Print error if already added
				System.out.println("Error: Album already added to library");
				return;
			}
			if(ms.searchAlbumWithTitle(argument).size() > 0)	// Check if album is in music store
				ul.addAlbum(argument);
			else
				System.out.println("Error: Album not in Music Store");
		}
		else if(type.toUpperCase().equals("S")) {
			boolean alreadyAdded = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {	// Check if the song has already been added
				if(s.toLowerCase().equals(argument.toLowerCase()))
					alreadyAdded = true;
			}
			if(alreadyAdded) {	// Print error if already added
				System.out.println("Error: Song already added to library");
				return;
			}
			if(ms.searchSongWithTitle(argument).size() > 0)	// Check that song is in music store
				ul.addSong(argument);
			else
				System.out.println("Error: Song not in Music Store");
		}
		else	// If invalid type, print error message
			System.out.println("Error: Invalid Type. Please input S or A for type");
	}
	
	/*
	 * void add(String type, String argument, String argument2) -- same functionality as above add
	 * method, except with additional input for the artist of the song. Only meant to be used
	 * for songs or albums that for which there are multiple in the music store with the same name.
	 * Will print error messages if the item is already in the library or if it doesn't exist.
	 */
	public static void add(String type, String argument, String argument2) {
		if(type.toUpperCase().equals("A")) {
			for(Album a : ul.searchAlbumWithTitle(argument)) {	// Check if album already in library
				if(a.artist.toLowerCase().equals(argument2)) {
					System.out.println("Error: Album already added to library");
					return;
				}
			}
			if(ms.searchAlbumWithTitle(argument).size() > 0) {
				boolean albumExists = false;
				for(Album a : ms.searchAlbumWithTitle(argument))	// Check if album of that title exists for artist
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
			for(Song s : ul.searchSongWithTitle(argument)) {	// Check if song already in library
				if(s.artist.toLowerCase().equals(argument2.toLowerCase())) {
					System.out.println("Error: Song already added to library");
					return;
				}
			}
			if(ms.searchSongWithTitle(argument).size() > 0) {
				boolean songExists = false;
				for(Song s : ms.searchSongWithTitle(argument))	// Check that song of that title exists for artist
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
	
	/*
	 * void addPL(String name, String argument) -- adds the song with title <argument> to the playlist
	 * whose title is <name>. Will print error messages if the playlist doesn't exist or if the song
	 * is not in the user's library. Also prints an error message if the song is already in the playlist.
	 */
	public static void addPL(String name, String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		boolean playlistExists = false;
		for(String s : playlists) {	// Make sure playlist has been created
			if(s.toLowerCase().equals(name.toLowerCase()))
				playlistExists = true;
		}
		if(playlistExists) {
			boolean songExists = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {	// Make sure song is in music store
				if(s.toLowerCase().equals(argument.toLowerCase()))
					songExists = true;
			}
			if(songExists) {
				for(Song s :ul.searchPlaylist(name)) {
					if(s.name.toLowerCase().equals(argument)) {
						System.out.println("Error: Song already in playlist");
						return;
					}
				}
				ul.addSongToPlaylist(name, argument);
			}
			else
				System.out.println("Error: Song not in your library");
		}
		else
			System.out.println("Error: Playlist not created within your library");
	}
	
	/*
	 * void createPL(String argument) -- create a playlist in the user library with name <argument>.
	 * Prints an error message if a playlist of that name has already been created.
	 */
	public static void createPL(String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		for(String s : playlists) {	// Check if playlist of same name already exists
			if(s.toLowerCase().equals(argument.toLowerCase())) {
				System.out.println("Error: Playlist already exists in your library");
				return;
			}
		}
		ul.createPlayList(argument);
	}
	
	/*
	 * void favorite(String argument) -- favorites songs within the user library with title <argument>.
	 * Will print an error message if the song cannot be found within the user library.
	 */
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
	
	/*
	 * void list(String type) -- lists all items of <type> in the user library. Type should
	 * be either S for songs, AR for artists, AL for albums, P for playlists, or F for favorites.
	 * Will print an error message if the type is invalid.
	 */
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
			ArrayList<Song> favorites = ul.getFavorites();
			System.out.println("Favorited songs in your library:");
			for(Song s : favorites)
				System.out.println(s.name);
		}
		else
			System.out.println("Error: Invalid Type. Input S for songs, AL for albums, AR for artists, P for playlists, or F for favorites");
	}
	
	/*public static void login(String username, String password) {
		String correctPassword = logins.get(username);
		if(correctPassword == null) {
			System.out.println("Error: No account created under this username. Check your spelling or create an account.");
		}
		else {
			if(correctPassword.equals(password))
				loadAccount(username);
			else
				System.out.println("Error: Incorrect password. Check your spelling");
		}
	}*/
	
	/*
	 * void rate(String argument, int rating) -- update the rating for a song with title <argument> in the user library.
	 * Will print an error message if <rating> is not between 1 and 5. Additionally will print an error message if the
	 * song indicated is not in the user library.
	 */
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
	
	/*
	 * void removePL(String name, String argument) -- removes a song with title <argument> from a playlist named <name>.
	 * Will print an error message if the indicated song is not in the playlist, also if it is not in the user library.
	 * Additionally prints an error message if a playlist with that name hasn't been created.
	 */
	public static void removePL(String name, String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		boolean playlistExists = false;
		for(String s : playlists) {	// Check that a playlist named <name> exists
			if(s.toLowerCase().equals(name.toLowerCase()))
				playlistExists = true;
		}
		if(playlistExists) {
			boolean songExists = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {	// Check that the song exists within the user library
				if(s.toLowerCase().equals(argument.toLowerCase()))
					songExists = true;
			}
			if(songExists) {
				ArrayList<Song> playlistSongs = ul.searchPlaylist(name);
				boolean songInPlaylist = false;
				for(Song s : playlistSongs) {	// Check that the song is in the playlist
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
	
	/*
	 * void search(String location, String searchType, String argument) -- searches <location> with a <searchType> search for <argument>.
	 * Prints out whatever is found by the search. Will print an error message if location is neither "MS" for music store or "UL" for user library.
	 * Also prints out an error if searchType is not ST for song by title, SA for song by artist, AT for album by title, AA for album by artist,
	 * or P (only in user library) for playlist. If nothing is found by the search, prints an appropriate message.
	 */
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
				if(songs == null) {
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
	
	/*private static void startup() {
		File file = new File("logins.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.exit(1);
		}
		 String line;
		 try {
			while((line = br.readLine()) != null) {
				 String[] login = line.split(",");
				 logins.put(login[0], login[1]);
			 }
		} catch (IOException e) {
			System.exit(1);
		}
	}*/
}
