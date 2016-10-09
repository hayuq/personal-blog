package com.xjc.dao;

import java.util.List;

import com.xjc.model.Link;

public interface LinkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Link record);

    int insertSelective(Link record);

    Link selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);
    
    List<Link> getLinkList();
}