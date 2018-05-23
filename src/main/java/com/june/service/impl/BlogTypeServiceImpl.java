package com.june.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june.dao.BlogMapper;
import com.june.dao.BlogTypeMapper;
import com.june.model.Blog;
import com.june.model.BlogType;
import com.june.service.BlogTypeService;

@Service
public class BlogTypeServiceImpl implements BlogTypeService {

	@Resource
	private BlogTypeMapper blogTypeMapper;
	
	@Resource
	private BlogMapper blogMapper;
	
	@Override
	public List<BlogType> getTypeList() {
		return blogTypeMapper.getTypeList(null);
	}

	@Override
	public List<BlogType> getTypeList(Map<String, Object> map) {
		return blogTypeMapper.getTypeList(map);
	}

	@Override
	public void delete(Integer id) {
		blogTypeMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional
	@Override
	public void batchDelete(Integer id) {
		delete(id);
		List<Blog> blogs = blogMapper.getByTypeId(id);
		for (Blog blog : blogs) {
			blog.setTypeId(1);
			blogMapper.updateByPrimaryKeySelective(blog);
		}
	}

	@Override
	public int update(BlogType blogType) {
		return blogTypeMapper.updateByPrimaryKeySelective(blogType);
	}

	@Override
	public int add(BlogType blogType) {
		return blogTypeMapper.insertSelective(blogType);
	}

	@Override
	public BlogType findById(Integer id) {
		return blogTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer getIdByName(String typeName) {
		return blogTypeMapper.getIdByName(typeName);
	}
	
	@Override
	public int getCount() {
		return blogTypeMapper.getCount();
	}

}
