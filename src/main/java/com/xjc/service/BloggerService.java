package com.xjc.service;

import com.xjc.model.Blogger;

public interface BloggerService {

	public Blogger find();
	
	public Blogger findByName(String name);
	
	public int update(Blogger blogger);
}
