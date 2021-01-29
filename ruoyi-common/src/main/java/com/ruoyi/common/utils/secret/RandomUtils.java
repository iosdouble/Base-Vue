package com.ruoyi.common.utils.secret;

import java.util.Random;

/**
 * 生成随机字符串
 */
public class RandomUtils {

	public static final String randomString(int len) {
		String ss = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		int size = ss.length();
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < len; i++) {
			int idx = r.nextInt(size); //
			sb.append(ss.substring(idx, idx + 1));
		}
		String rs = sb.toString();
		sb.setLength(0);
		sb = null;
		r = null;
		return rs;
	}

	public static int randomInt(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public static String randomNumber(int len) {
		Random r = new Random();
		// 获得随机数 1.233241*10的len次方
		double pross = (1 + r.nextDouble()) * Math.pow(10, len);
		String fixed = String.valueOf(pross);
		return fixed.substring(1, len + 1);
	}

}
