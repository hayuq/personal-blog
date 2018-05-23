package com.june.util;

import java.util.Collection;

public final class StringUtils {
	
	private StringUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}

	public static String escapeHtml(String s) {
		String result = "";
		if(isNotEmpty(s)) {
			result = s.replaceAll("<[^>]*>", "").replaceAll("[/>$]<", "");
		}
		return result;
	}
	
	public static boolean isEmpty(Object o){
		if (o == null) {
			return true;
		} else if (o instanceof Collection) {
			return ((Collection<?>) o).isEmpty();
		} else if (o instanceof String) {
			return ((String) o).trim().length() == 0;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String s){
		return !isEmpty(s);
	}
	
	public static boolean isBlank(String s){
		return isEmpty(s) || "null".equalsIgnoreCase(s);
	}
	
	public static boolean isNotBlank(String s){
		return !isBlank(s);
	}
}
