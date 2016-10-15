package teamidentifier;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 */
public abstract class GeneralDetector {

	/**
	 * Method Detect.
	 * 
	 * @param frames
	 *            ArrayList<Mat>
	 */
	public abstract void Detect(ArrayList<Mat> frames);

	/**
	 * Convert RGB Mat to HSV
	 * 
	 * @param frame
	 *            Mat
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
	 * @param image
	 *            Mat
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
	protected Mat imfill(Mat mat) {
		
		//flood fill black color
		Imgproc.rectangle(mat, new Point(0,0), new Point(50,50), new Scalar(0));
		Mat floodFilled = floodFill(mat, new Scalar(225));
		
		//image complement
		Mat invertedMat = new Mat();
		Core.bitwise_not(floodFilled, invertedMat);
		Core.bitwise_xor(mat,invertedMat,mat);
		
		return mat;
	}
	
	/*
	 * Flood fill
	 */
	private Mat floodFill(Mat mat, Scalar color) {
		Mat clone = mat.clone();
		Point point2Fill = new Point(0,clone.height() * 0.75);
	    Mat mask = new Mat(clone.rows() + 2, clone.cols() + 2, CvType.CV_8UC1);
	    
	    Imgproc.floodFill(clone, mask, point2Fill, color);
	    
	    return clone;
	}

}
