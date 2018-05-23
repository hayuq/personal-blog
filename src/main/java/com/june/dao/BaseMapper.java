package com.june.dao;

import java.io.Serializable;

public interface BaseMapper<T> {
	
	int deleteByPrimaryKey(Serializable id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Serializable id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKeyWithBLOBs(T record);

    int updateByPrimaryKey(T record);
    
    int batchDelete(Integer[] ids);

}
