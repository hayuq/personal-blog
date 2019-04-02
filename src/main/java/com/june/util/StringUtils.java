package com.june.util;

public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("No instance for this class!");
    }

    public static String escapeHtml(String s) {
        String result = "";
        if (StringUtils.isNotEmpty(s)) {
            result = s.replaceAll("<[^>]*>", "").replaceAll("[/>$]<", "");
        }
        return result;
    }

    public static boolean isEmpty(String s) {
        return ObjectUtils.isEmpty(s);
    }

    public static boolean isNotEmpty(String s) {
        return !StringUtils.isEmpty(s);
    }

    public static boolean isBlank(String s) {
        return StringUtils.isEmpty(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s);
    }

    public static boolean isNotBlank(String s) {
        return !StringUtils.isBlank(s);
    }

}
