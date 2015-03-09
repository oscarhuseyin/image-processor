package com.eye.see.image.utils;

import java.io.IOException;
import java.util.ArrayList;

import com.eye.see.image.ColorHistogram;
import com.eye.see.image.ImageFeatureVector;
import com.eye.see.image.processing.GlobalColorHistogramProcessor;
import com.eye.see.image.processing.LocalNeighbourhoodHistogramProcessor;

/**
 * Class performs all the searching operations in the database of feature vectors.
 * 
 * @author Ozcan Huseyin
 */
public class ImageSearcher {
	
	public static final int SEARCH_RESULTS_SIZE = 30;
	
	public static final int SEARCH_TYPE_GLOBAL = 0;
	
	public static final int SEARCH_TYPE_LOCAL = 1;
	
	private static ImageSearcher instance = null;
	
	private ImageDatabaseBuilder builder = new ImageDatabaseBuilder();
	
	private ArrayList<ImageFeatureVector> featureVectors;
	
	/**
	 * Private class to restrict instantiation, eg. Prototype.
	 */
	private ImageSearcher() {
		try {
			featureVectors = builder.read();
		} catch (IOException e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Singleton method to access instance of class.
	 * 
	 * @return ImageSearcher instance
	 */
	public static ImageSearcher getInstance() {
		if (instance == null) {
			instance = new ImageSearcher();
			return instance;
		}
		return instance;
	}
	
	/**
	 * Search the database of feature vectors comparing by global histogram.
	 * 
	 * @param fileName that is the image search query.
	 * @return array of search results with the first position containing the best similarity result and Nth 
	 * 		   position being the least similar.
	 * @throws Exception 
	 */
	public SearchResultItem[] globalHistogramSearch(String fileName) throws Exception {
		
		GlobalColorHistogramProcessor processor = new GlobalColorHistogramProcessor();
		ImageFeatureVector featureVector = new ImageFeatureVector(processor.calculateGlobalColorHistogram(fileName), null, 2, fileName);
		
		return searchHistorgamsInDatabase(featureVector, SEARCH_TYPE_GLOBAL);
	}

	
	/**
	 * Search the database for feature vectors comparing by local color histograms.
	 * 
	 * @param fileName
	 * @return
	 */
	public SearchResultItem[] localHistorgamsInDatabase(String fileName) {
		GlobalColorHistogramProcessor ghProcessor = new GlobalColorHistogramProcessor();
		LocalNeighbourhoodHistogramProcessor lnhProcessor = new LocalNeighbourhoodHistogramProcessor();
		
		ColorHistogram global = ghProcessor.calculateGlobalColorHistogram(fileName);
		ColorHistogram[] local = lnhProcessor.calculateLocalHNeighbourhoodHistogram(fileName);
		
		ImageFeatureVector featureVector = new ImageFeatureVector(global,local, local.length, fileName);

		return searchHistorgamsInDatabase(featureVector, SEARCH_TYPE_LOCAL);
	}

	/**
	 * Searches the database for all local histogram comparisons.
	 * 
	 * @param featureVector to search for.
	 * @return sorted results for the feature vector comparisons.
	 */
	private SearchResultItem[] searchHistorgamsInDatabase(ImageFeatureVector featureVector, int searchType) {
		SortingAlgorithm sorter = new SortingAlgorithm();
		// Construct the search results begin image match based on global histogram
		SearchResultItem[] euclids = new SearchResultItem[featureVectors.size()];
		int i = 0;
		for (ImageFeatureVector vector : featureVectors) {
			switch (searchType) {
				case SEARCH_TYPE_GLOBAL:
					euclids[i++] = featureVector.compareGlobalHistogram(vector);
					break;
				case SEARCH_TYPE_LOCAL:
					euclids[i++] = featureVector.compareLocalHistogram(vector);
					break;
					
			}
			
		}
		
		// Insertion sort the euclidean distances
		sorter.insertionSort(euclids);
		
		return narrowResults(euclids);
	}
	
	/**
	 * Returns a smaller set of result items from the total list of results.
	 * 
	 *  @param items that contains a larger set of seach results
	 *  @return the subset of items passed into the method
	 */
	private SearchResultItem[] narrowResults(SearchResultItem[] items) {
		SearchResultItem[] narrowed = new SearchResultItem[SEARCH_RESULTS_SIZE];
		
		for (int i = 0; i < SEARCH_RESULTS_SIZE; i++) {
			narrowed[i] = items[i];
		}
		
		return narrowed;
	}
}
