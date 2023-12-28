package com.sample.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * COMMANDS:
 * ImageCrop VSLICE [-padding N] [FILE-NAME]
 * ImageCrop HSLICE [-padding N] [FILE-NAME]
 * ImageCrop CLEAR [-pixels N] [-percentage N] [-direction [L|R|T|B]] [FILE-NAME]
 * ImageCrop ADD_PADDING [-pixels N] [-percentage N] [-direction [L|R|T|B]] [FILE-NAME]
 */
public class ImageCrop {
	
	public static void main(String s[]) {
		new ImageCrop().letsTry("SAMPLE-IMAGE.jpg", 10);
		
		// vertical slice - result 2 files
		// horizantal slice - result - 2 files
		// clear x% on the right | left | top | bottom
		// add padding to right | left | top | bottom
		if(s == null || s.length < 2) {
			printCommandExit();
			return;
		}
		List<String> arr = Arrays.asList(s);
		String command = arr.get(0);
		arr.remove(0);
		switch (command) {
			case "VSLICE":
				verticalSlice(arr);
			case "HSLICE":
				horizantalSlice(arr);
			case "CLEAR":

			case "ADD_PADDING":
			default: printCommandExit();
		}
	}

	private static void horizantalSlice(List<String> arr) {
		try {
			System.out.println("Horizantal slicing with extra padding space: " + padN);
			BufferedImage image = ImageIO.read(new File(fileLoc));

			int w = image.getWidth();
			int h = image.getHeight();

			if(w/2 < padN)
				throw new Exception("Padding pixels can not be greater than half of the image width");

			BufferedImage subImage01 = image.getSubimage(0, 0, w, h/2 + padN);
			BufferedImage subImage02 = image.getSubimage(0, h/2 - padN, w, h/2 + padN);

			File outputfile01 = new File("SAMPLE-IMAGE-01.jpg");
			File outputfile02 = new File("SAMPLE-IMAGE-02.jpg");

			ImageIO.write(subImage01, "jpg", outputfile01);
			ImageIO.write(subImage02, "jpg", outputfile02);

		} catch(Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
		}
	}

	static boolean padding, byPercentage, byPixel;
	static int padN = 0;
	static String fileLoc;
	static String direction;
	private static void setup(List<String> arr) {
		int index;
		if((index = arr.indexOf("-padding")) < 0) {
			padding = true;
			padN = Integer.parseInt(arr.get(index+1));
		}
		if((index = arr.indexOf("-pixels")) < 0) {
			byPixel = true;
			padN = Integer.parseInt(arr.get(index+1));
		}
		if((index = arr.indexOf("-percentage")) < 0) {
			byPercentage = true;
			padN = Integer.parseInt(arr.get(index+1));
		}
		if((index = arr.indexOf("-direction")) < 0) {
			direction = arr.get(index+1);
		}

		fileLoc = arr.get(arr.size()-1);
	}

	/**
	 * ImageCrop VSLICE [-padding N] [FILE-NAME]
	 * @param arr
	 */
	private static void verticalSlice(List<String> arr) {
		try {
			System.out.println("Vertical slicing with extra padding space: " + padN);
			BufferedImage image = ImageIO.read(new File(fileLoc));

			int w = image.getWidth();
			int h = image.getHeight();

			if(w/2 < padN)
				throw new Exception("Padding pixels can not be greater than half of the image width");

			BufferedImage subImage01 = image.getSubimage(0, 0, w/2 + padN, h);
			BufferedImage subImage02 = image.getSubimage(w/2 - padN, 0, w/2 + padN, h);

			File outputfile01 = new File("SAMPLE-IMAGE-01.jpg");
			File outputfile02 = new File("SAMPLE-IMAGE-02.jpg");

			ImageIO.write(subImage01, "jpg", outputfile01);
			ImageIO.write(subImage02, "jpg", outputfile02);

		} catch(Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
		}
	}

	private static void printCommandExit() {
		System.out.println("--- print command here ---");
	}

	void letsTry(String imgLoc, int verticalPaddingPx) {
		

	}
	

}
