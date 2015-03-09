package com.eye.see.image.utils;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedImageAdapter;
import javax.media.jai.RenderedOp;
import javax.media.jai.TileCache;

public class ImageResizer {
	
	public static void main(String args[]) {
		ImageResizer a = new ImageResizer();
		a.resize("/Users/ozcan/Desktop/TestImages/DSC_0302.jpg", 100, 100);
	}
	
	public void resize(String filename, int resizeHeight, int resizeWidth) {
		
		RenderedImage renderedImage = (RenderedImage)JAI.create("fileload", filename);
		RenderedImageAdapter planarImage = new RenderedImageAdapter(renderedImage);
		Object resultImage = (Object)planarImage;
		TileCache tc = JAI.getDefaultInstance().getTileCache();
		tc.setMemoryCapacity(2024 * 2024);

		double thumbRatio = (double)resizeWidth / (double)resizeHeight;
		int imageWidth = planarImage.getWidth();
		int imageHeight = planarImage.getHeight();
		double imageRatio = (double)imageWidth / (double)imageHeight;
		float resizeFactor = 1;
		if (thumbRatio < imageRatio) {
			// 	the image is resized to fit the width of the
			// thumbnail
			resizeFactor = (float)resizeWidth / (float)imageWidth;
		} else {
			// 	the image is resized to fit the height of the
			// thumbnail
			resizeFactor = (float)resizeHeight / (float)imageHeight;
		}
		if (resizeFactor >= 1) {
			// 	do no resize
		} else {
			// resize
			ParameterBlock pb = new ParameterBlock();
			pb.addSource(planarImage); // The source image
			pb.add(resizeFactor); // The xScale
			pb.add(resizeFactor); // The yScale
			pb.add(0.0F); // The x translation
			pb.add(0.0F); // The y translation
			pb.add(Interpolation.getInstance(Interpolation.INTERP_BICUBIC)); //
			//interpolation for resize operation
			RenderedOp opImage = JAI.create("scale", pb, null);
			resultImage = (Object)opImage;
		}

		JAI.create("filestore",(PlanarImage)resultImage, "/Users/ozcan/Desktop/TestImages/DSC_0302sm.jpg","JPEG");
	}
}
