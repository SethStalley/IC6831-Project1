package tests;

import static org.junit.Assert.assertEquals;

import org.opencv.core.Core;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

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
		assertEquals(compareMat(testHsv, hsv), true);		
	}
	
	@Test public void dilate() {
		Mat img = Highgui.imread(ROOT + "rgb.png");
		Mat testDilated = Highgui.imread(ROOT + "dilated.png");
		Mat dilated = Reader.dilate(img);
    	    
	    assertEquals(compareMat(testDilated,dilated), true);  
	}
	
	
	//Checks if two mats are identical
	private boolean compareMat(Mat img1, Mat img2) {
		Mat temp = new Mat(); 
		
		Imgproc.cvtColor(img1, img1, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(img2, img2, Imgproc.COLOR_RGB2GRAY);
		
	    Core.compare(img1, img2, temp, 1);	    
	    return Core.countNonZero(temp) == 0;
	}
	
	

}
