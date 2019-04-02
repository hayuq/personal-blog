package com.june.dao;

import java.util.List;
import java.util.Map;

import com.june.model.BlogType;

public interface BlogTypeMapper extends BaseMapper<BlogType> {
    
    List<BlogType> getAllTypeList();
    
    List<BlogType> getTypeList(Map<String, Object> params);
    
    Integer getIdByName(String typeName);

	int getCount();
}