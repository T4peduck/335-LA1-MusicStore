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
		assertTrue(ul.getAlbums().contains("Sigh No More"));
		ul.addSong("Little Lion Man");
		ul.addSong("Lullaby");
		assertTrue(ul.getSongs().size() == 5);
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
		ul.addSong("Secrets", "OneRepublic");
		ul.addSong("Lullaby", "OneRepublic");
		assertTrue(ul.getSongs().size() == 4);
		assertEquals(2, ul.searchSongWithTitle("Lullaby").size());
		assertEquals("OneRepublic", ul.searchSongWithTitle("Lullaby").get(1).artist);
	}
	
	@Test
	void testAddAlbum() {
		ul.addSong("Sigh No More");
		ul.addAlbum("Sigh No More");
		ul.addSong("Lullaby", "Leonard Cohen");
		ul.addAlbum("Waking Up");
		ArrayList<Song> songs = ms.searchAlbumWithTitle("Sigh No More").get(0).getAlbum();
		for(int i = 0; i < songs.size(); i++) {
			assertEquals(ul.getSongs().get(i), songs.get(i).name);
		}
		assertTrue(ul.getAlbums().contains("Sigh No More"));
	}
	
	@Test
	void testAddAlbumWithArtist() {
		ul.addAlbum("Sigh No More", "Mumford & Sons");
		assertTrue(ul.getAlbums().contains("Sigh No More"));
	}
	
	@Test
	void testRemoveSong() {
		ul.addSong("Little Lion Man");
		ul.removeSong("Little Lion Man");
		assertEquals(0, ul.getSongs().size());
		ul.addAlbum("Sigh No More");
		ul.removeSong("Timshel");
		assertEquals(11, ul.getSongs().size());
		ul.removeSong("Lullaby", "OneRepublic");
		ul.addSong("Lullaby, Leonard Cohen");
		ul.removeSong("Lullaby", "OneRepublic");
	}
	
	@Test
	void testRemoveSongArtistCondition() {
		ul.addSong("Sigh No More");
		ul.removeSong("Sigh No More", "Mumford & Sons");
		assertEquals(0, ul.getSongs().size());
		ul.addSong("Lullaby");
		ul.removeSong("Lullaby", "Leonard Cohen");
		assertEquals(1, ul.getSongs().size());
	}
	
	@Test
	void testRemoveAlbum() {
		ul.addAlbum("Sigh No More");
		ul.removeAlbum("Sigh No More");
		assertEquals(0, ul.getAlbums().size());
		assertEquals(0, ul.getSongs().size());
		ul.addSong("Little Lion Man");
		ul.removeAlbum("Sigh No More");
		assertEquals(0, ul.getAlbums().size());
		assertEquals(0, ul.getSongs().size());
	}
	
	@Test
	void testShuffle() {
		ul.addAlbum("Sigh No More");
		ArrayList<String> originalOrder = ul.getSongs();
		ul.shuffle();
		ArrayList<String> newOrder = ul.getSongs();
		boolean shuffled = false;
		for(int i = 0; i < originalOrder.size(); i++) {
			if(!originalOrder.get(i).equals(newOrder.get(i)))
				shuffled = true;
		}
		assertTrue(shuffled);
	}
	
	@Test
	void testCreatePlaylist() {
		ul.createPlayList("My Playlist");
		assertTrue(ul.getPlaylists().contains("My Playlist"));
		assertEquals(5, ul.getPlaylists().size());
		ul.createPlayList("Other Playlist");
		assertTrue(ul.getPlaylists().contains("Other Playlist"));
		assertEquals(6, ul.getPlaylists().size());
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
	void testAddSongToPlaylistArtistCondition() {
		ul.createPlayList("My Playlist");
		ul.addSong("Lullaby", "Leonard Cohen");
		ul.addSong("Lullaby", "OneRepublic");
		ul.addSongToPlaylist("My Playlist", "Lullaby", "Leonard Cohen");
		assertEquals(1, ul.searchPlaylist("My Playlist").size());
		assertEquals("Lullaby", ul.searchPlaylist("My Playlist").get(0).name);
		ul.addSongToPlaylist("My Playlist", "Lullaby", "OneRepublic");
		assertEquals(2, ul.searchPlaylist("My Playlist").size());
		assertEquals("Lullaby", ul.searchPlaylist("My Playlist").get(1).name);
		assertEquals("Lullaby", ul.searchPlaylist("My Playlist").get(0).name);
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
	void testRemoveSongFromPlaylistArtistCondition() {
		ul.addSong("Lullaby");
		ul.createPlayList("A");
		ul.addSongToPlaylist("A", "Lullaby");
		assertEquals(2, ul.searchPlaylist("A").size());
		ul.removeSongFromPlaylist("A", "Lullaby", "Leonard Cohen");
		assertEquals(1, ul.searchPlaylist("A").size());
	}
	
	@Test
	void testShufflePlaylist() {
		ul.createPlayList("My Playlist");
		ul.addAlbum("Sigh No More");
		ul.addSongToPlaylist("My Playlist", "Sigh No More");
		ul.addSongToPlaylist("My Playlist", "Little Lion Man");
		ul.addSongToPlaylist("My Playlist", "Timshel");
		ul.addSongToPlaylist("My Playlist", "Winter Winds");
		ul.addSongToPlaylist("My Playlist", "Awake My Soul");
		ul.addSongToPlaylist("My Playlist", "Dust Bowl Dance");
		ArrayList<Song> originalOrder = ul.searchPlaylist("My Playlist");
		ul.shufflePlaylist("My Playlist");
		ArrayList<Song> newOrder = ul.searchPlaylist("My Playlist");
		boolean shuffled = false;
		for(int i = 0; i < originalOrder.size(); i++) {
			if(!originalOrder.get(i).equals(newOrder.get(i)))
				shuffled = true;
		}
		assertTrue(shuffled);
	}
	
	@Test
	void testAutomaticPlaylists() {
		ul.addAlbum("Sigh No More");
		assertTrue(ul.getPlaylists().contains("Frequently Played"));
		assertTrue(ul.getPlaylists().contains("Recently Played"));
		assertTrue(ul.getPlaylists().contains("Favorites"));
		assertTrue(ul.getPlaylists().contains("Top Rated"));
		assertTrue(ul.getPlaylists().contains("Alternative"));
		ul.playSong("Sigh No More");
		ul.playSong("The Cave");
		ul.playSong("Winter Winds");
		ul.playSong("Roll Away Your Stone");
		ul.playSong("White Blank Page");
		ul.playSong("I Gave You All");
		ul.playSong("Little Lion Man");
		ul.playSong("Timshel");
		ul.playSong("Thistle & Weeds");
		ul.playSong("Awake My Soul");
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Sigh No More", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("The Cave", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Winter Winds", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Roll Away Your Stone", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("White Blank Page", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("I Gave You All", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Little Lion Man", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Timshel", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Thistle & Weeds", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Frequently Played").contains(new Song("Awake My Soul", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(0).equals(new Song("Awake My Soul", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(1).equals(new Song("Thistle & Weeds", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(2).equals(new Song("Timshel", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(3).equals(new Song("Little Lion Man", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(4).equals(new Song("I Gave You All", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(5).equals(new Song("White Blank Page", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(6).equals(new Song("Roll Away Your Stone", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(7).equals(new Song("Winter Winds", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(8).equals(new Song("The Cave", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(9).equals(new Song("Sigh No More", "Mumford & Sons", "Sigh No More")));
		ul.playSong("After the Storm");
		assertTrue(ul.searchPlaylist("Recently Played").get(0).equals(new Song("After the Storm", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Recently Played").get(9).equals(new Song("The Cave", "Mumford & Sons", "Sigh No More")));
		ul.markFavorite("Sigh No More");
		ul.rateSong("Little Lion Man", 5);
		ul.rateSong("Timshel", 4);
		assertTrue(ul.searchPlaylist("Favorites").contains(new Song("Sigh No More", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Favorites").contains(new Song("Little Lion Man", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Top Rated").contains(new Song("Sigh No More", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Top Rated").contains(new Song("Little Lion Man", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Top Rated").contains(new Song("Timshel", "Mumford & Sons", "Sigh No More")));
		assertTrue(ul.searchPlaylist("Alternative").size() == 12);
		
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
	void testSearchSongWithGenre() {
		ul.addAlbum("Sigh No More");
		assertEquals(12, ul.searchSongsWithGenre("Alternative").size());
		ArrayList<Song> songs = ul.searchSongWithArtist("Mumford & Sons");
		for(Song s : songs) {
			assertTrue(ul.searchSongsWithGenre("Alternative").contains(s));
		}
		assertEquals(0, ul.searchSongsWithGenre("Pop").size());
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
		assertEquals(null, ul.searchPlaylist("Funny Playlist"));
	}
	
	@Test
	void testSortedLists() {
		ul.addAlbum("Sigh No More");
		ArrayList<String> sorted = ul.getSongsSortedByTitle();
		assertEquals("After the Storm", ul.getSongsSortedByTitle().get(0));
		assertEquals("Awake My Soul", ul.getSongsSortedByTitle().get(1));
		assertEquals("Dust Bowl Dance", ul.getSongsSortedByTitle().get(2));
		assertEquals("I Gave You All", ul.getSongsSortedByTitle().get(3));
		assertEquals("Little Lion Man", ul.getSongsSortedByTitle().get(4));
		assertEquals("Roll Away Your Stone", ul.getSongsSortedByTitle().get(5));
		assertEquals("Sigh No More", ul.getSongsSortedByTitle().get(6));
		assertEquals("The Cave", ul.getSongsSortedByTitle().get(7));
		assertEquals("Thistle & Weeds", ul.getSongsSortedByTitle().get(8));
		assertEquals("Timshel", ul.getSongsSortedByTitle().get(9));
		assertEquals("White Blank Page", ul.getSongsSortedByTitle().get(10));
		assertEquals("Winter Winds", ul.getSongsSortedByTitle().get(11));
		ul.addSong("Rolling in the Deep");
		sorted = ul.getSongsSortedByArtist();
		assertEquals("Rolling in the Deep", sorted.get(0));
		ul.removeAlbum("Sigh No More");
		ul.removeSong("Rolling in the Deep");
		ul.addSong("Little Lion Man");
		ul.rateSong("Little Lion Man", 5);
		ul.addSong("Sigh No More");
		ul.rateSong("Sigh No More", 4);
		ul.addSong("Winter Winds");
		ul.rateSong("Winter Winds", 3);
		ul.addSong("White Blank Page");
		ul.rateSong("White Blank Page", 2);
		ul.addSong("Timshel");
		ul.rateSong("Timshel", 1);
		sorted = ul.getSongsSortedByRating();
		assertEquals("Timshel", sorted.get(0));
		assertEquals("White Blank Page", sorted.get(1));
		assertEquals("Winter Winds", sorted.get(2));
		assertEquals("Sigh No More", sorted.get(3));
		assertEquals("Little Lion Man", sorted.get(4));
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
			assertTrue(foundArtists.contains(artists.get(i)));
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
			assertTrue(foundAlbums.contains(albums.get(i)));
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
		assertEquals(6, foundPlaylists.size());
		for(int i = 0; i < 2; i++) {
			assertTrue(foundPlaylists.contains(playlists.get(i)));
		}
	}
	
	@Test
	void testGetGenres() {
		ul.addSong("Little Lion Man");
		assertTrue(ul.getGenres().contains("Alternative"));
		assertEquals(1, ul.getGenres().size());
		ul.addSong("Rolling in the Deep");
		assertTrue(ul.getGenres().contains("Pop"));
		assertEquals(2, ul.getGenres().size());
	}
	
	@Test
	void testGetFavorites() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		ul.addSong("Rolling In The Deep");
		ul.markFavorite("Timshel");
		ul.rateSong("Little Lion Man", "mumford & sons", 5);
		ArrayList<String> favorites = new ArrayList<String>();
		favorites.add("Timshel");
		favorites.add("Little Lion Man");
		ArrayList<Song> foundFavorites = ul.getFavorites();
		for(int i = 0; i < 2; i++) {
			assertTrue(foundFavorites.contains(new Song(favorites.get(i), "Mumford & Sons", "Sigh No More")));
		}
	}
	
	@Test
	void testRateWithArtist() {
		ul.addSong("Lullaby");
		ul.rateSong("Lullaby", "OneRepublic", 5);
		assertEquals(5, ul.getFavorites().get(0).getRating());
		assertEquals(0, ul.searchSongWithArtist("Leonard Cohen").get(0).getRating());
	}
	
	@Test
	void testPlaySongWithArtist() {
		ul.addSong("Lullaby");
		ul.playSong("Lullaby", "OneRepublic");
		assertTrue(ul.searchPlaylist("Recently Played").contains(new Song("Lullaby", "OneRepublic", "Waking Up")));
		assertFalse(ul.searchPlaylist("Recently Played").contains(new Song("Lullaby", "Leonard Cohen", "Old Ideas")));
	}
	
	@Test
	void testRateSong() {
		ul.addSong("Timshel");
		ul.addSong("Sigh No More");
		ul.addSong("Little Lion Man");
		ul.addSong("Rolling In The Deep");
		ul.markFavorite("Timshel");
		ul.rateSong("Little Lion Man", "mumford & sons", 5);
		ul.rateSong("Sigh no more", "mumford & sons", 2);
		
		assertEquals(ul.getRating("Rolling in the deep", "adele"), 0);
		assertEquals(ul.getRating("sigh no more", "mumford & sons"), 2);
		assertEquals(ul.getRating("Timshel", "mumford & sons"), 5);
		
		ArrayList<Song> favs = ul.getFavorites();
		assertTrue(favs.get(1).name.equals("Little Lion Man"));
	}
	
	

}
