package com.june.dao;

import java.io.Serializable;

public interface BaseMapper<T> {
	
	T selectByPrimaryKey(Serializable id);

	int updateByPrimaryKeySelective(T record);

    int insertSelective(T record);

    int deleteByPrimaryKey(Serializable id);
    
    int batchDelete(Serializable[] ids);

}
