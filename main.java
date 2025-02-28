/*
 * simulates the program
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class main {
	
	private static final String helpMenu = 	"Help Menu\nCommands:\nadd,<type>,<argument>\n	-adds an item to your library\n	"
			+ "-<type> should contain either A for album or S for song\n	-<argument> should be replaced by the appropriate name for the item you are trying to add\n"
			+ "addPL,<name>,<argument>\n	-adds a song to a given playlist\n	-<name> should be replaced with the name of the playlist to be added to\n	"
			+ "-<argument> should be replaced with the name of the song to be added\ncreatePL,<argument>\n	-<argument> should be replaced with your desired name for the created playlist\n"
			+ "exit\n	-exits the program\n"
			+ "fav,<argument>\n	-<argument> should be replaced with the name of the song that you wish to favorite\nlist,<type>\n	"
			+ "-prints a list of the each given item of the given type in your library\n	-<type> should be replaced with S for songs, AR for artists, AL for albums, P for playlists, or F for favorited songs\n"
			+ "rate,<argument>,<rating>\n	-<argument> should be replaced with the name of the song you wish to rate\n	-<rating> should be replaced with your desired rating (1 - 5)\n"
			+ "removePL,<name>,<argument>\n	-removes a song from a playlist\n	-<name> should be replaced with the name of the playlist from which you wish to remove a song\n	"
			+ "-<argument> should be replaced with the name of the song you wish to remove\nsearch,<location>,<search type>,<argument>\n	"
			+ "-<location> should have either MS for searching the music store or UL for searching your library\n	"
			+ "-<search type> should have ST for searching for a song by its title, SA for a song by artist, AT for an album by its title,\n"
			+ "		AA for an album by its artist, or P (only applies for your library) for a playlist by name\n	-<argument> should be replaced by the proper argument for your search (song name, song artist, etc.)";
	
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
					add(input[1], input[2]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
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
			int size = ms.searchAlbumWithTitle(argument).size();
			if(size > 0)
				ul.addAlbum(argument);
			else
				System.out.println("Error: Album not in Music Store");
		}
		else if(type.toLowerCase().equals("S")) {
			int size = ms.searchSongWithTitle(argument).size();
			if(size > 0)
				ul.addSong(argument);
			else
				System.out.println("Error: Song not in Music Store");
		}
		else
			System.out.println("Error: Invalid Type. Please input S or s for type");
	}
	
	public static void addPL(String name, String argument) {
		ArrayList<String> playlists = ul.getPlaylists();
		boolean playlistExists = false;
		for(String s : playlists) {
			if(s.equals(name))
				playlistExists = true;
		}
		if(playlistExists) {
			boolean songExists = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {
				if(s.equals(argument))
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
			if(s.equals(argument)) {
				System.out.println("Error: Playlist already exists in your library");
				return;
			}
		}
		ul.createPlayList(argument);
	}
	
	public static void favorite(String argument) {
		ArrayList<String> songs = ul.getSongs();
		for(String s : songs) {
			if(s.equals(argument)) {
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
			if(s.equals(argument)) {
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
			if(s.equals(name))
				playlistExists = true;
		}
		if(playlistExists) {
			boolean songExists = false;
			ArrayList<String> songs = ul.getSongs();
			for(String s : songs) {
				if(s.equals(argument))
					songExists = true;
			}
			if(songExists) {
				ul.removeSongFromPlaylist(name, argument);
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
