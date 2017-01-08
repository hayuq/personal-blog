package com.xjc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.oscache.util.StringUtil;

public class DateUtils {

	/**
	 * 日期对象转字符串
	 * @param date 日期对象
	 * @param format 格式字符串
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 字符串转日期对象
	 * @param DateStr 日期字符串
	 * @param format 格式字符串
	 */
	public static Date formatString(String DateStr,String format) throws Exception{
		if(StringUtil.isEmpty(DateStr)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(DateStr);
	}
	
	/**
	 * 获取当前时间字符串
	 */
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
}
