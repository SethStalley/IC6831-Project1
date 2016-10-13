package teamidentifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_videoio.*;
//import org.opencv.core.Mat;
//import org.opencv.highgui.VideoCapture;

//import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
//import org.bytedeco.javacpp.opencv_videoio.*;

/**
 */
public class Video {
	// private String videoId;
	private String path;
	private static final int CV_CAP_PROP_FRAME_COUNT = 7;
	ArrayList<Mat> frames;

	/**
	 * Constructor for Video.
	 * 
	 * @param path
	 *            String
	 */
	public Video(String path) {
		this.path = path;
		this.frames = new ArrayList<Mat>();
	}

	/**
	 * Read a video and divides it into frames
	 * 
	 * 
	 * @return - List of frames (Mat) * @throws FileNotFoundException
	 */
	public ArrayList<Mat> readVideo() throws FileNotFoundException {

		ArrayList<Mat> frames = new ArrayList<Mat>();

		VideoCapture cap = new VideoCapture();
		cap.open(path);

		if (!cap.isOpened()) {
			throw new FileNotFoundException();
		}

		int numFrames = (int) cap.get(CV_CAP_PROP_FRAME_COUNT);

		for (int i = 0; i < numFrames; i++) {
			Mat mat = new Mat();

			cap.read(mat);
			frames.add(mat);
		}
		this.frames = frames;
		return frames;
	}

	/**
	 * Writes frames as to an mp4 video.
	 * 
	 * 
	 * @param mats
	 *            ArrayList<org.bytedeco.javacpp.opencv_core.Mat>
	 * @param outputFile
	 *            String
	 */
	public void writeVideo(ArrayList<org.bytedeco.javacpp.opencv_core.Mat> mats, String outputFile) {
		int fourcc = VideoWriter.fourcc((byte) 'a', (byte) 'v', (byte) 'c', (byte) '1');
		Size size = mats.get(0).size();
		VideoWriter videoWriter = new VideoWriter(outputFile, fourcc, 23.7, size, true);

		for (org.bytedeco.javacpp.opencv_core.Mat mat : mats) {
			videoWriter.write(mat);
			mat.release();
		}

		videoWriter.release();
		videoWriter.close();
	}

	// public void segmentate(){
	//
	// PlayerDetector playerDetector = new PlayerDetector();
	// playerDetector.Detect(frames);
	// ArrayList<Mat> playerFrames = playerDetector.getProcessedPlayers();
	//
	// System.out.println(playerFrames.size());
	//
	// //for(Mat frame:playerFrames){
	// Imshow im2 = new Imshow("Display");
	// im2.showImage(playerFrames.get(0));
	// //}
	//
	// SoccerFieldDetector fieldDetector = new SoccerFieldDetector();
	// ArrayList<Mat> fieldFrames = fieldDetector.getProcessedFields();
	// fieldDetector.Detect(this.frames);
	//
	// Imshow im1 = new Imshow("Display");
	// im1.showImage(fieldFrames.get(0));
	//
	//
	// }

}
