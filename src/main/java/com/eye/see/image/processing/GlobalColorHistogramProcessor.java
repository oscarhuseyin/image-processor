package com.eye.see.image.processing;

import java.awt.image.Raster;

import javax.media.jai.PlanarImage;

import com.eye.see.image.ColorHistogram;
import com.eye.see.image.utils.ImageProcessingException;

/**
 * Calculates the Global Histogram of a supplied image.  This defines the statistical
 * distribution of the color for an image and is to be used in determining image
 * similatiry.
 * 
 * @author Ozcan Huseyin
 */
public class GlobalColorHistogramProcessor extends ColorHistogramProcessor {
	
	/**
	 * Method calculates a global color histogram for the image.
	 * 
	 * @param planarImage image to calculate the histogram for.
	 * @return color histogram of the image.
	 */
	public ColorHistogram calculateGlobalColorHistogram(String fileName) throws ImageProcessingException {

		PlanarImage planarImage = loadImage(fileName);
		// Get the image dimensions.
		int width = planarImage.getWidth();
		int height = planarImage.getHeight();
		int numBands = planarImage.getSampleModel().getNumBands();

		// Gets the whole image data on memory.
		int[] inputData = getImageData(planarImage);

		return processColorHistogram(inputData, height, width, numBands);
	}
}
