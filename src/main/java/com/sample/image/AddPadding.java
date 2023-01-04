package com.sample.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class AddPadding {
	
	public static void main(String s[]) {
		if(s == null || s.length < 5) {
			printUsage();
			// return;
			s = new String[5];
			s[0] = "SAMPLE-IMAGE.jpg";
			s[1] = "1.25x";
			s[2] = "1.25x";
			s[3] = "1.25x";
			s[4] = "1.25x";
		}
		new AddPadding().letsTry(s[0], s[1], s[2], s[3], s[4]);
	}

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("Following is by multiples of width (for left and right padding) or height (for top or bottom padding)");
		System.out.println("all pixels needs to have 'x' to denote multiple times");
		System.out.println("AddPadding <file-location> <left-multiple>x <right-multiple>x <top-multiple>x <bottom-multiple>x");
		System.out.println("OR");
		System.out.println("Following is by pixes");
		System.out.println("AddPadding <file-location> <leftpx> <right-px> <top-px> <bottom-px>");
	}
	
	void letsTry(String imgLoc, String left, String top, String right, String bottom) {

		try {
			File originalImage = new File(imgLoc);
			System.out.println(originalImage.getAbsolutePath());
			BufferedImage image = ImageIO.read(originalImage);

			int w = image.getWidth();
			int h = image.getHeight();

			int newWidth = 1000, newHeight= 1000;
			int imagex = 0, imagey = 0;
			
			if(left.contains("x")) {
				double leftx, rightx, topx, bottomx;
				leftx = Double.parseDouble(left.replace("x", ""));
				rightx = Double.parseDouble(right.replace("x", ""));
				topx = Double.parseDouble(top.replace("x", ""));
				bottomx = Double.parseDouble(bottom.replace("x", ""));
				System.out.printf("Padding multipliers identified: left: %d, top: %d, right: %d, bottom: %d\n", leftx, topx, rightx, bottomx);

				newWidth = (int) (w + w*leftx + w*rightx);
				newHeight = (int) (h + h*topx + h*bottomx);
				System.out.printf("New canvas measurements: %d x %d\n", newWidth, newHeight);

				imagex = (int) (w * leftx);
				imagey = (int) (h * topx);

			} else {
				int leftp, rightp, topp, bottomp;
				leftp = Integer.parseInt(left.replace("x", ""));
				rightp = Integer.parseInt(right.replace("x", ""));
				topp = Integer.parseInt(top.replace("x", ""));
				bottomp = Integer.parseInt(bottom.replace("x", ""));
				System.out.printf("Padding multipliers identified: left: %d, top: %d, right: %d, bottom: %d\n",
						leftp, topp, rightp, bottomp);

				newWidth = w + leftp + rightp;
				newHeight = h + topp + bottomp;
				System.out.printf("New canvas measurements: %d x %d\n", newWidth, newHeight);
				
				imagex = leftp;
				imagey = topp;
			}

			BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
			java.awt.Graphics g = newImage.getGraphics();
			g.setColor(java.awt.Color.white);
			g.fillRect(0, 0, newWidth, newHeight);
			g.drawImage(image, imagex, imagey, null);
			g.dispose();
			
			File newImageFile = new File("SAMPLE-IMAGE-02.jpg");
			ImageIO.write(newImage, "jpg", newImageFile);
			
		} catch(Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
		}
	}
	

}
