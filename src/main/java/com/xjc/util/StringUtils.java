package com.xjc.util;

public class StringUtils {

	public static String escapeHtml(String s){
		String result = s;
		if(s != null && !"".equals(s)){
			result = s.replaceAll("<[^>]*>", "")
					.replaceAll("[/>$]<", "");
		}else{
			result = "";
		}
		return result;
	}
}
