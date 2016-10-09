package com.xjc.model;

/****************************
 * @author xjc
 * @date 2016年8月28日下午9:30:15
 * @version 1.0.0
 * @desc 分页
 **************************/
public class PageBean {

	private Integer currentPage = 1; //当前页
	private Integer totalPage; //总页数
	private Integer start; //起始记录数
	private Integer pageSize; //每页显示记录数
	private Integer totalCount; //总记录数
	
	public PageBean(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public Integer getStart() {
		start = (currentPage - 1)*pageSize;
		return start;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		//根据总记录数和每页显示记录数计算总页数
		this.setTotalPage(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
