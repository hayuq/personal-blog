package com.june.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import lombok.extern.slf4j.Slf4j;

/**
 * MD5加密工具类
 */
public @Slf4j final class MD5EncodeUtils {
	
	private MD5EncodeUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}

	public static String encrypt(String source, String salt){
		
		MessageDigest instance = null;
		try {
			instance = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("No MD5 Algorithm available", e);
			throw new RuntimeException(e);
		}
		// 加密
		byte[] digest = instance.digest((source + salt).getBytes());
		// 十六进制加密
		char[] encodeHex = Hex.encodeHex(digest);
		return new String(encodeHex);
	}
	
}
