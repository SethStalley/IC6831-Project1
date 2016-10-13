package team_identifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Video {
	// private String videoId;
	private String path;
	private static final int CV_CAP_PROP_FRAME_COUNT = 7;
	
	public Video(String path){
		this.path = path;
	}
	
	/**
     * Read a video and divides it into frames
     * @return - List of frames (Mat)
     * @throws FileNotFoundException 
     */
	public ArrayList<Mat> readVideo() throws FileNotFoundException{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		ArrayList<Mat> frames  = new ArrayList<Mat>();
		
		VideoCapture cap = new VideoCapture();
		cap.open(path);
		
		if(!cap.isOpened()) {
			throw new FileNotFoundException();
		}
		
		int numFrames = (int) cap.get(CV_CAP_PROP_FRAME_COUNT);
		
		for(int i = 0; i < numFrames; i++){
			Mat frame = new Mat();
			
			cap.read(frame);
			frames.add(frame);
		}	
		//this.frames = frames;	
		return frames;
	}
	
	
}
