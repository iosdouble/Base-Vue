package com.ruoyi.common.utils.secret;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA 加密算法
 */
public class RSA {

	public static final String RSA = "RSA";

	/**
	 * 生成RSA密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RSADTO generateRSA() throws Exception {
		RSADTO rsa = null;

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
			keyPairGenerator.initialize(1024);

			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			rsa = new RSADTO();
			String publicString = keyToString(publicKey);
			String privateString = keyToString(privateKey);
			rsa.setPublicKey(publicString);
			rsa.setPrivateKey(privateString);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return rsa;
	}

	public static RSAKey generateRSAKey() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey key = new RSAKey();
		key.setPublicKey(publicKey);
		key.setPrivateKey(privateKey);
		return key;
	}

	/**
	 * BASE64 String 转换为 PublicKey
	 * 
	 * @param publicKeyString
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String publicKeyString) throws Exception {
		PublicKey publicKey = null;
		try {
			byte[] keyByteArray = Base64.decodeBase64(publicKeyString);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByteArray);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			publicKey = keyFactory.generatePublic(x509KeySpec);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return publicKey;
	}

	/**
	 * BASE64 String 转换为 PrivateKey
	 * 
	 * @param privateKeyString
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
		PrivateKey privateKey = null;

		try {
			byte[] keyByteArray = Base64.decodeBase64(privateKeyString);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyByteArray);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return privateKey;

	}

	/**
	 * RSA 加密返回byte[]
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encodeBytePrivate(byte[] content, PrivateKey privateKey) throws Exception {
		byte[] encodeContent = null;
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			encodeContent = cipher.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return encodeContent;
	}

	/**
	 * 解密返回byte[]
	 * 
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeBytePrivate(byte[] content, PrivateKey privateKey) throws Exception {
		byte[] decodeContent = null;
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			decodeContent = cipher.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return decodeContent;
	}

	public static byte[] encodeBytePublic(byte[] content, PublicKey publicKey) throws Exception {
		byte[] encodeContent = null;
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encodeContent = cipher.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return encodeContent;
	}

	/**
	 * 解密返回byte[]
	 * 
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeBytePublic(byte[] content, PublicKey publicKey) throws Exception {
		byte[] decodeContent = null;
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			decodeContent = cipher.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return decodeContent;
	}

	/**
	 * 私钥加密返回 base64
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64StringPrivate(byte[] content, PrivateKey privateKey) throws Exception {
		byte[] encodeContent = encodeBytePrivate(content, privateKey);
		String stringContent = Base64.encodeBase64String(encodeContent);
		return stringContent;
	}

	/**
	 * RSA 公钥 加密返回 Base64 
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64StringPublic(byte[] content, PublicKey key) throws Exception {
		byte[] encodeContent = encodeBytePublic(content, key);
		String stringContent = Base64.encodeBase64String(encodeContent);
		return stringContent;
	}

	/**
	 * RSA 私钥 加密返回 HexString
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String encodeStringPrivate(byte[] content, PrivateKey privateKey) throws Exception {
		byte[] encodeContent = encodeBytePrivate(content, privateKey);
		String stringContent = Hex.bytesToHex(encodeContent);
		return stringContent;
	}

	/**
	 * RSA 公钥加密 返回hex
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encodeStringPublic(byte[] content, PublicKey publicKey) throws Exception {
		byte[] encodeContent = encodeBytePublic(content, publicKey);
		String stringContent = Hex.bytesToHex(encodeContent);
		return stringContent;
	}

	/**
	 * RSA 公钥解密Base64 返回base64
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeBase64StringPublic(String content, PublicKey publicKey) throws Exception {
		byte[] decodeContent = null;
		try {
			byte[] sourceByteArray = Base64.decodeBase64(content);
			decodeContent = decodeBytePublic(sourceByteArray, publicKey);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return decodeContent;
	}

	/**
	 * RSA 私钥解密 base64加密内容
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeBase64StringPrivate(String content, PrivateKey privateKey) throws Exception {
		byte[] decodeContent = null;
		try {
			byte[] sourceByteArray = Base64.decodeBase64(content);
			decodeContent = decodeBytePrivate(sourceByteArray, privateKey);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return decodeContent;
	}

	/**
	 * RSA 解密 Hex字符串
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeStringPublic(String content, PublicKey publicKey) throws Exception {
		byte[] decodeContent = null;
		try {
			byte[] sourceByteArray = Hex.hexToBytes(content);
			decodeContent = decodeBytePublic(sourceByteArray, publicKey);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return decodeContent;
	}

	/**
	 * 将Key转为String
	 * 
	 * @param key
	 * @return
	 */
	public static String keyToString(Key key) {
		byte[] keyByteArray = key.getEncoded();
		String keyString = Base64.encodeBase64String(keyByteArray);
		return keyString;
	}

	public static class RSAKey {
		PublicKey publicKey;
		PrivateKey privateKey;

		public PublicKey getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(PublicKey publicKey) {
			this.publicKey = publicKey;
		}

		public PrivateKey getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(PrivateKey privateKey) {
			this.privateKey = privateKey;
		}

		public String getPublicKeyBase64String() {
			// TODO Auto-generated method stub
			return keyToString(publicKey);
		}

		public String getPrivateKeyBase64String() {
			// TODO Auto-generated method stub
			return keyToString(privateKey);
		}

	}

	public static class RSADTO {
		/**
		 * 公钥
		 */
		private String publicKey;

		/**
		 * 私钥
		 */
		private String privateKey;

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
	}

	public static class MD5Util {
		/**
		 * MD5加密
		 *
		 * @param s
		 * @return
		 */
		public static String MD5(String s) {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
					'f' };
			try {
				byte[] btInput = s.getBytes("utf8");
				// 获得MD5摘要算法的 MessageDigest 对象
				MessageDigest mdInst = MessageDigest.getInstance("MD5");
				// 使用指定的字节更新摘要
				mdInst.update(btInput);
				// 获得密文
				byte[] md = mdInst.digest();
				// 把密文转换成十六进制的字符串形式
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
