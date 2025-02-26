import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TestAlbum {	
	private ArrayList<String> songNames = new ArrayList<>(Arrays.asList("s1", "s2", "s3"));
	
	private Album a = new Album("myAlbum", "Cambra", "Romance", "2005", songNames);

	@Test
	void testGetSong() {
		Song first = a.getSong("s1");
		assertEquals("s1", first.name);
		
		Song second = a.getSong("s2");
		assertEquals("s2", second.name);
	}

}
