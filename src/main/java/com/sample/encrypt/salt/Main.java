package com.sample.encrypt.salt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Main {
	public static void main(String s[]) throws Exception {
		String password = "mypassword";
		String passwordEnc = SimpleProtector.encrypt(password);
		String passwordDec = SimpleProtector.decrypt(passwordEnc);

		System.out.println("Plain Text : " + password);
		System.out.println("Encrypted : " + passwordEnc);
		System.out.println("Decrypted : " + passwordDec);
	}
}

class SimpleProtector {

	private static final String ALGORITHM = "AES";
	private static final byte[] keyValue = "ThisIsASecretKey".getBytes();
	private static final Encoder encoder = Base64.getEncoder();
	private static final Decoder decoder = Base64.getDecoder();
	
	public static String encrypt(String valueToEnc) throws Exception {
		Key key = generateKey();
		
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		
		String encryptedValue = encoder.encodeToString(encValue);
		return encryptedValue;
	}

	public static String decrypt(String encryptedValue) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = decoder.decode(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGORITHM);
		return key;
	}
}#########END#########
#########FOLDER#############/home/harsha/data/git/justlikethat/src/main/java/com/sample/image
#########FILE#START#########/home/harsha/data/git/justlikethat/src/main/java/com/sample/image/ImageCrop.java
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
