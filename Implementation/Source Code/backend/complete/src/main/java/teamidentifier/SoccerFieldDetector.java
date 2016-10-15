package teamidentifier;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 */
public class SoccerFieldDetector extends GeneralDetector {
	private ArrayList<Mat> processedFields;

	public SoccerFieldDetector() {
		this.processedFields = new ArrayList<Mat>();
	}

	/**
	 * Method Detect.
	 * 
	 * @param frames
	 *            ArrayList<Mat>
	 */
	@Override
	public void Detect(ArrayList<Mat> frames) {
		for (Mat frame : frames) {
			Mat temp = getHueChannel(convertRgb2Hsv(frame));
			temp = getRange(temp);
			temp = dilate(temp);
			temp = imfill(temp);
			temp = bwareopen(temp);
			temp = removeLogo(temp);
			this.processedFields.add(temp);
		}

	}

	/**
	 * Creates a binary mask of green pixels of an image.
	 * 
	 * 
	 * @param image
	 *            Mat
	 * @return - A mask of green pixels (Mat).
	 */
	protected Mat getRange(Mat image) {
		Mat mask = new Mat();

		Scalar lowerGreen = new Scalar(18, 100, 50);
		Scalar upperGreen = new Scalar(93, 255, 255);

		Core.inRange(image, lowerGreen, upperGreen, mask);

		return mask;
	}

	/**
	 * Find and fill the contours of the players in the field.
	 * 
	 * 
	 * @param mask
	 *            Mat
	 * @return - Image with the holes filled (Mat).
	 */
	public Mat imfill(Mat mask) {
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

	/**
	 * Fill small spurious regions
	 * 
	 * 
	 * @param mask
	 *            Mat
	 * @return - Image with small regions filled (Mat).
	 */
	public Mat bwareopen(Mat mask) {
		Core.bitwise_not(mask, mask);
		Mat result = imfill(mask);
		Core.bitwise_not(result, result);
		return result;
	}

	/**
	 * Removes the logo of the image.
	 * 
	 * 
	 * @param image
	 *            Mat
	 * @return - Image without logo (Mat).
	 */
	private Mat removeLogo(Mat image) {
		Imgproc.rectangle(image, new Point(426, 1), new Point(637, 80), new Scalar(0), -1);
		return image;
	}

	/**
	 * Method getProcessedFields.
	 * 
	 * @return ArrayList<Mat>
	 */
	public ArrayList<Mat> getProcessedFields() {
		return this.processedFields;
	}

}
