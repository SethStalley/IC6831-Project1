package teamidentifier;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 */
public class PlayerDetector extends GeneralDetector {
	private ArrayList<Mat> processedPlayers;

	public PlayerDetector() {
		this.processedPlayers = new ArrayList<Mat>();
	}

	/**
	 * Method Detect.
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
			
			double umbral = graythresh(temp);
		    Imgproc.threshold(temp, temp, umbral, 255, Imgproc.THRESH_BINARY);			
			
		    temp = truncate(temp);
			temp = imfill(temp, new Point(0, temp.height()  * 0.70));
			temp.convertTo(temp, 0);
			this.processedPlayers.add(temp);
		}

	}

	/**
	 * @param image Mat
	 * @return -
	 */
	private Mat normalizeImage(Mat image) {
		Core.normalize(image, image, 0, 255, Core.NORM_MINMAX);
		return image;
	}
	
	
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
	 * @param image Mat
	 * @return - Image corresponding to the local variance
	 */
	private Mat stdfilt(Mat image) {
		Mat image32f = new Mat();
		image.convertTo(image32f, CvType.CV_32F);

		Mat mu = new Mat();
		Mat mu2 = new Mat();
		Imgproc.blur(image32f, mu, new Size(11,11));
		Imgproc.blur(image32f.mul(image32f), mu2, new Size(11, 11));

		Mat sigma = new Mat();
		Mat src = new Mat();

		Core.subtract(mu2, mu.mul(mu), src);
		Core.sqrt(src, sigma);

		Mat image2 = new Mat();
		sigma.convertTo(image2, image.type());

		return image2;
	}

	/**
	 * 
	 * @param image  Mat
	 * @return -
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
