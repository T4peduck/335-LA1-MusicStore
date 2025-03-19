import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;



public class TestUserController {
	UserController uc = new UserController();
	
	@Test
	void testLoadUser() {
		LibraryModel dummysLibrary = uc.loadUser("dummy", "PASSWORD");
		
		ArrayList<String> a = dummysLibrary.getAlbums();
		System.out.println("Albums: " + a.toString());
		
		ArrayList<String> s = dummysLibrary.getSongs();
		System.out.println("Songs: " + s.toString());
		assertTrue(s.get(0).equals("s1"));
		assertTrue((s.get(7)).equals("s1"));
		
		ArrayList<String> p = dummysLibrary.getPlaylists();
		System.out.println("Playlist: " + p.toString());
		assertTrue(p.get(0).equals("pl1"));
		}
}
