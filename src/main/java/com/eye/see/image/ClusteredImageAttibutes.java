package com.eye.see.image;

/**
 * Simple attribute class the contains information about the clustered image.
 * 
 * @author Ozcan Huseyin
 */
public class ClusteredImageAttibutes {

	private final String inputFileName;
	private final String clusteredImageFileName;
	
	private final float[][] clusterCenters;
	
	/**
	 * Non-default constructor.
	 * 
	 * @param inputFileName used to segment image
	 * @param clusteredImageFileName filename of the clustered image
	 * @param clusteredCenters pixel values of the cluster centers
	 */
	public ClusteredImageAttibutes(String inputFileName, String clusteredImageFileName, float[][] clusteredCenters) {
		this.inputFileName = inputFileName;
		this.clusteredImageFileName = clusteredImageFileName;
		this.clusterCenters = clusteredCenters;
	}

	/**
	 * Method converts the cluster centers to an array of Color objects.
	 * 
	 * @return array of Color objects from the cluster centers
	 */
	public Color[] getClusterCentersInColor() {
		Color[] colors = new Color[getClusterCenters().length];
		// Convert all cluster centers to the Color object
		for (int i = 0; i <colors.length; i++) {
			colors[i] = new Color(getClusterCenters()[i]);
		}
		
		return colors;
	}
	
	public float[][] getClusterCenters() {
		return clusterCenters;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public String getClusteredImageFileName() {
		return clusteredImageFileName;
	}
	
	/**
	 * Returns a string representation of
	 */
	public String toString() {
		StringBuffer clusterdCenters = new StringBuffer();
		for (int i = 0; i < this.clusterCenters.length; i++) {
			StringBuffer buff = new StringBuffer();
			buff.append("[");
			buff.append(this.clusterCenters[i]);
			buff.append("]");
			clusterdCenters.append(buff);
		}
		return "Clustered Image Attributes:\nFilename = " + this.inputFileName 
				+ "\nClustered Image Filename = " + this.clusteredImageFileName
				+ "\nCluster centers = " + clusterdCenters.toString();
	}
}
