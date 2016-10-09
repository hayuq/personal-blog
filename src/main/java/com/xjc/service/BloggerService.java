package com.xjc.service;

import com.xjc.model.Blogger;

public interface BloggerService {

	public Blogger findById(Integer id);
	
	public Blogger findByName(String name);
}
