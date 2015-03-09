package com.eye.see.image;

import java.io.Serializable;

import com.eye.see.image.utils.SearchResultItem;

/**
 * Encapsulates information about a single image.
 * 
 * @author ozcan
 */
public class ImageFeatureVector implements Serializable {

	private static final long serialVersionUID = 4532729617229753201L;
	
	private int regions;
	private ColorHistogram[] localHistograms;
	private ColorHistogram globalHistogram;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ImageFeatureVector(ColorHistogram globalHistogram, ColorHistogram[] localHistograms, int regions, String fileName) {
		this.globalHistogram = globalHistogram;
		this.localHistograms = localHistograms;
		this.fileName = fileName;
	}
	
	public ColorHistogram getGlobalHistogram() {
		return globalHistogram;
	}
	public void setGlobalHistogram(ColorHistogram globalHistogram) {
		this.globalHistogram = globalHistogram;
	}
	public ColorHistogram[] getLocalHistograms() {
		return localHistograms;
	}
	public void setLocalHistograms(ColorHistogram[] localHistograms) {
		this.localHistograms = localHistograms;
	}
	public int getRegions() {
		return regions;
	}
	public void setRegions(int regions) {
		this.regions = regions;
	}
	
	/**
	 * The overriden toString from Object
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("-------------------------------------------------------");
		buffer.append("\nClusters = " + regions + "\n");
		buffer.append("-------------------------------------------------------");
		buffer.append("\nGlobal Histogram: \n" + this.globalHistogram.toString());
		buffer.append("-------------------------------------------------------");
		buffer.append("\nLocal Histograms: \n");
		for (int i = 0; i < this.localHistograms.length; i++) {
			ColorHistogram localHistogram = this.localHistograms[i];
			buffer.append(localHistogram.toString());
		}
		buffer.append("-------------------------------------------------------");
		return buffer.toString();
	}
	
	/**
	 * Calculates the similarity of the instance of ColorHistogram in this class
	 * to the ImageFeatureVector passed in.
	 * 
	 * @param featureFector to perform global histogram similarity
	 * @return the Euclidean distance
	 */
	public SearchResultItem compareGlobalHistogram(ImageFeatureVector featureFector) {
		float euclideanDistance = 0.0f;
		float[] thisHistogram = this.globalHistogram.getStatisticalDistribution();
		float[] otherHistogram = featureFector.getGlobalHistogram().getStatisticalDistribution();
		for (int i = 0; i < this.globalHistogram.getStatisticalDistribution().length; i++) {
			euclideanDistance += Math.abs(thisHistogram[i] - otherHistogram[i]);
		}
		return new SearchResultItem(euclideanDistance, featureFector); 
	}
	
	/**
	 * Calculates the similarity of the instance of ColorHistogram in this class
	 * to the ImageFeatureVector passed in.
	 * 
	 * @param featureFector to perform global histogram similarity
	 * @return the Euclidean distance
	 */
	public SearchResultItem compareLocalHistogram(ImageFeatureVector featureFector) {
		float euclideanDistance = 0.0f;
		
		for (int i = 0; i < this.localHistograms.length; i++) {
			ColorHistogram queryHistogram = this.localHistograms[i];
			float[] queryHistogramDist = queryHistogram.getStatisticalDistribution();
			
			ColorHistogram[] targetHistogams = featureFector.getLocalHistograms();
			for (int j = 0; j < targetHistogams.length; j++) {
				float[] targetHistogramDist = targetHistogams[j].getStatisticalDistribution();
				// Calculate Euclidean distance on query histograms region i'th region with db images k'th region
				for (int k = 0; k < targetHistogramDist.length; k++) {
					euclideanDistance += Math.abs(queryHistogramDist[k] - targetHistogramDist[k]);
				}
			}
			
		}
		
		return new SearchResultItem(euclideanDistance, featureFector);
	}
}
