package teamidentifier;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.Mat;

//import org.opencv.core.Mat;

/**
 */
public class Main {

	/**
	 * Method main.
	 * 
	 * @param args
	 *            String[]
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String path = "/Users/seth/Desktop/test.mp4";
		Video video = new Video(path);
		ArrayList<Mat> frames = new ArrayList<Mat>();

		video.readVideo();
		// video.segmentate();
	}

}
