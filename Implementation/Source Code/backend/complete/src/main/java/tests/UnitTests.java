package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import teamidentifier.SoccerFieldDetector;

public class UnitTests {
	static {  
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_ffmpeg310_64");		
		System.loadLibrary("openh264-1.4.0-win64msvc");	  
	} 
	
	String userDir = System.getProperty("user.dir");
	String ROOT = userDir.substring(0, userDir.lastIndexOf("Implementation")) + "dataFiles/";
	String testVid = ROOT + "test.mp4";
	
	/**
	 * Checks if two mats are identical
	 *
	 * @param img1: Image in RGB format (Mat).
	 * @param img2: Image in RGB format (Mat).
	 * @return - Boolean value (true, false)
	 */
	private boolean compareMat(Mat img1, Mat img2) {
		Mat temp = new Mat(); 

	    Core.compare(img1, img2, temp, 1);	    
	    return Core.countNonZero(temp) == 0;
	}
	
	@Test
	public void testRgb2Hsv() {
		Mat rgb = Imgcodecs.imread(ROOT + "rgb.png");
		Mat hsv = new SoccerFieldDetector().convertRgb2Hsv(rgb);
		Mat testHsv = Imgcodecs.imread(ROOT + "hsv.png");
		
		Imgproc.cvtColor(testHsv, testHsv, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_RGB2GRAY);
		
		assertEquals(compareMat(testHsv, hsv), true);		
	}	
	
	@Test 
	public void testHsvHue() {
		Mat testHsv = Imgcodecs.imread(ROOT + "hsv.png");
		Mat h = Imgcodecs.imread(ROOT + "h.png");
		testHsv = new SoccerFieldDetector().getHueChannel(testHsv);
		
		Imgproc.cvtColor(h, h, Imgproc.COLOR_RGB2GRAY);
		
		assertEquals(compareMat(testHsv, h), true);		
	}
	
	  @Test
	  public void testGreenMask() {
		Mat greenMask = Imgcodecs.imread(ROOT + "green.png", Imgcodecs.IMREAD_GRAYSCALE);
		Mat testGreen = Imgcodecs.imread(ROOT + "channelH.png");
		testGreen = new SoccerFieldDetector().getRange(testGreen);
	    
		assertEquals(compareMat(testGreen, greenMask), true);
	}	
	
	  @Test
	  public void testLogo() {
		Mat withoutLogo = Imgcodecs.imread(ROOT + "withoutLogo.png");
		Imgproc.cvtColor(withoutLogo, withoutLogo, Imgproc.COLOR_BGR2GRAY);
		
		Mat testFrame = Imgcodecs.imread(ROOT + "withLogo.png");
		testFrame = new SoccerFieldDetector().removeLogo(testFrame);
		Imgproc.cvtColor(testFrame, testFrame, Imgproc.COLOR_BGR2GRAY);
		
		assertEquals(compareMat(testFrame, withoutLogo), true);
	}	
}
