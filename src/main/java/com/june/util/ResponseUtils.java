package com.june.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

public @Slf4j final class ResponseUtils {
	
	private ResponseUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}

	public static void writeJson(HttpServletResponse response, String text){
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		write(response, text);
	}

	public static void writeHtml(HttpServletResponse response, String text){
		response.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
		write(response, text);
	}
	
	public static void writeText(HttpServletResponse response, String text) {
		response.setContentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8");
		write(response, text);
	}
	
	private static void write(HttpServletResponse response, String text) {
		try (PrintWriter out = response.getWriter()) {
			out.write(text);
			out.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	
}
