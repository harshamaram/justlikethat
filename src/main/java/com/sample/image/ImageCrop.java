package com.sample.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageCrop {
	
	public static void main(String s[]) {
		new ImageCrop().letsTry("SAMPLE-IMAGE.jpg", 10);
	}
	
	void letsTry(String imgLoc, int verticalPaddingPx) {
		
		try {
			BufferedImage image = ImageIO.read(new File(imgLoc));
			
			int w = image.getWidth();
			int h = image.getHeight();
			
			if(w/2 < verticalPaddingPx) 
				throw new Exception("Padding pixels can not be greater than half of the image width");
			
			BufferedImage subImage01 = image.getSubimage(0, 0, w/2 + verticalPaddingPx, h);
			BufferedImage subImage02 = image.getSubimage(w/2 - verticalPaddingPx, 0, w/2 + verticalPaddingPx, h);
			
			File outputfile01 = new File("SAMPLE-IMAGE-01.jpg"); 
			File outputfile02 = new File("SAMPLE-IMAGE-02.jpg"); 
			
			ImageIO.write(subImage01, "jpg", outputfile01);
			ImageIO.write(subImage02, "jpg", outputfile02);
			
		} catch(Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
		}
	}
	

}
