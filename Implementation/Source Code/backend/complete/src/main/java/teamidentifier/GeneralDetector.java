package teamidentifier;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Vector;

/**
 */
public abstract class GeneralDetector {

	/**
	 * Method Detect.
	 * 
	 * @param frames ArrayList<Mat>
	 */
	public abstract void Detect(ArrayList<Mat> frames);

	/**
	 * Convert RGB Mat to HSV
	 * 
	 * @param frame Mat
	 * @return - HSV Image (Mat)
	 */
	protected Mat convertRgb2Hsv(Mat frame) {
		Mat frameHsv = new Mat(frame.size(),frame.type());
		Imgproc.cvtColor(frame, frameHsv, Imgproc.COLOR_RGB2HSV);
		return frameHsv;
	}

	/**
	 * Extract Hue Channel from HSV image
	 * 
	 * @param hsv Mat
	 * @return - Hue Channel of the image (Mat)
	 */
	protected Mat getHueChannel(Mat hsv) {
		Vector<Mat> channels = new Vector<Mat>();
		Core.split(hsv, channels);

		return channels.get(0);
	}
	
	

	/**
	 * 
	 * @param image Mat
	 * @return -
	 */
	protected Mat dilate(Mat image) {
		Mat dilatedMat = new Mat();
		Imgproc.dilate(image, dilatedMat, new Mat());
		return dilatedMat;
	}

	/**
	 * Find and fill the contours of the players in the field.
	 * 
	 * @param mask Mat
	 * @return - Image with the holes filled (Mat).
	 */
	public static Mat imfill(Mat mat, Point point) {
		
		//flood fill black color
		Imgproc.rectangle(mat, new Point(0,0), new Point(50,50), new Scalar(0));
		Mat floodFilled = floodFill(mat, new Scalar(225), point);
		
		//image complement
		Mat invertedMat = new Mat();
		Core.bitwise_not(floodFilled, invertedMat);
		Core.bitwise_xor(mat,invertedMat,mat);
		
		return mat;
	}
	
	/*
	 * Flood fill
	 */
	public static Mat floodFill(Mat mat, Scalar color, Point point) {
		Mat clone = mat.clone();
	    Mat mask = new Mat(clone.rows() + 2, clone.cols() + 2, CvType.CV_8UC1);
	    
	    Imgproc.floodFill(clone, mask, point, color);
	    
	    return clone;
	}

}
