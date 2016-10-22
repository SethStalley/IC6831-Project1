/*
 * @author Lucy Chaves - Seth Stalley
 * @version v1.1.1
 */
package teamidentifier;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * The purpose of this class is to detect the players of the soccer field.
 * The whole algorithm was taken from the Project Specification written by Saul Calderón.
 * Players must have their uniform different to green.
 * The soccer field must be green.
 */
public class PlayerDetector extends GeneralDetector {
	private ArrayList<Mat> processedPlayers;
	
	/**
  	* Instantiates a new player detector.
    	*
  	* 
  	*/
	public PlayerDetector() {
		this.processedPlayers = new ArrayList<Mat>();
	}

	/**
	 * Detects players in the soccer field.
	 * 
	 * @param frames ArrayList<Mat>
	 */
	@Override
	public void Detect(ArrayList<Mat> frames) {
		for (Mat frame : frames) {
			Mat temp = getHueChannel(convertRgb2Hsv(frame));
			temp = normalizeImage(temp);
			temp = stdfilt(temp);		
			temp = dilate(temp);
			temp = truncate(temp);
			
			double umbral = graythresh(temp);
		   	Imgproc.threshold(temp, temp, umbral, 255, Imgproc.THRESH_BINARY);					
		   	temp = imfill(temp, new Point(0, temp.height()  * 0.70));
			
			this.processedPlayers.add(temp);
		}
	}

  	/**
  	 * Normalize an image between 2 values
  	 * Uses OpenCV function: http://docs.opencv.org/java/2.4.2/org/opencv/core/Core.html
  	 * @param The Image to be normalized, must be in one channel (Mat).
  	 * @return The normalized OpenCV image (Mat).
  	 */
	private Mat normalizeImage(Mat image) {
		Core.normalize(image, image, 0, 255, Core.NORM_MINMAX);
		return image;
	}
	
	/**
  	* Gets optimum threshold of a binary image.
   	* Uses opencv function: http://docs.opencv.org/java/2.4.9/org/opencv/imgproc/Imgproc.html
   	* @param The image to get its optimum threshold. In binary format (Mat).
   	* @return A double with the optimum threshold.
   	*/	
	private double graythresh(Mat mat) {
	   Mat clone = mat.clone();
	   clone.convertTo(clone, CvType.CV_8UC1);
	   double umbral = Imgproc.threshold(clone, clone, 0, 255, 
	       Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
	   return umbral;
	}

	/**
	 * Calculates local variance.
	 * 
	 * @param The image to get its local variance (Mat).
	 * @return - Image corresponding to the local variance (Mat).
	 */
	private Mat stdfilt(Mat image) {
		Mat image32f = new Mat();
		image.convertTo(image32f, CvType.CV_32F);

		Mat mu = new Mat();
		Mat mu2 = new Mat();
		Imgproc.blur(image32f, mu, new Size(9,9));
		Imgproc.blur(image32f.mul(image32f), mu2, new Size(9, 9));

		Mat sigma = new Mat();
		Mat src = new Mat();

		Core.subtract(mu2, mu.mul(mu), src);
		Core.sqrt(src, sigma);

		Mat image2 = new Mat();
		sigma.convertTo(image2, image.type());

		return image2;
	}

	/** 
	 * Truncates an image.
	 * Uses opencv function: http://docs.opencv.org/java/2.4.9/org/opencv/imgproc/Imgproc.html
	 * @param The image to be truncated (Mat).
	 * @return - The truncated image (Mat).
	 */
	public Mat truncate(Mat image) {
		Imgproc.threshold(image, image, 0, 255, Imgproc.THRESH_OTSU);
		return image;
	}

	/**
	 * Method getProcessedPlayers.
	 * 
	 * @return ArrayList<Mat>
	 */
	public ArrayList<Mat> getProcessedPlayers() {
		return this.processedPlayers;
	}
}
