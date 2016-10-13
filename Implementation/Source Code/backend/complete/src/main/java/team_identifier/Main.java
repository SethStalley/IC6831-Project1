package team_identifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Mat;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Video video = new Video("/Users/lucychaves/Desktop/cut1_360.mp4");
		video.readVideo();
		video.segmentate();
	}

}
