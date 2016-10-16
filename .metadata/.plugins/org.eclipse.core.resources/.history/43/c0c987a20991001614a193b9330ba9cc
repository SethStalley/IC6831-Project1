package team_identifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Mat;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Video video = new Video("/Users/lucychaves/Desktop/cut1_360.mp4");
		ArrayList<Mat> frames  = new ArrayList<Mat>();
		
		frames = video.readVideo();
		
		System.out.println("Frames: "+ frames.size());

		 Imshow im1 = new Imshow("Display");
	     im1.showImage(frames.get(100));
	     
		ImageProcessor imgp = new ImageProcessor(frames);
		frames = imgp.process();
		
		Imshow im = new Imshow("Display");
        im.showImage(frames.get(100));
       

		/*
	     for(Mat frame: frames){
	         Imshow im = new Imshow("Display");
	         im.showImage(frame);

	        }
	*/
	}

}
