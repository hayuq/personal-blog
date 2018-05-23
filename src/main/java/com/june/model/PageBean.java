package com.june.model;

import lombok.Data;

/****************************
 * @author june
 * @date 2016年8月28日下午9:30:15
 * @version 1.0.0
 * @desc 分页
 **************************/
public @Data class PageBean {

	private Integer currentPage = 1; //当前页
	private Integer totalPage; //总页数
	private Integer start; //起始记录数
	private Integer pageSize; //每页显示记录数
	private Integer totalCount; //总记录数
	
	public PageBean(int totalCount, int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.start = (currentPage - 1) * pageSize;
		this.totalCount = totalCount;
		//根据总记录数和每页显示记录数计算总页数
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}
	
}
