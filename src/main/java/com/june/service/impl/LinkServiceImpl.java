package com.june.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.june.dao.LinkMapper;
import com.june.model.Link;
import com.june.service.LinkService;

@Service
public class LinkServiceImpl implements LinkService {
	
	@Resource
	private LinkMapper linkMapper;

	@Override
	public List<Link> getLinkList() {
		List<Link> links = linkMapper.getLinkList();
		if (CollectionUtils.isEmpty(links)) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(links);
	}
	
	@Override
	public Link findById(Integer id) {
		return linkMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean add(Link link) {
		return linkMapper.insertSelective(link) > 0;
	}
	
	@Override
	public boolean update(Link link) {
		return linkMapper.updateByPrimaryKeySelective(link) > 0;
	}

	@Override
	public void delete(Integer id) {
		linkMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public boolean batchDelete(Integer[] ids) {
		return linkMapper.batchDelete(ids) > 0;
	}

}
