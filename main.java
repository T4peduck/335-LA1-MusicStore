/*
 * simulates the program
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;


public class main {
	
	private static final String helpMenu = 	"Help Menu\nCommands:\nadd <type> <argument>\n	-adds an item to your library\n	"
			+ "-<type> should contain either A for album or S for song\n	-<argument> should be replaced by the appropriate name for the item you are trying to add\n"
			+ "addPL <name> <argument>\n	-adds a song to a given playlist\n	-<name> should be replaced with the name of the playlist to be added to\n	"
			+ "-<argument> should be replaced with the name of the song to be added\ncreatePL <argument>\n	-<argument> should be replaced with your desired name for the created playlist\n"
			+ "exit\n	exits the program\n"
			+ "fav <argument>\n	-<argument> should be replaced with the name of the song that you wish to favorite\nlist <type>\n	"
			+ "-prints a list of the each given item of the given type in your library\n	-<type> should be replaced with S for songs, AR for artists, AL for albums, P for playlists, or F for favorited songs\n"
			+ "rate <argument> <rating>\n	-<argument> should be replaced with the name of the song you wish to rate\n	-<rating> should be replaced with your desired rating (1 - 5)\n"
			+ "removePL <name> <argument>\n	-removes a song from a playlist\n	-<name> should be replaced with the name of the playlist from which you wish to remove a song\n	"
			+ "-<argument> should be replaced with the name of the song you wish to remove\nsearch <location> <search type> <argument>\n	"
			+ "-<location> should have either MS for searching the music store or UL for searching your library\n	"
			+ "-<search type> should have ST for searching for a song by its title, SA for a song by artist, AT for an album by its title,\n"
			+ "		AA for an album by its artist, or P (only applies for your library) for a playlist by name\n	-<argument> should be replaced by the proper argument for your search (song name, song artist, etc.)";

	public static void main(String args[]) {
		/*
		File dir = new File("albums");
		System.out.println(Arrays.toString(dir.listFiles()));
		
		BufferedReader br;
		
		
		for(File file: dir.listFiles()) {
			String line;
			br = new BufferedReader(new FileReader(file));
			
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println();
		}
		
		*/
		
		//MusicStore ms = new MusicStore();
		//System.out.println(ms);
		
		System.out.print(helpMenu);
		
	}
}
