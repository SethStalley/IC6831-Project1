package teamidentifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

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
 */
public class Video {
	private String path;
	private static final int CV_CAP_PROP_FRAME_COUNT = 7;
	ArrayList<Mat> frames;

	/**
	 * Constructor for Video.
	 * 
	 * @param path String
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
		//int numFrames = (int) cap.get(CV_CAP_PROP_FRAME_COUNT);
	
		while (true) {
			Mat mat = new Mat();
			cap.read(mat);
			if(mat.empty())
				break;
			frames.add(mat);
		}

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

	 public void segmentate(){
	
		 PlayerDetector playerDetector = new PlayerDetector();
		 playerDetector.Detect(this.frames);
		 ArrayList<Mat> playerFrames = playerDetector.getProcessedPlayers();
		

		 SoccerFieldDetector fieldDetector = new SoccerFieldDetector();
		 fieldDetector.Detect(this.frames);
		 ArrayList<Mat> fieldFrames = fieldDetector.getProcessedFields();
		 
		 
		 //freeMats
		 freeMats(this.frames);
		 this.frames = playerFrames;
		 		 
//		 for(int i=0; i< playerFrames.size(); i++) {
//			 Mat player = playerFrames.get(i);
//			 Mat field = fieldFrames.get(i);
//			 
//			 this.frames.add(getFinalMat(field,player));
//			 field.release();
//			 player.release();
//		 }
//		 
		 System.out.println("Segment done");
	 }
	 
	 private void freeMats(ArrayList<Mat> mats) {
		 for(Mat mat : mats) {
			 mat.release();
		 }
		 mats.clear();
	 }
	 
	  private Mat getFinalMat(Mat field, Mat player) {
	    Mat playerMat = xor(complement(field),player);
	    playerMat = fillPlayers(playerMat, new Point(0,0), new Scalar(0));

	    return playerMat;
	  }
	  
	  private Mat complement(Mat mat) {
		  Mat invertedMat = new Mat();
		  Core.bitwise_not(mat, invertedMat);
		  return invertedMat;
	  }
	  
	  private Mat xor(Mat mat1, Mat mat2) {
		  Mat orMat = new Mat();
		  Core.bitwise_xor(mat1, mat2, orMat);
		  return orMat;
	  }
	  
	  private Mat fillPlayers(Mat image, Point point, Scalar color) {
	    Mat matImageClone = image.clone();
	    Mat mask = new Mat(matImageClone.rows() + 2, matImageClone.cols() + 2, CvType.CV_8UC1);
	    Imgproc.floodFill(matImageClone, mask, point, color);
	    return matImageClone;
	  }


}
