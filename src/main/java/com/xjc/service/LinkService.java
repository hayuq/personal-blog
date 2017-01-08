package com.xjc.service;

import java.util.List;

import com.xjc.model.Link;

public interface LinkService {

	public List<Link> getLinkList();
	
	Link findById(Integer id);
	
	int add(Link link);
	
	int update(Link link);
	
	int delete(Integer id);
}
