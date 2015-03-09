package com.eye.see.image.utils;

import com.eye.see.image.ImageFeatureVector;

/**
 * Simple immutable class that represents the comparison result of a
 * image search.
 * 
 * @author Ozcan Huseyin
 */
public class SearchResultItem {
	private final float euclideanDistance;
	private final ImageFeatureVector featureVector;

	public SearchResultItem(float euclideanDistance, ImageFeatureVector featureVector) {
		this.featureVector = featureVector;
		this.euclideanDistance = euclideanDistance;
	}
	
	public float getEuclideanDistance() {
		return euclideanDistance;
	}

	public ImageFeatureVector getImageFeatureVector() {
		return featureVector;
	}
}
