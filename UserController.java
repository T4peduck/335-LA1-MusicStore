/*
 * Used to switch between different user libraries
 * and write different user libraries
 */
import java.util.Hashtable;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;


public class UserController {
	private ArrayList<String> userList;
	private BufferedReader br;
	private MusicStore ms;
	
	//create a list of users
	public UserController() {
		userList = new ArrayList<>();
		ms = new MusicStore();
		
		File users = new File("users/users.txt");
		
		
		try {
			 br = new BufferedReader(new FileReader(users));
			 
			 String line;
			 while((line = br.readLine()) != null) {
				 userList.add(line);
			 }
		 } catch (IOException e) {
			 System.exit(1);
		 }
	}
	
	
	/*
	 * FILE LAYOUT
	 * ----------
	 * password
	 * albumName artist genre year
	 * song1Name rating
	 * song2Name rating
	 * ...
	 * 			[two space]
	 * 
	 * playlist1
	 * song1 artist
	 * song2 artist
	 * 
	 * playlist2
	 * song1 artist
	 * song2 artist
	 * 			[2 extra line of whitespace]
	 * 
	 * --------
	 */
	
	
	//given a username and password, retrieve user from text
	//@PRE username entered isn't "users"
	public LibraryModel loadUser(String username, String password) {
		File user = new File("users/" + username + ".txt");
		LibraryModel lm = new LibraryModel(ms);
		
		try {
			br = new BufferedReader(new FileReader(user));
			
			//TODO: add some hashing and salting stuff for password
			String line;
			line = br.readLine();
			if(!line.equals(password)) {
				return null;
			}
			
			parseAlbums(lm);
			
			parsePlaylists(lm);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return lm;
	}
	
	private void parseAlbums(LibraryModel lm) throws IOException {
		String line = br.readLine();
		while(!line.equals("")) {
			String[] infoLine = line.split(" ");
			String albumName = infoLine[0];
			String artist = infoLine[1];
			String genre = infoLine[2];
			String year = infoLine[3];
			ArrayList<String> songNames = new ArrayList<>();
			
			while(!(line = br.readLine()).equals("")) {
				String[] songInfo = line.split(" ");
				songNames.add(songInfo[0]);
				lm.addSong(new Song(songInfo[0], artist, albumName));
				lm.rateSong(songInfo[0], Integer.parseInt(songInfo[1]));
			}
			
			lm.addAlbum(new Album(albumName, artist, genre, year, songNames));
			
			line = br.readLine();
		}
		return;
	}
	
	/*
	private void parseSongs(LibraryModel lm) throws IOException {
		String line;
		
		while(!(line = br.readLine()).equals("")) {
			String[] infoLine = line.split(" ");
			String songName = infoLine[0];
			String artist = infoLine[1];
			String album = infoLine[2];
			int rating = Integer.parseInt(infoLine[3]);
			
			lm.addSong(new Song(songName, artist, album));
			lm.rateSong(songName, rating);
		}
	}
	*/
		
	private void parsePlaylists(LibraryModel lm) throws IOException {
		String line = br.readLine();
		while(line != null && !line.equals("")) {
			String playlistName = line;
			lm.createPlayList(line);
			while(!(line = br.readLine()).equals("")) {
				String[] infoLine = line.split(" ");
				String songName = infoLine[0];
				String artist	= infoLine[1];
				lm.addSongToPlaylist(playlistName, songName);
			}
			line = br.readLine();
			
		}
		
		return;
	}
	//given a username, save a user into text
	
	/*
	 * 1) construct the initial hashtable of all users
	 * 2) let users request info with a user and password
	 */
	
}
