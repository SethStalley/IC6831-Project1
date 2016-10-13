package team_identifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.Mat;

//import org.opencv.core.Mat;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String path = "/Users/seth/Desktop/test.mp4";
		Video video = new Video(path);
		ArrayList<Mat> frames  = new ArrayList<Mat>();
		
		frames = video.readVideo();
		System.out.println("Frames: "+ frames.size());
		video.writeVideo(frames,path);
//
//		Imshow im1 = new Imshow("Display");
//	    im1.showImage(frames.get(100));
//	     
//		ImageProcessor imgp = new ImageProcessor(frames);
//		frames = imgp.process();
//		
//		Imshow im = new Imshow("Display");
//        im.showImage(frames.get(100));
       

		/*
	     for(Mat frame: frames){
	         Imshow im = new Imshow("Display");
	         im.showImage(frame);

	        }
	*/
	}

}
