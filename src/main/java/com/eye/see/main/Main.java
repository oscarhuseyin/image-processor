package com.eye.see.main;

import com.eye.see.image.utils.ImageSearcher;
import com.eye.see.image.utils.SearchResultItem;



public class Main {

	public static void main(String[] args) throws Exception {

		String path = "/Users/ohuseyin/Desktop/ImageRepository";
		final String imageFilename = "/Users/ozcan/Desktop/ImageRepository/DSC_150.jpg";
		
		//PlanarImage image = JAI.create("fileload", imageFilename);

	//	ImageDatabaseBuilder builder = new ImageDatabaseBuilder();
		
		//builder.processAllImagesInDirectory(path);
//		ArrayList<ImageFeatureVector> list = builder.read();

		
		
		SearchResultItem[] results = ImageSearcher.getInstance().globalHistogramSearch(imageFilename);
		
		for (int i = 0; i < results.length; i++) {
			System.out.print("[" + i + "] - ");
			System.out.print(results[i].getEuclideanDistance() + " - ");
			System.out.println(results[i].getImageFeatureVector().getFileName());
		}
	}
	
}
