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
		//System.out.println("Albums: " + a.toString());
		
		ArrayList<String> s = dummysLibrary.getSongs();
		//System.out.println("Songs: " + s.toString());
		
		ArrayList<String> p = dummysLibrary.getPlaylists();
		//System.out.println("Playlist: " + p.toString());
	}
	
	
	@Test
	void testSaveUser() {
		LibraryModel idiotsLibrary = new LibraryModel(ms);
		
		idiotsLibrary.addSong("daylight");
		idiotsLibrary.addSong("politik");
		idiotsLibrary.addSong("a whisper");
		
		idiotsLibrary.addSong("clear blue eyes (feat. lucinda williams)");
		idiotsLibrary.addSong("jesus");
		
		idiotsLibrary.createPlayList("p1");
		idiotsLibrary.addSongToPlaylist("p1", "politik");
		idiotsLibrary.addSongToPlaylist("p1", "jesus");
		idiotsLibrary.addSongToPlaylist("p1", "daylight");

		idiotsLibrary.createPlayList("p2");
		idiotsLibrary.addSongToPlaylist("p2", "politik");
		idiotsLibrary.addSongToPlaylist("p2", "a whisper");
		
		idiotsLibrary.rateSong("daylight", "Coldplay",  4);
		idiotsLibrary.rateSong("jesus", "Amos Lee", 2);
		idiotsLibrary.rateSong("clear blue eyes (feat. lucinda williams)", "Amos Lee", 3);
		idiotsLibrary.rateSong("a whisper", "coldplay", 5);
		
		idiotsLibrary.setPlays("jesus", "amos Lee", 1000);
		idiotsLibrary.setPlays("Politik", "Coldplay", 20);
		System.out.println(idiotsLibrary.getPlays("politik", "coldplay"));
		
		
		uc.saveUser(idiotsLibrary, "idiot", "thebirdsaredrones");
		
		LibraryModel i = uc.loadUser("idiot", "thebirdsaredrones");
		
		assertTrue(i.searchSongWithTitle("daylight") != null);
		assertTrue(i.searchAlbumWithTitle("a rush of blood to the head") != null);
		assertTrue(i.searchSongWithTitle("jesus") != null);
		
		assertEquals(i.getRating("jesus", "Amos Lee"), 2);
		assertEquals(i.getRating("a whisper", "coldplay"), 5); 
		
		System.out.println("DAY RATING: " + i.getRating("daylight", "Coldplay"));
		assertEquals(i.getRating("daylight", "Coldplay"), 4);
		assertFalse(i.getRating("jesus", "Coldplay") == -1);
	
		assertEquals(i.getPlays("Politik", "coldplay"), 20);
	}
}
