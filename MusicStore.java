/*
 * MusicStore.java
 * by Joseph Hill and Ethan Cushner
 * Represents something
 * 
 * TODO: fix comments at top
 * TODO: do uml
 */

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;

public class MusicStore {
	 private ArrayList<Album> albumList;
	 private BufferedReader br;
	 
	 public MusicStore() throws IOException {
		 albumList = new ArrayList<Album>();
		 
		 File albums = new File("albums/albums.txt");
		 
		 BufferedReader br = new BufferedReader(new FileReader(albums));
		 
		 String line;
		 while((line = br.readLine()) != null) {
			 String textFile = "albums/" + line.replace(',', '_') + ".txt";
			 albumList.add(parseAlbum(new File(textFile)));
		 }
	 }
	 
	 //check for empty file???
	 public Album parseAlbum(File textFile) throws IOException {
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
	 
	 public String toString() {
		 String rstr = "";
		 for(Album a: albumList) {
			 rstr += a.toString() + '\n';
		 }
		 return rstr;
	 }


 }