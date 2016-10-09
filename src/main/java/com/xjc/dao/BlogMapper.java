package com.xjc.dao;

import java.util.List;
import java.util.Map;

import com.xjc.model.Blog;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> getBlogList(Map<String, Object> map);
    
    Blog getLastBlog(Integer id);
    
    Blog getNextBlog(Integer id);
    
    int getCount(Map<String, Object> map);
    
    List<Blog> getByTypeId(Integer typeId);
    
	List<Blog> getTopReview();
	
	List<Blog> getTopReading();
	
	List<Blog> getByDate();
}