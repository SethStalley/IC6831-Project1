/*
 * @author Lucy Chaves - Seth Stalley
 * @version v1.1.1
 */
package teamidentifier;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Vector;


public abstract class GeneralDetector {

	/**
	 * Abstract Method Detect.
	 * 
	 * @param frames ArrayList<Mat>
	 */
	public abstract void Detect(ArrayList<Mat> frames);

	/**
	 * Convert RGB Mat to HSV
	 *
	 * @param frame (Mat)
	 * @return - HSV Image (Mat)
	 */
	public Mat convertRgb2Hsv(Mat frame) {
		Mat frameHsv = new Mat(frame.size(),frame.type());
		Imgproc.cvtColor(frame, frameHsv, Imgproc.COLOR_RGB2HSV);
		return frameHsv;
	}

	/**
	 * Extract Hue Channel from HSV image
	 *
	 * @param Must be a HSV Image (Mat). 
	 * @return - Hue Channel of the image (Mat)
	 */
	public Mat getHueChannel(Mat hsv) {
		Vector<Mat> channels = new Vector<Mat>();
		Core.split(hsv, channels);

		return channels.get(0);
	}

 	/**
  	* Dilates image.
  	*
   	* @param Image of the video in binary format. (Mat)
  	* @return The image dilated, in the format which entered.
   	*/
	protected Mat dilate(Mat image) {
		Mat dilatedMat = new Mat();
		Imgproc.dilate(image, dilatedMat, new Mat());
		return dilatedMat;
	}

	/**
	 * Find and fill the contours of the players in the field.
	 * 
	 * @param mask (Mat)
	 * @return - Image with the holes filled (Mat).
	 */
	public static Mat imfill(Mat mat, Point point) {
		
		// Flood fill black color
		Imgproc.rectangle(mat, new Point(0,0), new Point(50,50), new Scalar(0));
		Mat floodFilled = floodFill(mat, new Scalar(255), point);
		
		// Image complement
		Mat invertedMat = new Mat();
		Core.bitwise_not(floodFilled, invertedMat);
		Core.bitwise_xor(mat,invertedMat,mat);
		
		return mat;
	}
	
  	/**
   	* Flood fills an image.
   	*
   	* @param Image in any format (Mat).
   	* @param The color which want to be painted. (Scalar)
	* @param The point where to start flood filling. (Point)
   	* @return The image floodfilled. (Mat)
   	*/
	public static Mat floodFill(Mat mat, Scalar color, Point point) {
		Mat clone = mat.clone();
	    Mat mask = new Mat(clone.rows() + 2, clone.cols() + 2, CvType.CV_8UC1);
	    Imgproc.floodFill(clone, mask, point, color);
	    return clone;
	}

}
