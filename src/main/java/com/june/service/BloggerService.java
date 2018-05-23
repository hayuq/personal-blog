package com.june.service;

import com.june.model.Blogger;

public interface BloggerService {

	Blogger find();
	
	Blogger findByName(String name);
	
	int update(Blogger blogger);
	
}
