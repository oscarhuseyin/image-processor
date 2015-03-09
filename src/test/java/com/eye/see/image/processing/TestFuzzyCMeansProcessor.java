package com.eye.see.image.processing;

import junit.framework.TestCase;

import com.eye.see.image.ClusteredImageAttibutes;

public class TestFuzzyCMeansProcessor extends TestCase {
	
	public static final String imageFilename = "testImages/testimage.jpg";
	
	public void testFuzzyCMeansClustering() {
		FuzzyCMeansClusteringImageProcessor processor = new FuzzyCMeansClusteringImageProcessor();
		ClusteredImageAttibutes attributes = processor.segmentImage(imageFilename);
		assertNotNull(attributes);
		System.out.println(attributes.toString());
	}
}
