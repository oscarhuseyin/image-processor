package com.eye.see.image.processing;

import com.eye.see.image.ColorHistogram;

import junit.framework.TestCase;

public class TestGlobalColorHistogramProcessor extends TestCase {
	
	public static final String imageFilename = "testImages/testimage.jpg";
	
	public void testColorHistogram() {
		GlobalColorHistogramProcessor processor = new GlobalColorHistogramProcessor();
		ColorHistogram histogram = processor.calculateGlobalColorHistogram(imageFilename);
		assertNotNull(histogram);
		System.out.println(histogram.toString());
	}
}
