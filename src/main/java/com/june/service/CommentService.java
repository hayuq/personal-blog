package com.june.service;

import java.util.List;
import java.util.Map;

import com.june.model.Comment;

public interface CommentService {

	boolean add(Comment comment);
	
	Comment findById(Integer id);
	
	public List<Comment> getCommentList(Map<String, Object> map);
	
	/**
	 * 审核评论
	 * @param comment
	 * @return
	 */
	boolean audit(Comment comment);
	
	/**
	 * 删除评论，并将其所属博客评论数减一
	 * @param id
	 * @return
	 */
	void delete(Integer id);

	boolean batchDelete(Integer[] ids);
	
	Integer getCount(Map<String, Object> params);
}
