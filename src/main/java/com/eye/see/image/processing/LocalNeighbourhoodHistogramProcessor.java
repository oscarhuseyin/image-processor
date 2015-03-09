package com.eye.see.image.processing;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.media.jai.PlanarImage;

import com.eye.see.image.ClusteredImageAttibutes;
import com.eye.see.image.Color;
import com.eye.see.image.ColorHistogram;

/**
 * Class is responsible for creating regional color histograms for images that
 * have been separated into regions.  This classes uses a PlanarImage that will
 * be clustered and then each clusters color histogram will be created.
 *  
 * @author Ozcan Huseyin
 */
public class LocalNeighbourhoodHistogramProcessor extends ColorHistogramProcessor {
	
	/**
	 * Method processes an image given the images file name.
	 * 
	 * @param fileName of the file to calculate local color histograms for
	 * @return 
	 */
	public ColorHistogram[] calculateLocalHNeighbourhoodHistogram(String fileName) { 

		FuzzyCMeansClusteringImageProcessor fcmProcessor = new FuzzyCMeansClusteringImageProcessor();
		// Generate the clustered image 
		ClusteredImageAttibutes clusteredImageAttributes = fcmProcessor.segmentImage(fileName);
		// Load the generated clustered image 
		PlanarImage clusteredImage = loadImage(clusteredImageAttributes.getClusteredImageFileName());
		PlanarImage inputImage = loadImage(clusteredImageAttributes.getInputFileName());
		// Process the clustered image to calculate Local Neighbourhood Histograms
		ColorHistogram[] histograms = calculateLocalHistograms(clusteredImage, inputImage, clusteredImageAttributes);
		
		return histograms;
	}

	private ColorHistogram[] calculateLocalHistograms(PlanarImage clusteredImage, PlanarImage inputImage, ClusteredImageAttibutes imageAttributes) {
		// Get the image dimensions.
		int width = clusteredImage.getWidth();
		int height = clusteredImage.getHeight();
		int numBands = clusteredImage.getSampleModel().getNumBands();

		// Gets the whole clustered image data on memory. 
		int[] clusteredData = getImageData(clusteredImage);
		// Get the whole input image data into memory
		int[] inputData = getImageData(inputImage);

		return processLNHs(clusteredData, inputData, height, width, numBands, imageAttributes);
	}
	
	private ColorHistogram[] processLNHs(int[] clusteredImageData, int[] inputImageData, int height, int width, int numBands, ClusteredImageAttibutes attribs) {
		// A single clustered pixel value
		float[] clusteredPixel = new float[numBands];
		// A single input image pixel value
		float[] inputImagePixel = new float[numBands];
		
		Hashtable<String, ColorHistogram> histograms = createHistogramStore(attribs);
		
		// For each pixel in the image...
		for(int h=0;h<height;h++) {
			for (int w=0;w<width;w++) {
				// Get a pixel (as a single array).
				int index = (h*width+w)*numBands;
				for(int b=0;b<numBands;b++) {
					clusteredPixel[b] = clusteredImageData[index+b];
					inputImagePixel[b] = inputImageData[index+b];
				}
				Color clusterPixel = new Color(clusteredPixel);
				Color inputPixel = new Color(inputImagePixel);
				// Get the color histogram for the cluster pixel value
				ColorHistogram histogram = histograms.get(clusterPixel.toString());
				// Count it
				histogram.countPixel(inputPixel);
			}
		}
		
		return getHistogramsFromStore(histograms);
	}

	private ColorHistogram[] getHistogramsFromStore(Hashtable<String, ColorHistogram> histograms) {
		
		ColorHistogram[] histogramArray = new ColorHistogram[histograms.size()];
		int count = 0;
		for (Enumeration<ColorHistogram> enumeration = histograms.elements(); enumeration.hasMoreElements(); ) {
			ColorHistogram histogram = enumeration.nextElement();
			histogramArray[count++] = histogram;
		}
		return histogramArray;
	}

	private Hashtable<String, ColorHistogram> createHistogramStore(ClusteredImageAttibutes attribs) {
		Color[] centerColors = attribs.getClusterCentersInColor();
		Hashtable<String, ColorHistogram> histograms = new Hashtable<String, ColorHistogram>();
		for (int i = 0; i < centerColors.length; i++) {
			Color color = centerColors[i];
			histograms.put(color.toString(), new ColorHistogram());
		}
		return histograms; 
	}
}	
