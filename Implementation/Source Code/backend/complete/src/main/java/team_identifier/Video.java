package team_identifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Core;
//import org.opencv.core.Mat;

//import org.opencv.core.Size;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.*;
//import org.opencv.highgui.*;;


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
		
		ArrayList<Mat> frames  = new ArrayList<Mat>();
		
		VideoCapture cap = new VideoCapture();
		cap.open(path);
		
		if(!cap.isOpened()) {
			throw new FileNotFoundException();
		}
		
		int numFrames = (int) cap.get(CV_CAP_PROP_FRAME_COUNT);
		
		for(int i = 0; i < numFrames; i++){
			Mat mat = new Mat();
			
			cap.read(mat);
			frames.add(mat);
		}	
		//this.frames = frames;	
		return frames;
	}
	
	/**
	 * Writes frames as to an mp4 video.
	 * @Input - ArrayList<Mat> of frames to write
	 * @Input - String file name. Name of stored video file.
	 */
	public void writeVideo(ArrayList<Mat> mats, String outputFile) {
		int fourcc = VideoWriter.fourcc((byte)'a', (byte)'v', (byte)'c', (byte)'1');
		VideoWriter videoWriter = new VideoWriter(outputFile, fourcc,20.0,mats.get(0).size(),true);
		
		for(Mat mat : mats) {
			videoWriter.write(mat);
			mat.release();
		}
		
		videoWriter.release();
		videoWriter.close();
	}
	
	
}
