package com.xjc.dao;

import java.util.List;
import java.util.Map;

import com.xjc.model.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
    
    List<Comment> getCommentList(Map<String, Object> map);
    
    int deleteByBlogId(Integer blogId);
}