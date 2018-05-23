package com.june.util;

import java.util.ResourceBundle;

public interface Constants {
	
	/**
	 * lucene索引存放目录
	 */
	String LUCENE_INDEX_DIR = Util.getString("luceneIndexDir");
	
	/**
	 * 文章封面图片目录
	 */
	String COVER_DIR = Util.getString("coverDir");
	
	/**
	 * 头像目录
	 */
	String AVATAR_DIR = Util.getString("avatarDir");
	
	String BASE_PATH = Util.getString("basePath");
	
	String CURRENT_USER = "currentUser";
	String VALIDATE_CODE = "validateCode";
	
	String LIFE_CATEGORY = "生活随记";
	String LIFE_CATEGORY_EN = "life";
	String NEWS_CATEGORY = "行业动态";
	String NEWS_CATEGORY_EN = "news";
	String PROGRAMMING_CATEGORY = "编程技术";
	String PROGRAMMING_CATEGORY_EN = "programming";
	
	int FRONT_PAGE_SIZE = 8;
	int DEFAULT_PAGE_SIZE = 10;
	int BACK_PAGE_SIZE = 12;
	
	String DEFAULT_TITLE = "hayuq的博客";
	String DEFAULT_KEYWORDS = "个人博客,学习笔记,生活随记";
	String DEFAULT_DESCRIPTION = "hayuq的博客，一位程序员的个人博客，用来记录学习和生活中的大小事。包括Java，.NET，Android，以及前端知识等。";

}

class Util {
	
	static String getString(String key) {
		return getBundle().getString(key);
	}
	
	static Integer getInteger(String key) {
		return Integer.parseInt(getString(key));
	}

	private static ResourceBundle getBundle() {
		return ResourceBundle.getBundle("config");
	}
	
}
