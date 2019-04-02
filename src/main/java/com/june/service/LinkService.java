package com.june.service;

import java.util.List;

import com.june.model.Link;

public interface LinkService {

	List<Link> getLinkList();
	
	Link findById(Integer id);
	
	boolean add(Link link);
	
	boolean update(Link link);
	
	void delete(Integer id);
	
	boolean batchDelete(Integer[] ids);
}
