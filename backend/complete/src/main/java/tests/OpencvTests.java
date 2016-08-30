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
	   boolean success;
	   
	   try {
		   Reader.readVideo(ROOT + testVideoName );
		   success = true;
	   } catch (Exception e) {
		   success = false;
	   }  
	   
	   assertEquals(true, success);
	}
	
	@Test
	public void proccesVideo() {
		boolean success;
		
		try {
			Reader.readVideo(ROOT + testVideoName);
			Reader.addMask();
			Reader.normalize();
	        success = true;
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		}
		
		assertEquals(true, success);		
	}

}
