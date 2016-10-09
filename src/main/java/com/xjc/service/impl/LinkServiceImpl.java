package com.xjc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xjc.dao.LinkMapper;
import com.xjc.model.Link;
import com.xjc.service.LinkService;

@Service("linkService")
public class LinkServiceImpl implements LinkService {
	
	@Resource
	LinkMapper linkMapper;

	public List<Link> getLinkList() {
		return linkMapper.getLinkList();
	}

	public int add(Link link) {
		return linkMapper.insertSelective(link);
	}

	public int delete(Integer id) {
		return linkMapper.deleteByPrimaryKey(id);
	}

}
