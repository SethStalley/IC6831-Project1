package teamidentifier;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.opencv.core.Core;



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
		
		String path = Paths.get("C:\\Users\\Seth\\Desktop\\test.mp4").toString();
		
		Video video = new Video(path);

		video.readVideo();
		video.segmentate();
		
		System.out.println(video.frames.size());
		
		path = Paths.get("C:\\Users\\Seth\\Desktop\\output.mp4").toString();
		video.writeVideo(video.frames, path);
	}

}
