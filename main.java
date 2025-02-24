/*
 * simulates the program
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;


public class main {
	public static void main(String args[]) throws IOException {
		/*
		File dir = new File("albums");
		System.out.println(Arrays.toString(dir.listFiles()));
		
		BufferedReader br;
		
		
		for(File file: dir.listFiles()) {
			String line;
			br = new BufferedReader(new FileReader(file));
			
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println();
		}
		
		*/
		
		MusicStore ms = new MusicStore();
		System.out.println(ms);
		
		
	}
}
