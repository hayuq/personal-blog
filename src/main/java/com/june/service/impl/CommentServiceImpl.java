package com.june.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.june.dao.BlogMapper;
import com.june.dao.CommentMapper;
import com.june.model.Blog;
import com.june.model.Comment;
import com.june.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;
	
	@Resource
	private BlogMapper blogMapper;
	
	@Override
	public List<Comment> getCommentList(Map<String, Object> map) {
		return commentMapper.getCommentList(map);
	}

	@Override
	public int add(Comment comment) {
		return commentMapper.insertSelective(comment);
	}

	@Transactional
	@Override
	public int audit(Comment comment) {
		int ret = commentMapper.updateByPrimaryKeySelective(comment);
		if(comment.getIsPass()) {
			// 评论审核通过，所属博客评论数加一
			blogMapper.increaseReview(comment.getBlogId());
		}
		return ret;
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Comment comment = findById(id);
		// 删除审核通过的评论时，所属博客评论数减一
		if (comment.getIsPass()) {
			blogMapper.decreaseReview(comment.getBlogId());
		}
		commentMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional
	@Override
	public void batchDelete(Integer[] ids) {
		for (int i = 0; i < ids.length; i++) {		
			Comment comment = findById(ids[i]);
			// 删除审核通过的评论时，所属博客评论数减一
			if (comment.getIsPass()) {
				blogMapper.decreaseReview(comment.getBlogId());
			}
		}
		commentMapper.batchDelete(ids);
	}

	@Override
	public Comment findById(Integer id) {
		return commentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getCount(Map<String, Object> params) {
		return commentMapper.getCount(params);
	}

}
