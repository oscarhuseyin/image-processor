package com.eye.see.image;

import java.io.Serializable;


/**
 * Class implements a color histogram which can have each color component quantized
 * to an arbirary number.  The color histogram can only represent the RGB model
 * only.  Statistical calculation are also implemented to provide a 1D representation
 * for ease of Euclidean calculations.
 * 
 * @author Ozcan Huseyin
 */
public class ColorHistogram implements Serializable {

	private static final long serialVersionUID = -246315844175881655L;
	
	public static final int DEFAULT_BAND_QUANTIZATION_NO = 4;
	// 3D Color Bin used to store distribution of quantized color pixel data
	private float[][][] colorBins;
	
	// Non default levels used for quantization
	private final int redLevels;
	private final int greenLevels;
	private final int blueLevels;
	
	// Pixels counted
	private int pixelsCounted = 0;
	
	/**
	 * Default constuctor sets global histogram quantization to same value for each
	 * band.
	 */
	public ColorHistogram() {
		// Set the levels
		this.redLevels = DEFAULT_BAND_QUANTIZATION_NO;
		this.greenLevels = DEFAULT_BAND_QUANTIZATION_NO;
		this.blueLevels = DEFAULT_BAND_QUANTIZATION_NO;
		
		// Create the color histogram
		colorBins = new float[DEFAULT_BAND_QUANTIZATION_NO][DEFAULT_BAND_QUANTIZATION_NO][DEFAULT_BAND_QUANTIZATION_NO];
	}
	
	/**
	 * Non default constructor will create a quantized color histogram for the
	 * representation of an images color distribution.
	 * 
	 * @param redLevels the number of red levels to quantize
	 * @param greenLevels the number of green levels to quantize
	 * @param blueLevels the number of blue levels to quantize
	 */
	public ColorHistogram(int redLevels, int greenLevels, int blueLevels) {	
		if (redLevels != greenLevels && redLevels != blueLevels) {
			 throw new IllegalArgumentException("Illegal values for red, green and blue quantization levels.  They must all be equal.");
		}
		
		// Remember the levels (same levels)
		this.redLevels = redLevels;
		this.greenLevels = redLevels;
		this.blueLevels = redLevels;

		// Create the color histogram
		colorBins = new float[redLevels][greenLevels][blueLevels];
		
		// Initialize the color bins (just in case)
		initialize();
	}
	
	/**
	 * Initialize the color histogram to guarantee the values are set to 0;
	 */
	private void initialize() {
		for (int i=0; i < redLevels; i++) {
			for (int j=0; j < greenLevels; j++) {
				for (int k=0; k < blueLevels; k++) {
					colorBins[i][j][k] = 0.0f;
				}				
			}			
		}
	}
	
	/**
	 * Process the pixel and represent in color histogram.
	 * 
	 * @param pixelValue
	 */
	public void countPixel(Color pixel) {
		Color quantized = quantize(pixel);
		
		// Count the color
		colorBins[quantized.getRed()][quantized.getGreen()][quantized.getBlue()] += 1.0f;
		
		// Count the pixel in the image that the histogram is being calculated for
		pixelsCounted++;
	}
	
	/**
	 * Method returns the statistical distribution of the color histogram in a single dimensional array.
	 * 
	 * @return float color distribution for the counted pixels
	 */
	public float[] getStatisticalDistribution() {
		
		// Create the 1D distribution
		float[] distribution = new float[getRedLevels() * getGreenLevels() * getBlueLevels()];
		int dimension = 0;
		// Prepare the distribution
		for (int i=0; i < redLevels; i++) {
			for (int j=0; j < greenLevels; j++) {
				for (int k=0; k < blueLevels; k++) {
					distribution[dimension++] = colorBins[i][j][k] / getPixelsCounted();
				}				
			}			
		}
		
		return distribution;
	}

	/**
	 * Method returns the statistical distribution of the color histogram in a single dimentional array.
	 * 
	 * @return float color distribution for the counted pixels
	 */
	public float[] get1DHistogram() {
		
		// Create the 1D distribution
		float[] distribution = new float[getRedLevels() * getGreenLevels() * getBlueLevels()];
		int dimension = 0;
		// Prepare the distribution
		for (int i=0; i < redLevels; i++) {
			for (int j=0; j < greenLevels; j++) {
				for (int k=0; k < blueLevels; k++) {
					distribution[dimension++] = colorBins[i][j][k];
				}				
			}			
		}
		
		return distribution;
	}
	
	/**
	 * Method quantizes the pixel to the new level required for the ColorHistorgram.
	 */
	private Color quantize(Color pixel) {
		Color normalized = normalize(pixel);
		
		return new Color(normalized.getRedFloat() * (float) getRedLevels(),
				normalized.getGreenFloat() * (float) getGreenLevels(),
				normalized.getBlueFloat() * (float) getBlueLevels());
	}
	
	/**
	 * Method normalizes the pixel so the pixel value is in the range 0->1.
	 * 
	 * @param pixel
	 */
	private Color normalize(Color pixel) {
		return new Color(pixel.getRedFloat() / Color.MAX_PIXEL_BAND_VALUE,
				pixel.getGreenFloat() / Color.MAX_PIXEL_BAND_VALUE,
				pixel.getBlueFloat() / Color.MAX_PIXEL_BAND_VALUE);
	}

	public int getBlueLevels() {
		return blueLevels;
	}

	public int getGreenLevels() {
		return greenLevels;
	}

	public int getRedLevels() {
		return redLevels;
	}

	public int getPixelsCounted() {
		return pixelsCounted;
	}

	/**
	 * Override the toString from Object
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("Color Histrogram:\n");
		for (int i=0; i < redLevels; i++) {
			for (int j=0; j < greenLevels; j++) {
				for (int k=0; k < blueLevels; k++) {
					buff.append(colorBins[i][j][k] + " ");
				}
				buff.append("\n");
			}
			buff.append("\n");
		}
		return buff.toString();
	}

}
