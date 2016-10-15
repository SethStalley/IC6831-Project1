package teamidentifier;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;



/**
 */
public class Main {
	static {  
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				System.loadLibrary("opencv_ffmpeg310_64");
			    System.loadLibrary("openh264-1.4.0-win64msvc");
			} 
	/**
	 * Method main.
	 * 
	 * @param args String[]
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		String path = Paths.get("C:\\Users\\seths\\Desktop\\test.mp4").toString();
		
		Video video = new Video(path);

		video.readVideo();
		video.segmentate();
		path = Paths.get("C:\\Users\\seths\\Desktop\\output.mp4").toString();
		
		video.writeVideo(video.frames, path);
	}

}
