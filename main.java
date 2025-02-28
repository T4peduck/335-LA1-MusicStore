/*
 * simulates the program
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class main {
	
	private static final String helpMenu = 	"Help Menu\nCommands:\nadd <type> <argument>\n	-adds an item to your library\n	"
			+ "-<type> should contain either A for album or S for song\n	-<argument> should be replaced by the appropriate name for the item you are trying to add\n"
			+ "addPL <name> <argument>\n	-adds a song to a given playlist\n	-<name> should be replaced with the name of the playlist to be added to\n	"
			+ "-<argument> should be replaced with the name of the song to be added\ncreatePL <argument>\n	-<argument> should be replaced with your desired name for the created playlist\n"
			+ "exit\n	-exits the program\n"
			+ "fav <argument>\n	-<argument> should be replaced with the name of the song that you wish to favorite\nlist <type>\n	"
			+ "-prints a list of the each given item of the given type in your library\n	-<type> should be replaced with S for songs, AR for artists, AL for albums, P for playlists, or F for favorited songs\n"
			+ "rate <argument> <rating>\n	-<argument> should be replaced with the name of the song you wish to rate\n	-<rating> should be replaced with your desired rating (1 - 5)\n"
			+ "removePL <name> <argument>\n	-removes a song from a playlist\n	-<name> should be replaced with the name of the playlist from which you wish to remove a song\n	"
			+ "-<argument> should be replaced with the name of the song you wish to remove\nsearch <location> <search type> <argument>\n	"
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
			String[] input = keyboard.nextLine().strip().split(" ");
			String command = input[0];
			if(command.equals("help")) {
				System.out.println(helpMenu);
			}
			else if(command.equals("add")) {
				try{
					add(input[1], input[2]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}
			}
			else if(command.equals("addPL")) {
				try{
					addPL(input[1], input[2]);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Error: Invalid Input");
				}			}
		}
	}
	
	public static void add(String type, String argument);
	public static void addPL(String name, String argument);
	public static void createPL(String argument);
	public static void favorite(String argument);
	public static void list(String type);
	public static void rate(String argument, int rating);
	public static void removePL(String name, String argument);
	public static void search(String location, String searchType, String argument);
}
