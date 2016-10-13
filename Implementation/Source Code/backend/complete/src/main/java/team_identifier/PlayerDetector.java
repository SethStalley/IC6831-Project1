package team_identifier;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PlayerDetector extends GeneralDetector{
	private ArrayList<Mat> processedPlayers;
	
	public PlayerDetector(){
		this.processedPlayers = new ArrayList<Mat>();
	}
	
	@Override
	public void Detect(ArrayList<Mat> frames) {
		ArrayList<Mat> preparedFrames = getPreparedFrames(frames);
		/*
		for(Mat frame:preparedFrames){
			Mat temp = frame;

		}
	*/
	}
	
	/**
     * 
     * @param X: 
     * @return -
     */
	public Mat normalizeFrame(Mat hsv){
		int cols = hsv.cols();
		int rows = hsv.rows();

		Mat dest = new Mat(rows, cols, hsv.type());
			
		for(int row = 0; row<rows; row++){
			for(int col = 0; col<cols; col++){
				double[] channels = hsv.get(row, col);
				double[] normalizedChannels = new double[]{(int)((channels[0]/360) * 255), 0, 0};
                dest.put(row, col, normalizedChannels);
			}			
		}
		return dest;
	}
	
	/**
     * 
     * @param X: 
     * @return -
     */	
	public Mat stdfilt(Mat image){
	  Mat image32f = new Mat();
	  image.convertTo(image32f, CvType.CV_32F);

	  Mat mu = new Mat();
	  Mat mu2 = new Mat();
	  Imgproc.blur(image32f, mu, new Size(3, 3));
      Imgproc.blur(image32f.mul(image32f), mu2, new Size(3, 3));

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
     * @param X: 
     * @return -
     */
	public Mat truncate(Mat image){
		Mat result = new Mat(image.rows(), image.cols(), image.type());

        result = image;
        Imgproc.threshold(image, result, 0, 255, Imgproc.THRESH_OTSU);
        return result;
	}
	
	public ArrayList<Mat> getProcessedPlayers(){
		return this.processedPlayers;
	}

}
