package com.june.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june.dao.BlogMapper;
import com.june.dao.CommentMapper;
import com.june.lucene.BlogIndex;
import com.june.model.Blog;
import com.june.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogMapper blogMapper;
	
	@Resource
	private CommentMapper commentMapper;
	
	@Resource
	private BlogIndex blogIndex;
	
	@Override
	public Blog findById(Integer id) {
		return blogMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Blog> getBlogList(Map<String, Object> map) {
		return blogMapper.getBlogList(map);
	}

	@Override
	public Blog getLastBlog(Integer id) {
		return blogMapper.getLastBlog(id);
	}

	@Override
	public Blog getNextBlog(Integer id) {
		return blogMapper.getNextBlog(id);
	}

	@Override
	public int getCount(Map<String, Object> map) {
		return blogMapper.getCount(map);
	}

	@Override
	public List<Blog> getByTypeId(Integer typeId) {
		return blogMapper.getByTypeId(typeId);
	}
	
	@Override
	public List<Blog> getTopReading() {
		return blogMapper.getTopReading();
	}
	
	@Override
	public List<Blog> getTopReview() {
		return blogMapper.getTopReview();
	}
	
	@Override
	public int update(Blog blog) throws IOException {
	 	int ret = blogMapper.updateByPrimaryKeySelective(blog);
		blogIndex.updateIndex(blog);
		return ret;
	}
	
	@Override
	public List<Blog> getByDate() {
		return blogMapper.getByDate();
	}

	@Override
	public int add(Blog blog) throws IOException {
		int ret = blogMapper.insertSelective(blog);
		blogIndex.createIndex(blog);
		return ret;
	}
	
	@Transactional
	@Override
	public Blog increaseReading(Integer id) {
		blogMapper.increaseReading(id);
		return blogMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public void increaseReview(Integer id) {
		blogMapper.increaseReview(id);
	}
	
	@Transactional
	@Override
	public void delete(Integer id) throws IOException {
		commentMapper.deleteByBlogId(id);
		blogIndex.deleteIndex(String.valueOf(id));
		blogMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void batchDelete(Integer[] ids)  throws IOException {
		blogMapper.batchDelete(ids);
		for (int i = 0; i < ids.length; i++) {
			blogIndex.deleteIndex(String.valueOf(ids[i]));
		}
	}
	
}
