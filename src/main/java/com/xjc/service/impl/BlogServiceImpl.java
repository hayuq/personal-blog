package com.xjc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xjc.dao.BlogMapper;
import com.xjc.dao.CommentMapper;
import com.xjc.model.Blog;
import com.xjc.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	BlogMapper blogMapper;
	
	@Resource
	CommentMapper commentMapper;
	
	public Blog findById(Integer id) {
		return blogMapper.selectByPrimaryKey(id);
	}

	public List<Blog> getBlogList(Map<String, Object> map) {
		return blogMapper.getBlogList(map);
	}

	public Blog getLastBlog(Integer id) {
		return blogMapper.getLastBlog(id);
	}

	public Blog getNextBlog(Integer id) {
		return blogMapper.getNextBlog(id);
	}

	public int getCount(Map<String, Object> map) {
		return blogMapper.getCount(map);
	}

	public List<Blog> getByTypeId(Integer typeId) {
		return blogMapper.getByTypeId(typeId);
	}
	
	public List<Blog> getTopReading() {
		return blogMapper.getTopReading();
	}
	
	public List<Blog> getTopReview() {
		return blogMapper.getTopReview();
	}
	
	public int update(Blog blog) {
		return blogMapper.updateByPrimaryKeySelective(blog);
	}
	
	public List<Blog> getByDate() {
		return blogMapper.getByDate();
	}

	public int add(Blog blog) {
		return blogMapper.insertSelective(blog);
	}
	
	public int delete(Integer id) {
		commentMapper.deleteByBlogId(id);
		return blogMapper.deleteByPrimaryKey(id);
	}
}
