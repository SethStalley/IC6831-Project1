package team_identifier;

import java.util.ArrayList;

import org.opencv.core.Mat;

public interface IImageProcessor {

		public Mat convertRgb2Hsv(Mat frame);
		public Mat getHueChannel(Mat hsv);
		public Mat getRange(Mat image);
		public ArrayList<Mat> getPreparedFrames(ArrayList<Mat> frames);

}
