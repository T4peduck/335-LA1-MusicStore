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
import java.util.InputMismatchException;


public class view {
	
	private static final String helpMenu = 	"Help Menu\nNote: Commands and arguments are not case sensitive\nCommands:\nadd\n	- adds an item to your library\n"
			+ "addPL\n	- adds a song in your library to a given playlist\ncreateAcc\n	- create an account to hold your library for future use\n"
			+ "createPL\n	- creates a playlist in your library\n"
			+ "exit\n	- exits the program\n"
			+ "favorite\n	- favorites a given song within your library\nlist\n	- prints a list of the each given item of the given type in your library\n"
			+ "login\n	- prompts for username and password to log into your account\nlogout\n	- logs out of currently logged in account\n"
			+ "play\n	- plays the song input\n"
			+ "rate\n	- rates a song\nremove\n	- removes an item from your library\n"
			+ "removePL\n	- removes a song from a playlist\n"
			+ "search,\n	- searches either the music store or user library for given item\n"
			+ "shuffle\n	- shuffles your user library\nshufflePL\n	- shuffles the given playlist\n"
			+ "sortedList\n	- lists all the songs in your library sorted by supplied method\n";
	
	private static MusicStore ms = new MusicStore();
	private static LibraryModel ul = new LibraryModel(ms);
	private static UserController uc = new UserController();
	private static String username, password;
	

	public static void main(String args[]) {
		System.out.println("Welcome to the Music Store\nType help for the list of commands");
		boolean exit = false;
		Scanner keyboard = new Scanner(System.in);
		boolean loggedIn = false;
		while(!exit) {
			String[] input = keyboard.nextLine().strip().split(",");
			String command = input[0].toLowerCase();
			if(command.equals("help")) {
				System.out.println(helpMenu);
			}
			else if(command.equals("exit")) {
				if(loggedIn)
					logout();
				keyboard.close();
				System.exit(0);
			}
			else if(command.equals("login")) {
				boolean successful = login();
				if(successful)
					loggedIn = true;
			}
			else if(!loggedIn) {
				System.out.println("Please login before using this command");
			}
			else if(command.equals("add")) {
				boolean done = false;
				while(!done) {
					System.out.print("Must the artist be specified for this item (Y for yes, N for No)? ");
					String additional = keyboard.nextLine();
					if(additional.toUpperCase().equals("Y")) {
						addWithArtist();
						done = true;
					}
					else if(additional.toUpperCase().equals("N")) {
						add();
						done = true;
					}
					else
						System.out.println("Error: Invalid Input");
				}
			}
			else if(command.equals("addpl")) {
				boolean done = false;
				while(!done) {
					System.out.print("Must the artist be specified for this item (Y for yes, N for No)? ");
					String additional = keyboard.nextLine();
					if(additional.toUpperCase().equals("Y")) {
						addPLWithArtist();
						done = true;
					}
					else if(additional.toUpperCase().equals("N")) {
						addPL();
						done = true;
					}
					else
						System.out.println("Error: Invalid Input");
				}	
			}
			else if(command.equals("createacc")) {
				createAccount();
			}
			else if(command.equals("createpl")) {
				createPL();		
			}
			else if(command.equals("favorite")) {
				favorite();
			}
			else if(command.equals("list")) {
				list();
			}
			else if(command.equals("logout")) {
				logout();
				loggedIn = false;
			}
			else if(command.equals("play")) {
				boolean done = false;
				while(!done) {
					System.out.print("Must the artist be specified for this item (Y for yes, N for No)? ");
					String additional = keyboard.nextLine();
					if(additional.toUpperCase().equals("Y")) {
						playWithArtist();
						done = true;
					}
					else if(additional.toUpperCase().equals("N")) {
						play();
						done = true;
					}
					else
						System.out.println("Error: Invalid Input");
				}
			}
			else if(command.equals("rate")) {
				boolean done = false;
				while(!done) {
					System.out.print("Must the artist be specified for this item (Y for yes, N for No)? ");
					String additional = keyboard.nextLine();
					if(additional.toUpperCase().equals("Y")) {
						rateWithArtist();
						done = true;
					}
					else if(additional.toUpperCase().equals("N")) {
						rate();
						done = true;
					}
					else
						System.out.println("Error: Invalid Input");
				}
			}
			else if(command.equals("remove")) {
				boolean done = false;
				while(!done) {
					System.out.print("Must the artist be specified for this item (Y for yes, N for No)? ");
					String additional = keyboard.nextLine();
					if(additional.toUpperCase().equals("Y")) {
						removeWithArtist();
						done = true;
					}
					else if(additional.toUpperCase().equals("N")) {
						remove();
						done = true;
					}
					else
						System.out.println("Error: Invalid Input");
				}
			}
			else if(command.equals("removepl")) {
				boolean done = false;
				while(!done) {
					System.out.print("Must the artist be specified for this item (Y for yes, N for No)? ");
					String additional = keyboard.nextLine();
					if(additional.toUpperCase().equals("Y")) {
						removePLWithArtist();
						done = true;
					}
					else if(additional.toUpperCase().equals("N")) {
						removePL();
						done = true;
					}
					else
						System.out.println("Error: Invalid Input");
				}
			}
			else if(command.equals("search")) {
				search();	
			}
			else if(command.equals("shuffle")) {
				shuffle();
			}
			else if(command.equals("shufflepl")) {
				shufflePL();
			}
			else if(command.equals("sortedlist")) {
				sortedList();
			}
			else
				System.out.println("Error: Invalid Command. Type help for a list of commands");
		}
	}
	
	/*
	 * void add() -- asks the type (S for song or A for album),
	 * adds the corresponding item whose name is given from the music store to the user library. 
	 * If the item is already in the library, will not add and prints an error message. If the type
	 * or argument are invalid, prints appropriate error messages.
	 */
	public static void add() {
		Scanner keyboard = new Scanner(System.in);
		String type = "";
		String argument = "";
		boolean done = false;
		while(!done) {
			System.out.print("Input Type (A for album, S for song): ");
			type = keyboard.nextLine();
			if(!type.toUpperCase().equals("A") && !type.toUpperCase().equals("S")) {
				System.out.println("Error: Invalid Type.");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song/album you want to add: ");
		argument = keyboard.nextLine();
		if(type.toUpperCase().equals("A")) {
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
	}
	
	/*
	 * void addWithArtist() -- same functionality as above add
	 * method, except with additional input for the artist of the song. Only meant to be used
	 * for songs or albums that for which there are multiple in the music store with the same name.
	 * Will print error messages if the item is already in the library or if it doesn't exist.
	 */
	public static void addWithArtist() {
		Scanner keyboard = new Scanner(System.in);
		String type = "";
		String argument;	// item name
		String argument2;	// artist
		boolean done = false;
		while(!done) {
			System.out.print("Input Type (A for album, S for song): ");
			type = keyboard.nextLine();
			if(!type.toUpperCase().equals("A") && !type.toUpperCase().equals("S")) {
				System.out.println("Error: Invalid Type.");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song/album you want to add: ");
		argument = keyboard.nextLine();
		System.out.print("Input the artist of the song/album you want to add: ");
		argument2 = keyboard.nextLine();
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
	 * void addPL() -- adds the song with supplied title to the playlist
	 * with supplied name. Will print error messages if the playlist doesn't exist or if the song
	 * is not in the user's library. Also prints an error message if the song is already in the playlist.
	 */
	public static void addPL() {
		Scanner keyboard = new Scanner(System.in);
		String name = "";
		String argument;	// item name
		boolean done = false;
		while(!done) {
			System.out.print("Which playlist would you like to add to? ");
			name = keyboard.nextLine();
			if(ul.searchPlaylist(name) == null) {
				System.out.println("Error: No playlist with that name created.");
				list("p");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song you want to add: ");
		argument = keyboard.nextLine();
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
	
	/*
	 * void addPLWithArtist() -- adds the song with supplied title and artist to the playlist
	 * with supplied name. Will print error messages if the playlist doesn't exist or if the song
	 * is not in the user's library. Also prints an error message if the song is already in the playlist.
	 */
	public static void addPLWithArtist() {
		Scanner keyboard = new Scanner(System.in);
		String name = "";
		String argument;	// item name
		String argument2;	// artist
		boolean done = false;
		while(!done) {
			System.out.print("Which playlist would you like to add to? ");
			name = keyboard.nextLine();
			if(ul.searchPlaylist(name) == null) {
				System.out.println("Error: No playlist with that name created.");
				list("p");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song you want to add: ");
		argument = keyboard.nextLine();
		System.out.print("Input the artist of the song you want to add: ");
		argument2 = keyboard.nextLine();
		boolean songExists = false;
		ArrayList<Song> songs = ul.searchSongWithTitle(argument);
		for(Song s : songs) {	// Make sure song is in music store
			if(s.artist.toLowerCase().equals(argument2.toLowerCase()))
				songExists = true;
		}
		if(songExists) {
			for(Song s :ul.searchPlaylist(name)) {
				if(s.name.toLowerCase().equals(argument.toLowerCase()) && s.artist.toLowerCase().equals(argument2.toLowerCase())) {
					System.out.println("Error: Song already in playlist");
					return;
				}
			}
			ul.addSongToPlaylist(name, argument, argument2);
		}
		else
			System.out.println("Error: Song with that name and artist not in your library");
	}
	
	/*
	 * void createAccount() -- creates a new user account with supplied username and password. Will
	 * print an error if username already exists, prompting the user to reenter a new username. Will
	 * also print an error if password does not fit given criteria. When a valid username and password
	 * have been created, creates a new account.
	 */
	public static void createAccount() {
		Scanner keyboard = new Scanner(System.in);
		boolean done = false;
		ArrayList<String> usernames = uc.getUsernames();
		System.out.print("Please enter a username: ");
		while(!done) {
			username = keyboard.nextLine();
			if(usernames.contains(username))
				System.out.println("Error: An account with this name already exists. Please enter a different username: ");
			 else
				 done = true;
		}
		done = false;
		System.out.print("Please enter a password (Note that passwords can only contain alphanumeric characters and must be between 5 and 17 characters): ");
		while(!done) {
			password = keyboard.nextLine();
			boolean validPassword = true;
			for(char c : password.toCharArray()) {
				if(!Character.isLetter(c) && !Character.isDigit(c)) {
					validPassword = false;
				}
			}
			if(password.toCharArray().length > 17 || password.toCharArray().length < 5) {
				validPassword = false;
			}
			if(!validPassword) {
				System.out.println("Error: Passwords can include only letters or digits, no spaces, and must be of appropriate length.\n Please enter a valid password:");
			}
		}
		ul = new LibraryModel(ms);
		System.out.println("Logged in as new user: " + username);
	}
	
	/*
	 * void createPL() -- create a playlist in the user library with name supplied.
	 * Prints an error message if a playlist of that name has already been created.
	 */
	public static void createPL() {
		Scanner keyboard = new Scanner(System.in);
		String argument;
		System.out.println("What would you like to name your playlist? ");
		argument = keyboard.nextLine();
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
	 * void favorite() -- favorites songs within the user library with title supplied.
	 * Will print an error message if the song cannot be found within the user library.
	 */
	public static void favorite() {
		Scanner keyboard = new Scanner(System.in);
		String argument;
		System.out.println("What song would you like to favorite? ");
		argument = keyboard.nextLine();
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
	 * be either S for songs, AR for artists, AL for albums, P for playlists, G for genres, or F for favorites.
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
		else if(type.toUpperCase().equals("G")) {
			ArrayList<String> genres = ul.getGenres();
			System.out.println("Genres present in you library:");
			for(String s : genres) {
				System.out.println(s);
			}
		}
		else if(type.toUpperCase().equals("F")) {
			ArrayList<Song> favorites = ul.getFavorites();
			System.out.println("Favorited songs in your library:");
			for(Song s : favorites)
				System.out.println(s.name);
		}
	}
	
	/*
	 * void list() -- lists all items of supplied type in the user library. Type should
	 * be either S for songs, AR for artists, AL for albums, P for playlists, G for genres, or F for favorites.
	 * Will print an error message if the type is invalid.
	 */
	public static void list() {
		Scanner keyboard = new Scanner(System.in);
		String type = "";
		boolean done = false;
		while(!done) {
			System.out.print("Input list type: S for songs, AR for artists, AL for albums, P for playlists, G for genres, or F for favorites: ");
			type = keyboard.nextLine().toUpperCase();
			if(!type.equals("S") && !type.equals("AR") && !type.equals("AL") && !type.equals("P") && !type.equals("G") && !type.equals("F")) {
				System.out.println("Error: Invalid Type.");
			}
			else
				done = true;
		}
		if(type.equals("S")) {
			ArrayList<String> songs = ul.getSongs();
			System.out.println("Songs in your library:");
			for(String s : songs)
				System.out.println(s);
		}
		else if(type.equals("AR")) {
			ArrayList<String> artists = ul.getArtists();
			System.out.println("Artists in your library:");
			for(String s : artists)
				System.out.println(s);
		}
		else if(type.equals("AL")) {
			ArrayList<String> albums = ul.getAlbums();
			System.out.println("Albums in your library:");
			for(String s : albums)
				System.out.println(s);
		}
		else if(type.equals("P")) {
			ArrayList<String> playlists = ul.getPlaylists();
			System.out.println("Playlists in your library:");
			for(String s : playlists)
				System.out.println(s);
		}
		else if(type.equals("G")) {
			ArrayList<String> genres = ul.getGenres();
			System.out.println("Genres present in you library:");
			for(String s : genres) {
				System.out.println(s);
			}
		}
		else if(type.equals("F")) {
			ArrayList<Song> favorites = ul.getFavorites();
			System.out.println("Favorited songs in your library:");
			for(Song s : favorites)
				System.out.println(s.name);
		}
	}
	
	/*
	 * boolean login() -- logs in a user with supplied username and password. If either are incorrect, prints and error
	 * message and returns false. If a user is successfully logged in, returns true.
	 */
	public static boolean login() {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please enter your username: ");
		username = keyboard.nextLine();
		System.out.print("Please enter your password: ");
		password = keyboard.nextLine();
		ArrayList<String> usernames = uc.getUsernames();
		if(!usernames.contains(username)) {
			System.out.println("Error: Invalid login. If you haven't created an account yet, type createAcc. Otherwise, check your spelling.");
			return false;
		}
		ul = loadUser(username, password);
		if(ul == null) {
			System.out.println("Error: Invalid login. If you haven't created an account yet, type createAcc. Otherwise, check your spelling.");
			return false;
		}
		System.out.println("Successfully logged in as: " + username);
		return true;
	}
	
	/*
	 * void logout() -- logs out the logged in user, calling the user controller to save their data.
	 * Prints a message indicating that the user has successfully logged out.
	 */
	public static void logout() {
		uc.saveUser(ul, username, password);
		System.out.println("Successfully logged out");
	}
	
	/*
	 * void play() -- plays the song with supplied name within the library. Prints an error message if the song
	 * is not in the user library.
	 */
	public static void play() {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("What song would you like to play? ");
		String name = keyboard.nextLine();
		if(ul.searchSongWithTitle(name).size() == 0) {
			System.out.println("Error: Song not in your library");
		}
		else
			ul.playSong(name);
	}
	
	/*
	 * void playWithArtist() -- plays the song with supplied name and artist within the library. 
	 * Prints an error message if the song is not in the user library.
	 */
	public static void playWithArtist() {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("What song would you like to play? ");
		String name = keyboard.nextLine();
		System.out.println("Whose song is it? ");
		String artist = keyboard.nextLine();
		if(ul.searchSongWithTitle(name).size() == 0) {
			System.out.println("Error: Song not in your library");
			return;
		}
		boolean songExists = false;
		for(Song s : ul.searchSongWithTitle(name)) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase()))
				songExists = true;
		}
		if(!songExists) {
			System.out.println("Error: No song with that title and artists in your library");
			return;
		}
		ul.playSong(name, artist);
	}
	
	/*
	 * void rate() -- update the rating for a song with title supplied in the user library.
	 * Will print an error message if supplied rating is not between 1 and 5. Additionally will print an error message if the
	 * song indicated is not in the user library.
	 */
	public static void rate() {
		Scanner keyboard = new Scanner(System.in);
		String argument;
		int rating = 0;
		boolean done = false;
		System.out.print("What song do you wish to rate? ");
		argument = keyboard.nextLine();
		while(!done) {
			System.out.print("How would you like to rate this song (1 - 5)? ");
			try {
				rating = keyboard.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Error: Please input a valid integer between 1 and 5");
				continue;
			}
			if(rating < 1 || rating > 5) {
				System.out.println("Error: Invalid Rating. Ratings should be between 1 and 5");
			}
			else
				done = true;
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
	 * void rateWithArtist() -- update the rating for a song with supplied title and artist in the user library.
	 * Will print an error message if supplied rating is not between 1 and 5. Additionally will print an error message if the
	 * song indicated is not in the user library.
	 */
	public static void rateWithArtist() {
		Scanner keyboard = new Scanner(System.in);
		String argument;
		int rating = 0;
		boolean done = false;
		System.out.print("What song do you wish to rate? ");
		argument = keyboard.nextLine();
		System.out.println("Whose song is it? ");
		String artist = keyboard.nextLine();
		while(!done) {
			System.out.print("How would you like to rate this song (1 - 5)? ");
			try {
				rating = keyboard.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Error: Please input a valid integer between 1 and 5");
				continue;
			}
			if(rating < 1 || rating > 5) {
				System.out.println("Error: Invalid Rating. Ratings should be between 1 and 5");
			}
			else
				done = true;
		}
		ArrayList<Song> songs = ul.searchSongWithTitle(argument);
		if(songs.size() == 0) {
			System.out.println("Error: Song not in your library");
		}
		for(Song s : songs) {
			if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
				ul.rateSong(argument, artist, rating);
				return;
			}
		}
		System.out.println("Error: Song with that name and artist not in your library");
	}
	
	/*
	 * void remove() -- removes a song or album with given name from the user library. Prints and error message
	 * if the item is not in the library.
	 */
	public static void remove() {
		Scanner keyboard = new Scanner(System.in);
		String type = "";
		String name = "";
		boolean done = false;
		while(!done) {
			System.out.print("Input Type (A for album, S for song): ");
			type = keyboard.nextLine();
			if(!type.toUpperCase().equals("A") && !type.toUpperCase().equals("S")) {
				System.out.println("Error: Invalid Type.");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song/album you want to remove: ");
		name = keyboard.nextLine();
		if(type.toUpperCase().equals("A")) {
			ArrayList<Album> albums = ul.searchAlbumWithTitle(name);
			if(albums.size() != 0) {
				ul.removeAlbum(name);
			}
			else
				System.out.println("Error: Album with given name not in library");
		}
		else if(type.toUpperCase().equals("S")) {
			ArrayList<Song> songs = ul.searchSongWithTitle(name);
			if(songs.size() != 0) {
				ul.removeSong(name);
			}
			else
				System.out.println("Error: Song with given name not in library");
		}
	}
	
	/*
	 * void removeWithArtist() -- removes a song or album with given name and artist from the user library. Prints and error message
	 * if the item is not in the library.
	 */
	public static void removeWithArtist() {
		Scanner keyboard = new Scanner(System.in);
		String type = "";
		String name, artist;
		boolean done = false;
		while(!done) {
			System.out.print("Input Type (A for album, S for song): ");
			type = keyboard.nextLine();
			if(!type.toUpperCase().equals("A") && !type.toUpperCase().equals("S")) {
				System.out.println("Error: Invalid Type.");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song/album you want to remove: ");
		name = keyboard.nextLine();
		System.out.print("Input the artist of the song/album you want to remove: ");
		artist = keyboard.nextLine();
		if(type.toUpperCase().equals("S")) {
			ArrayList<Song> songs = ul.searchSongWithTitle(name);
			if(songs.size() != 0) {
				boolean removed = false;
				for(Song s : songs) {
					if(s.artist.toLowerCase().equals(artist.toLowerCase())) {
						ul.removeSong(name, artist);
						removed = true;
					}
				}
				if(!removed) {
					System.out.println("Error: Song with given name and artist not in library");
					return;
				}
			}
			System.out.println("Error: Song with given name not in library");
		}
	}
	
	/*
	 * void removePL() -- removes a song with title supplied from a playlist whose name is supplied.
	 * Will print an error message if the indicated song is not in the playlist, also if it is not in the user library.
	 * Additionally prints an error message if a playlist with that name hasn't been created.
	 */
	public static void removePL() {
		Scanner keyboard = new Scanner(System.in);
		String name = "";
		String argument;	// item name
		boolean done = false;
		while(!done) {
			System.out.print("Which playlist would you like to remove from? ");
			name = keyboard.nextLine();
			if(ul.searchPlaylist(name) == null) {
				System.out.println("Error: No playlist with that name created.");
				list("p");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song you want to remove: ");
		argument = keyboard.nextLine();
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
	}
	
	/*
	 * void removePLWithArtist() -- removes a song with supplied title and artist from a playlist whose name is supplied.
	 * Will print an error message if the indicated song is not in the playlist, also if it is not in the user library.
	 * Additionally prints an error message if a playlist with that name hasn't been created.
	 */
	public static void removePLWithArtist() {
		Scanner keyboard = new Scanner(System.in);
		String name = "";
		String argument;	// item name
		String argument2;	// artist
		boolean done = false;
		while(!done) {
			System.out.print("Which playlist would you like to remove from? ");
			name = keyboard.nextLine();
			if(ul.searchPlaylist(name) == null) {
				System.out.println("Error: No playlist with that name created.");
				list("p");
			}
			else
				done = true;
		}
		System.out.print("Input the name of the song you want to remove: ");
		argument = keyboard.nextLine();
		System.out.print("Input the artist of the song you want to remove: ");
		argument2 = keyboard.nextLine();
		boolean songExists = false;
		ArrayList<Song> songs = ul.searchSongWithTitle(argument);
		for(Song s : songs) {	// Make sure song is in music store
			if(s.artist.toLowerCase().equals(argument2.toLowerCase()))
				songExists = true;
		}
		boolean songInPlaylist = false;
		if(songExists) {
			for(Song s :ul.searchPlaylist(name)) {
				if(s.name.toLowerCase().equals(argument.toLowerCase()) && s.artist.toLowerCase().equals(argument2.toLowerCase())) {
					songInPlaylist = true;
				}
			}
			if(!songInPlaylist) {
				System.out.println("Error: Song with that name and artist not in this playlist");
				return;
			}
			ul.addSongToPlaylist(name, argument, argument2);
		}
		else
			System.out.println("Error: Song with that name and artist not in your library");
	}
	
	/*
	 * void search(String location, String searchType, String argument) -- searches <location> with a <searchType> search for <argument>.
	 * Prints out whatever is found by the search. Will print an error message if location is neither "MS" for music store or "UL" for user library.
	 * Also prints out an error if searchType is not ST for song by title, SA for song by artist, AT for album by title, AA for album by artist,
	 * or P (only in user library) for playlist. If nothing is found by the search, prints an appropriate message.
	 */
	public static void search() {
		Scanner keyboard = new Scanner(System.in);
		String location = "";
		String searchType = "";
		String argument;
		boolean done = false;
		while(!done) {
			System.out.print("Input the location to be searched (MS for music store, UL for user library): ");
			location = keyboard.nextLine().toUpperCase();
			if(!location.equals("MS") && !location.equals("UL")) {
				System.out.println("Error: Invalid location.");
			}
			else
				done = true;
		}
		done = false;
		while(!done) {
			System.out.print("Input the search type (ST for song by title, SA for song by artist, SG for song by genre (only in UL), AT for album by title, AA for album by artist, or P for playlist (only in UL):" );
			searchType = keyboard.nextLine().toUpperCase();
			if(!searchType.equals("ST") && !searchType.equals("SA") && !searchType.equals("SG") && !searchType.equals("AT") && !searchType.equals("AA") &&!searchType.equals("P")) {
				System.out.println("Error: Invalid Search Type. Refer to above message for valid search types");
			}
			else if(searchType.equals("SG") && location.equals("MS")) {
				System.out.println("Error: Songs can only be searched by genre in the user library");
			}
			else if(searchType.equals("P") && location.equals("MS")) {
				System.out.println("Error: Playlists can only be searched for in the user library");
			}
			else
				done = true;
		}
		System.out.print("What is the name of the item you would like to search for? ");
		argument = keyboard.nextLine();
		if(location.toUpperCase().equals("MS")) {
			if(searchType.equals("ST")) {
				ArrayList<Song> songs = ms.searchSongWithTitle(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.equals("SA")) {
				ArrayList<Song> songs = ms.searchSongWithArtist(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.equals("AT")) {
				ArrayList<Album> albums = ms.searchAlbumWithTitle(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else if(searchType.equals("AA")) {
				ArrayList<Album> albums = ms.searchAlbumWithArtist(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
		}
		else if(location.equals("UL")) {
			if(searchType.toUpperCase().equals("ST")) {
				ArrayList<Song> songs = ul.searchSongWithTitle(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.equals("SA")) {
				ArrayList<Song> songs = ul.searchSongWithArtist(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.equals("SG")) {
				ArrayList<Song> songs = ul.searchSongsWithGenre(argument);
				if(songs.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Song s : songs) {
					System.out.println(s);
				}
			}
			else if(searchType.equals("AT")) {
				ArrayList<Album> albums = ul.searchAlbumWithTitle(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else if(searchType.equals("AA")) {
				ArrayList<Album> albums = ul.searchAlbumWithArtist(argument);
				if(albums.size() == 0) {
					System.out.println("Search returned no results");
				}
				for(Album a : albums) {
					System.out.println(a);
				}
			}
			else if(searchType.equals("P")) {
				ArrayList<Song> songs = ul.searchPlaylist(argument);
				if(songs == null) {
					System.out.println("Search returned no results");
				}
				else {
					for(Song s : songs) {
						System.out.println(s);
					}
				}
			}
		}
	}
	
	/*
	 * void shuffle() -- shuffles the user library
	 */
	public static void shuffle() {
		ul.shuffle();
	}
	
	/*
	 * void shffulePL() -- shuffles a playlist with given name in the user library. Prints an error
	 * if the playlist hasn't been created or if it is one of the automatic playlists that can't be shuffled
	 */
	public static void shufflePL() {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Which playlist would you like to shuffle? ");
		String playlistName = keyboard.nextLine();
		if(playlistName.toLowerCase().equals("Frequently Played") || playlistName.toLowerCase().equals("Recently Played")) {
			System.out.println("Error: This playlist cannot be shuffled");
			return;
		}
		if(ul.searchPlaylist(playlistName) == null) {
			System.out.println("Error: Playlist not created in your library");
			return;
		}
		ul.shufflePlaylist(playlistName);
	}
	
	/*
	 * void sortedList() -- prints a list of all songs in the user library sorted by the method supplied.
	 */
	public static void sortedList() {
		Scanner keyboard = new Scanner(System.in);
		String type = "";
		boolean done = false;
		while(!done) {
			System.out.print("Please input the method for sorting (T for by title, A for by artist, or R for by rating): ");
			type = keyboard.nextLine().toUpperCase();
			if(!type.equals("T") && !type.equals("A") && !type.equals("R")) {
				System.out.println("Error: Invalid Type.");
			}
			else
				done = true;
		}
		if(type.equals("T")) {
			for(String s : ul.getSongsSortedByTitle()) {
				System.out.println(s);
			}
		}
		else if(type.equals("A")) {
			for(String s : ul.getSongsSortedByArtist()) {
				System.out.println(s);
			}
		}
		else if(type.equals("R")) {
			for(String s : ul.getSongsSortedByRating()) {
				System.out.println(s);
			}
		}
	}
