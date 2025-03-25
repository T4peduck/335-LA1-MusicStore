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
		assertFalse(s.isFavorite());
		
		s.setFavorite();
		assertTrue(s.isFavorite());
		
		assertEquals(s.getRating(), 5);
		s.setRating(3);
		assertEquals(s.getRating(), 3);
		
		t.setRating(5);
		assertTrue(t.isFavorite());
		
		s.play();
		s.play();
		assertTrue(s.getPlays() == 2);
	}
	
	@Test
	void testForEscapingReference() {
		Song s3 = new Song(s);
		s3.setRating(3);
		s.setRating(1);
		assertFalse(s3.getRating() == s.getRating());
	}
	
	@Test
	void testToString() {
		assertTrue(s.toString().equals("s1 by Cambra on MyAlbum."));
	}
}
