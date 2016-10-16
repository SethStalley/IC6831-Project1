package webserver;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	static {  
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_ffmpeg310_64");		
		System.loadLibrary("openh264-1.4.0-win64msvc");	  
	} 
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
