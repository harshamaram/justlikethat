package com.justlikethat.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * COMMANDS:
 * ExtendToTwo4x6 [FILE-NAME]
 *
 * javac -d . .\Create4x6Canvas.java
 * java com.justlikethat.image.Create4x6Canvas .\TM2023.JPG
 *
 * to create jar:
 * jar cfm create4x6canvas.jar .\mani.mf .\com\
 *
 * sample mani.mf contents:
 * Manifest-Version: 1.0
 * Main-Class: com.justlikethat.image.Create4x6Canvas
 */
public class Create4x6Canvas {
	
	public static void main(String args[]) {

		if (args.length != 1) {
			System.err.println("usage: " + Create4x6Canvas.class.getName() + " <image>");
			System.exit(1);
		}
		String fileName = args[0];

		if(args == null || args.length < 1) {
			printCommandExit();
			return;
		}

		copyInto4x6Canvas(fileName);
	}

	static String fileLoc;
	private static void setFileLoc(String file) {
		fileLoc = file;
	}

	/**
	 * Create4x6Canvas [FILE-NAME]
	 * @param file
	 */
	private static void copyInto4x6Canvas(String file) {
		try {
			setFileLoc(file);
			System.out.println("Copying into 4x6 canvas with two images");

			BufferedImage image = ImageIO.read(new File(fileLoc));

			int w = image.getWidth();
			int h = image.getHeight();

			int newWidth = w * 3;
			int newHeight = w * 2;

			BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
			java.awt.Graphics g = newImage.getGraphics();
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, newWidth, newHeight);
			g.drawImage(image, w/2, w/2, null);
			g.drawImage(image, w + w/2, w/2, null);
			g.dispose();

			File newImageFile = new File(file.substring(0, file.lastIndexOf("."))+"_v2.jpg");
			ImageIO.write(newImage, "jpg", newImageFile);

			BufferedImage newImage2 = new BufferedImage(newWidth, newHeight, image.getType());
			g = newImage2.getGraphics();
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, newWidth, newHeight);
			g.drawImage(image, 0, 0, null);
			g.drawImage(image, w, 0, null);
			g.drawImage(image, 2*w, 0, null);

			g.drawImage(image, 0, w, null);
			g.drawImage(image, w, w, null);
			g.drawImage(image, 2*w, w, null);

			g.dispose();

			File newImageFile2 = new File(file.substring(0, file.lastIndexOf("."))+"_v3.jpg");
			ImageIO.write(newImage2, "jpg", newImageFile2);

		} catch(Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
		}
	}

	private static void printCommandExit() {
		System.out.println("--- print command here ---");
	}

}
