import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestLibraryModel {
	
	private MusicStore ms = new MusicStore();
	private LibraryModel ul = new LibraryModel(ms);

	@Test
	void testAddSong() {
		ul.addSong("Crazy For You");
		assertTrue(ul.getSongs().size() == 1);
		assertEquals("Crazy For You".toLowerCase(), ul.searchSongWithTitle("Crazy For You").get(0).name.toLowerCase());
		ul.addSong("Timshel");
		assertTrue(ul.getSongs().size() == 2);
		assertEquals("Timshel", ul.searchSongWithTitle("Timshel").get(0).name);
		ul.addSong("Lullaby");
		assertTrue(ul.getSongs().size() == 4);
		assertTrue(ul.searchSongWithTitle("Lullaby").size() == 2);
	}
	
	@Test
	void testAddSongWithArtistCondition() {
		ul.addSong("Sigh No More", "Mumford & Sons");
		assertTrue(ul.getSongs().size() == 1);
		ul.addSong("Lullaby", "Leonard Cohen");
		assertTrue(ul.getSongs().size() == 2);
		assertEquals(1, ul.searchSongWithTitle("Lullaby").size());
		assertEquals("Leonard Cohen", ul.searchSongWithTitle("Lullaby").get(0).artist);
		ul.addSong("Lullaby", "OneRepublic");
		assertTrue(ul.getSongs().size() == 3);
		assertEquals(2, ul.searchSongWithTitle("Lullaby").size());
		assertEquals("OneRepublic", ul.searchSongWithTitle("Lullaby").get(1).artist);
	}
	
	@Test
	void testAddAlbum() {
		ul.addAlbum("Sigh No More");
		assertTrue(ul.getSongs().size() == ms.searchAlbumWithTitle("Sigh No More").get(0).getAlbum().size());
		ArrayList<Song> songs = ms.searchAlbumWithTitle("Sigh No More").get(0).getAlbum();
		for(int i = 0; i < songs.size(); i++) {
			assertEquals(ul.getSongs().get(i), songs.get(i).name);
		}
		assertEquals(ul.getAlbums().get(0), "Sigh No More");
	}
	
	@Test
	void testCreatePlaylist() {
		ul.createPlayList("My Playlist");
		assertEquals("My Playlist", ul.getPlaylists().get(0));
		assertEquals(1, ul.getPlaylists().size());
		ul.createPlayList("Other Playlist");
		assertEquals("Other Playlist", ul.getPlaylists().get(1));
		assertEquals(2, ul.getPlaylists().size());
	}
	
	@Test
	void testAddSongToPlaylist() {
		ul.createPlayList("My Playlist");
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSongToPlaylist("My Playlist", "Timshel");
		assertEquals(1, ul.searchPlaylist("My Playlist").size());
		assertEquals("Timshel", ul.searchPlaylist("My Playlist").get(0).name);
		ul.addSongToPlaylist("My Playlist", "Sigh No More");
		assertEquals(2, ul.searchPlaylist("My Playlist").size());
		assertEquals("Sigh No More", ul.searchPlaylist("My Playlist").get(1).name);
		assertEquals("Timshel", ul.searchPlaylist("My Playlist").get(0).name);
	}
	
	@Test
	void testRemoveSongFromPlaylist() {
		ul.createPlayList("My Playlist");
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSongToPlaylist("My Playlist", "Timshel");
		ul.addSongToPlaylist("My Playlist", "Sigh No More");
		assertEquals(2, ul.searchPlaylist("My Playlist").size());
		ul.removeSongFromPlaylist("My Playlist", "Timshel");
		assertEquals(1, ul.searchPlaylist("My Playlist").size());
		assertEquals("Sigh No More", ul.searchPlaylist("My Playlist").get(0).name);
		ul.removeSongFromPlaylist("My Playlist", "Awake My Soul");
		assertEquals(1, ul.searchPlaylist("My Playlist").size());
		assertEquals("Sigh No More", ul.searchPlaylist("My Playlist").get(0).name);
		ul.removeSongFromPlaylist("My Playlist", "Sigh No More");
		assertEquals(0, ul.searchPlaylist("My Playlist").size());
	}
	
	@Test
	void testSearchSongWithTitle() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		assertEquals(1, ul.searchSongWithTitle("Timshel").size());
		assertEquals("Timshel", ul.searchSongWithTitle("Timshel").get(0).name);
		assertEquals(1, ul.searchSongWithTitle("Sigh No More").size());
		assertEquals("Sigh No More", ul.searchSongWithTitle("Sigh No More").get(0).name);
		assertEquals(1, ul.searchSongWithTitle("Little Lion Man").size());
		assertEquals("Little Lion Man", ul.searchSongWithTitle("Little Lion Man").get(0).name);
		assertEquals(0, ul.searchSongWithTitle("White Blank Page").size());
	}
	
	@Test
	void testSearchSongWithArtist() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		ul.addSong("Rolling In The Deep");
		assertEquals(3, ul.searchSongWithArtist("Mumford & Sons").size());
		assertEquals(1, ul.searchSongWithArtist("Adele").size());
		assertEquals("Rolling in the Deep", ul.searchSongWithArtist("Adele").get(0).name);
		assertEquals(0, ul.searchAlbumWithArtist("Norah Jones").size());
	}
	
	@Test
	void testSearchAlbumWithTitle() {
		ul.addAlbum("Sigh No More");
		ul.addAlbum("21");
		assertEquals(1, ul.searchAlbumWithTitle("Sigh No More").size());
		assertEquals("Sigh No More", ul.searchAlbumWithTitle("Sigh No More").get(0).name);
		assertEquals(1, ul.searchAlbumWithTitle("21").size());
		assertEquals("21", ul.searchAlbumWithTitle("21").get(0).name);
		assertEquals(0, ul.searchAlbumWithTitle("19").size());
	}
	
	@Test
	void testSearchAlbumWithArtist() {
		ul.addAlbum("Sigh No More");
		ul.addAlbum("21");
		ul.addAlbum("19");
		assertEquals(1, ul.searchAlbumWithArtist("Mumford & Sons").size());
		assertEquals("Sigh No More", ul.searchAlbumWithArtist("Mumford & Sons").get(0).name);
		assertEquals(2, ul.searchAlbumWithArtist("Adele").size());
		assertEquals("21", ul.searchAlbumWithArtist("Adele").get(0).name);
		assertEquals("19", ul.searchAlbumWithArtist("Adele").get(1).name);
		assertEquals(0, ul.searchAlbumWithArtist("Begin Again").size());
	}
	
	@Test
	void testSearchPlaylist() {
		ul.addAlbum("Sigh No More");
		ul.addAlbum("21");
		ul.addAlbum("19");
		ul.createPlayList("My Playlist");
		ul.createPlayList("Other Playlist");
		ul.addSongToPlaylist("My Playlist", "Sigh No More");
		ul.addSongToPlaylist("My Playlist", "Timshel");
		ul.addSongToPlaylist("My Playlist", "Little Lion Man");
		ul.addSongToPlaylist("Other Playlist", "Sigh No More");
		assertEquals(3, ul.searchPlaylist("My Playlist").size());
		assertEquals(1, ul.searchPlaylist("Other Playlist").size());
		assertEquals(0, ul.searchPlaylist("Funny Playlist").size());
	}
	
	@Test
	void testGetSongs() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		ul.addSong("Rolling In The Deep");
		ArrayList<String> songs = new ArrayList<String>();
		songs.add("Timshel");
		songs.add("Sigh No More");
		songs.add("Little Lion Man");
		songs.add("Rolling in the Deep");
		ArrayList<String> foundSongs = ul.getSongs();
		for(int i = 0; i < 4; i++) {
			assertEquals(songs.get(i), foundSongs.get(i));
		}
	}
	
	@Test
	void testGetArtists() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		ul.addSong("Rolling In The Deep");
		ul.addAlbum("Begin Again");
		ArrayList<String> artists = new ArrayList<String>();
		artists.add("Mumford & Sons");
		artists.add("Adele");
		artists.add("Norah Jones");
		ArrayList<String> foundArtists = ul.getArtists();
		for(int i = 0; i < 3; i++) {
			assertEquals(artists.get(i), foundArtists.get(i));
		}
	}
	
	@Test
	void testGetAlbums() {
		ul.addSong("Timshel");
		ul.addAlbum("19");
		ul.addAlbum("21");
		ul.addAlbum("Begin Again");
		ArrayList<String> albums = new ArrayList<String>();
		albums.add("19");
		albums.add("21");
		albums.add("Begin Again");
		ArrayList<String> foundAlbums = ul.getAlbums();
		for(int i = 0; i < 3; i++) {
			assertEquals(albums.get(i), foundAlbums.get(i));
		}
	}
	
	@Test
	void testGetPlaylists() {
		ul.createPlayList("My Playlist");
		ul.createPlayList("Other Playlist");
		ArrayList<String> playlists = new ArrayList<String>();
		playlists.add("My Playlist");
		playlists.add("Other Playlist");
		ArrayList<String> foundPlaylists = ul.getPlaylists();
		assertEquals(2, foundPlaylists.size());
		for(int i = 0; i < 2; i++) {
			assertEquals(playlists.get(i), foundPlaylists.get(i));
		}
	}
	
	@Test
	void testGetFavorites() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		ul.addSong("Rolling In The Deep");
		ul.markFavorite("Timshel");
		ul.rateSong("Little Lion Man", 5);
		ArrayList<String> favorites = new ArrayList<String>();
		favorites.add("Timshel");
		favorites.add("Little Lion Man");
		ArrayList<String> foundFavorites = ul.getFavorites();
		for(int i = 0; i < 2; i++) {
			assertEquals(foundFavorites.get(i), favorites.get(i));
		}
	}
	
	

}
