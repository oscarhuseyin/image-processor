package com.eye.see.image.processing;

import com.eye.see.image.ColorHistogram;
import com.eye.see.image.ImageFeatureVector;
import com.eye.see.image.utils.SearchResultItem;

import junit.framework.TestCase;

public class TestHistorgramEuclideanDistance extends TestCase {

	public static final String IMAGE_FILENAME = "testImages/testimage.jpg";
	
	public void testGlobalHistogramComparison() {
		GlobalColorHistogramProcessor processor = new GlobalColorHistogramProcessor();
		ColorHistogram histogram = processor.calculateGlobalColorHistogram(IMAGE_FILENAME);
		
		ImageFeatureVector vector = new ImageFeatureVector(histogram, null, 0, IMAGE_FILENAME);
		SearchResultItem distance = vector.compareGlobalHistogram(vector);
		
		assertEquals(0.0f, distance.getEuclideanDistance());
	}
}
