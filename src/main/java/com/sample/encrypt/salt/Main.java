package com.sample.encrypt.salt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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

	public static String encrypt(String valueToEnc) throws Exception {
		Key key = generateKey();
		
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encValue);
		return encryptedValue;
	}

	public static String decrypt(String encryptedValue) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGORITHM);
		return key;
	}
}