package com.eye.see.image.processing;

import java.awt.image.Raster;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.eye.see.image.utils.ImageProcessingException;

/**
 * Base class for image processors to extend from as it provides utility methods
 * that create image library specific objects like the PlanarImage.
 *  
 * @author Ozcan Huseyin
 */
public class ImageProcessor {
	
	/**
	 * Load an image given a filename.
	 * 
	 * @param fileName of image to load
	 * @return a PlanarImage loaded from file
	 */
	public PlanarImage loadImage(String fileName) {
		try {
			return JAI.create("fileload", fileName);
		} catch (Exception e) {
			throw new ImageProcessingException();
		}
	}
	
	/**
	 * Access all pixel values in a planar image.
	 * 
	 * @param inputImage PlanarImage 
	 * @return all pixel values for image
	 */
	public int[] getImageData(PlanarImage inputImage) {
		// Get the image dimensions.
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		int numBands = inputImage.getSampleModel().getNumBands();
		  
		// Gets the raster for the input image.
		Raster raster = inputImage.getData();
		  
		// Gets the whole image data on memory. Get memory for a single pixel too.
		int[] inputData = new int[width*height*numBands];
		  
		// Copy the image data for processing
		raster.getPixels(0,0,width,height,inputData);
		
		return inputData;
	}
}
