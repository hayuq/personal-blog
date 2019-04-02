package com.june.dao;

import java.util.List;

import com.june.model.Link;

public interface LinkMapper extends BaseMapper<Link> {
	
    List<Link> getLinkList();

}