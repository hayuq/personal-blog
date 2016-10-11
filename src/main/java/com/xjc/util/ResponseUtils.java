package com.xjc.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

	public static void writeJson(HttpServletResponse response,String text){
		response.setContentType("application/json;charset=UTF-8");
		write(response, text);
	}

	public static void writeHtml(HttpServletResponse response,String text){
		response.setContentType("text/html;charset=UTF-8");
		write(response, text);
	}
	
	private static void write(HttpServletResponse response, String text) {
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeText(HttpServletResponse response, String text) {
		response.setContentType("text/plain;charset=UTF-8");
		write(response, text);
		
	}
	
}
