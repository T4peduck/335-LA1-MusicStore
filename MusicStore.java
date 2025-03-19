/*
 * MusicStore.java
 * by Joseph Hill and Ethan Cushner
 * Represents the data base that holds all the albums and songs
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;

public class MusicStore {
	 private ArrayList<Album> albumList;
	 private BufferedReader br;
	 
	 public MusicStore() {
		 albumList = new ArrayList<Album>();
		 
		 File albums = new File("albums/albums.txt");
		 
		 try {
		 
			 BufferedReader br = new BufferedReader(new FileReader(albums));
			 
			 String line;
			 while((line = br.readLine()) != null) {
				 String textFile = "albums/" + line.replace(',', '_') + ".txt";
				 albumList.add(parseAlbum(new File(textFile)));
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
		 ArrayList<Album> alist = new ArrayList<>();
		 for(Album a: albumList) {
			 if(a.name.toLowerCase().equals(title.toLowerCase())) {
				 alist.add(new Album(a));
			 }
		 }
		 
		 return alist;
	 }
	 
	 /*
	  * ArrayList<Album> searchAlbumWithArtist(String artist) -- returns a list of all albums in the music store
	  * with artist <artist>. Returns an empty list if no such album exists.
	  */
	 public ArrayList<Album> searchAlbumWithArtist(String artist) {
		 ArrayList<Album> alist = new ArrayList<>();
		 for(Album a: albumList) {
			 if(a.artist.toLowerCase().equals(artist.toLowerCase())) {
				 alist.add(new Album(a));
			 }
		 }
		 
		 return alist;
	 }
	 
	 /*
	  * ArrayList<Song> searchSongWithTitle(String title) -- returns a list of all songs in the music store
	  * with name <title>. Returns an empty list if no such song exists.
	  */
	 public ArrayList<Song> searchSongWithTitle(String title) {
		 ArrayList<Song> slist = new ArrayList<>();
		 for(Album a: albumList) {
			 Song s = a.getSong(title);
			 if(s != null) {slist.add(s);}
		 }
		 
		 return slist;
	 }
	 
	 /*
	  * ArrayList<Song> searchSongWithArtist(String artist) -- returns a list of all songs in the music store
	  * with artist <artist>. Returns an empty list if no such song exists.
	  */
	 public ArrayList<Song> searchSongWithArtist(String artist) {
		 ArrayList<Song> slist = new ArrayList<>();
		 for(Album a: albumList) {
			 if(a.artist.toLowerCase().equals(artist.toLowerCase())) {
				 slist.addAll(a.getAlbum());
			 }
		 }
		 
		 return slist;

	 }

 }
