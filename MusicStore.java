/*
 * MusicStore.java
 * by Joseph Hill and Ethan Cushner
 * Represents the data base that holds all the albums and songs
 * 
 * TODO: fix comments at top
 * TODO: do uml
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
		 } catch (IOException e) {
			 System.exit(1);
		 }
	 }
	 
	 //check for empty file???
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
	 
	 //TODO: deep copy?
	 //if empty list is returned, no song found matching the search parameters
	 public ArrayList<Album> searchAlbumWithTitle(String title) {
		 ArrayList<Album> alist = new ArrayList<>();
		 for(Album a: albumList) {
			 if(a.name.toLowerCase().equals(title.toLowerCase())) {
				 alist.add(new Album(a));
			 }
		 }
		 
		 return alist;
	 }
	 
	 public ArrayList<Album> searchAlbumWithArtist(String artist) {
		 ArrayList<Album> alist = new ArrayList<>();
		 for(Album a: albumList) {
			 if(a.artist.toLowerCase().equals(artist.toLowerCase())) {
				 alist.add(new Album(a));
			 }
		 }
		 
		 return alist;
	 }
	 
	 public ArrayList<Song> searchSongWithTitle(String title) {
		 ArrayList<Song> slist = new ArrayList<>();
		 for(Album a: albumList) {
			 Song s = a.getSong(title);
			 if(s != null) {slist.add(s);}
		 }
		 
		 return slist;
	 }
	 
	 public ArrayList<Song> searchSongWithArtist(String artist) {
		 ArrayList<Song> slist = new ArrayList<>();
		 for(Album a: albumList) {
			 if(a.artist.toLowerCase().equals(artist.toLowerCase())) {
				 slist.addAll(a.getAlbum());
			 }
		 }
		 
		 return slist;

	 }
	 
	 public String toString() {
		 String rstr = "";
		 for(Album a: albumList) {
			 rstr += a.toString() + '\n';
		 }
		 return rstr;
	 }


 }