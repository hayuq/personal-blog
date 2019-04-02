package com.june.util;

import com.june.vo.PageBean;

/****************************
 * @author june
 * @date 2016年8月28日下午2:25:32
 * @version 1.0.0
 * @desc 分页工具类
 **************************/
public final class PageUtils {
	
	private PageUtils() {
		throw new UnsupportedOperationException("No instance for this class!");
	}

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param pageBean
	 * @param paramString
	 * @return
	 */
	public static String genPagination(String targetUrl, PageBean pageBean, String paramString) {
		int totalPage = pageBean.getTotalPage();
		int currentPage = pageBean.getCurrentPage();
		if (totalPage == 0) {
			return "<font color='red'>未查询到数据</font>";
		}
		paramString = StringUtils.isEmpty(paramString) ? "" : "&" + paramString;
		StringBuilder pageCode = new StringBuilder("<li class='paginItem'><a href='" + targetUrl + "?page=1" + paramString + "'>首页</a></li>");
		if (currentPage > 1) { 
			pageCode.append("<li class='paginItem'><a href='" + targetUrl + "?page=" + (currentPage - 1) + paramString
					+ "'>上一页</a></li>");
		} else {
			pageCode.append("<li class='paginItem disabled'><a href='javascript:void(0)'>上一页</a></li>");
		}
		for (int i = currentPage - 2; i <= currentPage + 2; i++) {
			if (i < 1 || i > totalPage) {
				continue;
			}
			if (i == currentPage) {
				pageCode.append("<li class='paginItem current active'><a href='" + targetUrl + "?page=" + i + paramString
						+ "'>" + i + "</a></li>");
			} else {
				pageCode.append("<li class='paginItem'><a href='" + targetUrl + "?page=" + i + paramString + "'>" + i
						+ "</a></li>");
			}
		}
		if (currentPage < totalPage) {
			pageCode.append("<li class='paginItem'><a href='" + targetUrl + "?page=" + (currentPage + 1) + paramString
					+ "'>下一页</a></li>");
		} else {
			pageCode.append("<li class='paginItem disabled'><a href='javascript:void(0)'>下一页</a></li>");
		}
		pageCode.append(
				"<li class='paginItem'><a href='" + targetUrl + "?page=" + totalPage + paramString + "'>尾页</a></li>");
		return pageCode.toString();
	}
}
