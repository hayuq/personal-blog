package com.xjc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xjc.dao.BlogTypeMapper;
import com.xjc.model.BlogType;
import com.xjc.service.BlogTypeService;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

	@Resource
	BlogTypeMapper blogTypeMapper;
	
	public List<BlogType> getTypeList() {
		return blogTypeMapper.getAllTypeList();
	}

	public List<BlogType> getTypeList(Map<String, Object> map) {
		return blogTypeMapper.getTypeList(map);
	}

	public int delete(Integer id) {
		return blogTypeMapper.deleteByPrimaryKey(id);
	}

	public int update(BlogType blogType) {
		return blogTypeMapper.updateByPrimaryKeySelective(blogType);
	}

	public int add(BlogType blogType) {
		return blogTypeMapper.insertSelective(blogType);
	}

	public BlogType findById(Integer id) {
		return blogTypeMapper.selectByPrimaryKey(id);
	}

}
