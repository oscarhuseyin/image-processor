package com.eye.see.image;

import java.io.Serializable;

/**
 * Class abstracts color information which is represented in the red,
 * green and blue bands.  This class is immutable.
 * 
 * @author Ozcan Huseyin
 */
public class Color implements Serializable {

	private static final long serialVersionUID = 1637515013061983775L;

	public static final float MAX_PIXEL_BAND_VALUE = 256.0f;

	private final int red;
	private final int green;
	private final int blue;

	private final float redFloat;
	private final float greenFloat;
	private final float blueFloat;
	
	/**
	 * Non default constructor for integer color values.
	 * 
	 * @param red color 
	 * @param green color
	 * @param blue color
	 */
	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		
		this.redFloat = Float.intBitsToFloat(red);
		this.greenFloat = Float.intBitsToFloat(green);
		this.blueFloat = Float.intBitsToFloat(blue);
	}
	
	/**
	 * Non default constructor for floating point color values.
	 * 
	 * @param red color
	 * @param green color
	 * @param blue color
	 */
	public Color(float red, float green, float blue) {
		this.redFloat = red;
		this.greenFloat = green;
		this.blueFloat = blue;
		
		this.red = (int) this.redFloat;
		this.green = (int) this.greenFloat;
		this.blue = (int) this.blueFloat;
	}
	
	/**
	 * Non default constructor for pixel value.
	 * 
	 * @param pixel RGB values
	 */
	public Color(float[] pixel) {
		this(pixel[0], pixel[1], pixel[2]);
	}
	/*
	 * Accessors 
	 */
	
	public int getBlue() {
		return blue;
	}

	public int getGreen() {
		return green;
	}

	public int getRed() {
		return red;
	}

	public float getBlueFloat() {
		return blueFloat;
	}

	public float getGreenFloat() {
		return greenFloat;
	}

	public float getRedFloat() {
		return redFloat;
	}
	
	/**
	 * Overridden equals method
	 */
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Color)) return false;
		// Convert type
		Color color = (Color)o;
		if (this.red == color.getRed() && 
				this.green == color.getGreen() &&
				this.blue == color.getBlue()) return true;
		// Color instance being compared is not equal
		return false;
	}
	
	public String toString() {
		return "[" + this.red + ", " + this.green + ", " + this.blue + "]";
	}
}
