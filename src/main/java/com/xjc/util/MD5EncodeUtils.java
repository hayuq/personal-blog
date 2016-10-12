package com.xjc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * MD5加密工具类
 */
public class MD5EncodeUtils {

	public static String encrypt(String source, String salt){
		
		MessageDigest instance = null;
		
		try {
			instance = MessageDigest.getInstance("MD5");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 加密
		byte[] digest = instance.digest((source+salt).getBytes());

		// 十六进制加密
		char[] encodeHex = Hex.encodeHex(digest);

		return new String(encodeHex);
	}
	
	public static void main(String[] args) {
		
		System.out.println(encrypt("admin","xjc"));
	}
}
