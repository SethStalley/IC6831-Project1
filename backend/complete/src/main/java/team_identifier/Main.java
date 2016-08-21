package team_identifier;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		testKMeans();
		testReader();
	}
	
	public static void testKMeans() {
		System.out.println("Testing K-Means algorithm");
	}
	
	public static void testReader() {
		Reader r = new Reader();
		try {
			r.readVideo("/Users/seth/Git/IC6831-Project1/dataFiles/test.mp4");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
