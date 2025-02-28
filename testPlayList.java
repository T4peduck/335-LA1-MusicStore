import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class testPlayList {

	PlayList p = new PlayList("My Playlist");
	
	@Test
	void testAddSong() {
		Song s = new Song("Timshel", "Mumphord & Sons", "Sigh No More");
		p.addSong(s);
		assertEquals(1, p.getPlaylist().size());
		p.addSong(s);
		assertEquals(1, p.getPlaylist().size());
		p.addSong(new Song("Sigh No More", "Mumford & Sons", "Sigh No More"));
		assertEquals(2, p.getPlaylist().size());
	}
	
	@Test
	void testRemoveSong() {
		Song s = new Song("Timshel", "Mumphord & Sons", "Sigh No More");
		p.addSong(s);
		Song s1 = new Song("Sigh No More", "Mumford & Sons", "Sigh No More");
		Song s2 = new Song("Little Lion Man", "Mumford & Sons", "Sigh No More");
		p.addSong(s1);
		p.addSong(s2);
		assertEquals(3, p.getPlaylist().size());
		p.removeSong(s1);
		assertEquals(2, p.getPlaylist().size());
		p.removeSong(s2);
		assertEquals(1, p.getPlaylist().size());
		p.removeSong(s);
		assertEquals(0, p.getPlaylist().size());
	}
	
	@Test
	void testGetPlaylist() {
		Song s = new Song("Timshel", "Mumphord & Sons", "Sigh No More");
		p.addSong(s);
		Song s1 = new Song("Sigh No More", "Mumford & Sons", "Sigh No More");
		Song s2 = new Song("Little Lion Man", "Mumford & Sons", "Sigh No More");
		p.addSong(s1);
		p.addSong(s2);
		ArrayList<Song> songs = p.getPlaylist();
		assertEquals(s.name, songs.get(0).name);
		assertEquals(s.artist, songs.get(0).artist);
		assertEquals(s.album, songs.get(0).album);
		assertEquals(s1.name, songs.get(1).name);
		assertEquals(s1.artist, songs.get(0).artist);
		assertEquals(s1.album, songs.get(0).album);
		assertEquals(s2.name, songs.get(2).name);
		assertEquals(s2.artist, songs.get(0).artist);
		assertEquals(s2.album, songs.get(0).album);
	}

}
