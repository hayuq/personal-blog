package com.xjc.service;

import java.util.List;
import java.util.Map;

import com.xjc.model.BlogType;

public interface BlogTypeService {

	List<BlogType> getTypeList();
	
	List<BlogType> getTypeList(Map<String,Object> map);
	
	int delete(Integer id);
	
	int update(BlogType blogType);
	
	int add(BlogType blogType);
	
	BlogType findById(Integer id);
}
