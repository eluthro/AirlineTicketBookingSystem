package app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 
 * 
 * @author eluthro
 *
 */

public class User {
	
	private String username;
	
	User(String username) {
		this.username = username;
	}
	
	public int getIndex() {
		int count = 0;
		try {
			Path path = Paths.get("users.txt");
			Scanner sc = new Scanner(path);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (this.username.equals(line)) return count;
				else count++;
			}
			
			sc.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
		return count;
	}
	
	public String getPass() {
		int index = this.getIndex();
		int count = 0;
		try {
			Path path = Paths.get("passwords.txt");
			Scanner sc = new Scanner(path);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (count == index) return line;
				else count++;
			}
			
			sc.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
		
		return null;
		
	}
	
	public String getSalt() {
		int index = this.getIndex();
		int count = 0;
		try {
			Path path = Paths.get("salt.txt");
			Scanner sc = new Scanner(path);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (count == index) return line;
				else count++;
			}
			
			sc.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
		
		return null;
		
	}
	
	public String getName() {
		return this.username;
	}
	
	
}