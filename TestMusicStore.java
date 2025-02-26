/*
 * JUnit test for MusicStore.java
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TestMusicStore {
	private MusicStore ms = new MusicStore();
	
	@Test
	void testSearchAlbumByArtist() {
		Album a = ms.searchAlbumWithArtist("The Heavy").get(0);
		
		assertTrue(a.artist.equals("The Heavy"));
		assertTrue(a.name.equals("Sons"));
		
		Album b1 = ms.searchAlbumWithArtist("Adele").get(0);
		Album b2 = ms.searchAlbumWithArtist("Adele").get(1);
		
		assertTrue(b1.name.equals("19"));
		assertTrue(b2.name.equals("21"));
	}
	
	@Test
	void testSearchAlbumByName() {
		Album a = ms.searchAlbumWithTitle("Sigh No More").get(0);
		assertTrue(a.artist.equals("Mumford & Sons"));
		
		Album b = ms.searchAlbumWithTitle("Cuando Los Angeles Lloran").get(0);
		assertTrue(b.artist.equals("Mana"));
	}
	
	@Test
	void testSearchSongByArtist() {
		Song a = ms.searchSongWithArtist("Adele").get(0);
		assertTrue(a.name.equals("Daydreamer"));
		Song b = ms.searchSongWithArtist("Adele").get(12);
		assertTrue(b.name.equals("Rolling in the Deep"));
		
	}
	
	@Test
	void testSearchSongByName() {
		Song a = ms.searchSongWithTitle("Daydreamer").get(0);
		assertTrue(a.artist.equals("Adele"));
				
		Song b = ms.searchSongWithTitle("Here I Am").get(0);
		assertTrue(b.artist.equals("Dolly Parton"));
		
		Song c = ms.searchSongWithTitle("Lullaby").get(0);
		assertTrue(c.artist.equals("Leonard Cohen"));
	}
	
	
}
