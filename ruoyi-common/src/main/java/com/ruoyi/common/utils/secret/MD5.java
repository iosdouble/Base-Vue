package com.ruoyi.common.utils.secret;

import java.security.MessageDigest;

/**
 * MD5加密
 */
public class MD5 {
	public static String getMD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes("UTF-8"));
			byte b[] = md.digest();
			result = bytes2Hex(b);
			result = result.toLowerCase();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public static String getMD5_16(String sourceStr) {
		return getMD5(sourceStr).substring(8, 24);
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
