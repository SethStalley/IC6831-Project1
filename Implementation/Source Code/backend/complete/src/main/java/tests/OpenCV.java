package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;
import org.opencv.core.Mat;

import teamidentifier.Video;

public class OpenCV {

	String userDir = System.getProperty("user.dir");
	String ROOT = userDir.substring(0, userDir.lastIndexOf("Implementation"));
	String testVid = ROOT + "dataFiles/test.mp4";

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

}
