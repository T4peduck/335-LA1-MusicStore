/*
 * Used to switch between different user libraries
 * and write different user libraries
 */
import java.util.Hashtable;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;


public class UserController {
	private Hashtable <String, LibraryModel> userList;
	private BufferedReader br;
	private MusicStore ms;
	
	//create a list of users
	public UserController(MusicStore ms) {
		userList = new Hashtable<String, LibraryModel>();
		this.ms = ms;
		
		File users = new File("users/users.txt");
		
		
		try {
			 
			 BufferedReader br = new BufferedReader(new FileReader(users));
			 
			 String line;
			 while((line = br.readLine()) != null) {
				 String textFile = "users/" + line + ".txt";
				 userList.put(line, parseUsers(new File(textFile)));
			 }
		 } catch (IOException e) {
			 System.exit(1);
		 }
	}
	
	//TODO: PROBS DONT NEED THE MUSIC LIBRARY
	private LibraryModel parseUsers(File textFile) throws IOException{
		LibraryModel lm = new LibraryModel(ms);
		
		BufferedReader br = new BufferedReader(new FileReader(textFile));
		
		
		return null;
	}
	
	
	
	//given a username, retrieve user from text
	
	
	
	//given a username, save a user into text
	
	/*
	 * 1) construct the initial hashtable of all users
	 * 2) let users request info with a user and password
	 */
	
}
