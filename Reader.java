package modelo;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class Reader {
	private static final int CV_CAP_PROP_FRAME_COUNT = 7;
	
	private static ArrayList<Mat> frames  = new ArrayList<Mat>();
	private static ArrayList<Mat> framesHsv  = new ArrayList<Mat>();
	private static ArrayList<Mat> masks = new ArrayList<Mat>();
	private static ArrayList<Mat> normalizedHsv = new ArrayList<Mat>();

	
	public static void readVideo(String url) throws FileNotFoundException{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture cap = new VideoCapture();
		cap.open(url);

		int numFrames = (int) cap.get(CV_CAP_PROP_FRAME_COUNT);
		  
		for(int i = 1; i < numFrames; i++){
			Mat frame = new Mat();
			
			cap.read(frame);
			frames.add(frame);
			}	
		
		convertToHsv();
	}

	// Extract Hue Channel from HSV image
	public static Mat getHueChannel(Mat hsv){
		List<Mat> channels = new Vector<Mat>();
		Core.split(hsv, channels);
		
		return channels.get(0);
	}
	
	// Convert frames to HSV color model
	public static void convertToHsv(){
		for(Mat frame:frames){
			Mat framehsv = new Mat(frame.height(), frame.width(), frame.type());
			Imgproc.cvtColor(frame, framehsv, Imgproc.COLOR_RGB2HSV);
			framesHsv.add(getHueChannel(framehsv));
		}
	}
	
	// Get a binary mask of green pixels
	public static Mat getRange(Mat image){
		Mat mask = new Mat();
		
		Scalar lowerGreen = new Scalar(35, 50, 50);
		Scalar upperGreen = new Scalar(70, 255, 255);
		Core.inRange(image, lowerGreen, upperGreen, mask);
		
		return mask;		
	}
	
	public static void addMask(){
		for(Mat frame: framesHsv){
			masks.add(getRange(frame));
		}
	}
	
	// Morphological Transformations 
	public static Mat dilate(Mat hsv){
		int dilation_size = 2;
		Mat result = new Mat(hsv.rows(), hsv.cols(), hsv.type());
		
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2*dilation_size + 1, 2*dilation_size+1));
        Imgproc.dilate(hsv, result, kernel);
    
        return result;

	}
	
	// Soccer Players Detection
	public static Mat normalizeFrame(Mat hsv){
		int cols = hsv.cols();
		int rows = hsv.rows();

		Mat dest = new Mat(rows, cols, hsv.type());
		
		for(int row = 0; row<rows; row++){
			for(int col = 0; col<cols; col++){
				double[] channels = hsv.get(row, col);
				double[] normalizedChannels = new double[]{(int)((channels[0]/360) * 255), 0, 0};
                dest.put(row, col, normalizedChannels);
			}			
		}
		return dest;
	}
	
	public static void normalize(){
		for(Mat hsv: framesHsv){
			normalizedHsv.add(normalizeFrame(hsv));
		}
	}
	
	public static Mat stdfilt(Mat image){
	  Mat image32f = new Mat();
	  image.convertTo(image32f, CvType.CV_32F);

	  Mat mu = new Mat();
	  Mat mu2 = new Mat();
	  Imgproc.blur(image32f, mu, new Size(3, 3));
      Imgproc.blur(image32f.mul(image32f), mu2, new Size(3, 3));

      Mat sigma = new Mat();
      Mat src = new Mat();
      
      Core.subtract(mu2, mu.mul(mu), src);
      Core.sqrt(src, sigma);
      
      Mat image2 = new Mat();
	  sigma.convertTo(image2, image.type());
      
      return image2;
	}
	
	public static Mat truncate(Mat image){
		Mat res = new Mat(image.rows(), image.cols(), image.type());

        res = image;
        Imgproc.threshold(image, res, 0, 255, Imgproc.THRESH_OTSU);
        return res;
	}

	
	public static ArrayList<Mat> getFrames() {
		return frames;
	}

	public static ArrayList<Mat> getMasks() {
		return masks;
	}

	public static ArrayList<Mat> getFramesHsv() {
		return framesHsv;
	}
	
	public static ArrayList<Mat> getNormalizedHsv() {
		return normalizedHsv;
	}
	
}
