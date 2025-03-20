/*
 * MusicStore.java
 * by Joseph Hill and Ethan Cushner
 * Represents the data base that holds all the albums and songs
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;

public class MusicStore {
	 private HashMap<Integer, ArrayList<Album>> albumsByArtist;
	 private HashMap<Integer, ArrayList<Album>> albumsByTitle;
	 private BufferedReader br;
	 
	 public MusicStore() {
		 albumsByArtist = new HashMap<Integer, ArrayList<Album>>();
		 albumsByTitle = new HashMap<Integer, ArrayList<Album>>();
		 
		 File albumNames = new File("albums/albums.txt");
		 
		 try {
		 
			 BufferedReader br = new BufferedReader(new FileReader(albumNames));
			 
			 String line;
			 while((line = br.readLine()) != null) {
				 String textFile = "albums/" + line.replace(',', '_') + ".txt";
				 Album a = parseAlbum(new File(textFile));
				 if(albumsByArtist.containsKey(a.hashCodeArtist())) {
					 albumsByArtist.get(a.hashCodeArtist()).add(a);
				 }
				 else {
					 ArrayList<Album> albums = new ArrayList<Album>();
					 albums.add(a);
					 albumsByArtist.put(a.hashCodeArtist(), albums);
				 }
				 if(albumsByTitle.containsKey(a.hashCodeName())) {
					 albumsByTitle.get(a.hashCodeName()).add(a);
				 }
				 else {
					 ArrayList<Album> albums = new ArrayList<Album>();
					 albums.add(a);
					 albumsByTitle.put(a.hashCodeName(), albums);
				 }
			 }
		 } catch (IOException e) { // If albums.txt cannot be found, exit with error code 1
			 System.exit(1);
		 }
	 }
	 
	 /* 
	  * Album parseAlbum(File textFile) throws IOException -- private helper method that takes a file
	  * as input and returns an album object with the data from the file. 
	  */
	 
	 private Album parseAlbum(File textFile) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(textFile));
		 
		 String line;
		 String[] infoLine = br.readLine().split(",");
		 String name = infoLine[0];
		 String artist = infoLine[1];
		 String genre = infoLine[2];
		 String year  = infoLine[3];
		 
		 ArrayList<String> songNames = new ArrayList<>();
		 
		 while((line = br.readLine()) != null) {
			 songNames.add(line);
		 }
		 
		 return new Album(name, artist, genre, year, songNames);
	 }
	 
	/*
	 * ArrayList<Album> searchAlbumWithTitle(String title) -- returns a list of all albums in the musics store
	 * with name <title>. Returns an empty list if no such album exists.
	 */
	 public ArrayList<Album> searchAlbumWithTitle(String title) {
		 ArrayList<Album> alist = albumsByTitle.get(title.toLowerCase().hashCode());
		 if(alist == null)
			 alist = new ArrayList<Album>();
		 return alist;
	 }
	 
	 /*
	  * ArrayList<Album> searchAlbumWithArtist(String artist) -- returns a list of all albums in the music store
	  * with artist <artist>. Returns an empty list if no such album exists.
	  */
	 public ArrayList<Album> searchAlbumWithArtist(String artist) {
		 ArrayList<Album> alist = albumsByArtist.get(artist.toLowerCase().hashCode());
		 if(alist == null)
			 alist = new ArrayList<Album>();
		 return alist;
	 }
	 
	 /*
	  * ArrayList<Song> searchSongWithTitle(String title) -- returns a list of all songs in the music store
	  * with name <title>. Returns an empty list if no such song exists.
	  */
	 public ArrayList<Song> searchSongWithTitle(String title) {
		 ArrayList<Song> slist = new ArrayList<>();
		 for(Integer i: albumsByTitle.keySet()) {
			 ArrayList<Album> alist = albumsByTitle.get(i);
			 for(Album a : alist) {
				 Song s = a.getSong(title);
				 if(s != null) {slist.add(s);}
			 }
		 }
		 
		 return slist;
	 }
	 
	 /*
	  * ArrayList<Song> searchSongWithArtist(String artist) -- returns a list of all songs in the music store
	  * with artist <artist>. Returns an empty list if no such song exists.
	  */
	 public ArrayList<Song> searchSongWithArtist(String artist) {
		 ArrayList<Album> alist = albumsByArtist.get(artist.toLowerCase().hashCode());
		 ArrayList<Song> slist = new ArrayList<Song>();
		 if(alist != null)
			 for(Album a : alist) {
				 slist.addAll(a.getAlbum());
			 }
		 return slist;

	 }
 }
