package com.eye.see.image.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.eye.see.image.ColorHistogram;
import com.eye.see.image.ImageFeatureVector;
import com.eye.see.image.processing.GlobalColorHistogramProcessor;
import com.eye.see.image.processing.LocalNeighbourhoodHistogramProcessor;

/**
 * Processes all the images in a file path.
 * 
 * @author Ozcan Huseyin
 */
public class ImageDatabaseBuilder {

	public static final String FEATURE_VECTOR_STORAGE_DIRECTORY = "/opt/EyeSee/TestImages/Indexes/";
	public static final String FEATURE_VECTOR_FILENAME = "FeatureVectors.txt";
	
	private ObjectOutput out;
	
	
	/**
	 * Process all the images in single directory.
	 * 
	 * @param path where images are located
	 * @return array ProcessedImages that contain content information
	 */
	public void processAllImagesInDirectory(String path) {
		// The filenames in directory
		String[] fileNames = readFilesFromSingleDirectory(new File(path));
		// The image processors
		GlobalColorHistogramProcessor ghp = new GlobalColorHistogramProcessor();
		LocalNeighbourhoodHistogramProcessor lnhp = new LocalNeighbourhoodHistogramProcessor();
		
		int i = 0;
		try {
			ColorHistogram globalHistogram = null;
			ColorHistogram[] localHistograms = null;
			for (i = 0; i < fileNames.length; i++) {
				try {
					globalHistogram = ghp.calculateGlobalColorHistogram(fileNames[i]);
					localHistograms = lnhp.calculateLocalHNeighbourhoodHistogram(fileNames[i]);
				} catch (RuntimeException e) {
					// TODO
					System.err.println("Count not process: " + fileNames[i] + ": Exception =" + e.toString());
				} 
				
				if (globalHistogram != null) {
					// Create the image feature vector 
					ImageFeatureVector featureVector = new ImageFeatureVector(globalHistogram, localHistograms, localHistograms.length, fileNames[i]);
					writeFeatureVectorToFile(featureVector);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Count not process: " + fileNames[i] + ": Exception =" + e.toString());
		} catch (IOException e) {
			System.err.println("Count not process: " + fileNames[i] + ": Exception =" + e.toString());
		} finally {
			try {
				// close the stream
				if (out != null)
					out.close();
			} catch (IOException e) {
				// What can we do in this case..?
			}
		}
	}
	
	private void writeFeatureVectorToFile(ImageFeatureVector featureVector) throws FileNotFoundException, IOException {
		if (out == null) {
			out = new ObjectOutputStream(
					new FileOutputStream(FEATURE_VECTOR_STORAGE_DIRECTORY + FEATURE_VECTOR_FILENAME));
		}
		out.writeObject(featureVector);
	}
	
	public ArrayList<ImageFeatureVector> read() throws IOException, ClassNotFoundException {
		System.out.println("Reading database...");
	    ObjectInputStream in = new ObjectInputStream(new FileInputStream(FEATURE_VECTOR_STORAGE_DIRECTORY + FEATURE_VECTOR_FILENAME));
	    ArrayList<ImageFeatureVector> arrayList = new ArrayList<ImageFeatureVector>();
	    ImageFeatureVector vector = null;
	    do {
	    	try {
	    		vector = (ImageFeatureVector) in.readObject();
	    	} catch (Exception e) {
	    		// EOF?
	    		in.close();
	    		System.out.println("Completed reading database");
	    		return arrayList;
	    	}
	    	//System.err.println(vector.toString());
	    	arrayList.add(vector);
	    } while (vector != null);
	    System.out.println("Completed reading database");
	    in.close();
	    return arrayList;
	}
	
	private String[] readFilesFromSingleDirectory(File path) {
		  File files[]; 
		  files = path.listFiles();
		  String[] filesString = new String[files.length];
		  Arrays.sort(files);
		  
		  for (int i = 0, n = files.length; i < n; i++) {
			  filesString[i] = files[i].toString();
		  }
		  
		  return filesString;
	}
}
