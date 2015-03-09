package com.eye.see.image.processing;

import junit.framework.TestCase;

import com.eye.see.image.ColorHistogram;
import com.eye.see.image.ImageFeatureVector;
import com.eye.see.image.utils.SearchResultItem;

public class TestLocalColorHistogramProcessor extends TestCase {
	public static final String imageFilename = "testImages/testimage.jpg";
	
	public void testLocalColorHistogram() {
		LocalNeighbourhoodHistogramProcessor processor = new LocalNeighbourhoodHistogramProcessor();
		ColorHistogram[] histograms = processor.calculateLocalHNeighbourhoodHistogram(imageFilename);
		
		ImageFeatureVector featureVector = new ImageFeatureVector(null, histograms, histograms.length, imageFilename);
		
		SearchResultItem item = featureVector.compareLocalHistogram(featureVector);
		//assertEquals(0.0f, item.getEuclideanDistance());
		
	}
}
