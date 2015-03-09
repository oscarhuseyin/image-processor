package com.eye.see.image.processing;

import com.eye.see.image.Color;
import com.eye.see.image.ColorHistogram;

/**
 * Base class for all that require a calculation of a global color histogram
 * 
 * @author Ozcan Huseyin	
 */
public abstract class ColorHistogramProcessor extends ImageProcessor {

	/**
	 * Constructs a color histogram given image data, dimensions of the image and number of bands
	 * representing the image.
	 * 
	 * @param imageData pixel data to be used to create the color histogram
	 * @param height of the image
	 * @param width of the image
	 * @param numBands number of bands.
	 * @return a ColorHistogram object
	 */
	protected ColorHistogram processColorHistogram(int[] imageData, int height, int width, int numBands) {

		// The color histogram
		ColorHistogram colorHistogram = new ColorHistogram();
		// A single pixel value
		float[] pixel = new float[numBands];
		
		// For each pixel in the image...
		for(int h=0;h<height;h++) {
			for(int w=0;w<width;w++) {
				// Get a pixel (as a single array).
				int index = (h*width+w)*numBands;
				for(int b=0;b<numBands;b++) {
					pixel[b] = imageData[index+b];
				}
				Color colorPixel = new Color(pixel);
	            
				// Tally the pixel inforation
				colorHistogram.countPixel(colorPixel);
			}
		}
		return colorHistogram;
	}
}
