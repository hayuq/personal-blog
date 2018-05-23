package com.june.service;

import java.util.List;

import com.june.model.Link;

public interface LinkService {

	public List<Link> getLinkList();
	
	Link findById(Integer id);
	
	int add(Link link);
	
	int update(Link link);
	
	int delete(Integer id);
	
	int batchDelete(Integer[] ids);
}
