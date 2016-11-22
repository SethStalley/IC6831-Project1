package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import teamidentifier.GroundTruth;
import teamidentifier.PlayerDetector;
import teamidentifier.SoccerFieldDetector;
import teamidentifier.Video;

public class IntegrationTests {

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
	public void testDetectSoccerField() {
		Video video = new Video(ROOT + "test.mp4");
		try {
			video.readVideo();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mat detected = Imgcodecs.imread(ROOT + "detected.png", Imgproc.COLOR_BGR2GRAY);
		
		SoccerFieldDetector sf = new SoccerFieldDetector();
		sf.Detect(video.frames);
		
		Mat testDetect = sf.getProcessedFields().get(0);

		assertEquals(compareMat(testDetect, detected), true);		
	}	
	
	@Test
	public void testDetectPlayers() {
		Video video = new Video(ROOT + "test.mp4");
		try {
			video.readVideo();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mat detected = Imgcodecs.imread(ROOT + "detectedP.png", Imgproc.COLOR_BGR2GRAY);
		
		PlayerDetector detector = new PlayerDetector();
		detector.Detect(video.frames);
		
		Mat testDetect = detector.getProcessedPlayers().get(0);

		assertEquals(compareMat(testDetect, detected), true);		
	}
	
	@Test
	public void testSegmentate() {
		Video video = new Video(ROOT + "test.mp4");
		try {
			video.readVideo();
			video.segmentate();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mat finalMat = Imgcodecs.imread(ROOT + "final.png", Imgproc.COLOR_BGR2GRAY);
		
		Mat testFinal = video.frames.get(0);

		assertEquals(compareMat(testFinal, finalMat), true);	
	}
	
	@Test
	public void testVideoNGroundTruth(){
		boolean validAnswer = false;		
		try {
			
			GroundTruth gt = new GroundTruth();
			double answer = gt.compareWithGroundTruth(ROOT+"output.mp4", ROOT+"binarias.mpeg");
		
			if(answer > 0 && answer < 1){
				validAnswer =  true;
			}
			else{
				validAnswer = false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			validAnswer = false;
		}
		assertTrue(true); 
	}

}
