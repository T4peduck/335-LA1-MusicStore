/*
 * Used to switch between different user libraries
 * and write different user libraries
 */
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;


public class UserController {
	private ArrayList<String> userList;
	private MusicStore ms;
	
	//create a list of users
	public UserController() {
		userList = new ArrayList<>();
		ms = new MusicStore();
		
		File users = new File("users/users.txt");
		
		
		try {
			 BufferedReader br = new BufferedReader(new FileReader(users));
			 
			 String line;
			 while((line = br.readLine()) != null) {
				 userList.add(line);
			 }
			 
			 br.close();
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
	 * ---- ----
	 */
	
	
	//given a username and password, retrieve user from text
	//@PRE username entered isn't "users"
	public LibraryModel loadUser(String username, String password) {
		File user = new File("users/" + username + ".txt");
		LibraryModel lm = new LibraryModel(ms);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(user));
			
			//TODO: add some hashing and salting stuff for password
			String line;
			line = br.readLine();
			if(!line.equals(password)) {
				br.close();
				return null;
			}
			
			parseAlbums(lm, br);
			
			parsePlaylists(lm, br);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return lm;
	}
	
	private void parseAlbums(LibraryModel lm, BufferedReader br) throws IOException {
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
				lm.addSong(songInfo[0], artist);
				songNames.add(songInfo[0]);
				//lm.addSong(new Song(songInfo[0], artist, albumName));
				lm.rateSong(songInfo[0], Integer.parseInt(songInfo[1]));
			}
			
			//lm.addAlbum(new Album(albumName, artist, genre, year, songNames));
			
			line = br.readLine();
		}
		return;
	}
		
	private void parsePlaylists(LibraryModel lm, BufferedReader br) throws IOException {
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
	//TODO: may end up changing the way librar, username in arg may be changed
	public void saveUser(LibraryModel lm, String username, String password) {
		try {
			if(!userList.contains(username)) {
				userList.add(username);
				FileWriter userEdit = new FileWriter(new File("users/users.txt"), true);
				userEdit.write(username + "\n");
				userEdit.close();
			}
			
			FileWriter saveFile = new FileWriter(new File("users/" + username + ".txt"));
			
			saveAlbums(lm, saveFile);
			
			savePlaylists(lm, saveFile);
			
			saveFile.close();
		} catch (IOException e) {
			System.exit(1);
		}
		
	}
	
	private void saveAlbums(LibraryModel lm, FileWriter saveFile) throws IOException {
		ArrayList<String> albumList = lm.getAlbums();
		for(String aName: albumList) {
			Album a = lm.searchAlbumWithTitle(aName).get(0);
			saveFile.write(a.name + " " + a.artist + " " + a.genre + " " + a.year + "\n");
			
			ArrayList<Song> songList = a.getAlbum();
			for(Song s: songList) {
				saveFile.write(s.name + " " + s.getRating() + "\n");
			}
			
			saveFile.write("\n");
		}
		
		saveFile.write("\n");
		return;
	}
	
	private void savePlaylists(LibraryModel lm, FileWriter saveFile) throws IOException {
		ArrayList<String> playlistList = lm.getPlaylists();
		for(String pName: playlistList) {
			System.out.println(pName);
			ArrayList<Song> p = lm.searchPlaylist(pName);
			saveFile.write(pName + "\n");
			
			for(Song s: p) {
				saveFile.write(s.name + " " + s.artist + "\n");
			}
			saveFile.write("\n");
		}
		
		saveFile.write("\n");
		return;
	}
	
	
}
