package com.xjc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xjc.dao.CommentMapper;
import com.xjc.model.Comment;
import com.xjc.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Resource
	CommentMapper commentMapper;
	
	public List<Comment> getCommentList(Map<String, Object> map) {
		return commentMapper.getCommentList(map);
	}

	public int add(Comment comment) {
		return commentMapper.insertSelective(comment);
	}

	public int review(Comment comment) {
		return commentMapper.updateByPrimaryKeySelective(comment);
	}

	public int delete(Integer id) {
		return commentMapper.deleteByPrimaryKey(id);
	}

	public Comment findById(Integer id) {
		return commentMapper.selectByPrimaryKey(id);
	}

}
