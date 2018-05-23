package com.june.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	private DateUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}

	/**
	 * 日期对象转字符串
	 * @param date 日期对象
	 * @param format 格式字符串
	 */
	public static String formatDate(Date date, String format) {
		String result = "";
		if (date != null) {
			sdf.applyPattern(format);
			result = sdf.format(date);
		}
		return result;
	}

	/**
	 * 字符串转日期对象
	 * @param DateStr 日期字符串
	 * @param format 格式字符串
	 */
	public static Date formatString(String DateStr, String format) throws Exception {
		if (StringUtils.isEmpty(DateStr)) {
			return null;
		}
		sdf.applyPattern(format);
		return sdf.parse(DateStr);
	}

	/**
	 * 获取当前时间字符串
	 */
	public static String getCurrentDateStr() throws Exception {
		sdf.applyPattern("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间字符串作为图片路径
	 * @return
	 * @throws Exception
	 */
	public static String getTimeStrForImage() throws Exception {
		sdf.applyPattern("yyyyMMdd");
		return sdf.format(new Date()) + "/" + System.currentTimeMillis();
	}
}
