package com.xjc.service;

import java.util.List;
import java.util.Map;

import com.xjc.model.Comment;

public interface CommentService {

	int add(Comment comment);
	
	Comment findById(Integer id);
	
	public List<Comment> getCommentList(Map<String, Object> map);
	
	int review(Comment comment);
	
	int delete(Integer id);
}
