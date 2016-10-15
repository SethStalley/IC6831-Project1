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
			temp = selectField(temp);
//			temp = getRange(temp);
			temp = dilate(temp);
			temp = imfill(temp);
			temp = bwareopen(temp);
			temp = removeLogo(temp);
			this.processedFields.add(temp);
		}

	}
	
	/*
	 * Gets the field from image
	 */
	private Mat selectField(Mat image) {
		int hsvGreen = 60, sensitivity = 30;
	    Scalar minAlpha = new Scalar(hsvGreen - sensitivity, 
	        0, 0); 
	    Scalar maxAlpha = new Scalar(hsvGreen + sensitivity, 255, 255);
	    
	    Mat twoToneImage =  new Mat();
	    Core.inRange(image, minAlpha, maxAlpha, twoToneImage);
	    return twoToneImage;
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
