package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.opencv.core.Core;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import team_identifier.Reader;


public class OpencvTests {
	
	@Before
	public void setUp() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
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
	public void rgbToHsv() {
		Mat rgb = Highgui.imread(ROOT + "rgb.png");
		Mat hsv = Reader.convertRgb2Hsv(rgb);
		
		Mat testHsv = Highgui.imread(ROOT + "hsv.png");
		assertEquals(compareMat(rgb, hsv), true);		
	}
	
	
	//Checks if two mats are identical
	private boolean compareMat(Mat img1, Mat img2) {
		Mat temp = new Mat(); 
	    Core.compare(img1, img2, temp, 1);	    
	    return Core.countNonZero(temp) == 0;
	}
	
	

}
