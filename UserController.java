/*
 * Used to switch between different user libraries
 * and write different user libraries
 */
import java.util.ArrayList;
import java.util.HexFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	
	//Uses a mix of hashing and salting to encrypt the password
	public String hashPassword(String password) {
		String input = password + (password.hashCode()/password.length());
		byte[] hashBytes;
		String hash = "";
		try {
			hashBytes = MessageDigest.getInstance("SHA-256")
					.digest(input.getBytes(StandardCharsets.UTF_8));
			hash = HexFormat.of().formatHex(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	
		return hash;
	}
	
	public ArrayList<String> getUsernames() {
		return userList;
	}
	
	
	/*
	 * FILE LAYOUT
	 * ----------
	 * password
	 * song1Name artist rating numPlays
	 * song2Name artist rating numPlays
	 * ...
	 * 			[two lines of whitespace]
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
	 * 
	 * ---- ----
	 */
	
	
	//given a username and password, retrieve user from text
	//@PRE username entered isn't "users"
	public LibraryModel loadUser(String username, String password) {
		if(!userList.contains(username)) {
			return null;
		}
		
		File user = new File("users/" + username + ".txt");
		LibraryModel lm = new LibraryModel(ms);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(user));
			
			//TODO: add some hashing and salting stuff for password
			String line;
			line = br.readLine();
			if(!line.equals(hashPassword(password))) {
				br.close();
				return null;
			}
			
			parseSongs(lm, br);
			
			parsePlaylists(lm, br);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return lm;
	}
	

	private void parseSongs(LibraryModel lm, BufferedReader br) throws IOException {
		String line;
		while(!(line = br.readLine()).equals("")) {
			String[] infoLine = line.split(",");
			String songName = infoLine[0];
			String artist = infoLine[1];
			int rating = Integer.parseInt(infoLine[2]);
			int numPlays = Integer.parseInt(infoLine[3]);
			lm.addSong(songName, artist);
			lm.rateSong(songName, artist, rating);
			lm.setPlays(songName, artist, numPlays);
		}
		return;
	}
		
	private void parsePlaylists(LibraryModel lm, BufferedReader br) throws IOException {
		String line = br.readLine();
		while(line != null && !line.equals("")) {
			String playlistName = line;
			lm.createPlayList(line);
			while(!(line = br.readLine()).equals("")) {
				String[] infoLine = line.split(",");
				String songName = infoLine[0];
				String artist	= infoLine[1];
				lm.addSongToPlaylist(playlistName, songName, artist);
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
			
			saveFile.write(hashPassword(password) + '\n');
			
			saveSongs(lm, saveFile);
			
			savePlaylists(lm, saveFile);
			
			saveFile.close();
		} catch (IOException e) {
			System.exit(1);
		}
		
	}
	
	private void saveSongs(LibraryModel lm, FileWriter saveFile) throws IOException {
		ArrayList<String> songNames = lm.getSongs();
		for(String sName: songNames) {
			ArrayList<Song> songList = lm.searchSongWithTitle(sName);
			for(Song s: songList) {
				saveFile.write(s.name + "," + s.artist + "," + s.getRating() + "," + s.getPlays() + "\n");
			}
		}
		
		saveFile.write("\n");
		return;
	}
	
	private void savePlaylists(LibraryModel lm, FileWriter saveFile) throws IOException {
		ArrayList<String> playlistList = lm.getPlaylists();
		for(String pName: playlistList) {
			if(pName.equals("Frequently Played")) {
				continue;
			}
		
			ArrayList<Song> p = lm.searchPlaylist(pName);
			saveFile.write(pName + "\n");
			
			for(Song s: p) {
				saveFile.write(s.name + "," + s.artist + "\n");
			}
			saveFile.write("\n");
		}
		
		saveFile.write("\n");
		return;
	}
	
	
}
