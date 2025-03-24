import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;



public class TestUserController {
	UserController uc = new UserController();
	MusicStore ms = new MusicStore();
	
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
	
	@Test
	void testSaveUser() {
		LibraryModel idiotsLibrary = new LibraryModel(ms);
		
		idiotsLibrary.addSong("daylight");
		idiotsLibrary.addSong("politik");
		idiotsLibrary.addSong("a whipser");
		
		idiotsLibrary.addSong("clear blue eyes (feat. lucinda williams)");
		idiotsLibrary.addSong("jesus");
		
		idiotsLibrary.createPlayList("p1");
		idiotsLibrary.addSongToPlaylist("p1", "politik");
		idiotsLibrary.addSongToPlaylist("p1", "jesus");
		idiotsLibrary.addSongToPlaylist("p1", "daylight");

		idiotsLibrary.createPlayList("p2");
		idiotsLibrary.addSongToPlaylist("p2", "politik");
		idiotsLibrary.addSongToPlaylist("p2", "a whisper");
		
		idiotsLibrary.rateSong("daylight", 4);
		idiotsLibrary.rateSong("jesus", 2);
		
		uc.saveUser(idiotsLibrary, "idiot", "thebirdsaredrones");
		
		LibraryModel i = uc.loadUser("idiot", "thebirdsaredrones");
		
		assertTrue(i.searchSongWithTitle("daylight") != null);
		assertTrue(i.searchAlbumWithTitle("a rush of blood to the head") != null);
		assertTrue(i.searchSongWithTitle("jesus") != null);
		
		assertTrue(i.getRating("daylight") == 4);
		assertTrue(i.getRating("jesus") == 2 );
		
		
	}
}
