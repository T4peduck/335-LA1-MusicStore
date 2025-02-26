/*
 * JUnit test for Song.java
 */

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

public class TestSong {
	Song s = new Song("s1", "Cambra", "MyAlbum");
	Song t = new Song("s2", "Cambra", "MyAlbum");
	
	@Test
	void testGetters() {
		assertFalse(s.getFavorite());
		
		s.setFavorite();
		assertTrue(s.getFavorite());
		
		assertEquals(s.getRating(), 0);
		s.setRating(3);
		assertEquals(s.getRating(), 3);
		
		t.setRating(5);
		assertTrue(t.getFavorite());
		
	}
	
	@Test
	void testToString() {
		assertTrue(s.toString().equals("s1 by Cambra on MyAlbum."));
	}
}
