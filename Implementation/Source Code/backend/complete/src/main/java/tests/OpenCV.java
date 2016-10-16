package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import teamidentifier.GeneralDetector;
import teamidentifier.SoccerFieldDetector;
import teamidentifier.Video;

public class OpenCV {
	static {  
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_ffmpeg310_64");		
		System.loadLibrary("openh264-1.4.0-win64msvc");	  
	} 
	
	String userDir = System.getProperty("user.dir");
	String ROOT = userDir.substring(0, userDir.lastIndexOf("Implementation")) + "dataFiles/";
	String testVid = ROOT + "test.mp4";

	@Test
	public void loadVideo() {
		boolean result = false;

		Video video = new Video(testVid);
		try {
			result = !video.readVideo().get(0).empty();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(result, true);
	}

	@Test
	public void writeVideo() {
		boolean result = false;

		Video video = new Video(testVid);
		try {
			ArrayList<Mat> mats = video.readVideo();
			video.writeVideo(mats, testVid + ".output");
			result = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(result, true);
	}
	
	@Test
	public void rgbToHsv() {
		Mat rgb = Imgcodecs.imread(ROOT + "rgb.png");
		Mat hsv = new SoccerFieldDetector().convertRgb2Hsv(rgb);
		Mat testHsv = Imgcodecs.imread(ROOT + "hsv.png");
		
		Imgproc.cvtColor(testHsv, testHsv, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_RGB2GRAY);
		
		assertEquals(compareMat(testHsv, hsv), true);		
	}
	
	
	@Test 
	public void hsvHue() {
		Mat testHsv = Imgcodecs.imread(ROOT + "hsv.png");
		Mat h = Imgcodecs.imread(ROOT + "h.png");
		testHsv = new SoccerFieldDetector().getHueChannel(testHsv);
		
		Imgproc.cvtColor(h, h, Imgproc.COLOR_RGB2GRAY);
		
		assertEquals(compareMat(testHsv, h), true);		
	}
	
	//Checks if two mats are identical
	private boolean compareMat(Mat img1, Mat img2) {
		Mat temp = new Mat(); 

	    Core.compare(img1, img2, temp, 1);	    
	    return Core.countNonZero(temp) == 0;
	}

}
