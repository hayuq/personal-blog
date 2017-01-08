package com.xjc.dao;

import java.util.List;
import java.util.Map;

import com.xjc.model.BlogType;

public interface BlogTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(BlogType record);

    int insertSelective(BlogType record);

    BlogType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(BlogType record);

    int updateByPrimaryKey(BlogType record);
    
    List<BlogType> getAllTypeList();
    
    List<BlogType> getTypeList(Map<String, Object> map);
    
    Integer getIdByName(String typeName);
}