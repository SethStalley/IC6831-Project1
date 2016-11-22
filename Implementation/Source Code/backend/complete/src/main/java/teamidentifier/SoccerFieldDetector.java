/*
 * @author Lucy Chaves - Seth Stalley
 * @version v1.1.1
 */
package teamidentifier;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/*
 * The purpose of this class is to detect the soccer field.
 * The whole algorithm was taken from the Project Specification written by Saul Calderón.
 * Players must have their uniform different to green.
 * The soccer field must be green.
 */
public class SoccerFieldDetector extends GeneralDetector {
	private ArrayList<Mat> processedFields;

	/**
  	* Instantiates a new soccer field detector.
    	*
  	* 
  	*/
	public SoccerFieldDetector() {
		this.processedFields = new ArrayList<Mat>();
	}

	/**
	 * Detects the soccer field in the image.
	 *
	 * @param frames: List of the video frames (ArrayList<Mat>).
	 */
	@Override
	public void Detect(ArrayList<Mat> frames) {
		for (Mat frame : frames) {
			Mat temp = getHueChannel(convertRgb2Hsv(frame));
			temp = getRange(temp);
			temp = dilate(temp);
			temp = imfill(temp, new Point(0,0));
			temp = bwareopen(temp);
			
			Core.bitwise_not(temp, temp);
			temp = dilate(temp);
			temp = dilate(temp);
			temp = dilate(temp);
			temp = dilate(temp);
			temp = dilate(temp);
			temp = dilate(temp);
			
			Core.bitwise_not(temp, temp);
			
			temp = removeLogo(temp);
			
			this.processedFields.add(temp);
		}

	}
	

	/**
	 * Creates a binary mask of green pixels of an image.
	 * @param image: the image to extract the green pixels. Must be in RGB format (Mat).
	 * @return - A mask of green pixels (Mat).
	 */
	public Mat getRange(Mat image) {
		Mat mask = image.clone();

		Scalar lowerGreen = new Scalar(18, 100, 30);
		Scalar upperGreen = new Scalar(92, 255, 255);
		Core.inRange(image, lowerGreen, upperGreen, mask);
		
		return mask;
	}


	/**
	 * Fill small spurious regions
	 * 
	 * @param mask: the image with the pixels detected (Mat).
	 * @return - Image with small regions filled (Mat).
	 */
	public Mat bwareopen(Mat mask) {
		
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		List<MatOfPoint> littleContours = new ArrayList<MatOfPoint>();
	    Mat result = mask.clone();
	    
	    Imgproc.findContours(mask, contours, new Mat(), 
	        Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
	    
	    if (!contours.isEmpty()) {
	      for (int i = 0; i < contours.size(); i++) {
	        if (Imgproc.contourArea((contours.get(i))) < 7000) {
	          littleContours.add(contours.get(i));
	        }
	     }
	      
	     Imgproc.drawContours(result, littleContours, -1, new Scalar(0),-1);
	     return result;
	    }
	    return null;
		
	}

	/**
	 * Removes the logo of the image.
	 * 
	 * @param image: The image with the logo (Mat).
	 * @return - Image without logo (Mat).
	 */
	public Mat removeLogo(Mat image) {
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
