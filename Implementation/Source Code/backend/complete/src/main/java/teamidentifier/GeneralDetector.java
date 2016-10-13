package teamidentifier;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

//opencv3
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_ximgproc;
//import org.bytedeco.javacpp.opencv_core;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 */
public abstract class GeneralDetector {

	/**
	 * Method Detect.
	 * @param frames ArrayList<Mat>
	 */
	public abstract void Detect(ArrayList<Mat> frames);

	/**
	 * Convert RGB Mat to HSV
	 * @param frame Mat
	 * @return - HSV Image (Mat)
	 */
	protected Mat convertRgb2Hsv(Mat frame) {
		Mat frameHsv = new Mat(frame.height(), frame.width(), frame.type());
		// Imgproc.cvtColor(frame, frameHsv, Imgproc.COLOR_RGB2HSV);
		int color = Imgproc.COLOR_RGB2HSV;
		Imgproc.cvtColor(frame, frameHsv, color);
		return frameHsv;
	}

	/**
	 * Extract Hue Channel from HSV image
	 * @param hsv Mat
	 * @return - Hue Channel of the image (Mat)
	 */
	protected Mat getHueChannel(Mat hsv) {
		List<Mat> channels = new Vector<Mat>();
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
	protected Mat imfill(Mat mask) {
		Mat result, hierarchy, destiny;
		result = new Mat(mask.rows(), mask.cols(), mask.type());
		destiny = new Mat(mask.rows(), mask.cols(), mask.type());
		hierarchy = new Mat();

		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Core.bitwise_not(mask, result);
		Imgproc.findContours(result, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

		for (MatOfPoint contour : contours) {
			ArrayList<MatOfPoint> list = new ArrayList<>();
			list.add(contour);
			Imgproc.drawContours(result, list, 0, new Scalar(255), -1);
		}
		Core.bitwise_not(result, destiny);
		return destiny;
	}

}
