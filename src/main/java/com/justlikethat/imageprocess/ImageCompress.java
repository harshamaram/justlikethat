package com.justlikethat.imageprocess;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;

public class ImageCompress {
    public static void main(String[] args) throws IOException {

        File input = new File("SM202638.JPG");
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File("compressed_image_v2.jpg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.5f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }
}
