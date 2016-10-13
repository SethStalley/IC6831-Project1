package team_identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public abstract class GeneralDetector{
			
	public abstract void Detect(ArrayList<Mat> frames);
	
	/**
	* Convert RGB Mat to HSV
    * @param X: RGB Image (Mat)
	* @return - HSV Image (Mat)
	*/
	protected Mat convertRgb2Hsv(Mat frame) {
		Mat frameHsv = new Mat(frame.height(), frame.width(), frame.type());
		Imgproc.cvtColor(frame, frameHsv, Imgproc.COLOR_RGB2HSV);
		return frameHsv;
	}
			
	/**
	* Extract Hue Channel from HSV image
	* @param X: HSV Image (Mat)
	* @return - Hue Channel of the image (Mat)
	*/
	protected Mat getHueChannel(Mat hsv){
		List<Mat> channels = new Vector<Mat>();
		Core.split(hsv, channels);
				
		return channels.get(0);
	}

	/**
     * 
     * @param X: 
     * @return -
     */
	protected Mat dilate(Mat hsv){
		int dilation_size = 2;
		Mat result = new Mat(hsv.rows(), hsv.cols(), hsv.type());
		
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2*dilation_size + 1, 2*dilation_size+1));
        Imgproc.dilate(hsv, result, kernel);
    
        return result;

	}
	
	/**
     * Find and fill the contours of the players in the field.
     * @param: Image to be filled (Mat).
     * @return - Image with the holes filled (Mat).
     */
	protected Mat imfill(Mat mask){
		Mat result, hierarchy, destiny;
		result = new Mat(mask.rows(), mask.cols(), mask.type());
		destiny = new Mat(mask.rows(), mask.cols(), mask.type());
		hierarchy = new Mat();
		
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Core.bitwise_not(mask, result);
		Imgproc.findContours(result, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
		for(MatOfPoint contour:contours){
			ArrayList<MatOfPoint> list = new ArrayList<>();
			list.add(contour);
			Imgproc.drawContours(result, list, 0, new Scalar(255),-1);
		}
		Core.bitwise_not(result, destiny);
		return destiny;
	}
	

}
