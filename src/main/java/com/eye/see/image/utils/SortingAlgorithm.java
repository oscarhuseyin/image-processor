package com.eye.see.image.utils;

/**
 * Class is used to sort primative arrays of integers, floats etc.
 * 
 * @author Ozcan
 */
public class SortingAlgorithm {
	
	/**
	 * Insertion sort an array of floats.
	 * 
	 * @param a floats to sort.
	 */
    void insertionSort(SearchResultItem a[])
    {
    	// Loop through the array & put each item in the proper slot,
    	// shifting other items out of the way as you go.
    	for (int i = 1; i < a.length; i++ ) {
    		float value = a[i].getEuclideanDistance();		// next item to be inserted in order
    		SearchResultItem item = a[i];
    		int j;

    		// Back up in the array to find the spot for value.
    		for ( j = i - 1; j >= 0 && a[j].getEuclideanDistance() > value; j-- ) {
                // This element is bigger than the element being inserted,
                // so move it up one slot.
                a[j+1] = a[j];
    		}
 
    		// a[j] is the item that should precede value.
    		// Put value after it.
    		a[j+1] = item;
    	}
    }
}