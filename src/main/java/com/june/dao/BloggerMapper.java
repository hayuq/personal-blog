package com.june.dao;

import com.june.model.Blogger;

public interface BloggerMapper extends BaseMapper<Blogger> {
	
	Blogger find();

	Blogger findByName(String name);

}
