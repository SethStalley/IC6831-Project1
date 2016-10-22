/*
 * @author Lucy Chaves - Seth Stalley
 * @version v1.1.1
 */
package teamidentifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

/**
* The purpose of this class is to read, process and generates the final video with the players detection.
*/
public class Video {
	private String path;
	public ArrayList<Mat> frames;

	/**
	 * Constructor for Video.
	 * 
	 * @param The file path of the video to analyze (String).
	 */
	public Video(String path) {
		this.path = path;
		this.frames = new ArrayList<Mat>();
	}

	/**
	 * Read a video and divides it into frames
	 *  
	 * @return - List of frames (Mat) * @throws FileNotFoundException
	 */
	public ArrayList<Mat> readVideo() throws FileNotFoundException {

		ArrayList<Mat> frames = new ArrayList<Mat>();

		VideoCapture cap = new VideoCapture(path);

		if (!cap.isOpened()) {
			throw new FileNotFoundException();
		}
	
		while (true) {
			Mat mat = new Mat();
			cap.read(mat);
			if(mat.empty())
				break;
			frames.add(mat);
		}
		cap.release();
		this.frames = frames;
		return frames;
	}

	/**
	 * Writes frames as to an mp4 video.
	 * 
	 * @param mats ArrayList<org.bytedeco.javacpp.opencv_core.Mat>
	 * @param outputFile String
	 */
	public void writeVideo(ArrayList<Mat> mats, String outputFile) {
		int fourcc = VideoWriter.fourcc('H','2','6','4');
		Size size = mats.get(0).size();

		VideoWriter videoWriter = new VideoWriter(outputFile, fourcc, 23.7, size,false);

		for (Mat mat : mats) {
			videoWriter.write(mat);
			mat.release();
		}
		videoWriter.release();
	}

	private void freeMats(ArrayList<Mat> mats) {
		 for(Mat mat : mats) {
			 mat.release();
		 }
		 mats.clear();
	 }
	 
	  private Mat getFinalMat(Mat field, Mat player) {	
		Mat result = player.clone();
		Core.bitwise_and(result, field, result);
	    return result;
	  }
	 
	  /**
 	  * Complements an image.
 	  *
  	  * @param The image in binary format (Mat).
 	  * @return The image in binary format inverted (Mat).
  	  */
	  private Mat complement(Mat mat) {
		  Mat invertedMat = new Mat();
		  Core.bitwise_not(mat, invertedMat);
		  return invertedMat;
	  }
	
	  /**
 	  * Segmentates the players of the video.
 	  *
  	  * 
  	  */
	 public void segmentate(){
	
		 PlayerDetector playerDetector = new PlayerDetector();
		 playerDetector.Detect(this.frames);
		 ArrayList<Mat> playerFrames = playerDetector.getProcessedPlayers();
		

		 SoccerFieldDetector fieldDetector = new SoccerFieldDetector();
		 fieldDetector.Detect(this.frames);
		 ArrayList<Mat> fieldFrames = fieldDetector.getProcessedFields();
		 
		 
		 //freeMats
		 freeMats(this.frames);
		 
		 for(int i=0; i< playerFrames.size(); i++) {
			 Mat player = playerFrames.get(i);
			 Mat field = fieldFrames.get(i);
			 
			 this.frames.add(getFinalMat(field,player));
		 }
		 
		 System.out.println("Segment done");
	 }
	 
}
