package teamidentifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

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
		cap.release();
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
	public void writeVideo(ArrayList<Mat> mats, String outputFile) {
		int fourcc = VideoWriter.fourcc('M','P','4','V');
		Size size = mats.get(0).size();

		VideoWriter videoWriter = new VideoWriter(outputFile, fourcc, 23.7, size, false);

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
		
		 System.out.println(playerFrames.size());
		
		 //for(Mat frame:playerFrames){
//		 Imshow im2 = new Imshow("Display");
//		 im2.showImage(playerFrames.get(0));
		 //}
		
		 SoccerFieldDetector fieldDetector = new SoccerFieldDetector();
		 ArrayList<Mat> fieldFrames = fieldDetector.getProcessedFields();
		 fieldDetector.Detect(this.frames);

//		 Imshow im1 = new Imshow("Display");
//		 im1.showImage(playerFrames.get(100));
		 this.frames=playerFrames;
	
	 }

}
