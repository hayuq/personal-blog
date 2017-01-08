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

	@Override
	public List<Link> getLinkList() {
		return linkMapper.getLinkList();
	}
	
	@Override
	public Link findById(Integer id) {
		return linkMapper.selectByPrimaryKey(id);
	}

	@Override
	public int add(Link link) {
		return linkMapper.insertSelective(link);
	}
	
	@Override
	public int update(Link link) {
		return linkMapper.updateByPrimaryKeySelective(link);
	}

	public int delete(Integer id) {
		return linkMapper.deleteByPrimaryKey(id);
	}

}
