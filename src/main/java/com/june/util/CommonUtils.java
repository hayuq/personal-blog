package com.june.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public final class CommonUtils {

	private CommonUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}
	
	public static String encryptPassword(String password) {
		return MD5EncodeUtils.encrypt(password, "june");
	}
	
	public static String getUUID() {
		return getUUID(false);
	}
	
	public static String getUUID(boolean upperCase) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if (!upperCase) {
			return uuid;
		}
		return uuid.toUpperCase();
	}
	
	public static boolean isIEBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isBlank(userAgent)) {
			return false;
		}
		userAgent = userAgent.toLowerCase();
		// IE 11 之前的版本是 msie, IE 11 是 like gecko
		return userAgent.contains("msie") || userAgent.contains("like gecko");
	}

}
