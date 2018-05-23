package com.june.dao;

import java.util.List;
import java.util.Map;

import com.june.model.Comment;

public interface CommentMapper extends BaseMapper<Comment> {
    
    List<Comment> getCommentList(Map<String, Object> map);
    
    int deleteByBlogId(Integer blogId);

	int getCount(Map<String, Object> params);
}