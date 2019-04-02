package com.june.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.june.dao.BlogMapper;
import com.june.dao.CommentMapper;
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
		List<Comment> comments = commentMapper.getCommentList(map);
		if (CollectionUtils.isEmpty(comments)) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(comments);
	}

	@Override
	public boolean add(Comment comment) {
		return commentMapper.insertSelective(comment) > 0;
	}

//	@Transactional
	@Override
	public boolean audit(Comment comment) {
		boolean isSuccess = commentMapper.updateByPrimaryKeySelective(comment) > 0;
//		if(isSuccess && comment.getIsPass()) {
//			// 评论审核通过，所属博客评论数加一
//			blogMapper.increaseReview(comment.getBlogId());
//		}
		return isSuccess;
	}

//	@Transactional
	@Override
	public void delete(Integer id) {
		commentMapper.deleteByPrimaryKey(id);
//		Comment comment = findById(id);
//		if (isSuccess && comment.getIsPass()) {
//			// 删除审核通过的评论时，所属博客评论数减一
//			blogMapper.decreaseReview(comment.getBlogId());
//		}
	}
	
//	@Transactional
	@Override
	public boolean batchDelete(Integer[] ids) {
		boolean isSuccess = commentMapper.batchDelete(ids) > 0;
		if (isSuccess) {
//			for (int i = 0; i < ids.length; i++) {		
//				Comment comment = findById(ids[i]);
//				// 删除审核通过的评论时，所属博客评论数减一
//				if (comment.getIsPass()) {
//					blogMapper.decreaseReview(comment.getBlogId());
//				}
//			}
		}
		return isSuccess;
	}

	@Override
	public Comment findById(Integer id) {
		return commentMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer getCount(Map<String, Object> params) {
		return commentMapper.getCount(params);
	}

}
