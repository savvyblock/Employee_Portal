package net.esc20.txeis.EmployeeAccess.service;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordEncryption {
	
	private static String key = "D3n!m!23R3gi0n20";

	public static String encrypt(String pPlainText) {
		return new String(Base64.encodeBase64(doCipher(Cipher.ENCRYPT_MODE, pPlainText.getBytes())));
	}
	
	public static String failBlankDecrypt(String pCipherText) {
		try {
			return decrypt(pCipherText);
		}
		catch (Exception e) {
			return "";
		}
	}
	
	public static String decrypt(String pCipherText) {
		if (null == pCipherText || 0 == pCipherText.length()) {
			return pCipherText;
		}
		return new String(doCipher(Cipher.DECRYPT_MODE, Base64.decodeBase64(pCipherText.getBytes())));
	}
	
	private static byte[] doCipher(int operation, byte[] pInput) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(operation, new SecretKeySpec(key.getBytes(), "AES"));
			return cipher.doFinal(pInput);
		}
		catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final char[] PWD_CHARS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
	      'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	      'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
	      'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	public static String generatePassword(int minLength, int maxLength) {
		SecureRandom random = getRandom();
		int length = getPasswordLength(random, minLength, maxLength);
		StringBuffer password = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			password.append(PWD_CHARS[random.nextInt(PWD_CHARS.length)]);
		}
		return password.toString();
	}
	
	private static SecureRandom getRandom() {
		try {
			return SecureRandom.getInstance("SHA1PRNG");
		} 
		catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static int getPasswordLength(SecureRandom pRandom, int minLength, int maxLength) {
		return pRandom.nextInt(maxLength - minLength) + minLength;
	}
}
