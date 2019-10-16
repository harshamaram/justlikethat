/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justlikethat.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Creates a PDF document from an image.
 *
 * The example is taken from the pdf file format specification.
 */
public final class ImageToPDF {
	private ImageToPDF() {
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("usage: " + ImageToPDF.class.getName() + " <image>");
			System.exit(1);
		}
		String fileName = args[0];
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		imageToPdf(args[0], fileName + ".pdf");
		libreImageToPdf(args[0], fileName + "_v2.pdf");
	}

	public static void imageToPdf(String imagePath, String pdfPath) throws IOException {

		try (PDDocument doc = new PDDocument()) {
			PDPage page = new PDPage();
			doc.addPage(page);

			// createFromFile is the easiest way with an image file
			// if you already have the image in a BufferedImage,
			// call LosslessFactory.createFromImage() instead
			PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);

			// draw the image at full size at (x=20, y=20)
			try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
				// draw the image at full size at (x=20, y=20)
				contents.drawImage(pdImage, 20, 20);

				// to draw the image at half size at (x=20, y=20) use
				// contents.drawImage(pdImage, 20, 20, pdImage.getWidth() / 2,
				// pdImage.getHeight() / 2);
			}
			doc.save(pdfPath);
		}
	}
	
	public static void libreImageToPdf(String imageFile, String outputFile) throws IOException {
		byte[] fileBytes = FileUtils.readFileToByteArray(new File(imageFile));
		OutputStream fos = new FileOutputStream(outputFile);
		libreImageToPdf(fileBytes, fos);
	}
	/**
	 * Converts arbitrary image file to PDF
	 * http://stackoverflow.com/a/42937466/241986
	 * @param imageFile contents of JPEG or PNG file
	 * @param outputStream stream to write out pdf, always closed after this method execution.
	 * @throws IOException when there' an actual exception or image is not valid
	 */
	public static void libreImageToPdf(byte[] imageFile, OutputStream outputStream) throws IOException {
	    try {
	        Image image;
	        try {
	            image = Image.getInstance(imageFile);
	        } catch (BadElementException bee) {
	            throw new IOException(bee);
	        }

	        //See http://stackoverflow.com/questions/1373035/how-do-i-scale-one-rectangle-to-the-maximum-size-possible-within-another-rectang
	        Rectangle A4 = PageSize.A4;

	        float scalePortrait = Math.min(A4.getWidth() / image.getWidth(),
	                A4.getHeight() / image.getHeight());

	        float scaleLandscape = Math.min(A4.getHeight() / image.getWidth(),
	                A4.getWidth() / image.getHeight());

	        // We try to occupy as much space as possible
	        // Sportrait = (w*scalePortrait) * (h*scalePortrait)
	        // Slandscape = (w*scaleLandscape) * (h*scaleLandscape)

	        // therefore the bigger area is where we have bigger scale
	        boolean isLandscape = scaleLandscape > scalePortrait;

	        float w;
	        float h;
	        if (isLandscape) {
	            A4 = A4.rotate();
	            w = image.getWidth() * scaleLandscape;
	            h = image.getHeight() * scaleLandscape;
	        } else {
	            w = image.getWidth() * scalePortrait;
	            h = image.getHeight() * scalePortrait;
	        }

	        Document document = new Document(A4, 10, 10, 10, 10);

	        try {
	            PdfWriter.getInstance(document, outputStream);
	        } catch (DocumentException e) {
	            throw new IOException(e);
	        }
	        document.open();
	        try {
	            image.scaleAbsolute(w, h);
	            float posH = (A4.getHeight() - h) / 2;
	            float posW = (A4.getWidth() - w) / 2;

	            image.setAbsolutePosition(posW, posH);
	            image.setBorder(Image.NO_BORDER);
	            image.setBorderWidth(0);

	            try {
	                document.newPage();
	                document.add(image);
	            } catch (DocumentException de) {
	                throw new IOException(de);
	            }
	        } finally {
	            document.close();
	        }
	    } finally {
	        outputStream.close();
	    }
	}
}
