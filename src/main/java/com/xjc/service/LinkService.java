package com.xjc.service;

import java.util.List;

import com.xjc.model.Link;

public interface LinkService {

	public List<Link> getLinkList();
	
	int add(Link link);
	
	int delete(Integer id);
}
