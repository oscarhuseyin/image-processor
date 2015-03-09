/*
 * Created on Jun 27, 2005
 * @author Rafael Santos (rafael.santos@lac.inpe.br)
 * 
 * Part of the Java Advanced Imaging Stuff site
 * (http://www.lac.inpe.br/~rafael.santos/Java/JAI)
 * 
 * STATUS: Complete.
 * 
 * Redistribution and usage conditions must be done under the
 * Creative Commons license:
 * English: http://creativecommons.org/licenses/by-nc-sa/2.0/br/deed.en
 * Portuguese: http://creativecommons.org/licenses/by-nc-sa/2.0/br/deed.pt
 * More information on design and applications are on the projects' page
 * (http://www.lac.inpe.br/~rafael.santos/Java/JAI).
 */
package com.eye.see.image.processing;

import java.util.Calendar;
import java.util.Random;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.eye.see.image.ClusteredImageAttibutes;

/**
 * This application demonstrates the usage of the cluster validity measures that
 * can be calculated by the FuzzyCMeansImageClustering algorithm. It attempts to
 * find an optimal number of clusters for a particular image.
 */
public class FuzzyCMeansClusteringImageProcessor extends ImageProcessor {

	public static final int MAX_CLUSTERS = 6;

	public static final String OUTPUT_IMAGE_TYPE = "TIFF";

	public static final String TEMP_SEGMENTED_IMAGE_OUTPUT_DIRECTORY = "/opt/EyeSee/TestImages/SegmentedImages/";

	/**
	 * The application entry point.
	 */
	public ClusteredImageAttibutes segmentImage(String inputImageFileName) {

		PlanarImage inputImage = loadImage(inputImageFileName);
		int clusters = calculateOptimalClusters(inputImage);

		return generateClusteredImage(inputImage, inputImageFileName, clusters);
	}

	/**
	 * Methods runs the fuzzy c-means pixel classification algorithm to
	 * determine the best segmentation for the image. Returns an integer that
	 * defines the optimal clusters for the image.
	 * 
	 * @param inputImage
	 *            to be processed
	 * @return integer that is the optimal clusters for the image.
	 */
	private int calculateOptimalClusters(PlanarImage inputImage) {
		// Create several tasks, each with a different number of clusters.
		double partitionCoefficient, partitionEntropy, compactnessAndSeparation;
		double bestPartitionCoefficient = Double.MIN_VALUE;
		double bestPartitionEntropy = Double.MAX_VALUE;
		double bestCompactnessAndSeparation = Double.MAX_VALUE;
		int bestByPartitionCoefficient = 1, bestByPartitionEntropy = 1, bestByCompactnessAndSeparation = 1;

		System.out
				.println("+--------+-------------+-------------+-------------+");
		System.out
				.println("|Clusters| Part.Coeff. |Part.Entropy |Compact.&Sep.|");

		for (int c = 2; c < MAX_CLUSTERS; c++) {
			// Create the task.
			FuzzyCMeansClustering task = new FuzzyCMeansClustering(inputImage,
					c);

			task.run(); // Run it (without threading).

			// Get the resulting validity measures.�
			partitionCoefficient = task.getPartitionCoefficient();
			partitionEntropy = task.getPartitionEntropy();
			compactnessAndSeparation = task.getCompactnessAndSeparation();

			// See which is the best so far.
			if (partitionCoefficient > bestPartitionCoefficient) {
				bestPartitionCoefficient = partitionCoefficient;
				bestByPartitionCoefficient = c;
			}
			if (partitionEntropy < bestPartitionEntropy) {
				bestPartitionEntropy = partitionEntropy;
				bestByPartitionEntropy = c;
			}
			if (compactnessAndSeparation < bestCompactnessAndSeparation) {
				bestCompactnessAndSeparation = compactnessAndSeparation;
				bestByCompactnessAndSeparation = c;
			}

			// Print a simple report.
			System.out.println("|   "
					+ String.format("%2d", new Object[] { new Integer(c) })
					+ "   |"
					+ String.format("%13.6f|%13.6f|%13.6f|", new Object[] {
							new Double(partitionCoefficient),
							new Double(partitionEntropy),
							new Double(compactnessAndSeparation) }));
		}

		System.out
				.println("+--------+-------------+-------------+-------------+");

//		int optimalClusters = (int) (bestByCompactnessAndSeparation
//				+ bestByPartitionCoefficient + bestByPartitionEntropy) / 3;
		int optimalClusters = bestByCompactnessAndSeparation;
		
		System.out
				.println("according to all segmentation results optimal clusters are : "
						+ bestByCompactnessAndSeparation);

		return optimalClusters;
	}

	/**
	 * 
	 * @param inputImageFileName 
	 * @param rankedImage
	 * @return
	 */
	private ClusteredImageAttibutes generateClusteredImage(
			PlanarImage inputImage, String inputImageFileName, int clusters) {
		Random random = new Random();
		// Create a random filename
		String fileName = new String(new Long((long) random.nextInt()
				+ Calendar.getInstance().getTimeInMillis()).toString());
		// Create the task.
		FuzzyCMeansClustering task = new FuzzyCMeansClustering(inputImage,
				clusters);
		// The the image processor
		task.run();

		ClusteredImageAttibutes attributes = new ClusteredImageAttibutes(inputImageFileName,
				TEMP_SEGMENTED_IMAGE_OUTPUT_DIRECTORY + fileName + "."
						+ OUTPUT_IMAGE_TYPE, task.getClusterCenters());

		// Save the image on a file.
		JAI.create("filestore", task.getRankedImage(0),
				TEMP_SEGMENTED_IMAGE_OUTPUT_DIRECTORY + fileName + "."
						+ OUTPUT_IMAGE_TYPE, OUTPUT_IMAGE_TYPE);

		return attributes;
	}

	/**
	 * The application entry point, which will need some parameters, described
	 * below, and which must be passed in the command line: - The input file
	 * name (string, existing file) - The output file name (string, file will be
	 * created/overwriten) - The desired number of classes (integer) - The
	 * maximum number of iterations (integer) - The fuzziness factor (floating
	 * point) - The epsilon value (floating point)
	 *
	public void generateClusteredImage(PlanarImage inputImage, int clusters, String outputDirectory, String outputFilename) {

		FuzzyCMeansClustering task = new FuzzyCMeansClustering(
				inputImage, clusters, 50, 2, 1.05f);

		task.run(); // Run it (without threading).
		// Get the resulting image.
		PlanarImage outputImage = task.getRankedImage(0);
		
		float[][] clusterPixelValues = task.getClusterCenters();
		
		for (int i = 0 ; i < clusterPixelValues.length ; i++) {
			for (int j = 0 ; j < clusterPixelValues[i].length ; j++) {
				System.out.println("Cluster [" + i + "] = " + clusterPixelValues[i][j]);
			}
		}
		
		// Save the image on a file.
		JAI.create("filestore", outputImage,
				outputDirectory + outputFilename, "TIFF");
	} */
}