package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import team_identifier.Reader;

public class OpencvTests {
	
	String dir = System.getProperty("user.dir");
	String ROOT  =  dir.substring(0, dir.lastIndexOf("backend")) + "dataFiles/";
	String testVideoName = "test.mp4";
	
	@Test
	public void loadVideo() {	
	   Reader r = new Reader();
	   
	   try {
		   System.out.println(ROOT + testVideoName);
		   Reader.readVideo(ROOT + testVideoName);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }   
	}

}
