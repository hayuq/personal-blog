package com.june.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();

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
            DateUtils.DATE_FORMAT.applyPattern(format);
            result = DateUtils.DATE_FORMAT.format(date);
        }
        return result;
    }

    /**
     * 字符串转日期对象
     * @param dateStr 日期字符串
     * @param format 格式字符串
     */
    public static Date parseDate(String dateStr, String format) throws Exception {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        DateUtils.DATE_FORMAT.applyPattern(format);
        return DateUtils.DATE_FORMAT.parse(dateStr);
    }

    /**
     * 获取当前时间字符串 yyyyMMddHHmmss格式
     */
    public static String getCurrentDateTimeStr() {
        return DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取当前日期字符串 yyyyMMdd格式
     */
    public static String getCurrentDateStr() {
        return DateUtils.formatDate(new Date(), "yyyyMMdd");
    }

}
