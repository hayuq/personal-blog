package com.june.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class ObjectUtils {
	
	private ObjectUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}
	
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}
	
	public static boolean isNotEmpty(Object obj) {
		return !ObjectUtils.isEmpty(obj);
	}
	
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if(obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}
		if(obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		if(obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		return false;
	}
	
}
