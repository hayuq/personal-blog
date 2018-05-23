package com.june.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.june.dao.BloggerMapper;
import com.june.model.Blogger;
import com.june.service.BloggerService;

@Service
public class BloggerServiceImpl implements BloggerService {

	@Resource
	private BloggerMapper bloggerMapper;
	
	@Override
	public Blogger find() {
		return bloggerMapper.find();
	}

	@Override
	public Blogger findByName(String name) {
		return bloggerMapper.findByName(name);
	}

	@Override
	public int update(Blogger blogger) {
		return bloggerMapper.updateByPrimaryKeySelective(blogger);
	}

}
