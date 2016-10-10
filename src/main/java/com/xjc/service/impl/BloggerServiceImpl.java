package com.xjc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xjc.dao.BloggerMapper;
import com.xjc.model.Blogger;
import com.xjc.service.BloggerService;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

	@Resource
	BloggerMapper bloggerMapper;
	
	public Blogger findById(Integer id) {
		return bloggerMapper.selectByPrimaryKey(id);
	}

	public Blogger findByName(String name) {
		return bloggerMapper.findByName(name);
	}

	public int update(Blogger blogger) {
		return bloggerMapper.updateByPrimaryKeySelective(blogger);
	}

}
