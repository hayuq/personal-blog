package com.xjc.service;

import java.util.List;
import java.util.Map;

import com.xjc.model.Blog;

public interface BlogService {

	public Blog findById(Integer id);

	public List<Blog> getBlogList(Map<String, Object> map);
	
	public Blog getLastBlog(Integer id);
	
	public Blog getNextBlog(Integer id);
	
	public int getCount(Map<String, Object> map);
	
	public List<Blog> getByTypeId(Integer typeId);
	
	public List<Blog> getTopReview();
	
	public List<Blog> getTopReading();
	
	int add(Blog blog);
	
	int update(Blog blog);
	
	List<Blog> getByDate();
	
	int delete(Integer id);
 }
