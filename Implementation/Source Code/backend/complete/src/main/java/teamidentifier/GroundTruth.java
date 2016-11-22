package teamidentifier;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.*;
import org.opencv.core.*;

public class GroundTruth {
	
	/* Dice porcentage of every frame of the video*/
	private ArrayList<Double> dicePorcentages;
	
	/**
	 * Compare the result video with the ground truth
	 * @param pVideoPath
	 * @param pGroundTruthPath
	 * @throws FileNotFoundException
	 */
	public double compareWithGroundTruth(String pVideoPath, String pGroundTruthPath) throws FileNotFoundException{
		double avarageSimilarity = 0;
		
		Video videoProcessor = new Video(pVideoPath);
		Video groundTruthProcessor = new Video(pGroundTruthPath);
		
		ArrayList<Mat> videoFrames = videoProcessor.readVideo();
		ArrayList<Mat> groundTruthFrames = groundTruthProcessor.readVideo();
		
		this.dicePorcentages = new ArrayList<Double>();
		
		double diceIndex;
		
		for(int frameIndex = 0; frameIndex < videoFrames.size(); frameIndex++){
			Mat videoFrame = videoFrames.get(frameIndex);
			Mat groundTruthFrame = groundTruthFrames.get(frameIndex);
			
			diceIndex = calculateDiceIndex(videoFrame, groundTruthFrame);
			
			dicePorcentages.add(diceIndex);			
		}
		for(int dicePosition = 0; dicePosition < dicePorcentages.size(); dicePosition++){
			avarageSimilarity += dicePorcentages.get(dicePosition);
		}
		avarageSimilarity = (avarageSimilarity/dicePorcentages.size());
		
		return avarageSimilarity;
	}
	
	/**
	 * Calculates the dice index between two matrix
	 * @param pVideoFrame  Matrix of a frame from the video
	 * @param pGroundTruthFrame Matrix of a frame from the ground truth
	 * @return double, represents the dice value between the video frame and the ground truth frame
	 */
	public double calculateDiceIndex(Mat pVideoFrame, Mat pGroundTruthFrame){
		
		Imgproc.cvtColor(pGroundTruthFrame, pGroundTruthFrame,Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(pVideoFrame, pVideoFrame,Imgproc.COLOR_BGR2GRAY);
		
		double playerPixelsVideo = Core.countNonZero(pVideoFrame);
		double playerPixelsGroundTruth = Core.countNonZero(pGroundTruthFrame);
		
		Mat intersectionMat = new Mat();
		
		Core.bitwise_and(pVideoFrame, pGroundTruthFrame, intersectionMat);
		
		double playerPixelsIntersection = Core.countNonZero(intersectionMat);
		
		//Dice Index Formula = (Number of Matrix * Intersection) / (Number elements of matrix 1 + Number elements matrix 2)
		double diceIndex = (2 * playerPixelsIntersection ) / (playerPixelsVideo + playerPixelsGroundTruth);
		
		return diceIndex;
	}

}


