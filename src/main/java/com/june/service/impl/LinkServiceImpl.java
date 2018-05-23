package com.june.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.june.dao.LinkMapper;
import com.june.model.Link;
import com.june.service.LinkService;

@Service
public class LinkServiceImpl implements LinkService {
	
	@Resource
	private LinkMapper linkMapper;

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

	@Override
	public int delete(Integer id) {
		return linkMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int batchDelete(Integer[] ids) {
		return linkMapper.batchDelete(ids);
	}

}
