package com.june.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public final class ShiroUtils {

	private ShiroUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}
	
	public static String encryptPassword(String password) {
		return MD5EncodeUtils.encrypt(password, "june");
	}
	
	public static void logout() {
		getSubject().logout();
	}
	
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

}
