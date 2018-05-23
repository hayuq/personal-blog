package com.june.dao;

import java.util.List;
import java.util.Map;

import com.june.model.Blog;

public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> getBlogList(Map<String, Object> map);
    
    Blog getLastBlog(Integer id);
    
    Blog getNextBlog(Integer id);
    
    int getCount(Map<String, Object> map);
    
    List<Blog> getByTypeId(Integer typeId);
    
	List<Blog> getTopReview();
	
	List<Blog> getTopReading();
	
	List<Blog> getByDate();

	/**
	 * 浏览量加一
	 * @param id
	 */
	void increaseReading(Integer id);

	/**
	 * 评论数加一
	 * @param id
	 */
	void increaseReview(Integer id);

	/**
	 * 评论数减一
	 * @param blogId
	 */
	void decreaseReview(Integer id);
}