package com.yzz.lr.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	/**
	 * 利用MD5进行加密
	 * 
	 * @param str待加密的字符串
	 * 
	 * @return 加密后的字符串
	 */
	public static String encoderByMd5(String str) {
		if (str == null) {
			return null;
		}

		String ret = "";
		try {
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			MyEncode base64en = new MyEncode();
			// 加密后的字符串
			ret = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException ue) {
			logger.error("EncoderByMd5 UnsupportedEncodingException: ", ue);
		} catch (NoSuchAlgorithmException ne) {
			logger.error("EncoderByMd5 NoSuchAlgorithmException: ", ne);
		}
		return ret;
	}

	public static String apiMd5(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}

		return DigestUtils.md5Hex(str);
	}

}
