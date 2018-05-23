package com.june.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.june.model.Blog;

public interface BlogService {

	Blog findById(Integer id);

	List<Blog> getBlogList(Map<String, Object> map);
	
	Blog getLastBlog(Integer id);
	
	Blog getNextBlog(Integer id);
	
	int getCount(Map<String, Object> map);
	
	List<Blog> getByTypeId(Integer typeId);
	
	List<Blog> getTopReview();
	
	List<Blog> getTopReading();
	
	/**
	 * 添加博客及索引
	 * @param blog
	 * @return
	 * @throws IOException
	 */
	int add(Blog blog) throws IOException;
	
	/**
	 * 更新博客及索引
	 * @param blog
	 * @return
	 * @throws IOException
	 */
	int update(Blog blog) throws IOException;
	
	List<Blog> getByDate();
	
	/**
	 * 删除博客、索引及该篇博客的评论
	 * @param id
	 * @return
	 * @throws IOException
	 */
	void delete(Integer id) throws IOException;

	/**
	 * 浏览量加一
	 * @param id 博客id
	 * @return 
	 */
	Blog increaseReading(Integer id);
	
	/**
	 * 评论数加一
	 * @param id 博客id
	 * @return 
	 */
	void increaseReview(Integer id);
	
	void batchDelete(Integer[] ids) throws IOException;
 }
