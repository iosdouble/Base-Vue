package com.ruoyi.common.utils.secret;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


/**
 * AES 加密算法
 */
public class AES {

	public static byte[] encryptAES(String content, String keyWord, String vector) {
		if (keyWord == null || keyWord.length() != 16) {
			return null;
		}
		try {
			byte[] raw = keyWord.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
			cipher.init(1, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content.getBytes());

			return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] encryptAES(String content, String keyWord) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(keyWord.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(byteContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptAES(String data, String keyWord) throws Exception {
		byte[] content = data.getBytes();
		return decryptAES(content, keyWord);
	}

	public static String decryptAES(byte[] data, String keyWord) throws Exception {
		byte[] content = data;
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(keyWord.getBytes());
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		//		 System.out.println( Hex.bytesToHex (enCodeFormat));
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(content);
		return new String(result);
	}

	public static void main(String[] args) throws Exception {
		String aaa = "aaabbbbccccdddd123";
		String password = RandomUtils.randomString(16);
		String data = Base64.encodeBase64String(encryptAES(aaa, password));
		System.out.println("encrpt: " + data);

		String bbb = decryptAES(Base64.decodeBase64(data), password);
		System.out.println("decrpt: " + bbb);

	}
}
