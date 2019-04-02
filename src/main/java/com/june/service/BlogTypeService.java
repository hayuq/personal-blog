package com.june.service;

import java.util.List;
import java.util.Map;

import com.june.model.BlogType;

public interface BlogTypeService {

	List<BlogType> getTypeList();
	
	List<BlogType> getTypeList(Map<String,Object> map);
	
	/**
	 * 只删除分类
	 * @param id
	 */
	boolean delete(Integer id);
	
	/**
	 * 删除的同时将相关博客的类别置为默认分类
	 * @param id
	 */
	boolean batchDelete(Integer id);
	
	boolean update(BlogType blogType);
	
	boolean add(BlogType blogType);
	
	BlogType findById(Integer id);

	Integer getIdByName(String typeName);

	Integer getCount();
}
